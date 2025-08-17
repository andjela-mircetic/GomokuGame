/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import DomenskiObjekat.GenerickiDomObj;
import DomenskiObjekat.Korisnik;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import konstante.Operations;
import TransferObjekat.ClientRequest;
import TransferObjekat.ServerResponse;

/**
 *
 * @author ANDJELA
 */
class Klijent extends Thread {

    private Socket soketS;
    ObjectOutputStream out;
    ObjectInputStream in;
    KontrolerServer server;
    int brojKlijenta;
    Korisnik k;
    
    public Klijent(Socket soketS1, int brojKlijenta, KontrolerServer ks) {
  
            soketS = soketS1;
            this.brojKlijenta = brojKlijenta;
            this.server = ks;

            System.out.println("Klijent:" + brojKlijenta + " je povezan!");
            start();

    }

    public void run() {
        try {

                out = new ObjectOutputStream(soketS.getOutputStream());
                in = new ObjectInputStream(soketS.getInputStream());

        while (true) {
            Object object = in.readObject();
            if (object instanceof ClientRequest) {
                ClientRequest kz = (ClientRequest) object;
                ServerResponse so = obradiZahtev(kz);
                posaljiOdgovor(soketS, so);
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
private ServerResponse obradiZahtev(ClientRequest kz) {
        int operacija = kz.getOperation();
        ServerResponse response = new ServerResponse();

        switch (operacija) {
            case Operations.ULOGUJ_KORISNIKA:
                Korisnik k = (Korisnik) kz.getData();
                 {
                    try {
                        GenerickiDomObj odo = KontrolerServer.getInstance().ulogujKorisnika(k.getKorisnickoIme(), k.getSifra());
                        if (odo != null) {
                            Korisnik kor = (Korisnik) odo;
                            boolean exists = false;
                            for (Klijent kn : server.lkl) {
                                if (kn.getKorisnik() != null && kn.getKorisnik().equals(kor)) {
                                    exists = true;
                                    break;
                                }
                            }
                            if (!exists) {
                                 this.k = (Korisnik) odo;
                                server.lkl.add(this);
                               
                                System.out.println("Korisnik: " + odo + " je pristupio/la sistemu.");
                                response.setIsSuccess(true);
                                response.setParameter(odo);
                                response.setOperation(Operations.ULOGUJ_KORISNIKA);
                            } else {
                                Exception e = new Exception("Korisnik sa tim parametrima je vec ulogovan!");
                                response.setIsSuccess(false);
                                response.setOperation(Operations.ULOGUJ_KORISNIKA);
                                response.setE(e);
                            }
                        } else {
                            response.setIsSuccess(true);
                            response.setParameter(odo);
                            response.setOperation(Operations.ULOGUJ_KORISNIKA);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
                        response.setIsSuccess(false);
                        response.setOperation(Operations.ULOGUJ_KORISNIKA);
                        response.setE(ex);
                    }
                }
                break;
            case Operations.REGISTRUJ_KORISNIKA:
                  Korisnik k2 = (Korisnik) kz.getData();
                 {
                    try {
                        Long odo = KontrolerServer.getInstance().registrujKorisnika(k2.getKorisnickoIme(), k2.getSifra());
                        if (odo != null) {
  
                                System.out.println("Korisnik sa idjem " + odo + " je registrovan");
                                response.setIsSuccess(true);
                                response.setParameter(odo);
                                response.setOperation(Operations.REGISTRUJ_KORISNIKA);

                        } else {
                            response.setIsSuccess(false);
                            response.setParameter(odo);
                            response.setOperation(Operations.REGISTRUJ_KORISNIKA);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
                        response.setIsSuccess(false);
                        response.setOperation(Operations.REGISTRUJ_KORISNIKA);
                        response.setE(ex);
                    }
                }
                
                break;
                 case Operations.PRIKAZI_RANG:
                
                 {
                    try {
                        List<GenerickiDomObj> odo = KontrolerServer.getInstance().vratiRangListu();
                        if (odo != null) {
  
                                System.out.println("Vracena lista korisnika");
                                response.setIsSuccess(true);
                                response.setParameter(odo);
                                response.setOperation(Operations.PRIKAZI_RANG);

                        } else {
                            response.setIsSuccess(false);
                            response.setParameter(odo);
                            response.setOperation(Operations.PRIKAZI_RANG);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
                        response.setIsSuccess(false);
                        response.setOperation(Operations.PRIKAZI_RANG);
                        response.setE(ex);
                    }
                }
                
                break;
                      case Operations.VRATI_SVE_PARTIJE:
                
                 {
                    try {
                        List<GenerickiDomObj> odo = KontrolerServer.getInstance().vratiSvePartije();
                        if (odo != null) {
  
                                System.out.println("Vracena lista partija");
                                response.setIsSuccess(true);
                                response.setParameter(odo);
                                response.setOperation(Operations.VRATI_SVE_PARTIJE);

                        } else {
                            response.setIsSuccess(false);
                            response.setParameter(odo);
                            response.setOperation(Operations.VRATI_SVE_PARTIJE);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
                        response.setIsSuccess(false);
                        response.setOperation(Operations.VRATI_SVE_PARTIJE);
                        response.setE(ex);
                    }
                }
                
                break;
                
            default:
                break;

        }
        System.out.println(response.getOperation());
        return response;
    }

    private void posaljiOdgovor(Socket socket, ServerResponse so) throws IOException {
        out.writeObject(so);
    }
    
    public Korisnik getKorisnik() {
        return k;
    }

}
