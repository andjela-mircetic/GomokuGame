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
import java.util.List;

/**
 *
 * @author ANDJELA
 */

public interface GenerickiDomObj extends Serializable{
    public String getTableName();
    public List<GenerickiDomObj> getList(ResultSet resultSet) throws Exception;
    public GenerickiDomObj getResult(ResultSet resultSet) throws Exception;
    public String getAttributeNames();
    public String getUnknownValues();
    public void prepareStatement(PreparedStatement ps, GenerickiDomObj entity) throws Exception;
    public String getUpdateQuery();
    public String getID(GenerickiDomObj entity);
    public String getCondition(GenerickiDomObj entity);
    public String getOrderCondition();
}
