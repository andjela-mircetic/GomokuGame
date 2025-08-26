/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import DomenskiObjekat.Korisnik;
import DomenskiObjekat.KontrolerKlijent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author ANDJELA
 */
public class KontrolerGUILogin {
          FXMLLoginController fxcon;
          
     public KontrolerGUILogin(FXMLLoginController fxcon) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
        { this.fxcon = fxcon; 
         
          this.fxcon.loginBtn.setOnAction(e->ulogujKorisnika(this.fxcon.username.getText(), this.fxcon.password.getText()));
         
        } 
     
 
 public void poruka(String poruka)
 {  Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    infoAlert.setTitle("Poruka:");
    infoAlert.setHeaderText(null); 
    infoAlert.setContentText(poruka); 
    infoAlert.showAndWait();  
 }
 
      public void ulogujKorisnika(String username, String password)
 {  
          try {  
             System.out.println("ovde 5");
              Korisnik kor = KontrolerKlijent.getInstance().ulogujKorisnika(username, password);
              System.out.println("ovde 6");
                  mainscreen.JFXMain mainScreen;
                  System.out.println("ovde 1");
                  Stage s;
                  mainScreen = new mainscreen.JFXMain();
                  s = new Stage();
                     System.out.println("ovde 2");
                  mainScreen.start(s);
                     System.out.println("ovde 3");
                  this.fxcon.closeStage();
             
          } catch (Exception ex) {
    
             poruka("Korisnik sa ovim kredencijalima ne postoji u sistemu ili je vec ulogovan");
              ex.printStackTrace();
          }
 }
 
}
