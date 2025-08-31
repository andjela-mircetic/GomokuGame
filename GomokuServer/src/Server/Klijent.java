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
import DomenskiObjekat.Operations;
import DomenskiObjekat.Partija;
import DomenskiObjekat.Potez;
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
               // posaljiOdgovor(so);
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
private ServerResponse obradiZahtev(ClientRequest kz) throws IOException {
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
                 
                 posaljiOdgovor(response);
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
                 posaljiOdgovor(response);
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
                 posaljiOdgovor(response);
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
                 posaljiOdgovor(response);
                break;
                      case Operations.KREIRAJ_PARTIJU:
                           Partija p = (Partija) kz.getData();
                 {
                    try {
                        Long odo = KontrolerServer.getInstance().kreirajNovuPartiju(p.getIdIgrac1(), p.getSifraIgre());
                        if (odo != null) {
  
                                System.out.println("partija je kreirana");
                                response.setIsSuccess(true);
                                response.setParameter(odo);
                                response.setOperation(Operations.KREIRAJ_PARTIJU);

                        } else {
                            response.setIsSuccess(false);
                            response.setParameter(odo);
                            response.setOperation(Operations.KREIRAJ_PARTIJU);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
                        response.setIsSuccess(false);
                        response.setOperation(Operations.KREIRAJ_PARTIJU);
                        response.setE(ex);
                    }
                }
                 
                  posaljiOdgovor(response);
                          break;
                          
                        case Operations.PRIDRUZI_SE_PARTIJI:
                        
                          Partija p1 = (Partija) kz.getData();
                 {
                    try {
                        Long odo = KontrolerServer.getInstance().pridruziSeIgri(p1.getIdIgrac1(), p1.getSifraIgre());
                        if (odo != null) {
  
                                System.out.println("pridruzen partiji");
                                response.setIsSuccess(true);
                                response.setParameter(odo);
                                response.setOperation(Operations.PRIDRUZI_SE_PARTIJI);

                        } else {
                            response.setIsSuccess(false);
                            response.setParameter(odo);
                            response.setOperation(Operations.PRIDRUZI_SE_PARTIJI);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
                        response.setIsSuccess(false);
                        response.setOperation(Operations.PRIDRUZI_SE_PARTIJI);
                        response.setE(ex);
                    }
                }
                 //KontrolerServer.getInstance().broadcast2(response, this);
                 
                 KontrolerServer.getInstance().broadcast(response);
                          break;  
                             case Operations.ZAVRSI_PARTIJU:
                        
                          Partija partpob = (Partija) kz.getData();
                 {
                    try {
                        int odo = KontrolerServer.getInstance().zavrsiPartiju(partpob.getSifraIgre(), partpob.getIdPobednik());
                        int odo2 = KontrolerServer.getInstance().povecajBrPobedaKorisniku(KontrolerServer.getInstance().idPobednik);
                        if (odo != 0 && odo2 != 0) {
  
                                System.out.println("zavrsena partija i dodat pobednik");
                                response.setIsSuccess(true);
                                response.setParameter(odo);
                                response.setOperation(Operations.ZAVRSI_PARTIJU);

                        } else {
                            response.setIsSuccess(false);
                            response.setParameter(odo);
                            response.setOperation(Operations.ZAVRSI_PARTIJU);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
                        response.setIsSuccess(false);
                        response.setOperation(Operations.ZAVRSI_PARTIJU);
                        response.setE(ex);
                    }
                }
                  KontrolerServer.getInstance().broadcast(response);
                          break;  
                          
                case Operations.ODIGRAJ_POTEZ:
                        int kodIgre = KontrolerServer.getInstance().kodPartije;
                          Potez potez = (Potez) kz.getData();
                 {
                    try {
                        int rezultat = KontrolerServer.getInstance().odigrajPotez(potez.idKorisnika, potez.x, potez.y);
                        if(rezultat == 3) {
                            int odo = KontrolerServer.getInstance().zavrsiPartiju(kodIgre, potez.idKorisnika);
                             int odo2 = KontrolerServer.getInstance().povecajBrPobedaKorisniku(potez.idKorisnika);
                        }
                        if (rezultat != 0) {
                            Potez prez = new Potez(potez.x, potez.y, potez.idKorisnika, rezultat);
                                System.out.println("odigran potez");
                                response.setIsSuccess(true);
                                response.setParameter(prez);
                                response.setOperation(Operations.ODIGRAJ_POTEZ);

                        } else {
                            response.setIsSuccess(false);
                            response.setParameter(rezultat);
                            response.setOperation(Operations.ODIGRAJ_POTEZ);
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
                        response.setIsSuccess(false);
                        response.setOperation(Operations.ODIGRAJ_POTEZ);
                        response.setE(ex);
                    }
                }
                  KontrolerServer.getInstance().broadcast(response);
                          break;  
                          
            default:
                break;

        }
        System.out.println(response.getOperation());
        return response;
    }

     void posaljiOdgovor(ServerResponse so) throws IOException {
        out.writeObject(so);
    }
    
    public Korisnik getKorisnik() {
        return k;
    }

}
