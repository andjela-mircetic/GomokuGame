/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SO;

import DomenskiObjekat.Korisnik;
import DomenskiObjekat.Partija;

/**
 *
 * @author ANDJELA
 */
public class DodajRezultatIgre extends so.SystemOperation{
   
    private int generalEntity;
    
     @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Partija)) {
            throw new Exception("Invalid entity parameter!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        Partija k=(Partija)entity;
        generalEntity = databaseBroker.azuriraj(k);
    }
    
    public int getResult() {
        return generalEntity;
    }

}
