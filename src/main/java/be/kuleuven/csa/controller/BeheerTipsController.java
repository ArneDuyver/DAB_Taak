package be.kuleuven.csa.controller;

import be.kuleuven.csa.ProjectMain;
import be.kuleuven.csa.ResourceHelper;
import be.kuleuven.csa.domain.CouchDbClient;
import be.kuleuven.csa.domain.ProductTips;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BeheerTipsController {
    public static final String COLUMNNAME1 = "Product_id";
    public static final String COLUMNNAME2 = "naam";
    public static final String COLUMNNAME3 = "soort";
    public static final String COLUMNNAME4 = "tips";
    public static final String COLUMNNAME5 = "links";
    public static final String COLUMNNAME6 = "recipes";
    public static final String COLUMNNAME7 = "extra_info";
    private ArrayList<ProductTips> tips;
    @FXML
    private TableView tblTips;

    public void initialize() {
        initTable();
        tblTips.setOnMouseClicked(e -> {
            tblTips.getSelectionModel().setCellSelectionEnabled(true);
            if(e.getClickCount() == 2 && tblTips.getSelectionModel().getSelectedItem() != null) {
                ObservableList<TablePosition> selectedCells = tblTips.getSelectionModel().getSelectedCells();
                var selectedRow = (List<String>) tblTips.getSelectionModel().getSelectedItem();
                System.out.println(selectedRow.get(1));
                System.out.println(selectedCells.get(0).getTableColumn().getText());
                cellSelected(tips.get(Integer.parseInt(selectedRow.get(0))-1),selectedCells.get(0).getTableColumn().getText());
            }
        });
    }

    private void cellSelected(ProductTips productTips, String text) {
        ArrayList<String> list = new ArrayList<>();
        ViewTipsController.TEXT = text;
        if (text.equalsIgnoreCase(COLUMNNAME4)){
            if (productTips.getTips()!=null) {
                list = productTips.getTips();
                ViewTipsController.ISLINKS = false;
            }
        } else if (text.equalsIgnoreCase(COLUMNNAME5)){
            if (productTips.getLinks()!=null) {
                list = productTips.getLinks();
                ViewTipsController.ISLINKS = true;
            }
        } else if (text.equalsIgnoreCase(COLUMNNAME6)){
            if (productTips.getRecipes()!=null) {
                list = productTips.getRecipes();
                ViewTipsController.ISLINKS = false;
            }
        } else if (text.equalsIgnoreCase(COLUMNNAME7)){
            if (productTips.getExtra_info()!=null) {
                list = productTips.getExtra_info();
                ViewTipsController.ISLINKS = false;
            }
        } else {
            return;
        }
        ViewTipsController.LIST = list;
        ViewTipsController.PRODUCT_ID = productTips.getProduct_id();
        String resourceName = "tips.fxml";
        try {
            var stage = new Stage();
            var root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource(resourceName));
            var scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle(text+" van "+ productTips.getName());
            stage.initOwner(ProjectMain.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    initialize();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Kan beheerscherm " + resourceName + " niet vinden", e);
        }
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
    }

    private boolean isMac() {
        return System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0;
    }

    private void runResource(String resource) {
        try {
            // TODO dit moet niet van de resource list komen maar van een DB.
            var data = this.getClass().getClassLoader().getResourceAsStream(resource).readAllBytes();
            var path = Paths.get("out-" + resource);
            Files.write(path, data);
            Thread.sleep(1000);

            var process = new ProcessBuilder();

            if(isWindows()) {
                process.command("cmd.exe", "/c", "start " + path.toRealPath().toString());
            } else if(isMac()) {
                process.command("open", path.toRealPath().toString());
            } else {
                throw new RuntimeException("Ik ken uw OS niet jong");
            }

            process.start();
        } catch (Exception e) {
            throw new RuntimeException("resource " + resource + " kan niet ingelezen worden", e);
        }
    }

    private void initTable() {
        tblTips.getColumns().clear();
        tblTips.getItems().clear();
        tblTips.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblTips.getColumns().clear();

        int colIndex = 0;
        for(var colName : new String[]{COLUMNNAME1,COLUMNNAME2,COLUMNNAME3,COLUMNNAME4,COLUMNNAME5,COLUMNNAME6,COLUMNNAME7}) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            final int finalColIndex = colIndex;
            col.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().get(finalColIndex)));
            tblTips.getColumns().add(col);
            colIndex++;
        }

        tips = new CouchDbClient().getAllProductTips();
        for(ProductTips pt : tips){
            tblTips.getItems().add(FXCollections.observableArrayList(pt.getProduct_id()+"",pt.getName(),pt.getSoort(), ResourceHelper.getString("Click_here"),ResourceHelper.getString("Click_here"),ResourceHelper.getString("Click_here"),ResourceHelper.getString("Click_here")));
        }

    }
}
