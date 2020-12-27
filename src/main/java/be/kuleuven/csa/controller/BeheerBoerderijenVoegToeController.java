package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.Boerderij;
import be.kuleuven.csa.domain.csaRepositoryJpaImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerBoerderijenVoegToeController {
    @FXML
    private Button btnAdd;
    @FXML
    private TextField tfNaam;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfAdres;
    @FXML
    private TextField tfRekeningnummer;
    private csaRepositoryJpaImpl repo;

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);
        btnAdd.setOnAction(e -> addNewRow());
    }
    public void addNewRow(){
        Boerderij boerderij = new Boerderij(tfNaam.getText(),tfAdres.getText(),tfEmail.getText(),tfRekeningnummer.getText());
        repo.saveNewBoerderij(boerderij);
    }
}
