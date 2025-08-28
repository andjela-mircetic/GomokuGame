/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DomenskiObjekat;

import DomenskiObjekat.Korisnik;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import DomenskiObjekat.Operations;
import TransferObjekat.ClientRequest;
import TransferObjekat.ServerResponse;

/**
 *
 * @author ANDJELA
 */

public class KontrolerKlijent {
    private static KontrolerKlijent instance;
    private Korisnik ulogovaniKor;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;

    private KontrolerKlijent() {
        

    }

    public static KontrolerKlijent getInstance() {
        if (instance == null) {
            instance = new KontrolerKlijent();
        }
        return instance;
    }

    public void initStreams(Socket s) throws IOException {
        out = new ObjectOutputStream(s.getOutputStream());
        in = new ObjectInputStream(s.getInputStream());

    }
    
    public Korisnik getUlogovaniKorisnik(){
        return ulogovaniKor;
    }
    
    public Korisnik ulogujKorisnika(String username, String password) throws Exception {
       
        ClientRequest kz = new ClientRequest();
        kz.setOperation(Operations.ULOGUJ_KORISNIKA);
        kz.setData(new Korisnik(username, password));
        out.writeObject(kz);
  
        ServerResponse so = (ServerResponse) in.readObject();
      
        if (so.getOperation() == Operations.DISKONEKTUJ_KORISNIKA) {
            //exitApp();
        }
        if (so.isIsSuccess() == true && so.getParameter()!= null) {
            ulogovaniKor = (Korisnik) so.getParameter();
            System.out.println("uspeh 64 Server.KontrolerKlijent.ulogujKorisnika()");
            return (Korisnik) so.getParameter();
            
        } else {
           //  System.out.println("NETACNI KREDENCIJALI Server.KontrolerKlijent.ulogujKorisnika() else 67");
            throw so.getE();
           
        }
    }
   
     
    public Long registrujKorisnika(String username, String password) throws Exception {
       
        ClientRequest kz = new ClientRequest();
        kz.setOperation(Operations.REGISTRUJ_KORISNIKA);
        kz.setData(new Korisnik(username, password));
        out.writeObject(kz);
      
        
        ServerResponse so = (ServerResponse) in.readObject();
        
        if (so.isIsSuccess() == true) {
    
            return (Long) so.getParameter();
            
        } else {
             System.out.println("NETACNI KREDENCIJALI Server.KontrolerKlijent.ulogujKorisnika() else 67");
            throw so.getE();
           
        }
    }
    
      public List<GenerickiDomObj> prikaziRangListuKorisnika() throws Exception {
       
        ClientRequest kz = new ClientRequest();
        kz.setOperation(Operations.PRIKAZI_RANG);
        
        out.writeObject(kz);
      
        
        ServerResponse so = (ServerResponse) in.readObject();
        
        if (so.isIsSuccess() == true) {
          
            return (List<GenerickiDomObj>) so.getParameter();
            
        } else {
             System.out.println("Ne mogu se dovuci korisnici iz baze");
            throw so.getE();
           
        }
    }
      
        public List<GenerickiDomObj> prikaziPartije() throws Exception {
       
        ClientRequest kz = new ClientRequest();
        kz.setOperation(Operations.VRATI_SVE_PARTIJE);
        
        out.writeObject(kz);
      
        
        ServerResponse so = (ServerResponse) in.readObject();
        
        if (so.isIsSuccess() == true) {
          
            return (List<GenerickiDomObj>) so.getParameter();
            
        } else {
             System.out.println("Ne mogu se dovuci partije iz baze");
            throw so.getE();
           
        }
    }
            
    public Long kreirajNovuPartiju(Long igrac1, int kod) throws Exception {
     ClientRequest kz = new ClientRequest();
        kz.setOperation(Operations.KREIRAJ_PARTIJU);
         kz.setData(new Partija(kod, igrac1));
        out.writeObject(kz);
      
        
        ServerResponse so = (ServerResponse) in.readObject();
        
        if (so.isIsSuccess() == true) {
          
            return (Long) so.getParameter();
            
        } else {
             System.out.println("Ne moze se kreirati nova partija");
            throw so.getE();
           
        }
    }

    public Long pridruziSeIgri(int kod, Long igrac2) throws Exception {
        ClientRequest kz = new ClientRequest();
        kz.setOperation(Operations.PRIDRUZI_SE_PARTIJI);
        Partija part = new Partija(kod, igrac2);
        kz.setData(part);
        
        out.writeObject(kz);
      
        
        ServerResponse so = (ServerResponse) in.readObject();
        
        if (so.isIsSuccess() == true) {
          //dodaj kontroleru kod i igraca2
            return (Long) so.getParameter();
            
        } else {
             System.out.println("Ne moze se pridruziti ovoj partiji");
            throw so.getE();
           
        }
        

    }

    public int zavrsiPartiju(int sifraPartije, Long idGubitnika) throws Exception {
        ClientRequest kz = new ClientRequest();
        kz.setOperation(Operations.ZAVRSI_PARTIJU);
        Partija p = new Partija(0L, sifraPartije, 0L, 0L, idGubitnika);
        kz.setData(p);
        out.writeObject(kz);
      
        
        ServerResponse so = (ServerResponse) in.readObject();
        
        if (so.isIsSuccess() == true) {
          
            return (int) so.getParameter();
            
        } else {
             System.out.println("Ne moze se zavrsiti partija");
            throw so.getE();
           
        }
        
    }

    //public void izmeniKorisnika brpobeda ili sifru(){
    
    //}
}

