/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.KontrolerGUILogin;

/**
 * FXML Controller class
 *
 * @author ANDJELA
 */
public class FXMLRegisterController {

      
    @FXML
    public TextField username;
     
    @FXML
    public TextField password;

    @FXML
    public Button registerBtn;
    
    
    @FXML
    public void initialize() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
       KontrolerGUIRegister kngui = new KontrolerGUIRegister(this);
              
    }    
       
         public Stage stage;

    void setStage(Stage stage) {
        this.stage = stage;
    }

    public void closeStage() {
        stage.close();
    }
}
