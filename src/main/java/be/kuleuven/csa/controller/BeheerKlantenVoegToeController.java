package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.Klant;
import be.kuleuven.csa.domain.csaRepositoryJpaImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerKlantenVoegToeController {
    @FXML
    private Button btnAdd;
    @FXML
    private TextField tfNaam;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfAdres;
    @FXML
    private TextField tfTelefoonnummer;
    private csaRepositoryJpaImpl repo;

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);
        btnAdd.setOnAction(e -> addNewRow());
    }
    public void addNewRow(){
        Klant klant = new Klant(tfNaam.getText(),tfAdres.getText(),tfEmail.getText(),tfTelefoonnummer.getText());
        repo.saveNewKlant(klant);
    }
}