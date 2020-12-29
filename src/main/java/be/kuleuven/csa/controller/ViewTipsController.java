package be.kuleuven.csa.controller;

import be.kuleuven.csa.domain.CouchDbClient;
import be.kuleuven.csa.domain.ProductTips;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ViewTipsController {
    public static ArrayList<String> LIST = new ArrayList<>();
    public static Boolean ISLINKS = false;
    public static int PRODUCT_ID = -1;
    public static String TEXT = "";
    @FXML
    private AnchorPane tips_ap;
    @FXML
    private Button tips_btn_voegToe;
    @FXML
    private ListView<String> tips_lv;
    @FXML
    private TextArea tips_ta;

    public void initialize() {
        initialiseListView();
        if (ISLINKS){
            tips_lv.setOnMouseClicked(e ->{
                if(e.getClickCount() == 2 && tips_lv.getSelectionModel().getSelectedItem() != null) {
                    String uri = tips_lv.getSelectionModel().getSelectedItem();
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        try {
                            Desktop.getDesktop().browse(new URI(uri));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        } catch (URISyntaxException uriSyntaxException) {
                            uriSyntaxException.printStackTrace();
                        }
                    }
                }
            });
        };
        tips_btn_voegToe.setOnAction(event -> addTip());
        tips_ap.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.DELETE){
                removeTip();
            }
        });
    }

    private void initialiseListView() {
        tips_lv.getItems().clear();
        tips_lv.getItems().addAll(LIST);
    }

    private void addTip() {
        String s = tips_ta.getText();
        System.out.println(s);
        LIST.add(s);
        updateNoSqlDb();
        initialiseListView();
    }

    private void removeTip(){
        if (tips_lv.getSelectionModel().getSelectedItem() != null){
            LIST.remove(tips_lv.getSelectionModel().getSelectedIndex());
        }
        updateNoSqlDb();
        initialiseListView();
    }

    private void updateNoSqlDb(){
        ProductTips pt = new CouchDbClient().getProductTips(PRODUCT_ID);
        if (TEXT.equalsIgnoreCase(BeheerTipsController.COLUMNNAME4)){
            pt.setTips(LIST);
        } else if (TEXT.equalsIgnoreCase(BeheerTipsController.COLUMNNAME5)){
            pt.setLinks(LIST);
        } else if (TEXT.equalsIgnoreCase(BeheerTipsController.COLUMNNAME6)){
            pt.setRecipes(LIST);
        } else if (TEXT.equalsIgnoreCase(BeheerTipsController.COLUMNNAME7)){
            pt.setExtra_info(LIST);
        } else {
            return;
        }
        new CouchDbClient().updateProductTip(pt);
    }

}
