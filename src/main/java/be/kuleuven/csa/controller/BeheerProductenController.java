package be.kuleuven.csa.controller;

import be.kuleuven.csa.ProjectMain;
import be.kuleuven.csa.domain.Product;
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

public class BeheerProductenController {

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<Product> tblProducten;
    @FXML
    private TableColumn<Product, Integer> ProductId;
    @FXML
    private TableColumn<Product,String>Naam;
    @FXML
    private TableColumn<Product,String>Soort;


    public ObservableList<Product> data;
    private csaRepositoryJpaImpl repo;

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);
        ProductId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productId"));
        Naam.setCellValueFactory(new PropertyValueFactory<Product, String>("Naam"));
        Soort.setCellValueFactory(new PropertyValueFactory<Product, String>("Soort"));
        tblProducten.getItems().setAll(initTable());

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

    private List<Product> initTable() {
        data = FXCollections.observableArrayList();
        Iterator ite = repo.getProduct().listIterator();
        while (ite.hasNext()){
            Product product = (Product) ite.next();
            data.add(product);
        }
        return data;
    }

    private void addNewRow() {
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("BeheerProductenVoegToe.fxml"));
            var scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Beheer van BoerderijVoegToe");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblProducten.getItems().setAll(initTable());
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm niet vinden", e);
        }
    }

    private void deleteCurrentRow() {
        Product product = tblProducten.getSelectionModel().getSelectedItem();
        repo.deleteProduct(product);
        tblProducten.getItems().setAll(initTable());
    }

    private void modifyCurrentRow() {
        try {
           Product product = tblProducten.getSelectionModel().getSelectedItem();

            var root = new FXMLLoader(getClass().getClassLoader().getResource("beheerProductenModify.fxml"));
            var stage = new Stage();
            var scene = new Scene(root.load());
            stage.setScene(scene);
            stage.setTitle("Beheer van ProductModify");
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            BeheerProductenModifyController bm = root.getController();
            bm.initData(product);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    tblProducten.getItems().setAll(initTable());
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
        if(tblProducten.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Selecteer!", "Eerst een product selecteren.");
        }
    }
}

