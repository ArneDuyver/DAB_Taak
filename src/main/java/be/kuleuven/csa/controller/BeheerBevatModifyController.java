package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.Bevat;
import be.kuleuven.csa.domain.Boerderij;
import be.kuleuven.csa.domain.Product;
import be.kuleuven.csa.domain.csaRepositoryJpaImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerBevatModifyController {
    @FXML
    private TextField tfEenheid;
    @FXML
    private TextField tfHoeveelheid;
    @FXML
    private ComboBox<Product> cbProducten;
    @FXML
    private Button btnModify;
    private csaRepositoryJpaImpl repo;

    private Bevat bevat;

    public void initData(Bevat bevat) {
        this.bevat = bevat;
        tfEenheid.setText(bevat.getEenheid());
        tfHoeveelheid.setText(String.valueOf(bevat.getHoeveelheid()));
        cbProducten.getItems().setAll(repo.getProduct());
        cbProducten.getSelectionModel().select(bevat.getProduct());
    }

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);

        btnModify.setOnAction(e -> ModifyRow());
    }
    public void ModifyRow(){
        bevat.setEenheid(tfEenheid.getText());
        bevat.setHoeveelheid(Integer.valueOf(tfHoeveelheid.getText()));
        bevat.setProduct(cbProducten.getSelectionModel().getSelectedItem());
        repo.updateBevat(bevat);
    }
}
