package be.kuleuven.csa.controller;

import be.kuleuven.csa.ProjectMain;
import be.kuleuven.csa.domain.Boerderij;
import be.kuleuven.csa.domain.PakketInhoud;
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

public class BeheerPakketinhoudController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnBeheerProducten;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<PakketInhoud> tblPakketinhouden;
    @FXML
    private TableColumn<PakketInhoud, Integer> pakketinhoudId;
    @FXML
    private TableColumn<PakketInhoud,String>Naam;

    public ObservableList<PakketInhoud> data;
    private csaRepositoryJpaImpl repo;

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);
        pakketinhoudId.setCellValueFactory(new PropertyValueFactory<PakketInhoud, Integer>("pakketInhoudId"));
        Naam.setCellValueFactory(new PropertyValueFactory<PakketInhoud, String>("Naam"));
        tblPakketinhouden.getItems().setAll(initTable());

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
        btnBeheerProducten.setOnAction(event -> {
            verifyOneRowSelected();
            beheerProductenCurrentRow();
        });
    }

    private List<PakketInhoud> initTable() {
        data = FXCollections.observableArrayList();
        Iterator ite = repo.getPakketinhouden().listIterator();
        while (ite.hasNext()){
            PakketInhoud pakketInhoud = (PakketInhoud) ite.next();
            data.add(pakketInhoud);
        }
        return data;
    }

    private void addNewRow() {
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("BeheerPakketinhoudenVoegToe.fxml"));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Beheer van PakketinhoudenVoegToe");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblPakketinhouden.getItems().setAll(initTable());
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm niet vinden", e);
        }
    }

    private void deleteCurrentRow() {
        PakketInhoud pakketInhoud = tblPakketinhouden.getSelectionModel().getSelectedItem();
        repo.deletePakketinhoud(pakketInhoud);
        tblPakketinhouden.getItems().setAll(initTable());
    }

    private void modifyCurrentRow() {
        try {
            PakketInhoud pakketinhoud = tblPakketinhouden.getSelectionModel().getSelectedItem();

            var root = new FXMLLoader(getClass().getClassLoader().getResource("beheerPakketinhoudenModify.fxml"));
            var stage = new Stage();
            var scene = new Scene(root.load());
            stage.setScene(scene);
            stage.setTitle("Beheer van PakketinhoudenModify");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            BeheerPakketinhoudModifyController bm = root.getController();
            bm.initData(pakketinhoud);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblPakketinhouden.getItems().setAll(initTable());
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm niet vinden", e);
        }
    }
    private void beheerProductenCurrentRow() {
        try {
            PakketInhoud pakketinhoud = tblPakketinhouden.getSelectionModel().getSelectedItem();

            var root = new FXMLLoader(getClass().getClassLoader().getResource("beheerBevat.fxml"));
            var stage = new Stage();
            var scene = new Scene(root.load());
            stage.setScene(scene);
            stage.setTitle("Beheer van Bevat");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            BeheerBevatController bm = root.getController();
            bm.initData(pakketinhoud);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblPakketinhouden.getItems().setAll(initTable());
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
        if(tblPakketinhouden.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Selecteer!", "Eerst een boer selecteren.");
        }
    }
}

