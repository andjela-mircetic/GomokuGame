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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import so.SystemOperation;

/**
 *
 * @author ANDJELA
 */
public class KontrolerServer {

    static ServerSocket ss;
    static List<Klijent> lkl = new ArrayList<>();
    static int brojKlijenta;
   
   
    private static KontrolerServer instance;
  

    private KontrolerServer() {
    
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
}
