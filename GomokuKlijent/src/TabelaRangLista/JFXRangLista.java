/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TabelaRangLista;

import DomenskiObjekat.Korisnik;
import java.net.URL;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.FXMLLoginController;

/**
 *
 * @author ANDJELA
 */
public class JFXRangLista extends Application {
         FXMLRangListController con;
           private static List<Korisnik> initialData;

    public JFXRangLista(List<Korisnik> lista) {
    initialData = lista;    
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        String resourcePath = "FXMLRangList.fxml";
        URL location = getClass().getResource(resourcePath);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = fxmlLoader.load();
        con = (FXMLRangListController) fxmlLoader.getController();
        con.setData(initialData);
       // con.setStage(stage);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("CSS/stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("rang lista");
        stage.show();
        
    }
}
