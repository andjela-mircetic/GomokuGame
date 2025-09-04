/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

import DomenskiObjekat.Korisnik;
import kontroler.KontrolerKlijent;
import Server_client.GenerickiKontrolerServer;
import Server_client.GenerickiKontrolerServer_Service;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import login.FXMLLoginController;
import login.KontrolerGUILogin;
import pocetna.*;

/**
 *
 * @author ANDJELA
 */
public class KontrolerGUIRegister {
          FXMLRegisterController fxcon;
                 
     GenerickiKontrolerServer_Service service;
     GenerickiKontrolerServer kal;

     public KontrolerGUIRegister(FXMLRegisterController fxcon) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
        { this.fxcon = fxcon; 
          service = new GenerickiKontrolerServer_Service();
         kal = service.getGenerickiKontrolerServerPort();
          this.fxcon.registerBtn.setOnAction(e->registrujKorisnika(this.fxcon.username.getText(), this.fxcon.password.getText()));
         
        } 
     
 
 public void poruka(String poruka)
 {  Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    infoAlert.setTitle("Poruka:");
    infoAlert.setHeaderText(null); 
    infoAlert.setContentText(poruka); 
    infoAlert.showAndWait();  
 }

       
      public void registrujKorisnika(String username, String password)
 {  
          try {  
          
            //  Long kor = KontrolerKlijent.getInstance().registrujKorisnika(username, password);
           Long kor = kal.registrujKorisnika(username, password);
              
              
                  JFXPocetna pocetna;
                  
                  Stage s;
                  pocetna = new JFXPocetna();
                  s = new Stage();
                  
                  pocetna.start(s);
                  
                  this.fxcon.closeStage();
             
          } catch (Exception ex) {
    
             poruka("Korisnik sa ovim korisnickim imenom vec postoji");
          }
 }
}