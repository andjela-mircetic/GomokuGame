/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Database;


import DomenskiObjekat.GenerickiDomObj;
import java.util.List;

/**
 *
 * @author ANDJELA
 */

public interface IDBBroker {
    List<GenerickiDomObj> vratiSve(GenerickiDomObj odo) throws Exception;
    GenerickiDomObj nadji(GenerickiDomObj odo,Long id) throws Exception;
    Long sacuvaj(GenerickiDomObj odo) throws Exception;
    void azuriraj(GenerickiDomObj odo) throws Exception;
    void obrisi(GenerickiDomObj odo) throws Exception;
    GenerickiDomObj uloguj(GenerickiDomObj odo,String username,String password) throws Exception;
    Long registruj(GenerickiDomObj odo,String username,String password) throws Exception;
    List<GenerickiDomObj> filtriraj(GenerickiDomObj odo) throws Exception;
    void sacuvajVoid(GenerickiDomObj odo) throws Exception;
}
