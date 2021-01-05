package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import javax.persistence.Persistence;
import java.util.List;

public class BeheerKooptVoegToeController {
    @FXML
    private CheckBox checkbBetaald;
    @FXML
    private ComboBox<Verkoopt> cbVerkopen;
    @FXML
    private ComboBox<Klant> cbKlanten;
    @FXML
    private Button btnVoegToe;
    private csaRepositoryJpaImpl repo;

    private Koopt koopt;


    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);
        cbVerkopen.getItems().setAll(repo.getVerkopen());
        cbKlanten.getItems().setAll(repo.getKlant());
        btnVoegToe.setOnAction(e -> addNewRow());
    }
    public void addNewRow(){
        Koopt koopt = new Koopt(cbVerkopen.getSelectionModel().getSelectedItem(),cbKlanten.getSelectionModel().getSelectedItem(),checkbBetaald.isSelected());

        repo.saveNewKoopt(koopt, checkbBetaald.isSelected());
        List<BehoortTot> behoortTotList = koopt.getVerkoopt().getBehoortTotList();
        for(var eenBehoortTot : behoortTotList) {
            repo.saveNewHaaltAf(new HaaltAf(eenBehoortTot,koopt.getKlant()));
        }
    }
}
