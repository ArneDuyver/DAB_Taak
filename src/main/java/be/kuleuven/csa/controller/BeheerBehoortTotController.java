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

public class BeheerBehoortTotController {
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<BehoortTot> tblBehoortTot;
    @FXML
    private TableColumn<BehoortTot,Integer> behoortTotId;
    @FXML
    private TableColumn<BehoortTot,Integer>Weeknr;
    @FXML
    private TableColumn<BehoortTot, Verkoopt>VerkooptString;
    @FXML
    private TableColumn<BehoortTot,PakketInhoud>PakketInhoudString;

    public ObservableList<BehoortTot> data;
    private csaRepositoryJpaImpl repo;


    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);

        behoortTotId.setCellValueFactory(new PropertyValueFactory<BehoortTot, Integer>("behoortTotId"));
        Weeknr.setCellValueFactory(new PropertyValueFactory<BehoortTot, Integer>("weekNummer"));
        VerkooptString.setCellValueFactory(new PropertyValueFactory<BehoortTot, Verkoopt>("verkoopt"));
        PakketInhoudString.setCellValueFactory(new PropertyValueFactory<BehoortTot, PakketInhoud>("pakketInhoud"));

        tblBehoortTot.getItems().setAll(initTable());

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

    private List<BehoortTot> initTable() {
        data = FXCollections.observableArrayList();
        Iterator ite = repo.getBehoortTot().listIterator();
        while (ite.hasNext()){
            BehoortTot behoortTot = (BehoortTot) ite.next();
            data.add(behoortTot);
        }
        return data;
    }

    private void addNewRow() {
        try {
            var root = new FXMLLoader(getClass().getClassLoader().getResource("beheerBehoortTotVoegToe.fxml"));
            var stage = new Stage();
            var scene = new Scene(root.load());
            stage.setScene(scene);
            stage.setTitle("Beheer van bevatVoegToe");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblBehoortTot.getItems().setAll(initTable());
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm niet vinden", e);
        }
    }

    private void deleteCurrentRow() {
        BehoortTot behoortTot = tblBehoortTot.getSelectionModel().getSelectedItem();
        List<HaaltAf>haaltAfList = behoortTot.getHaaltAfList();
        for(var eenHaaltAf : haaltAfList) {
            repo.deleteHaaltAf(eenHaaltAf);
        }
        repo.deleteBehoortTot(behoortTot);
        tblBehoortTot.getItems().setAll(initTable());
    }

    private void modifyCurrentRow() {
        try {
            BehoortTot behoortTot = tblBehoortTot.getSelectionModel().getSelectedItem();

            var root = new FXMLLoader(getClass().getClassLoader().getResource("beheerBehoortTotModify.fxml"));
            var stage = new Stage();
            var scene = new Scene(root.load());
            stage.setScene(scene);
            stage.setTitle("Beheer van bevat");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            BeheerBehoortTotModifyController bm = root.getController();
            bm.initData(behoortTot);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblBehoortTot.getItems().setAll(initTable());
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
        if(tblBehoortTot.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Selecteer!", "Eerst een boer selecteren.");
        }
    }
}
