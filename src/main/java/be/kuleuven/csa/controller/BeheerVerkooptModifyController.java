package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerVerkooptModifyController {
    @FXML
    private TextField tfStartdatum;
    @FXML
    private TextField tfPrijs;

    @FXML
    private Button btnModify;
    private csaRepositoryJpaImpl repo;

    private Verkoopt verkoopt;

    public void initData(Verkoopt verkoopt) {
        this.verkoopt = verkoopt;
        tfStartdatum.setText(verkoopt.getStartdatum());
        tfPrijs.setText(String.valueOf(verkoopt.getPrijs()));
    }

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);

        btnModify.setOnAction(e -> ModifyRow());
    }
    public void ModifyRow(){
        verkoopt.setStartdatum(tfStartdatum.getText());
        verkoopt.setPrijs(Integer.valueOf(tfPrijs.getText()));
        repo.updateVerkoopt(verkoopt);
    }
}
