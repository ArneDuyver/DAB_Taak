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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeheerBoerderijenController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<Boerderij> tblBoerderijen;
    @FXML
    private TableColumn<Boerderij, Integer>boerderijId;
    @FXML
    private TableColumn<Boerderij,String>Naam;
    @FXML
    private TableColumn<Boerderij,String>Adres;
    @FXML
    private TableColumn<Boerderij,String>Email;
    @FXML
    private TableColumn<Boerderij,String>Rekeningnummer;
    @FXML
    private TableColumn<Boerderij,Integer>Opbrengst;

    public ObservableList<Boerderij> data;
    private csaRepositoryJpaImpl repo;

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);
        boerderijId.setCellValueFactory(new PropertyValueFactory<Boerderij, Integer>("boerderijId"));
        Naam.setCellValueFactory(new PropertyValueFactory<Boerderij, String>("Naam"));
        Adres.setCellValueFactory(new PropertyValueFactory<Boerderij, String>("Adres"));
        Email.setCellValueFactory(new PropertyValueFactory<Boerderij, String>("Email"));
        Rekeningnummer.setCellValueFactory(new PropertyValueFactory<Boerderij, String>("Rekeningnummer"));
        Opbrengst.setCellValueFactory(new PropertyValueFactory<Boerderij, Integer>("opbrengst"));
        tblBoerderijen.getItems().setAll(initTable());

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

    private List<Boerderij> initTable() {
        data = FXCollections.observableArrayList();
        Iterator ite = repo.getBoerderij().listIterator();
        while (ite.hasNext()){
            Boerderij boerderij = (Boerderij) ite.next();
            data.add(boerderij);
        }
        return data;
    }

    private void addNewRow() {
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("BeheerBoerderijenVoegToe.fxml"));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Beheer van BoerderijVoegToe");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblBoerderijen.getItems().setAll(initTable());
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm niet vinden", e);
        }
    }

    private void deleteCurrentRow() {
        Boerderij boerderij = tblBoerderijen.getSelectionModel().getSelectedItem();
        List<Verkoopt>verkooptList = boerderij.getVerkooptList();
        List<Koopt>kooptList = new ArrayList<Koopt>();
        for(var eenVerkoop : verkooptList) {
            kooptList.addAll(eenVerkoop.getKooptList());
        }
        List<BehoortTot>behoortTotList = new ArrayList<BehoortTot>();
        for(var eenVerkoop : verkooptList) {
            behoortTotList.addAll(eenVerkoop.getBehoortTotList());
        }
        List<HaaltAf> haaltAfList = new ArrayList<HaaltAf>();
        for(var eenBehoortTot : behoortTotList) {
            haaltAfList.addAll(eenBehoortTot.getHaaltAfList());
        }
        for(var eenHaaltAf : haaltAfList) {
            repo.deleteHaaltAf(eenHaaltAf);
        }
        for(var eenBehoortTot : behoortTotList) {
            repo.deleteBehoortTot(eenBehoortTot);
        }
        for(var eenKoop : kooptList) {
            repo.deleteKoopt(eenKoop);
        }

        for(var eenVerkoop : verkooptList) {
            repo.deleteVerkoopt(eenVerkoop);
        }
        repo.deleteBoerderij(boerderij);
        tblBoerderijen.getItems().setAll(initTable());
    }

    private void modifyCurrentRow() {
        try {
            Boerderij boerderij = tblBoerderijen.getSelectionModel().getSelectedItem();

            var root = new FXMLLoader(getClass().getClassLoader().getResource("beheerBoerderijenModify.fxml"));
            var stage = new Stage();
            var scene = new Scene(root.load());
            stage.setScene(scene);
            stage.setTitle("Beheer van BoerderijModify");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            BeheerBoerderijenModifyController bm = root.getController();
            bm.initData(boerderij);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblBoerderijen.getItems().setAll(initTable());
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
        if(tblBoerderijen.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Selecteer!", "Eerst een boer selecteren.");
        }
    }
}
