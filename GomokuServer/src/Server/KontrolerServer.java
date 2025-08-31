/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import DomenskiObjekat.GenerickiDomObj;
import DomenskiObjekat.Korisnik;
import DomenskiObjekat.Partija;
import SO.*;
import TransferObjekat.ServerResponse;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import so.SystemOperation;

/**
 *
 * @author ANDJELA
 */
public class KontrolerServer {

    static ServerSocket ss;
    static List<Klijent> lkl = new ArrayList<>();
    static int brojKlijenta;
   Long idIgrac1;
   Long idIgrac2;
   Long idPobednik;
   int kodPartije;
   Long idPartije;
   
   private int[][] tabla; 
    private int size = 10;
    private static KontrolerServer instance;
  

    private KontrolerServer() {
    tabla = new int[size][size];
    }

    public static KontrolerServer getInstance() {
        if (instance == null) {
            instance = new KontrolerServer();
        }
        return instance;
    }
   

    public static void main(String arg[]) throws Exception {
     
        KontrolerServer.getInstance().izvrsiGenerickiKontroler();
    }

    void izvrsiGenerickiKontroler() throws Exception {
        ss = new ServerSocket(8189);
        System.out.println("Server je pokrenut!");
        while (true) {
            Socket soketS = ss.accept();
            Klijent kl = new Klijent(soketS, ++brojKlijenta, this);
            lkl.add(kl);
        }
    }

    public GenerickiDomObj ulogujKorisnika(String username, String password) throws Exception {

        Korisnik kor = new Korisnik();
        kor.setKorisnickoIme(username);
        kor.setSifra(password);
       
        SystemOperation so = new UlogujKorisnika();
        
        so.templateExecute(kor);
        return ((UlogujKorisnika) so).getResult();
    }

    public Long registrujKorisnika(String username, String password) throws Exception {

        Korisnik kor = new Korisnik();
        kor.setKorisnickoIme(username);
        kor.setSifra(password);
       
        SystemOperation so = new RegistrujKorisnika();
        
        so.templateExecute(kor);
        return ((RegistrujKorisnika) so).getResult();
    }
    
      public List<GenerickiDomObj> vratiRangListu() throws Exception {

        Korisnik kor = new Korisnik();
        SystemOperation so = new VratiRangListu();
        
        so.templateExecute(kor);
        return ((VratiRangListu) so).getResult();
    }
      
        public List<GenerickiDomObj> vratiSvePartije() throws Exception {

        Partija p = new Partija();
        SystemOperation so = new VratiSvePartije();
        
        so.templateExecute(p);
        return ((VratiSvePartije) so).getResult();
    }
       
    public Long kreirajNovuPartiju(Long igrac1, int kod) throws Exception {

        Partija p = new Partija(kod, igrac1);
        SystemOperation so = new KreirajNovuPartiju();
        
        so.templateExecute(p);
        kodPartije = kod;
        idIgrac1 = igrac1;
        return ((KreirajNovuPartiju) so).getResult();
    }
    
     public Long pridruziSeIgri(Long igrac2, int kod) throws Exception {

        Partija p = new Partija(kod, igrac2, 0L);
        SystemOperation so = new PridruziSeIgri();
        
        so.templateExecute(p);
        idIgrac2 = igrac2;
        return ((PridruziSeIgri) so).getResult();
    }
    
     public int zavrsiPartiju(int kod, Long idGubitnika) throws Exception {
        
         Long idPobednika = Objects.equals(idGubitnika, idIgrac1) ? idIgrac2 : idIgrac1;

          Partija p = new Partija(0L, kod, 0L, 0L, idPobednika);
          idPobednik = idPobednika;
        SystemOperation so = new ZavrsiPartiju();
        
        so.templateExecute(p);
        return ((ZavrsiPartiju) so).getResult();
     }
     
       public int povecajBrPobedaKorisniku(Long idKor) throws Exception {
         Korisnik kor = new Korisnik(idKor);
        SystemOperation so = new IzmeniBrPobedaKorisnika();
        
        so.templateExecute(kor);
        return ((IzmeniBrPobedaKorisnika) so).getResult();
     }
            
    public int odigrajPotez(Long igracId, int x, int y) {
        int simbol = (igracId == idIgrac1) ? 1 : 2; 

        if (tabla[x][y] != 0) {
            // već zauzeto mesto
            return -1; // potez nevalidan
        }

        // upiši potez
        tabla[x][y] = simbol;

        // proveri pobedu
        if (proveriPobedu(x, y, simbol)) {
            return 3; // pobedio igrac
        }

        return 4; // dobar potez, ali nema pobede
    }
           

        private boolean proveriPobedu(int x, int y, int simbol) {
        
        return (count(x, y, 1, 0, simbol) + count(x, y, -1, 0, simbol) - 1 >= 5) ||
               (count(x, y, 0, 1, simbol) + count(x, y, 0, -1, simbol) - 1 >= 5) ||
               (count(x, y, 1, 1, simbol) + count(x, y, -1, -1, simbol) - 1 >= 5) ||
               (count(x, y, 1, -1, simbol) + count(x, y, -1, 1, simbol) - 1 >= 5);
    }

    private int count(int x, int y, int dx, int dy, int simbol) {
        int cnt = 0;
        while (x >= 0 && x < size && y >= 0 && y < size && tabla[x][y] == simbol) {
            cnt++;
            x += dx;
            y += dy;
        }
        return cnt;
    }    
    
    public void broadcast(ServerResponse so) {
    for (Klijent k : lkl) {
        if (k != null) {
            try {
                k.posaljiOdgovor(so);
            } catch (IOException ex) {
                Logger.getLogger(KontrolerServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
    
    
}
