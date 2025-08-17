/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TabelaPartije;

import DomenskiObjekat.Korisnik;
import DomenskiObjekat.Partija;
import TabelaRangLista.FXMLRangListController;
import java.net.URL;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ANDJELA
 */

public class JFXPartije extends Application {
         FXMLPartijeController con;
           private static List<Partija> initialData;

    public JFXPartije(List<Partija> lista) {
    initialData = lista;    
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        String resourcePath = "FXMLPartije.fxml";
        URL location = getClass().getResource(resourcePath);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = fxmlLoader.load();
        con = (FXMLPartijeController) fxmlLoader.getController();
        con.setData(initialData);
       // con.setStage(stage);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("CSS/stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("Istorija igara");
        stage.show();
        
    }
}