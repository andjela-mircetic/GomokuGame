/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import DomenskiObjekat.Korisnik;
import SO.RegistrujKorisnika;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import SO.SystemOperation;

/**
 *
 * @author ANDJELA
 */
@WebService(serviceName = "GenerickiKontrolerServer")
public class GenerickiKontrolerServer {

    public Long registrujKorisnika(String username, String password) throws Exception {

        Korisnik kor = new Korisnik();
        kor.setKorisnickoIme(username);
        kor.setSifra(password);
       
        SystemOperation so = new RegistrujKorisnika();
        
        so.templateExecute(kor);
        return ((RegistrujKorisnika) so).getResult();
    }
}
