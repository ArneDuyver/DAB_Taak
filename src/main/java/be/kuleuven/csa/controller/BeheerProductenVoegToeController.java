package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.CouchDbClient;
import be.kuleuven.csa.domain.Product;
import be.kuleuven.csa.domain.ProductTips;
import be.kuleuven.csa.domain.csaRepositoryJpaImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerProductenVoegToeController {
    @FXML
    private Button btnAdd;
    @FXML
    private TextField tfNaam;
    @FXML
    private TextField tfSoort;

    private csaRepositoryJpaImpl repo;

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);
        btnAdd.setOnAction(e -> addNewRow());
    }
    public void addNewRow(){
        Product product= new Product(tfNaam.getText(),tfSoort.getText());
        repo.saveNewProduct(product);
        ProductTips pt = new ProductTips(product.getNaam(), product.getSoort(), product.getProductId());
        new CouchDbClient().saveProductTip(pt);
    }
}