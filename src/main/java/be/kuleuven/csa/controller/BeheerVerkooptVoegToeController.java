package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.Boerderij;
import be.kuleuven.csa.domain.Pakketbeschrijving;
import be.kuleuven.csa.domain.Verkoopt;
import be.kuleuven.csa.domain.csaRepositoryJpaImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerVerkooptVoegToeController {
    @FXML
    private TextField tfStartdatum;
    @FXML
    private TextField tfPrijs;
    @FXML
    private ComboBox<Pakketbeschrijving> cbPakketbeschrijvingen;
    @FXML
    private ComboBox<Boerderij> cbBoerderijen;
    @FXML
    private Button btnVoegToe;
    private csaRepositoryJpaImpl repo;



    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);
        cbBoerderijen.getItems().setAll(repo.getBoerderij());
        cbPakketbeschrijvingen.getItems().setAll(repo.getPakketbeschijving());
        btnVoegToe.setOnAction(e -> addNewRow());
    }
    public void addNewRow(){
        Verkoopt verkoopt = new Verkoopt(Integer.valueOf(tfPrijs.getText()),tfStartdatum.getText(),cbBoerderijen.getSelectionModel().getSelectedItem(),cbPakketbeschrijvingen.getSelectionModel().getSelectedItem());
        repo.saveNewVerkoopt(verkoopt);
    }
}
