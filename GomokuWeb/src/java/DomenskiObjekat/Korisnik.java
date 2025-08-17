/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DomenskiObjekat;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ANDJELA
 */
public class Korisnik implements GenerickiDomObj, Serializable {
private static final long serialVersionUID = 1L;
    Long idKorisnik;
    String korisnickoIme;
    String sifra;
    int brojPobeda;

    public Korisnik() {
        this.idKorisnik = 0L;
        this.korisnickoIme = "";
        this.sifra = "";
        this.brojPobeda = 0;
        
    }

    public Korisnik(Long idKorisnik, String korisnickoIme, String sifra, int brojPobeda) {
        this.idKorisnik = idKorisnik;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
        this.brojPobeda = brojPobeda;
    }

    public Korisnik(String korisnickoIme, String sifra) {
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
        
    }
    
    public Korisnik(Long idKorisnik, String korisnickoIme, int brojPobeda) {
       this.idKorisnik = idKorisnik;
        this.korisnickoIme = korisnickoIme;
        this.brojPobeda = brojPobeda;
    }

    public Korisnik(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public Korisnik(Long idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public Long getIDKorisnik() {
        return this.idKorisnik;
    }

    public String getKorisnickoIme() {
        return this.korisnickoIme;
    }

    public String getSifra() {
        return this.sifra;
    }
    
    public int getBrojPobeda() {
        return this.brojPobeda;
    }

    public void setIDKorisnika(Long idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }
    
       public void setBrojPobeda(int brojPobeda) {
        this.brojPobeda = brojPobeda;
    }

    @Override
    public String getTableName() {
        return "Korisnici"; 
    }

        @Override
    public List<GenerickiDomObj> getList(ResultSet resultSet) throws Exception {
        List<GenerickiDomObj> list = new LinkedList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong("idKorisnik");
            String ime = resultSet.getString("korisnickoIme");
            String sifra = resultSet.getString("sifra");
            int brojPobeda = resultSet.getInt("brojPobeda");
        

            Korisnik kor = new Korisnik(id, ime, sifra, brojPobeda);

            list.add(kor);

        }
        return list;
    }

    @Override
    public GenerickiDomObj getResult(ResultSet resultSet) throws Exception {
 
        GenerickiDomObj odo = null;
        if (resultSet.next()) {
            Long id = resultSet.getLong("idKorisnik");
            String ime = resultSet.getString("korisnickoIme");
            String pass = resultSet.getString("sifra");
           
            int brojpobeda = resultSet.getInt("brojPobeda");

            odo = new Korisnik(id, ime, pass, brojpobeda);
            return odo;
        }
        return null;
    
    }
    
    @Override
    public String getAttributeNames() {
        return "korisnickoIme,sifra";
    }

    @Override
    public String getUnknownValues() {
        return "?,?";
    }

      @Override
    public void prepareStatement(PreparedStatement ps, GenerickiDomObj odo) throws Exception {
        Korisnik kor = (Korisnik) odo;
        ps.setString(1, kor.getKorisnickoIme());
        ps.setString(2, kor.getSifra());
    
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
        return "";
    }

    @Override
    public String getOrderCondition() {
        return "brojPobeda DESC";
    }

   @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Korisnik korisnik = (Korisnik) o;
    return Objects.equals(korisnickoIme, korisnik.korisnickoIme);
}

@Override
public int hashCode() {
    return Objects.hash(korisnickoIme);
}
}

