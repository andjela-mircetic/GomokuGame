/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TabelaPartije;

import DomenskiObjekat.Korisnik;
import DomenskiObjekat.Partija;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author ANDJELA
 */


public class FXMLPartijeController {
  private ObservableList<Partija> data = FXCollections.observableArrayList();
       
    
    @FXML
    private TableView<Partija> partijeTabela;

    @FXML
    private TableColumn<Partija, Integer> id;
    
     @FXML
    private TableColumn<Partija, Integer> sifra;
    

     @FXML
    private TableColumn<Partija, Integer> id1;

      @FXML
    private TableColumn<Partija, Integer> id2;
      
       @FXML
    private TableColumn<Partija, Integer> pobednik;



    @FXML
    public void initialize() {
        
          id.setCellValueFactory(new PropertyValueFactory<>("idPartija"));
    sifra.setCellValueFactory(new PropertyValueFactory<>("sifraIgre"));
    id1.setCellValueFactory(new PropertyValueFactory<>("idIgrac1"));
    id2.setCellValueFactory(new PropertyValueFactory<>("idIgrac2"));
    pobednik.setCellValueFactory(new PropertyValueFactory<>("idPobednik"));

        partijeTabela.setItems(data);
        
  
    }
   
    
    public void setData(java.util.List<Partija> lista) {
        data.setAll(lista);
    }
}
