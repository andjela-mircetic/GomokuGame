/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainscreen;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author ANDJELA
 */
public class JFXMain extends Application {
         FXMLDocumentController con;

    @Override
    public void start(Stage stage) throws Exception {
        
        String resourcePath = "FXMLDocument.fxml";
        URL location = getClass().getResource(resourcePath);
        System.out.println("location" + location);
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainscreen/FXMLDocument.fxml"));
          System.out.println("main ovde 2");
        Parent root = fxmlLoader.load();
        
        
       // FXMLLoader fxmlLoader = new FXMLLoader(location);
        // System.out.println("main ovde 2");
        //Parent root = fxmlLoader.load();
           System.out.println("main ovde 3");
        con = (FXMLDocumentController) fxmlLoader.getController();
        con.setStage(stage);
           System.out.println("main ovde 4");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("CSS/stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("main");
        stage.show();
        
    }
}
