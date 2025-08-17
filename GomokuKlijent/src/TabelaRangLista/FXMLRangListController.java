/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TabelaRangLista;

import DomenskiObjekat.Korisnik;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author ANDJELA
 */
public class FXMLRangListController {
  private ObservableList<Korisnik> data = FXCollections.observableArrayList();
       
    
        @FXML
    private TableView<Korisnik> rangTabela;

    @FXML
    private TableColumn<Korisnik, Integer> id;

    @FXML
    private TableColumn<Korisnik, String> korIme;

    @FXML
    private TableColumn<Korisnik, Integer> brPobeda;


    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("IDKorisnik"));
        korIme.setCellValueFactory(new PropertyValueFactory<>("korisnickoIme"));
        brPobeda.setCellValueFactory(new PropertyValueFactory<>("brojPobeda"));

        rangTabela.setItems(data);
    }
   
    
    public void setData(java.util.List<Korisnik> lista) {
        data.setAll(lista);
    }
}
