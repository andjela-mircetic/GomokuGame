/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;


import DomenskiObjekat.GenerickiDomObj;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


/**
 *
 * @author ANDJELA
 */
public class DatabaseBroker implements IDBBroker{
    @Override
    public List<GenerickiDomObj> vratiSve(GenerickiDomObj odo) throws Exception {
        List<GenerickiDomObj> list = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "SELECT * FROM " + odo.getTableName() + " ORDER BY " + odo.getOrderCondition();
           // System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            list = odo.getList(resultSet);
            resultSet.close();
            statement.close();
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    @Override
    public GenerickiDomObj nadji(GenerickiDomObj odo, Long id) throws Exception {
        GenerickiDomObj generalEntity = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "SELECT * FROM " + odo.getTableName() + " WHERE id=" + id;
            //System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            generalEntity = odo.getResult(resultSet);
            resultSet.close();
            statement.close();
            return generalEntity;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public Long sacuvaj(GenerickiDomObj odo) throws Exception {
        ResultSet resultSet;
        Long id = null;
        
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "INSERT INTO " + odo.getTableName() + " (" + odo.getAttributeNames() + ") VALUES(" + odo.getUnknownValues() + ")";
            //System.out.println(query);
            PreparedStatement ps = DatabaseConnection.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            odo.prepareStatement(ps, odo);
            int updatedRow = ps.executeUpdate();
            if (updatedRow == 1) {
                resultSet = ps.getGeneratedKeys();
                resultSet.next();
                id = resultSet.getLong(1);
            }
            ps.close();
            return id;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void azuriraj(GenerickiDomObj odo) throws Exception {
        try {
            String query = "UPDATE " + odo.getTableName() + " SET " + odo.getUpdateQuery() + " WHERE id=" + odo.getID(odo);
            //System.out.println(query);
            PreparedStatement ps = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            odo.prepareStatement(ps, odo);
            int updatedRow = ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            if (DatabaseConnection.getInstance().getConnection() != null) {
                DatabaseConnection.getInstance().getConnection().rollback();
            }
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void obrisi(GenerickiDomObj odo) throws Exception {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "DELETE FROM " + odo.getTableName() + " WHERE id=" + odo.getID(odo);
            Statement statement = connection.createStatement();
            //System.out.println(query);
            statement.executeUpdate(query);
            statement.close();
        } catch (Exception ex) {
            if (DatabaseConnection.getInstance().getConnection() != null) {
                DatabaseConnection.getInstance().getConnection().rollback();
            }
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public GenerickiDomObj uloguj(GenerickiDomObj odo, String username, String password) throws Exception {
        GenerickiDomObj generalEntity = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "SELECT * FROM " + odo.getTableName() + " WHERE korisnickoIme='" + username + "' AND sifra='" + password + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            generalEntity = odo.getResult(resultSet);
            resultSet.close();
            statement.close();
            return generalEntity;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public List<GenerickiDomObj> filtriraj(GenerickiDomObj odo) throws Exception {
        List<GenerickiDomObj> list = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "SELECT * FROM " + odo.getTableName() + " WHERE " + odo.getCondition(odo) + " ORDER BY " + odo.getOrderCondition();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            list = odo.getList(resultSet);
            resultSet.close();
            statement.close();
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void sacuvajVoid(GenerickiDomObj odo) throws Exception {
        ResultSet resultSet;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "INSERT INTO " + odo.getTableName() + " (" + odo.getAttributeNames() + ") VALUES(" + odo.getUnknownValues() + ")";
            //System.out.println(query);
            PreparedStatement ps = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            odo.prepareStatement(ps, odo);
            int updatedRow = ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public Long registruj(GenerickiDomObj odo, String username, String password) throws Exception {
        ResultSet resultSet;
        Long id = null;
        
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "INSERT INTO " + odo.getTableName() + " (" + odo.getAttributeNames() + ") VALUES(" + odo.getUnknownValues() + ")";
            System.out.println(query);
            PreparedStatement ps = DatabaseConnection.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            odo.prepareStatement(ps, odo);
            int updatedRow = ps.executeUpdate();
            if (updatedRow == 1) {
                resultSet = ps.getGeneratedKeys();
                resultSet.next();
                id = resultSet.getLong(1);
            }
            ps.close();
            return id;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
