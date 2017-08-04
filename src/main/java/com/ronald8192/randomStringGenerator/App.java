package com.ronald8192.randomStringGenerator;

import com.ronald8192.randomStringGenerator.viewController.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 */
public class App extends Application {
    static Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //code runs when program exit
        primaryStage.setOnCloseRequest(e -> {
            log.trace("Exiting...");
        });

        primaryStage.setTitle("Random String Generator");

        FXMLLoader myLoader = new FXMLLoader(getClass().getClassLoader().getResource("view/home.fxml"));
        Pane myPane = (Pane) myLoader.load();
//        HomeController controller = myLoader.<HomeController>getController();
        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
