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

public class BeheerHaaltAfController {
    @FXML
    private Button btnAfgehaald;
    @FXML
    private Button btnNietAfgehaald;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<HaaltAf> tblAfgehaald;
    @FXML
    private TableColumn<HaaltAf,Integer> haaltAfId;
    @FXML
    private TableColumn<HaaltAf,Boolean>Afgehaald;
    @FXML
    private TableColumn<HaaltAf,BehoortTot>BehoortTotString;
    @FXML
    private TableColumn<HaaltAf, Klant>KlantString;


    public ObservableList<HaaltAf> data;
    private csaRepositoryJpaImpl repo;
    private Klant klant;

    public void initData(Klant klant) {
        this.klant = klant;
        tblAfgehaald.getItems().setAll(initTable());
    }

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);

        haaltAfId.setCellValueFactory(new PropertyValueFactory<HaaltAf, Integer>("haaltAfId"));
        Afgehaald.setCellValueFactory(new PropertyValueFactory<HaaltAf,Boolean>("afgehaald"));
        BehoortTotString.setCellValueFactory(new PropertyValueFactory<HaaltAf, BehoortTot>("behoortTot"));
        KlantString.setCellValueFactory(new PropertyValueFactory<HaaltAf, Klant>("klant"));

        tblAfgehaald.getItems().setAll(initTable());

        btnAfgehaald.setOnAction(e -> {
            verifyOneRowSelected();
            afgehaaldCurrentRow();
        });
        btnNietAfgehaald.setOnAction(e -> {
            verifyOneRowSelected();
            NietAfgehaaldCurrentRow();
        });


        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }

    private List<HaaltAf> initTable() {
        data = FXCollections.observableArrayList();
        Iterator ite = repo.getHaaltAf(klant).listIterator();
        while (ite.hasNext()){
            HaaltAf haaltAf = (HaaltAf) ite.next();
            data.add(haaltAf);
        }
        return data;
    }

    private void afgehaaldCurrentRow() {
        HaaltAf haaltAf = tblAfgehaald.getSelectionModel().getSelectedItem();
        haaltAf.setAfgehaald(true);
        repo.updateHaaltAf(haaltAf);
        tblAfgehaald.getItems().setAll(initTable());
    }
    private void NietAfgehaaldCurrentRow() {
        HaaltAf haaltAf = tblAfgehaald.getSelectionModel().getSelectedItem();
        haaltAf.setAfgehaald(false);
        repo.updateHaaltAf(haaltAf);
        tblAfgehaald.getItems().setAll(initTable());
    }

    public void showAlert(String title, String content) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void verifyOneRowSelected() {
        if(tblAfgehaald.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Selecteer!", "Eerst een Klant selecteren.");
        }
    }
}

