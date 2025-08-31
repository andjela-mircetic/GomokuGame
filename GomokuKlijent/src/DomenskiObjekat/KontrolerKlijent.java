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
import Server_client.GenerickiKontrolerServer;
import Server_client.GenerickiKontrolerServer_Service;
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

private Consumer<ServerResponse> asyncListener;

public void setAsyncListener(Consumer<ServerResponse> listener) {
    this.asyncListener = listener;
}


    private void handleAsyncResponse(ServerResponse so) {
    
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

    public Potez posaljiPotez(int kodIgre, int red, int kolona) throws Exception {
        Potez p = new Potez(red, kolona, ulogovaniKor.idKorisnik);
        
        ClientRequest req = new ClientRequest();
        req.setOperation(Operations.ODIGRAJ_POTEZ);
        req.setData(p);

        ServerResponse so = sendRequest(req); //ovde stize onom ko igra potez
        if (so.isIsSuccess()) {
            return (Potez) so.getParameter();
        } else {
            throw so.getE();
        }
    }
    
    
}
