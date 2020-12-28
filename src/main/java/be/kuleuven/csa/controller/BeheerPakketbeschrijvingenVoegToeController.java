package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.Boerderij;
import be.kuleuven.csa.domain.Pakketbeschrijving;
import be.kuleuven.csa.domain.csaRepositoryJpaImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerPakketbeschrijvingenVoegToeController {
    @FXML
    private Button btnAdd;
    @FXML
    private TextField tfNaam;
    @FXML
    private TextField tfKinderen;
    @FXML
    private TextField tfVolwassenen;

    private csaRepositoryJpaImpl repo;

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);
        btnAdd.setOnAction(e -> addNewRow());
    }
    public void addNewRow(){
        Pakketbeschrijving pakketbeschrijving= new Pakketbeschrijving(tfNaam.getText(),Integer.valueOf(tfVolwassenen.getText()),Integer.valueOf(tfKinderen.getText()));
        repo.saveNewPakketbeschrijving(pakketbeschrijving);
    }
}

