/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainscreen;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ANDJELA
 */
public class FXMLDocumentController {

    @FXML
    public Menu igraj; 
    @FXML
    public Menu informacijeOProgramu;
    @FXML
    public Menu izlazIzPrograma;
                
              
    @FXML
    public MenuItem zapocninovuigru;
    @FXML
    public MenuItem istorija;
    @FXML
    public MenuItem Izlaz;
    @FXML
    public MenuItem pridruziseigri;
    @FXML
    public MenuItem ranglista;
    
    
    @FXML
    public GridPane tablica;

    @FXML
    public Button p00;
    @FXML
    public Button p01;
    @FXML
    public Button p02;
    @FXML
    public Button p03;
    @FXML
    public Button p04;
    @FXML
    public Button p05;
    @FXML
    public Button p06;
    @FXML
    public Button p07;
    @FXML
    public Button p08;
    @FXML
    public Button p09;
       @FXML
    public Button p10;
    @FXML
    public Button p11;
    @FXML
    public Button p12;
    @FXML
    public Button p13;
    @FXML
    public Button p14;
    @FXML
    public Button p15;
    @FXML
    public Button p16;
    @FXML
    public Button p17;
    @FXML
    public Button p18;
    @FXML
    public Button p19;
       @FXML
    public Button p20;
    @FXML
    public Button p21;
    @FXML
    public Button p22;
    @FXML
    public Button p23;
    @FXML
    public Button p24;
    @FXML
    public Button p25;
    @FXML
    public Button p26;
    @FXML
    public Button p27;
    @FXML
    public Button p28;
    @FXML
    public Button p29;
       @FXML
    public Button p30;
    @FXML
    public Button p31;
    @FXML
    public Button p32;
    @FXML
    public Button p33;
    @FXML
    public Button p34;
    @FXML
    public Button p35;
    @FXML
    public Button p36;
    @FXML
    public Button p37;
    @FXML
    public Button p38;
    @FXML
    public Button p39;
       @FXML
    public Button p40;
    @FXML
    public Button p41;
    @FXML
    public Button p42;
    @FXML
    public Button p43;
    @FXML
    public Button p44;
    @FXML
    public Button p45;
    @FXML
    public Button p46;
    @FXML
    public Button p47;
    @FXML
    public Button p48;
    @FXML
    public Button p49;
       @FXML
    public Button p50;
    @FXML
    public Button p51;
    @FXML
    public Button p52;
    @FXML
    public Button p53;
    @FXML
    public Button p54;
    @FXML
    public Button p55;
    @FXML
    public Button p56;
    @FXML
    public Button p57;
    @FXML
    public Button p58;
    @FXML
    public Button p59;
       @FXML
    public Button p60;
    @FXML
    public Button p61;
    @FXML
    public Button p62;
    @FXML
    public Button p63;
    @FXML
    public Button p64;
    @FXML
    public Button p65;
    @FXML
    public Button p66;
    @FXML
    public Button p67;
    @FXML
    public Button p68;
    @FXML
    public Button p69;
       @FXML
    public Button p70;
    @FXML
    public Button p71;
    @FXML
    public Button p72;
    @FXML
    public Button p73;
    @FXML
    public Button p74;
    @FXML
    public Button p75;
    @FXML
    public Button p76;
    @FXML
    public Button p77;
    @FXML
    public Button p78;
    @FXML
    public Button p79;
       @FXML
    public Button p80;
    @FXML
    public Button p81;
    @FXML
    public Button p82;
    @FXML
    public Button p83;
    @FXML
    public Button p84;
    @FXML
    public Button p85;
    @FXML
    public Button p86;
    @FXML
    public Button p87;
    @FXML
    public Button p88;
    @FXML
    public Button p89;
       @FXML
    public Button p90;
    @FXML
    public Button p91;
    @FXML
    public Button p92;
    @FXML
    public Button p93;
    @FXML
    public Button p94;
    @FXML
    public Button p95;
    @FXML
    public Button p96;
    @FXML
    public Button p97;
    @FXML
    public Button p98;
    @FXML
    public Button p99;
    
    
    
    
    
    @FXML
    public void initialize() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
       KontrolerGUIMain kngui = new KontrolerGUIMain(this);
              
    }    
        
      public Stage stage;

    void setStage(Stage stage) {
        this.stage = stage;
    }

    public void closeStage() {
        stage.close();
    }  
    
}
