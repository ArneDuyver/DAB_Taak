package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.BehoortTot;
import be.kuleuven.csa.domain.PakketInhoud;
import be.kuleuven.csa.domain.Verkoopt;
import be.kuleuven.csa.domain.csaRepositoryJpaImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerBehoortTotVoegToeController {
    @FXML
    private TextField tfWeeknr;
    @FXML
    private Button btnVoegToe;
    @FXML
    private ComboBox<Verkoopt>cbVerkopen;
    @FXML
    private ComboBox<PakketInhoud>cbPakketInhouden;
    private csaRepositoryJpaImpl repo;

    private BehoortTot behoortTot;


    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);
        cbVerkopen.getItems().setAll(repo.getVerkopen());
        cbPakketInhouden.getItems().setAll(repo.getPakketinhouden());
        btnVoegToe.setOnAction(e -> addNewRow());
    }
    public void addNewRow(){
        BehoortTot behoortTot = new BehoortTot(Integer.valueOf(tfWeeknr.getText()),cbVerkopen.getValue(),cbPakketInhouden.getValue());
        repo.saveNewBehoortTot(behoortTot);
    }
}
