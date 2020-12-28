package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.Boerderij;
import be.kuleuven.csa.domain.Pakketbeschrijving;
import be.kuleuven.csa.domain.csaRepositoryJpaImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerPakketbeschrijvingenModifyController {
    @FXML
    private Button btnModify;
    @FXML
    private TextField tfNaam;
    @FXML
    private TextField tfKinderen;
    @FXML
    private TextField tfVolwassenen;

    private csaRepositoryJpaImpl repo;

    private Pakketbeschrijving pakketbeschrijving;

    public void initData(Pakketbeschrijving pakketbeschrijving) {
        this.pakketbeschrijving = pakketbeschrijving;
        tfNaam.setText(pakketbeschrijving.getNaam());
        tfKinderen.setText(String.valueOf(pakketbeschrijving.getKinderen()));
        tfVolwassenen.setText(String.valueOf(pakketbeschrijving.getVolwassenen()));
    }

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);

        btnModify.setOnAction(e -> ModifyRow());
    }
    public void ModifyRow(){
        pakketbeschrijving.setNaam(tfNaam.getText());
        pakketbeschrijving.setKinderen(Integer.valueOf(tfKinderen.getText()));
        pakketbeschrijving.setVolwassenen(Integer.valueOf(tfVolwassenen.getText()));
        repo.updatePakketbeschrijving(pakketbeschrijving);
    }
}

