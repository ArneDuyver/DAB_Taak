package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerBehoortTotModifyController {
    @FXML
    private TextField tfWeeknr;
    @FXML
    private Button btnModify;
    private csaRepositoryJpaImpl repo;

    private BehoortTot behoortTot;

    public void initData(BehoortTot behoortTot) {
        this.behoortTot = behoortTot;
        tfWeeknr.setText(String.valueOf(behoortTot.getWeekNummer()));
    }

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);

        btnModify.setOnAction(e -> ModifyRow());
    }
    public void ModifyRow(){
        behoortTot.setWeekNummer(Integer.valueOf(tfWeeknr.getText()));
        repo.updateBehoortTot(behoortTot);
    }
}

