package be.kuleuven.csa.controller;

import be.kuleuven.csa.ProjectMain;
import be.kuleuven.csa.domain.Bevat;
import be.kuleuven.csa.domain.PakketInhoud;
import be.kuleuven.csa.domain.Verkoopt;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.persistence.Persistence;
import java.util.Iterator;
import java.util.List;

public class BeheerVerkooptController {
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<Verkoopt> tblVerkoopt;
    @FXML
    private TableColumn<Verkoopt,Integer> VerkooptId;
    @FXML
    private TableColumn<Verkoopt,String>Startdatum;
    @FXML
    private TableColumn<Verkoopt,Integer>Prijs;
    @FXML
    private TableColumn<Verkoopt,String>BoerderijString;
    @FXML
    private TableColumn<Verkoopt,String>PakketbeschrijvingString;

    public ObservableList<Verkoopt> data;
    private csaRepositoryJpaImpl repo;

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);

        VerkooptId.setCellValueFactory(new PropertyValueFactory<Verkoopt, Integer>("verkooptId"));
        Startdatum.setCellValueFactory(new PropertyValueFactory<Verkoopt, String>("Startdatum"));
        Prijs.setCellValueFactory(new PropertyValueFactory<Verkoopt, Integer>("Prijs"));
        BoerderijString.setCellValueFactory(new PropertyValueFactory<Verkoopt, String>("boerderij"));
        PakketbeschrijvingString.setCellValueFactory(new PropertyValueFactory<Verkoopt, String>("pakketbeschrijving"));

        tblVerkoopt.getItems().setAll(initTable());

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

    private List<Verkoopt> initTable() {
        data = FXCollections.observableArrayList();
        Iterator ite = repo.getVerkopen().listIterator();
        while (ite.hasNext()){
            Verkoopt verkoopt = (Verkoopt) ite.next();
            data.add(verkoopt);
        }
        return data;
    }

    private void addNewRow() {
        try {
            var root = new FXMLLoader(getClass().getClassLoader().getResource("beheerVerkopenVoegToe.fxml"));
            var stage = new Stage();
            var scene = new Scene(root.load());
            stage.setScene(scene);
            stage.setTitle("Beheer van VerkopenVoegToe");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblVerkoopt.getItems().setAll(initTable());
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm niet vinden", e);
        }
    }

    private void deleteCurrentRow() {
        Verkoopt verkoopt = tblVerkoopt.getSelectionModel().getSelectedItem();
        repo.deleteVerkoopt(verkoopt);
        tblVerkoopt.getItems().setAll(initTable());
    }

    private void modifyCurrentRow() {
        try {
            Verkoopt verkoopt = tblVerkoopt.getSelectionModel().getSelectedItem();

            var root = new FXMLLoader(getClass().getClassLoader().getResource("beheerVerkopenModify.fxml"));
            var stage = new Stage();
            var scene = new Scene(root.load());
            stage.setScene(scene);
            stage.setTitle("Beheer van VerkooptModify");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            BeheerVerkooptModifyController bm = root.getController();
            bm.initData(verkoopt);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblVerkoopt.getItems().setAll(initTable());
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
        if(tblVerkoopt.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Selecteer!", "Eerst een verkoopt selecteren.");
        }
    }
}

