package pocetna;

import kontroler.KontrolerKlijent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ANDJELA
 */
public class KontrolerGUIPocetna {
      FXMLDocumentController fxcon;
          
     public KontrolerGUIPocetna(FXMLDocumentController fxcon) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
        { this.fxcon = fxcon; 

           
   
          try {
               Socket socket = new Socket("localhost", 8189);
              KontrolerKlijent.getInstance().initStreams(socket);
              
                 
          this.fxcon.login.setOnAction(e->login());
          this.fxcon.register.setOnAction(e->register());
          } catch (IOException ex) {
              Logger.getLogger(KontrolerGUIPocetna.class.getName()).log(Level.SEVERE, null, ex);
              JOptionPane.showMessageDialog(null, "Server nije pokrenut, pokušajte kasnije.");
          }
            
         
        } 
         
     public void login()
 {  
          try {
              login.JFXLogin loginStage;
              Stage s;
              loginStage = new login.JFXLogin();
              s = new Stage();
              
              loginStage.start(s);
       
              this.fxcon.closeStage();
          } catch (Exception ex) {
              Logger.getLogger(KontrolerGUIPocetna.class.getName()).log(Level.SEVERE, null, ex);
          }
 }
      
       public void register()
 { 
        try {
              register.JFXRegister registerStage;
              Stage s;
              registerStage = new register.JFXRegister();
              s = new Stage();
              
              registerStage.start(s);
       
              this.fxcon.closeStage();
          } catch (Exception ex) {
              Logger.getLogger(KontrolerGUIPocetna.class.getName()).log(Level.SEVERE, null, ex);
          }
 }

}
