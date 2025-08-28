/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SO;

import DomenskiObjekat.GenerickiDomObj;
import DomenskiObjekat.Korisnik;

/**
 *
 * @author ANDJELA
 */
public class IzmeniBrPobedaKorisnika extends so.SystemOperation{
   
    private int generalEntity;
    
     @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Korisnik)) {
            throw new Exception("Invalid entity parameter!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        Korisnik k=(Korisnik)entity;
        generalEntity = databaseBroker.azuriraj(k);
    }
    
    public int getResult() {
        return generalEntity;
    }

}
