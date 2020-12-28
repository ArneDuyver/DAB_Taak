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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.persistence.Persistence;
import java.util.Iterator;
import java.util.List;

public class BeheerBevatController {
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<Bevat> tblBevat;
    @FXML
    private TableColumn<Bevat,Integer> bevatId;
    @FXML
    private TableColumn<Bevat,String>Eenheid;
    @FXML
    private TableColumn<Bevat,Integer>Hoeveelheid;
    @FXML
    private TableColumn<Bevat,String>ProductNaam;
    @FXML
    private TableColumn<Bevat,String>PakketinhoudNaam;

    public ObservableList<Bevat> data;
    private csaRepositoryJpaImpl repo;
    private PakketInhoud pakketinhoud;

    public void initData(PakketInhoud pakketInhoud) {
        this.pakketinhoud = pakketInhoud;
        tblBevat.getItems().setAll(initTable());
    }

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);

        bevatId.setCellValueFactory(new PropertyValueFactory<Bevat, Integer>("bevatId"));
        Eenheid.setCellValueFactory(new PropertyValueFactory<Bevat, String>("Eenheid"));
        Hoeveelheid.setCellValueFactory(new PropertyValueFactory<Bevat, Integer>("Hoeveelheid"));
        ProductNaam.setCellValueFactory(new PropertyValueFactory<Bevat, String>("product"));
        PakketinhoudNaam.setCellValueFactory(new PropertyValueFactory<Bevat, String>("pakketInhoud"));

        tblBevat.getItems().setAll(initTable());

        btnAdd.setOnAction(e -> addNewRow());
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

    private List<Bevat> initTable() {
        data = FXCollections.observableArrayList();
        Iterator ite = repo.getBevat(pakketinhoud).listIterator();
        while (ite.hasNext()){
            Bevat bevat = (Bevat) ite.next();
            data.add(bevat);
        }
        return data;
    }

    private void addNewRow() {
        try {
            var root = new FXMLLoader(getClass().getClassLoader().getResource("beheerBevatVoegToe.fxml"));
            var stage = new Stage();
            var scene = new Scene(root.load());
            stage.setScene(scene);
            stage.setTitle("Beheer van bevatVoegToe");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            BeheerBevatVoegToeController bm = root.getController();
            bm.initData(pakketinhoud);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblBevat.getItems().setAll(initTable());
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm niet vinden", e);
        }
    }

    private void deleteCurrentRow() {
        Bevat bevat = tblBevat.getSelectionModel().getSelectedItem();
        repo.deleteBevat(bevat);
        tblBevat.getItems().setAll(initTable());
    }

    private void modifyCurrentRow() {
        try {
            Bevat bevat = tblBevat.getSelectionModel().getSelectedItem();

            var root = new FXMLLoader(getClass().getClassLoader().getResource("beheerBevatModify.fxml"));
            var stage = new Stage();
            var scene = new Scene(root.load());
            stage.setScene(scene);
            stage.setTitle("Beheer van bevat");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            BeheerBevatModifyController bm = root.getController();
            bm.initData(bevat);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblBevat.getItems().setAll(initTable());
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
        if(tblBevat.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Selecteer!", "Eerst een boer selecteren.");
        }
    }
}
