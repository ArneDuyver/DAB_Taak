package be.kuleuven.csa;

import be.kuleuven.csa.domain.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProjectMain extends Application {

    private static Stage rootStage;

    public static Stage getRootStage() {
        return rootStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
//        Model model = new Model();
//        model.initialise();
        rootStage = stage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("csamain.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle(ResourceHelper.getString("program_title"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
