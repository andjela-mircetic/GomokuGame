/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SO;

import DomenskiObjekat.GenerickiDomObj;
import DomenskiObjekat.Korisnik;
import DomenskiObjekat.Partija;
import java.util.List;

/**
 *
 * @author ANDJELA
 */

public class VratiSvePartije extends so.SystemOperation{
    private List<GenerickiDomObj> generalEntity;
    
     @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Partija)) {
            throw new Exception("Invalid entity parameter!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
       
        generalEntity = databaseBroker.vratiSve((Partija)entity);
    }
    
    public List<GenerickiDomObj> getResult() {
        return generalEntity;
    }
}