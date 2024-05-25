package com.fvgprinc.app.crudfx;

import com.fvgprinc.app.crudfx.globals.GlobalConstants;
import com.fvgprinc.tools.db.DIContainer;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private StudentController sController  = new StudentController();

    public void start(Stage stage) throws IOException {
        // Init connections
        DIContainer.registerDataManager(GlobalConstants.SQLITECONN2);
        scene = new Scene(loadFXML("student"));
        stage.setTitle("Java Fx CRUD Application 2");
        sController.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
       // System.out.println("com.fvgprinc.app.crudfx.App.main()");
        // DIContainer.registerDataManager(GlobalConstants.MARIADBCONN);  // Se pasó al start
       launch();
               
        // Test para las rutinas de BD y conexión
    }

}