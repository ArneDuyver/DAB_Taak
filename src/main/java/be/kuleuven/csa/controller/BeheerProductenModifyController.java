package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.CouchDbClient;
import be.kuleuven.csa.domain.Product;
import be.kuleuven.csa.domain.ProductTips;
import be.kuleuven.csa.domain.csaRepositoryJpaImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;

public class BeheerProductenModifyController {
    private Product product;
    @FXML
    private Button btnModify;
    @FXML
    private TextField tfNaam;
    @FXML
    private TextField tfSoort;

    private csaRepositoryJpaImpl repo;

    public void initData(Product product) {
        this.product = product;
        tfNaam.setText(product.getNaam());
        tfSoort.setText(product.getSoort());
    }

    public void initialize() {
        var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.csa.domain");
        var entityManager = sessionFactory.createEntityManager();
        this.repo = new csaRepositoryJpaImpl(entityManager);

        btnModify.setOnAction(e -> ModifyRow());
    }
    public void ModifyRow(){
        product.setNaam(tfNaam.getText());
        product.setSoort(tfSoort.getText());
        repo.updateProduct(product);
        ProductTips pt = new CouchDbClient().getProductTips(product.getProductId());
        pt.setName(product.getNaam());
        pt.setSoort(product.getSoort());
        new CouchDbClient().updateProductTip(pt);
    }
}
