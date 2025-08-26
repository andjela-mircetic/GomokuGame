/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomenskiObjekat;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ANDJELA
 */
public class Partija  implements GenerickiDomObj, Serializable {
    private static final long serialVersionUID = 1L;
    Long idPartija;
    int sifraIgre;
    Long idIgrac1;
    Long idIgrac2;
     Long idPobednik;
     
      public Partija() {
        this.idPartija = 0L;
        this.sifraIgre = 0;
        this.idIgrac1 =  0L;
           this.idIgrac2 =  0L;
           this.idPobednik =  0L;
       
        
    }

    public Partija(Long idPartija, int sifraIgre, Long idIgrac1, Long idIgrac2, Long idPobednik) {
              this.idPartija = idPartija;
        this.sifraIgre = sifraIgre;
        this.idIgrac1 =  idIgrac1;
           this.idIgrac2 =  idIgrac2;
           this.idPobednik =  idPobednik;
       
    }

 public Partija(int sifraIgre, Long idIgrac1) {
             
        this.sifraIgre = sifraIgre;
        this.idIgrac1 =  idIgrac1;
        
       
    }
 
 public Partija(int sifraIgre, Long idIgrac2, Long idIgrac1) {
             
        this.sifraIgre = sifraIgre;
        this.idIgrac1 =  idIgrac1;
        this.idIgrac2 = idIgrac2;
       
    }
 
  public Partija(Long idIgrac2) {
             
        this.sifraIgre = 0;
        this.idIgrac2 =  idIgrac2;
        
       
    }

    public Long getIDPartija() {
        return idPartija;
    }

    public void setIdPartija(Long idPartija) {
        this.idPartija = idPartija;
    }

    public int getSifraIgre() {
        return sifraIgre;
    }

    public void setSifraIgre(int sifraIgre) {
        this.sifraIgre = sifraIgre;
    }

    public Long getIdIgrac1() {
        return idIgrac1;
    }

    public void setIdIgrac1(Long idIgrac1) {
        this.idIgrac1 = idIgrac1;
    }

    public Long getIdIgrac2() {
        return idIgrac2;
    }

    public void setIdIgrac2(Long idIgrac2) {
        this.idIgrac2 = idIgrac2;
    }

    public Long getIdPobednik() {
        return idPobednik;
    }

    public void setIdPobednik(Long idPobednik) {
        this.idPobednik = idPobednik;
    }

 
 
    @Override
    public String getTableName() {
        return "Partije"; //To change body of generated methods, choose Tools | Templates.
    }


         @Override
    public List<GenerickiDomObj> getList(ResultSet resultSet) throws Exception {
        List<GenerickiDomObj> list = new LinkedList<>();
        while (resultSet.next()) {
           
          Long idPartija= resultSet.getLong("idPartija");
    int sifraIgre= resultSet.getInt("sifraIgre");
    Long idIgrac1= resultSet.getLong("idIgrac1");
    Long idIgrac2= resultSet.getLong("idIgrac2");
     Long idPobednik= resultSet.getLong("idPobednik");

           Partija p = new Partija(idPartija, sifraIgre, idIgrac1, idIgrac2, idPobednik);

            list.add(p);

        }
        return list;
    }

   
  @Override
    public GenerickiDomObj getResult(ResultSet resultSet) throws Exception {
 
        GenerickiDomObj odo = null;
        if (resultSet.next()) {
              Long idPartija= resultSet.getLong("idPartija");
    int sifraIgre= resultSet.getInt("sifraIgre");
    Long idIgrac1= resultSet.getLong("idIgrac1");
    Long idIgrac2= resultSet.getLong("idIgrac2");
     Long idPobednik= resultSet.getLong("idPobednik");

           odo = new Partija(idPartija, sifraIgre, idIgrac1, idIgrac2, idPobednik);
           
            return odo;
        }
        return null;
    
    }
    @Override
    public String getAttributeNames() {
        return "sifraIgre,idIgrac1"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUnknownValues() {
        return "?,?"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void prepareStatement(PreparedStatement ps, GenerickiDomObj entity) throws Exception {
            Partija p = (Partija) entity;
       // ps.setLong(1, p.getIdPartija());
        ps.setInt(1, p.getSifraIgre());
        ps.setObject(2, p.getIdIgrac1(), java.sql.Types.BIGINT);
      //  ps.setLong(2, p.getIdIgrac1());
       // ps.setLong(3, p.getIdIgrac2());
       // ps.setLong(4, p.getIdPobednik());
    }

    @Override
    public String getUpdateQuery() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getID(GenerickiDomObj entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCondition(GenerickiDomObj entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getOrderCondition() {
        return "idPartija DESC"; //To change body of generated methods, choose Tools | Templates.
    }
     
     

}
