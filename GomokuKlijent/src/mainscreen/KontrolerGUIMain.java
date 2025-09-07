/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainscreen;

import DomenskiObjekat.GenerickiDomObj;
import kontroler.KontrolerKlijent;
import DomenskiObjekat.Korisnik;
import TransferObjekat.Operations;
import DomenskiObjekat.Partija;
import TransferObjekat.Potez;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import login.FXMLLoginController;

/**
 *
 * @author ANDJELA
 */

public class KontrolerGUIMain {
    FXMLDocumentController fxcon;

    private int kodIgre;
    private Long idPartija;
    private Long igrac1;
    private Long igrac2;
    private Long trenutniKorisnik;
    char oznakaIgraca;
    boolean zavrsiPartijuFlag = false;
    boolean korisnikSePridruzioFlag = false;
          
          
     public KontrolerGUIMain(FXMLDocumentController fxcon) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, Exception
        { this.fxcon = fxcon; 

        
        this.fxcon.Izlaz.setOnAction(e->zavrsiIgruSaPobednikom());
        this.fxcon.ranglista.setOnAction(e->prikaziRangListu());
        this.fxcon.istorija.setOnAction(e->prikaziIstorijuIgara());
        this.fxcon.zapocninovuigru.setOnAction(e -> zapocniNovuIgru());
        this.fxcon.pridruziseigri.setOnAction(e -> pridruziSeIgri());
        
        KontrolerKlijent.getInstance().setAsyncListener(so -> {
    Platform.runLater(() -> { 
        switch (so.getOperation()) {
            case Operations.ZAVRSI_PARTIJU:
                if (so.isIsSuccess() && zavrsiPartijuFlag == false && zavrsi == false) {
                    zavrsiPartijuFlag = true;
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Kraj igre");
                    info.setHeaderText(null);
                    info.setContentText("Korisnik je predao igru, pobedili ste! ");
                    info.showAndWait();
                }
                break;

            case Operations.PRIDRUZI_SE_PARTIJI:
                if (so.isIsSuccess() && korisnikSePridruzioFlag == false && !igrac2.equals(0L)) {
                    korisnikSePridruzioFlag = true;
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Protivnik se pridružio");
                    info.setHeaderText(null);
                    info.setContentText("Protivnik se uspešno pridružio!");
                    info.showAndWait();
                }
                break;

            case Operations.ODIGRAJ_POTEZ:
                if (so.isIsSuccess()) {
            try {
                Potez protivnikov = (Potez) so.getParameter();
              String poljeId = String.format("p%02d", protivnikov.x * 10 + protivnikov.y);
              System.out.println("Primio potez: id=" + poljeId + " red=" + protivnikov.x + " kolona=" + protivnikov.y);
                    if (zauzetaPolja.contains(poljeId)) {
                 break;
                 }

                 zauzetaPolja.add(poljeId);
                Field f = FXMLDocumentController.class.getDeclaredField(poljeId);
                f.setAccessible(true);
                Button polje = (Button) f.get(fxcon);
                char znak = oznakaIgraca == 'X' ? 'O' : 'X';
                polje.setText("" + znak);
                polje.setDisable(true);
                
                if (protivnikov.rezultat == 3){
                         Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Izgubili ste");
                    info.setHeaderText(null);
                    info.setContentText("Protivnik je pobedio!");
                    info.showAndWait();
                    
         
                }
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(KontrolerGUIMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(KontrolerGUIMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(KontrolerGUIMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(KontrolerGUIMain.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
                break;
        }
    });
});

        } 
     
 private Set<String> zauzetaPolja = new HashSet<>();
 public void zavrsiIgruSaPorukom(String poruka)
 {  Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    infoAlert.setTitle("Poruka:");
    infoAlert.setHeaderText(null); 
    infoAlert.setContentText(poruka); 
    infoAlert.showAndWait();  
    
    this.fxcon.closeStage();
 }

 public void postaviAkcijeNaDugmad() {
    for (Node node : fxcon.tablica.getChildren()) {
        if (node instanceof Button) {
            Button dugme = (Button) node;
            dugme.setOnAction(event -> {
                String id = dugme.getId();
                try {
                    odigrajPotez(id, dugme);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
  public void postaviIdjeve() {
               //  int red = 0, kolona = 0;
    for (Node node : fxcon.tablica.getChildren()) {
        if (node instanceof Button) {
             Button dugme = (Button) node;
            Integer r = GridPane.getRowIndex(dugme);
            Integer c = GridPane.getColumnIndex(dugme);

            if (r == null) r = 0;
            if (c == null) c = 0;

            String id = "p" + r + c;
            dugme.setId(id);
            System.out.println("Postavljen ID: " + id);
        }
    }
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
                        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    infoAlert.setTitle("Poruka:");
    infoAlert.setHeaderText(null); 
    infoAlert.setContentText("Ne moze se prikazati rang lista korisnika!"); 
    infoAlert.showAndWait(); 
    }
}
 
  public void prikaziIstorijuIgara() {
    try {
        List<GenerickiDomObj> partije = KontrolerKlijent.getInstance().prikaziPartije();

       
        List<Partija> lista = new ArrayList<>();
        for (GenerickiDomObj gdo : partije) {
            Partija k = (Partija) gdo; 
            lista.add(new Partija(k.getIDPartija(), k.getSifraIgre(), k.getIdIgrac1(), k.getIdIgrac2(), k.getIdPobednik()));
        }

       
        new TabelaPartije.JFXPartije(lista).start(new Stage());

    } catch (Exception ex) {
        ex.printStackTrace();
                 Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    infoAlert.setTitle("Poruka:");
    infoAlert.setHeaderText(null); 
    infoAlert.setContentText("Ne moze se prikazati istorija igara!"); 
    infoAlert.showAndWait();  
    
    }
}
 
  public void odigrajPotez(String poljeId, Button polje) throws Exception {
      if (kodIgre == 0) {
          return; } 

    int index = Integer.parseInt(poljeId.substring(1)); 
    int red = index / 10;
    int kolona = index % 10;
     zauzetaPolja.add(poljeId);

     polje.setText("" + oznakaIgraca); 
    polje.setDisable(true);
    
  System.out.println("Šaljem potez: id=" + poljeId + " red=" + red + " kolona=" + kolona);
    Potez p = KontrolerKlijent.getInstance().posaljiPotez(kodIgre, red, kolona); 
    if(p.rezultat == 3) {
         Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    infoAlert.setTitle("Poruka:");
    infoAlert.setHeaderText(null); 
    infoAlert.setContentText("Pobedili ste, igra je zavrsena!"); 
    infoAlert.showAndWait();  
    
    this.fxcon.closeStage(); 
    }
   
}

    private void zapocniNovuIgru() {
        int kodIgre2 = generisiRandomKod(6);
        trenutniKorisnik = KontrolerKlijent.getInstance().getUlogovaniKorisnik().getIDKorisnik();
        try {
            idPartija = KontrolerKlijent.getInstance().kreirajNovuPartiju(trenutniKorisnik, kodIgre2);
            igrac1 = trenutniKorisnik;
            igrac2 = 0L;
            kodIgre = kodIgre2;
            oznakaIgraca = 'X';
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Nova igra");
            info.setHeaderText(null);
            info.setContentText("Vaš kod igre: " + kodIgre + "\nSačekajte drugog igrača.");
            info.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();
            zavrsiIgruSaPorukom("Greška pri kreiranju igre.");
        }
    }

    private int generisiRandomKod(int duzina) {
    Random rnd = new Random();
    int min = (int) Math.pow(10, duzina - 1); 
    int max = (int) Math.pow(10, duzina) - 1; 
    return rnd.nextInt(max - min + 1) + min;
}

    private void pridruziSeIgri() {
        trenutniKorisnik = KontrolerKlijent.getInstance().getUlogovaniKorisnik().getIDKorisnik();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Pridruži se igri");
        dialog.setHeaderText(null);
        dialog.setContentText("Unesite kod igre:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(kodStr -> {
            int kod = Integer.parseInt(kodStr.trim());
            try {
                idPartija = KontrolerKlijent.getInstance().pridruziSeIgri(kod, trenutniKorisnik);
                if (idPartija > 0) {
                    kodIgre = kod;
                    igrac2 = trenutniKorisnik;
                    igrac1= 0L;
                    oznakaIgraca = 'O';
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Pridruživanje");
                    info.setHeaderText(null);
                    info.setContentText("Uspešno ste se pridružili igri!");
                    info.showAndWait();

                } else {
                    Alert err = new Alert(Alert.AlertType.ERROR);
                    err.setTitle("Greška");
                    err.setHeaderText(null);
                    err.setContentText("Kod nije validan ili je igra već popunjena.");
                    err.showAndWait();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    boolean zavrsi = false;
    private void zavrsiIgruSaPobednikom() {
        try {
           
            if (igrac1 == null && igrac2 == null) {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Nema aktivne igre");
            info.setHeaderText(null);
            info.setContentText("Niste započeli ili se pridružili nijednoj igri.");
            info.showAndWait();
            return;
        }

        Long gubitnikID = (igrac1 != null && igrac1 == 0L) ? igrac2 : igrac1;
            
            if (kodIgre != 0) {
                zavrsi = true;
            KontrolerKlijent.getInstance().zavrsiPartiju(kodIgre, gubitnikID);}
            else {
              Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Ne moze se zavrsiti igra jer nije ni zapoceta");
            info.setHeaderText(null);
            info.setContentText("Ne moze se zavrsiti igra jer nije ni zapoceta.");
            info.showAndWait();
            }

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Kraj igre");
            info.setHeaderText(null);
            info.setContentText("Napustili ste igru. Pobedio je protivnik.");
            info.showAndWait();

            fxcon.closeStage();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

 
}