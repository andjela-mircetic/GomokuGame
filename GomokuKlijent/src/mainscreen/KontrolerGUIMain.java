/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainscreen;

import DomenskiObjekat.GenerickiDomObj;
import DomenskiObjekat.KontrolerKlijent;
import DomenskiObjekat.Korisnik;
import DomenskiObjekat.Partija;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import login.FXMLLoginController;

/**
 *
 * @author ANDJELA
 */

public class KontrolerGUIMain {
          FXMLDocumentController fxcon;
          
     public KontrolerGUIMain(FXMLDocumentController fxcon) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
        { this.fxcon = fxcon; 
         
          this.fxcon.Izlaz.setOnAction(e->zavrsiIgruSaPorukom("Zavrsava se igra"));
         this.fxcon.ranglista.setOnAction(e->prikaziRangListu());
           this.fxcon.istorija.setOnAction(e->prikaziIstorijuIgara());
        } 
     
 
 public void zavrsiIgruSaPorukom(String poruka)
 {  Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    infoAlert.setTitle("Poruka:");
    infoAlert.setHeaderText(null); 
    infoAlert.setContentText(poruka); 
    infoAlert.showAndWait();  
    
    this.fxcon.closeStage();
 }

 
 public void prikaziRangListu() {
    try {
        List<GenerickiDomObj> kor = KontrolerKlijent.getInstance().prikaziRangListuKorisnika();

       
        List<Korisnik> lista = new ArrayList<>();
        for (GenerickiDomObj gdo : kor) {
            Korisnik k = (Korisnik) gdo; 
            lista.add(new Korisnik(k.getIDKorisnik(), k.getKorisnickoIme(), k.getBrojPobeda()));
        }

       
        new TabelaRangLista.JFXRangLista(lista).start(new Stage());

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}
 
  public void prikaziIstorijuIgara() {
    try {
        List<GenerickiDomObj> partije = KontrolerKlijent.getInstance().prikaziPartije();

       
        List<Partija> lista = new ArrayList<>();
        for (GenerickiDomObj gdo : partije) {
            Partija k = (Partija) gdo; 
            lista.add(new Partija(k.getIdPartija(), k.getSifraIgre(), k.getIdIgrac1(), k.getIdIgrac2(), k.getIdPobednik()));
        }

       
        new TabelaPartije.JFXPartije(lista).start(new Stage());

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}
 
}