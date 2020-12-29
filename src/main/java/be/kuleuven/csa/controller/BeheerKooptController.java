package be.kuleuven.csa.controller;

import be.kuleuven.csa.ProjectMain;
import be.kuleuven.csa.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.persistence.Persistence;
import java.util.Iterator;
import java.util.List;

public class BeheerKooptController {
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBetaald;
    @FXML
    private Button btnNietBetaald;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<Koopt> tblKoopt;
    @FXML
    private TableColumn<Koopt,Integer> kooptId;
    @FXML
    private TableColumn<Koopt,Boolean>Betaald;
    @FXML
    private TableColumn<Koopt, Verkoopt>VerkooptString;
    @FXML
    private TableColumn<Koopt,Klant>KlantString;

    public ObservableList<Koopt> data;
    private csaRepositoryJpaImpl repo;

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);

        kooptId.setCellValueFactory(new PropertyValueFactory<Koopt, Integer>("kooptId"));
        Betaald.setCellValueFactory(new PropertyValueFactory<Koopt, Boolean>("Betaald"));
        VerkooptString.setCellValueFactory(new PropertyValueFactory<Koopt, Verkoopt>("verkoopt"));
        KlantString.setCellValueFactory(new PropertyValueFactory<Koopt, Klant>("klant"));

        tblKoopt.getItems().setAll(initTable());

        btnAdd.setOnAction(e -> addNewRow());
        btnBetaald.setOnAction(e -> {
            verifyOneRowSelected();
            betaaldCurrentRow();
        });
        btnNietBetaald.setOnAction(e -> {
            verifyOneRowSelected();
            NietBetaaldCurrentRow();
        });
        btnDelete.setOnAction(e -> {
            verifyOneRowSelected();
            deleteCurrentRow();
        });

        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    private List<Koopt> initTable() {
        data = FXCollections.observableArrayList();
        Iterator ite = repo.getKopen().listIterator();
        while (ite.hasNext()){
            Koopt koopt = (Koopt) ite.next();
            data.add(koopt);
        }
        return data;
    }

    private void addNewRow() {
        try {
            var root = new FXMLLoader(getClass().getClassLoader().getResource("beheerKopenVoegToe.fxml"));
            var stage = new Stage();
            var scene = new Scene(root.load());
            stage.setScene(scene);
            stage.setTitle("Beheer van bevatVoegToe");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblKoopt.getItems().setAll(initTable());
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm niet vinden", e);
        }
    }

    private void deleteCurrentRow() {
        Koopt koopt = tblKoopt.getSelectionModel().getSelectedItem();
        repo.deleteKoopt(koopt);
        tblKoopt.getItems().setAll(initTable());
        List<BehoortTot> behoortTotList = koopt.getVerkoopt().getBehoortTotList();
        for(var eenBehoortTot : behoortTotList) {

        }
    }

    private void betaaldCurrentRow() {
        Koopt koopt = tblKoopt.getSelectionModel().getSelectedItem();
        koopt.setBetaald(true);
        repo.updateKoopt(koopt);
        tblKoopt.getItems().setAll(initTable());

    }
    private void NietBetaaldCurrentRow() {
        Koopt koopt = tblKoopt.getSelectionModel().getSelectedItem();
        koopt.setBetaald(false);
        repo.updateKoopt(koopt);
        tblKoopt.getItems().setAll(initTable());

    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void verifyOneRowSelected() {
        if(tblKoopt.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Selecteer!", "Eerst een boer selecteren.");
        }
    }
}

