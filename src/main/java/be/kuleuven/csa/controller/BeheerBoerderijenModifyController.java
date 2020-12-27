package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.Boerderij;
import be.kuleuven.csa.domain.csaRepositoryJpaImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerBoerderijenModifyController {
    @FXML
    private Button btnModify;
    @FXML
    private TextField tfNaam;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfAdres;
    @FXML
    private TextField tfRekeningnummer;

    private csaRepositoryJpaImpl repo;

    private Boerderij boerderij;

    public void initData(Boerderij boerderij) {
        this.boerderij = boerderij;
        tfNaam.setText(boerderij.getNaam());
        tfEmail.setText(boerderij.getEmail());
        tfAdres.setText(boerderij.getAdres());
        tfRekeningnummer.setText(boerderij.getRekeningnummer());
    }

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);

        btnModify.setOnAction(e -> ModifyRow());
    }
    public void ModifyRow(){
        boerderij.setNaam(tfNaam.getText());
        boerderij.setAdres(tfAdres.getText());
        boerderij.setEmail(tfEmail.getText());
        boerderij.setRekeningnummer(tfRekeningnummer.getText());
        repo.updateBoerderij(boerderij);
    }
}

