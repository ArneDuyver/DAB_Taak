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

public class BeheerPakketbeschrijvingenController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<Pakketbeschrijving> tblPakketbeschijvingen;
    @FXML
    private TableColumn<Pakketbeschrijving, Integer> PakketbeschrijvingId;
    @FXML
    private TableColumn<Pakketbeschrijving,String>Naam;
    @FXML
    private TableColumn<Pakketbeschrijving,Integer>Kinderen;
    @FXML
    private TableColumn<Pakketbeschrijving,Integer>Volwassenen;


    public ObservableList<Pakketbeschrijving> data;

    private csaRepositoryJpaImpl repo;

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);
        PakketbeschrijvingId.setCellValueFactory(new PropertyValueFactory<Pakketbeschrijving, Integer>("pakketbeschrijvingId"));
        Naam.setCellValueFactory(new PropertyValueFactory<Pakketbeschrijving, String>("Naam"));
        Kinderen.setCellValueFactory(new PropertyValueFactory<Pakketbeschrijving, Integer>("Kinderen"));
        Volwassenen.setCellValueFactory(new PropertyValueFactory<Pakketbeschrijving, Integer>("Volwassenen"));
        tblPakketbeschijvingen.getItems().setAll(initTable());

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

    private ObservableList<Pakketbeschrijving> initTable() {
        data = FXCollections.observableArrayList();
        Iterator ite = repo.getPakketbeschijving().listIterator();
        while (ite.hasNext()){
            Pakketbeschrijving pakketbeschrijving = (Pakketbeschrijving) ite.next();
            data.add(pakketbeschrijving);
        }
        return data;
    }

    private void addNewRow() {
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("BeheerPakketbeschrijvingenVoegToe.fxml"));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Beheer van PakketbeschrijvingVoegToe");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblPakketbeschijvingen.getItems().setAll(initTable());
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm niet vinden", e);
        }
    }

    private void deleteCurrentRow() {
        Pakketbeschrijving pakketbeschrijving = tblPakketbeschijvingen.getSelectionModel().getSelectedItem();
        List<Verkoopt>verkooptList = repo.getVerkopenPakketbeschrijving(pakketbeschrijving);
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
        System.out.println(haaltAfList);
        for(var eenKoop : kooptList) {
            repo.deleteKoopt(eenKoop);
        }
        for(var eenHaaltAf : haaltAfList) {
            repo.deleteHaaltAf(eenHaaltAf);
        }

        for(var eenBehoortTot : behoortTotList) {
            repo.deleteBehoortTot(eenBehoortTot);
        }
        for(var eenVerkoop : verkooptList) {
            repo.deleteVerkoopt(eenVerkoop);
        }

        repo.deletePakketbeschrijving(pakketbeschrijving);

        tblPakketbeschijvingen.getItems().setAll(initTable());
    }

    private void modifyCurrentRow() {
        try {
            Pakketbeschrijving pakketbeschrijving = tblPakketbeschijvingen.getSelectionModel().getSelectedItem();

            var root = new FXMLLoader(getClass().getClassLoader().getResource("beheerPakketbeschrijvingenModify.fxml"));
            var stage = new Stage();
            var scene = new Scene(root.load());
            stage.setScene(scene);
            stage.setTitle("Beheer van PakketbeschrijvinegnModify");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            BeheerPakketbeschrijvingenModifyController bm = root.getController();
            bm.initData(pakketbeschrijving);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblPakketbeschijvingen.getItems().setAll(initTable());
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
        if(tblPakketbeschijvingen.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Selecteer!", "Eerst een pakketbeschrijving selecteren.");
        }
    }
}

