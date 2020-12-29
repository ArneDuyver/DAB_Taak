package be.kuleuven.csa.controller;

import be.kuleuven.csa.ProjectMain;
import be.kuleuven.csa.domain.HaaltAf;
import be.kuleuven.csa.domain.Klant;
import be.kuleuven.csa.domain.Koopt;
import be.kuleuven.csa.domain.csaRepositoryJpaImpl;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.persistence.Persistence;
import java.util.Iterator;
import java.util.List;

public class BeheerKlantenController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnAfhalingen;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<Klant> tblKlanten;
    @FXML
    private TableColumn<Klant, Integer> klantId;
    @FXML
    private TableColumn<Klant,String>Naam;
    @FXML
    private TableColumn<Klant,String>Adres;
    @FXML
    private TableColumn<Klant,String>Email;
    @FXML
    private TableColumn<Klant,String>TelefoonNummer;

    public ObservableList<Klant> data;
    private csaRepositoryJpaImpl repo;

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);
        klantId.setCellValueFactory(new PropertyValueFactory<Klant, Integer>("KlantId"));
        Naam.setCellValueFactory(new PropertyValueFactory<Klant, String>("Naam"));
        Adres.setCellValueFactory(new PropertyValueFactory<Klant, String>("Adres"));
        Email.setCellValueFactory(new PropertyValueFactory<Klant, String>("Email"));
        TelefoonNummer.setCellValueFactory(new PropertyValueFactory<Klant, String>("Telefoonnummer"));
        tblKlanten.getItems().setAll(initTable());

        btnAdd.setOnAction(e -> addNewRow());
        btnAfhalingen.setOnAction(e -> afhalingen());
        btnModify.setOnAction(e -> {
            verifyOneRowSelected();
            modifyCurrentRow();
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

    private List<Klant> initTable() {
        data = FXCollections.observableArrayList();
        Iterator ite = repo.getKlant().listIterator();
        while (ite.hasNext()){
            Klant klant = (Klant) ite.next();
            data.add(klant);
        }
        return data;
    }

    private void addNewRow() {
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("BeheerKlantenVoegToe.fxml"));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Beheer van BoerderijVoegToe");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblKlanten.getItems().setAll(initTable());
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm niet vinden", e);
        }
    }

    private void deleteCurrentRow() {
        Klant klant = tblKlanten.getSelectionModel().getSelectedItem();
        List<Koopt>kooptList = klant.getKooptList();
        List<HaaltAf>haaltAfList = klant.getHaaltAfList();
        for(var eenHaaltAf : haaltAfList) {
            repo.deleteHaaltAf(eenHaaltAf);
        }
        for(var eenKoopt : kooptList) {
            repo.deleteKoopt(eenKoopt);
        }
        repo.deleteKlant(klant);
        tblKlanten.getItems().setAll(initTable());
    }

    private void modifyCurrentRow() {
        try {
             Klant klant = tblKlanten.getSelectionModel().getSelectedItem();

            var root = new FXMLLoader(getClass().getClassLoader().getResource("beheerKlantenModify.fxml"));
            var stage = new Stage();
            var scene = new Scene(root.load());
            stage.setScene(scene);
            stage.setTitle("Beheer van BoerderijModify");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            BeheerKlantenModifyController bm = root.getController();
            bm.initData(klant);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblKlanten.getItems().setAll(initTable());
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm niet vinden", e);
        }
    }
    private void afhalingen() {
        try {
            Klant klant = tblKlanten.getSelectionModel().getSelectedItem();

            var root = new FXMLLoader(getClass().getClassLoader().getResource("beheerHaaltAf.fxml"));
            var stage = new Stage();
            var scene = new Scene(root.load());
            stage.setScene(scene);
            stage.setTitle("Beheer van Afhalingen");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            BeheerHaaltAfController bm = root.getController();
            bm.initData(klant);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblKlanten.getItems().setAll(initTable());
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm niet vinden", e);
        }
    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void verifyOneRowSelected() {
        if(tblKlanten.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Selecteer!", "Eerst een klant selecteren.");
        }
    }
}

