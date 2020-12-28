package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.Boerderij;
import be.kuleuven.csa.domain.PakketInhoud;
import be.kuleuven.csa.domain.csaRepositoryJpaImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerPakketinhoudModifyController {
    @FXML
    private Button btnModify;
    @FXML
    private TextField tfNaam;

    private csaRepositoryJpaImpl repo;

    private PakketInhoud pakketInhoud;

    public void initData(PakketInhoud pakketInhoud) {
        this.pakketInhoud = pakketInhoud;
        tfNaam.setText(pakketInhoud.getNaam());
    }

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);

        btnModify.setOnAction(e -> ModifyRow());
    }
    public void ModifyRow(){
        pakketInhoud.setNaam(tfNaam.getText());
        repo.updatePakketinhoud(pakketInhoud);
    }
}
