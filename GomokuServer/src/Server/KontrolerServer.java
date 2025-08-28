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
   Long idIgrac1;
   Long idIgrac2;
   Long idPobednik;
   int kodPartije;
   Long idPartije;
   
   private int[][] tabla; // 0 = prazno, 1 = X, 2 = O
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
        return ((KreirajNovuPartiju) so).getResult();
    }
    
     public Long pridruziSeIgri(Long igrac2, int kod) throws Exception {

        Partija p = new Partija(kod, igrac2, 0L);
        SystemOperation so = new PridruziSeIgri();
        
        so.templateExecute(p);
        return ((PridruziSeIgri) so).getResult();
    }
    
     public int zavrsiPartiju(int kod, Long idPobednika) throws Exception {
          Partija p = new Partija(0L, kod, 0L, 0L, idPobednika);
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
            

       
       /**
     * Obradi potez igraca
     * @param igracId - id korisnika koji salje potez
     * @param x - koordinata
     * @param y - koordinata
     * @return 1 ako je samo dobar potez, 2 ako je pobeda
     */
    public int odigrajPotez(Long igracId, int x, int y) {
        int simbol = (igracId == idIgrac1) ? 1 : 2; // 1 = X, 2 = O

        if (tabla[x][y] != 0) {
            // već zauzeto mesto
            return -1; // možeš ovako javiti da je potez nevalidan
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
        // Proveri 4 smera: horizontalno, vertikalno, dijagonala / i dijagonala \
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
    
    
    //server treba da obavesti sve kljente u slucju:
    //dodat drugi igrac, zavrsi igru, odigraj potez
    //implementiraj kako korisnik zna koji je igrac napustio igru
    //prvo disable dugme pa salji request
    //javi se pitaj za iskustva
    //neki odvojeni listener koji uvek slusa sve zahteve? 3 tipa = dodat vam je suparnik, pobedili ste jer je izasao, izgubili ste jer je dobar potez drugog
    //kako da zna dal je x ili o, server zna jel je on id1 ili 2, i on zna jer se pridruzio ?  isto ima jedan id null
    //kako cu znati koju poziciju je kliknuo
}
