package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerKooptModifyController {
    @FXML
    private CheckBox checkbBetaald;
    @FXML
    private ComboBox<Verkoopt> cbVerkopen;
    @FXML
    private ComboBox<Klant> cbKlanten;
    @FXML
    private Button btnModify;
    private csaRepositoryJpaImpl repo;

    private Koopt koopt;

    public void initData(Koopt koopt) {
        this.koopt = koopt;
        cbVerkopen.getItems().setAll(repo.getVerkopen());
        cbVerkopen.getSelectionModel().select(koopt.getVerkoopt());
        cbKlanten.getItems().setAll(repo.getKlant());
        cbKlanten.getSelectionModel().select(koopt.getKlant());
        checkbBetaald.setSelected(koopt.isBetaald());
    }

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);

        btnModify.setOnAction(e -> ModifyRow());
    }
    public void ModifyRow(){
        koopt.setBetaald(checkbBetaald.isSelected());
        koopt.setKlant(cbKlanten.getSelectionModel().getSelectedItem());
        koopt.setVerkoopt(cbVerkopen.getSelectionModel().getSelectedItem());
        boolean alBetaald = koopt.isBetaald();
        boolean nuBetaald = checkbBetaald.isSelected();
        repo.updateKoopt(koopt, alBetaald, nuBetaald);
    }
}
