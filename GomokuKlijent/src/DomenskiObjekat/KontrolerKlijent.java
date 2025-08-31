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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 *
 * @author ANDJELA
 */

//public class KontrolerKlijent {
//    private static KontrolerKlijent instance;
//    private Korisnik ulogovaniKor;
//    private ObjectOutputStream out;
//    private ObjectInputStream in;
//    private Socket socket;
//     private Thread listenerThread; 
//
//    private KontrolerKlijent() {
//        
//
//    }
//
//    public static KontrolerKlijent getInstance() {
//        if (instance == null) {
//            instance = new KontrolerKlijent();
//        }
//        return instance;
//    }
//
//    public void initStreams(Socket s) throws IOException {
//        out = new ObjectOutputStream(s.getOutputStream());
//        in = new ObjectInputStream(s.getInputStream());
//        startListener();
//    }
//    
//     private void startListener() {
//        listenerThread = new Thread(() -> {
//            try {
//                while (!Thread.currentThread().isInterrupted()) {
//                    Object obj = in.readObject(); 
//
//                    if (obj instanceof ServerResponse) {
//                        ServerResponse so = (ServerResponse) obj;
//                        handleServerResponse(so);
//}
//                }
//            } catch (IOException | ClassNotFoundException e) {
//                System.out.println("Prekinuta konekcija sa serverom.");
//            }
//        });
//        listenerThread.start();
//    }
//     
// private void handleServerResponse(ServerResponse so, Consumer<Korisnik> onSuccess, Consumer<Exception> onError) {
//    switch (so.getOperation()) {
//        case Operations.ULOGUJ_KORISNIKA: {
//         
//             if (so.isIsSuccess()) {
//            Korisnik k = (Korisnik) so.getParameter();
//            ulogovaniKor = k;
//            onSuccess.accept(k);
//        } else {
//            onError.accept(so.getE());
//        }
//            break;
//        }
//        case Operations.PRIKAZI_RANG: {
//            if (so.isIsSuccess()) {
//                List<GenerickiDomObj> rangLista = (List<GenerickiDomObj>) so.getParameter();
//                System.out.println("Rang lista stigla: " + rangLista.size() + " korisnika.");
//            }
//            break;
//        }
//        case Operations.ZAVRSI_PARTIJU: {
//            if (so.isIsSuccess()) {
//                System.out.println("Partija završena. Rezultat: " + so.getParameter());
//            }
//            break;
//        }
//        default:
//            System.out.println("Nepoznata operacija: " + so.getOperation());
//            break;
//    }
//}
//
//
//  
//    public void posaljiZahtev(ClientRequest req) throws IOException {
//        out.writeObject(req);
//        out.flush();
//    }
//
//    
//    public Korisnik getUlogovaniKorisnik(){
//        return ulogovaniKor;
//    }
//    
//    public Korisnik ulogujKorisnika(String username, String password, Consumer<Korisnik> onSuccess, Consumer<Exception> onError) throws Exception {
//       
//        ClientRequest kz = new ClientRequest();
//        kz.setOperation(Operations.ULOGUJ_KORISNIKA);
//        kz.setData(new Korisnik(username, password));
//        out.writeObject(kz);
//  
//        ServerResponse so = (ServerResponse) in.readObject();
//      
//
//        if (so.isIsSuccess() == true && so.getParameter()!= null) {
//            ulogovaniKor = (Korisnik) so.getParameter();
//            System.out.println("uspeh 64 Server.KontrolerKlijent.ulogujKorisnika()");
//            return (Korisnik) so.getParameter();
//            
//        } else {
//           //  System.out.println("NETACNI KREDENCIJALI Server.KontrolerKlijent.ulogujKorisnika() else 67");
//            throw so.getE();
//           
//        }
//    }
//   
//     
//    public Long registrujKorisnika(String username, String password) throws Exception {
//       
//        ClientRequest kz = new ClientRequest();
//        kz.setOperation(Operations.REGISTRUJ_KORISNIKA);
//        kz.setData(new Korisnik(username, password));
//        out.writeObject(kz);
//      
//        
//        ServerResponse so = (ServerResponse) in.readObject();
//        
//        if (so.isIsSuccess() == true) {
//    
//            return (Long) so.getParameter();
//            
//        } else {
//             System.out.println("NETACNI KREDENCIJALI Server.KontrolerKlijent.ulogujKorisnika() else 67");
//            throw so.getE();
//           
//        }
//    }
//    
//      public List<GenerickiDomObj> prikaziRangListuKorisnika() throws Exception {
//       
//        ClientRequest kz = new ClientRequest();
//        kz.setOperation(Operations.PRIKAZI_RANG);
//        
//        out.writeObject(kz);
//      
//        
//        ServerResponse so = (ServerResponse) in.readObject();
//        
//        if (so.isIsSuccess() == true) {
//          
//            return (List<GenerickiDomObj>) so.getParameter();
//            
//        } else {
//             System.out.println("Ne mogu se dovuci korisnici iz baze");
//            throw so.getE();
//           
//        }
//    }
//      
//        public List<GenerickiDomObj> prikaziPartije() throws Exception {
//       
//        ClientRequest kz = new ClientRequest();
//        kz.setOperation(Operations.VRATI_SVE_PARTIJE);
//        
//        out.writeObject(kz);
//      
//        
//        ServerResponse so = (ServerResponse) in.readObject();
//        
//        if (so.isIsSuccess() == true) {
//          
//            return (List<GenerickiDomObj>) so.getParameter();
//            
//        } else {
//             System.out.println("Ne mogu se dovuci partije iz baze");
//            throw so.getE();
//           
//        }
//    }
//            
//    public Long kreirajNovuPartiju(Long igrac1, int kod) throws Exception {
//     ClientRequest kz = new ClientRequest();
//        kz.setOperation(Operations.KREIRAJ_PARTIJU);
//         kz.setData(new Partija(kod, igrac1));
//        out.writeObject(kz);
//      
//        
//        ServerResponse so = (ServerResponse) in.readObject();
//        
//        if (so.isIsSuccess() == true) {
//          
//            return (Long) so.getParameter();
//            
//        } else {
//             System.out.println("Ne moze se kreirati nova partija");
//            throw so.getE();
//           
//        }
//    }
//
//    public Long pridruziSeIgri(int kod, Long igrac2) throws Exception {
//        ClientRequest kz = new ClientRequest();
//        kz.setOperation(Operations.PRIDRUZI_SE_PARTIJI);
//        Partija part = new Partija(kod, igrac2);
//        kz.setData(part);
//        
//        out.writeObject(kz);
//      
//        
//        ServerResponse so = (ServerResponse) in.readObject();
//        
//        if (so.isIsSuccess() == true) {
//          //dodaj kontroleru kod i igraca2
//            return (Long) so.getParameter();
//            
//        } else {
//             System.out.println("Ne moze se pridruziti ovoj partiji");
//            throw so.getE();
//           
//        }
//        
//
//    }
//
//    public int zavrsiPartiju(int sifraPartije, Long idGubitnika) throws Exception {
//        ClientRequest kz = new ClientRequest();
//        kz.setOperation(Operations.ZAVRSI_PARTIJU);
//        Partija p = new Partija(0L, sifraPartije, 0L, 0L, idGubitnika);
//        kz.setData(p);
//        out.writeObject(kz);
//      
//        
//        ServerResponse so = (ServerResponse) in.readObject();
//        
//        if (so.isIsSuccess() == true) {
//          
//            return (int) so.getParameter();
//            
//        } else {
//             System.out.println("Ne moze se zavrsiti partija");
//            throw so.getE();
//           
//        }
//        
//    }

    //public void izmeniKorisnika brpobeda ili sifru(){
    
    //}
//}
//
//import java.io.*;
//import java.net.Socket;
//import java.util.List;
//import java.util.concurrent.*;

public class KontrolerKlijent {
    private static KontrolerKlijent instance;

    private Korisnik ulogovaniKor;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    private Thread listenerThread;

    private CompletableFuture<ServerResponse> pendingResponse;

    private KontrolerKlijent() {}

    public static KontrolerKlijent getInstance() {
        if (instance == null) {
            instance = new KontrolerKlijent();
        }
        return instance;
    }

    public void initStreams(Socket s) throws IOException {
        this.socket = s;
        out = new ObjectOutputStream(s.getOutputStream());
        in = new ObjectInputStream(s.getInputStream());
        startListener();
    }

    private void startListener() {
        listenerThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Object obj = in.readObject();
                    if (obj instanceof ServerResponse) {
                        ServerResponse so = (ServerResponse) obj;

                        // Ako postoji pendingResponse, znači da je ovo odgovor na naš request
                        if (pendingResponse != null) {
                            pendingResponse.complete(so);
                            pendingResponse = null;
                            System.out.println("odgovor na postojeci response" + so.getOperation());
                        } else {
                            handleAsyncResponse(so); // Asinhrona poruka (notifikacija)
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Prekinuta konekcija sa serverom.");
            }
        });
        listenerThread.start();
    }

    //AKO HOCU VISE komponenti
//    private final List<Consumer<ServerResponse>> asyncListeners = new ArrayList<>();
//
//    public void addAsyncListener(Consumer<ServerResponse> listener) {
//    asyncListeners.add(listener);
//}
private Consumer<ServerResponse> asyncListener;

public void setAsyncListener(Consumer<ServerResponse> listener) {
    this.asyncListener = listener;
}


    private void handleAsyncResponse(ServerResponse so) {
        //PROVERI OVO 
         if (asyncListener != null) {
        asyncListener.accept(so);
    }
        
        switch (so.getOperation()) {
        
            case Operations.ZAVRSI_PARTIJU:
                if (so.isIsSuccess()) {
                    System.out.println("handle async: Partija završena. Rezultat: " + so.getParameter());
                }
                break;
                 case Operations.PRIDRUZI_SE_PARTIJI:
                if (so.isIsSuccess()) {
                    System.out.println("hanle async: Pridruzi se partiji. Rezultat: " + so.getParameter());
                }
                break;
                 case Operations.ODIGRAJ_POTEZ:
                     
                      if (so.isIsSuccess()) {
                    System.out.println("hanle async: Pridruzi se partiji. Rezultat: " + so.getParameter());
                }
                     
                     break;
            default:
               // System.out.println("Async nepoznata operacija: " + so.getOperation());
        }
    }

    
    private ServerResponse sendRequest(ClientRequest req) throws Exception {
        pendingResponse = new CompletableFuture<>();
        out.writeObject(req);
        out.flush();
        return pendingResponse.get(); 
    }

    
    public Korisnik ulogujKorisnika(String username, String password) throws Exception {
        ClientRequest req = new ClientRequest();
        req.setOperation(Operations.ULOGUJ_KORISNIKA);
        req.setData(new Korisnik(username, password));

        ServerResponse so = sendRequest(req);
        if (so.isIsSuccess()) {
            ulogovaniKor = (Korisnik) so.getParameter();
            return ulogovaniKor;
        } else {
            throw so.getE();
        }
    }

    public Long registrujKorisnika(String username, String password) throws Exception {
        ClientRequest req = new ClientRequest();
        req.setOperation(Operations.REGISTRUJ_KORISNIKA);
        req.setData(new Korisnik(username, password));

        ServerResponse so = sendRequest(req);
        if (so.isIsSuccess()) {
            return (Long) so.getParameter();
        } else {
            throw so.getE();
        }
    }

    public List<GenerickiDomObj> prikaziRangListuKorisnika() throws Exception {
        ClientRequest req = new ClientRequest();
        req.setOperation(Operations.PRIKAZI_RANG);

        ServerResponse so = sendRequest(req);
        if (so.isIsSuccess()) {
            return (List<GenerickiDomObj>) so.getParameter();
        } else {
            throw so.getE();
        }
    }

    public List<GenerickiDomObj> prikaziPartije() throws Exception {
        ClientRequest req = new ClientRequest();
        req.setOperation(Operations.VRATI_SVE_PARTIJE);

        ServerResponse so = sendRequest(req);
        if (so.isIsSuccess()) {
            return (List<GenerickiDomObj>) so.getParameter();
        } else {
            throw so.getE();
        }
    }

    public Long kreirajNovuPartiju(Long igrac1, int kod) throws Exception {
        ClientRequest req = new ClientRequest();
        req.setOperation(Operations.KREIRAJ_PARTIJU);
        req.setData(new Partija(kod, igrac1));

        ServerResponse so = sendRequest(req);
        if (so.isIsSuccess()) {
            return (Long) so.getParameter();
        } else {
            throw so.getE();
        }
    }

    public Long pridruziSeIgri(int kod, Long igrac2) throws Exception {
        ClientRequest req = new ClientRequest();
        req.setOperation(Operations.PRIDRUZI_SE_PARTIJI);
        req.setData(new Partija(kod, igrac2));

        ServerResponse so = sendRequest(req);
        if (so.isIsSuccess()) {
            return (Long) so.getParameter();
        } else {
            throw so.getE();
        }
    }

    public int zavrsiPartiju(int sifraPartije, Long idGubitnika) throws Exception {
        ClientRequest req = new ClientRequest();
        req.setOperation(Operations.ZAVRSI_PARTIJU);
        req.setData(new Partija(0L, sifraPartije, 0L, 0L, idGubitnika));

        ServerResponse so = sendRequest(req);
        if (so.isIsSuccess()) {
            return (int) so.getParameter();
        } else {
            throw so.getE();
        }
    }

    public Korisnik getUlogovaniKorisnik() {
        return ulogovaniKor;
    }
}
