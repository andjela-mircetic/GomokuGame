/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TransferObjekat;

import java.io.Serializable;

/**
 *
 * @author ANDJELA
 */
public class ClientRequest implements Serializable{
    private int operation;
    private Object data;

    public ClientRequest() {
    }

    public ClientRequest(int operation, Object data) {
        this.operation = operation;
        this.data = data;
    }
    
    public int getOperation() {
        return operation;
    }


    public void setOperation(int operation) {
        this.operation = operation;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
}
