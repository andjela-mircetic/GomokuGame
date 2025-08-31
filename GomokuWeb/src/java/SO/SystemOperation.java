/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SO;

import Database.DatabaseBroker;
import Database.DatabaseConnection;
import Database.IDBBroker;

/**
 *
 * @author ANDJELA
 */
public abstract class SystemOperation {
      protected IDBBroker databaseBroker;

    public SystemOperation() {
        this.databaseBroker = new DatabaseBroker();
    }

    public final void templateExecute(Object entity) throws Exception {
        try {
            validate(entity);
            startTransaction();
            execute(entity);
            commitTransaction();
        } catch (Exception ex) {
            rollbackTransaction();
            throw ex;
        }
    }

    protected abstract void validate(Object entity) throws Exception;

    protected abstract void execute(Object entity) throws Exception;

    private void startTransaction() throws Exception {
        DatabaseConnection.getInstance().getConnection().setAutoCommit(false);
    }

    private void commitTransaction() throws Exception {
        DatabaseConnection.getInstance().commit();
    }

    private void rollbackTransaction() throws Exception {
        DatabaseConnection.getInstance().rollback();
    }
}
