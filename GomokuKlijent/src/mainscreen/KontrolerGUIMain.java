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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import login.FXMLLoginController;

/**
 *
 * @author ANDJELA
 */

public class KontrolerGUIMain {
    FXMLDocumentController fxcon;
           
   // Button[][] tabla = new Button[10][10];
   // char[][] stanje = new char[10][10]; // X/O ili '\0'
   // char trenutniIgrac = 'X';

    private int kodIgre;
    private Long idPartija;
    private Long igrac1;
    private Long igrac2;
    private Long trenutniKorisnik;
          
          
          
     public KontrolerGUIMain(FXMLDocumentController fxcon) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, Exception
        { this.fxcon = fxcon; 
         
       // inicijalizujTablu();
        
        
          this.fxcon.Izlaz.setOnAction(e->zavrsiIgruSaPorukom("Zavrsava se igra"));
         this.fxcon.ranglista.setOnAction(e->prikaziRangListu());
           this.fxcon.istorija.setOnAction(e->prikaziIstorijuIgara());
            this.fxcon.zapocninovuigru.setOnAction(e -> zapocniNovuIgru());
        this.fxcon.pridruziseigri.setOnAction(e -> pridruziSeIgri());
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
            lista.add(new Partija(k.getIDPartija(), k.getSifraIgre(), k.getIdIgrac1(), k.getIdIgrac2(), k.getIdPobednik()));
        }

       
        new TabelaPartije.JFXPartije(lista).start(new Stage());

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}
  
  //KOD--------------------------------------------------------------------------------------------
  
//  private void inicijalizujTablu() throws Exception {
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                Field f = fxcon.getClass().getDeclaredField("p" + i + j);
//                tabla[i][j] = (Button) f.get(fxcon);
//                int finalI = i, finalJ = j;
//                tabla[i][j].setOnAction(ev -> odigrajPotez(finalI, finalJ));
//            }
//        }
//    }

//    private void odigrajPotez(int i, int j) {
//        if (stanje[i][j] != '\0') return;
//
//        stanje[i][j] = trenutniIgrac;
//        tabla[i][j].setText(String.valueOf(trenutniIgrac));
//
//        if (proveriPobednika(i, j, trenutniIgrac)) {
//            zavrsiIgruSaPobednikom(trenutniIgrac);
//            return;
//        }
//
//        trenutniIgrac = (trenutniIgrac == 'X') ? 'O' : 'X';
//    }
//
//    private boolean proveriPobednika(int row, int col, char igrac) {
//        int[][] dirs = {{1,0},{0,1},{1,1},{1,-1}};
//        for (int[] d : dirs) {
//            int count = 1;
//            int r = row + d[0], c = col + d[1];
//            while (r >= 0 && r < 10 && c >= 0 && c < 10 && stanje[r][c] == igrac) {
//                count++; r += d[0]; c += d[1];
//            }
//            r = row - d[0]; c = col - d[1];
//            while (r >= 0 && r < 10 && c >= 0 && c < 10 && stanje[r][c] == igrac) {
//                count++; r -= d[0]; c -= d[1];
//            }
//            if (count >= 5) return true;
//        }
//        return false;
//    }

    // ------------------ NOVA IGRA ------------------

    private void zapocniNovuIgru() {
        kodIgre = generisiRandomKod(6);
        trenutniKorisnik = KontrolerKlijent.getInstance().getUlogovaniKorisnik().getIDKorisnik();
        try {
            idPartija = KontrolerKlijent.getInstance().kreirajNovuPartiju(trenutniKorisnik, kodIgre);
            igrac1 = trenutniKorisnik;
            igrac2 = 0L;

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

    // ------------------ PRIDRUŽIVANJE ------------------

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

    // ------------------ NAPUŠTANJE IGRE ------------------

    private void napustiIgru() {
        char pobednik = (trenutniIgrac == 'X') ? 'O' : 'X'; // drugi igrač pobeđuje
        zavrsiIgruSaPobednikom(pobednik);
    }

    private void zavrsiIgruSaPobednikom(char pobednik) {
        try {
            Long pobednikID = (pobednik == 'X') ? igrac1 : igrac2;
            KontrolerKlijent.getInstance().zavrsiPartiju(kodIgre, pobednikID);

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Kraj igre");
            info.setHeaderText(null);
            info.setContentText("Pobednik je: " + pobednik);
            info.showAndWait();

            fxcon.closeStage();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ------------------ POMOĆNE ------------------

    public void zavrsiIgruSaPorukom2(String poruka) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Poruka");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(poruka);
        infoAlert.showAndWait();
        fxcon.closeStage();
    }

 
}