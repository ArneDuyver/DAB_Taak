package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerBevatVoegToeController {
    @FXML
    private TextField tfEenheid;
    @FXML
    private TextField tfHoeveelheid;
    @FXML
    private ComboBox<Product> cbProducten;
    @FXML
    private Button btnAdd;
    private csaRepositoryJpaImpl repo;
    private PakketInhoud pakketInhoud;


    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);
        btnAdd.setOnAction(e -> addNewRow());
        cbProducten.getItems().setAll(repo.getProduct());
    }
    public void addNewRow(){
        Bevat bevat = new Bevat(tfEenheid.getText(),Integer.parseInt(tfHoeveelheid.getText()),pakketInhoud,cbProducten.getSelectionModel().getSelectedItem());
        repo.saveNewBevat(bevat);
    }

    public void initData(PakketInhoud pakketinhoud) {
        this.pakketInhoud = pakketinhoud;
    }
}