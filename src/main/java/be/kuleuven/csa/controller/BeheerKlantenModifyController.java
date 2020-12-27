package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.Klant;
import be.kuleuven.csa.domain.csaRepositoryJpaImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerKlantenModifyController {
    @FXML
    private Button btnModify;
    @FXML
    private TextField tfNaam;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfAdres;
    @FXML
    private TextField tfTelefoonNummer;

    private csaRepositoryJpaImpl repo;

    private Klant klant;

    public void initData(Klant klant) {
        this.klant = klant;
        tfNaam.setText(klant.getNaam());
        tfEmail.setText(klant.getEmail());
        tfAdres.setText(klant.getAdres());
        tfTelefoonNummer.setText(klant.getTelefoonnummer());
    }

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);

        btnModify.setOnAction(e -> ModifyRow());
    }
    public void ModifyRow(){
        klant.setNaam(tfNaam.getText());
        klant.setAdres(tfAdres.getText());
        klant.setEmail(tfEmail.getText());
        klant.setTelefoonnummer(tfTelefoonNummer.getText());
        repo.updateKlant(klant);
    }
}

