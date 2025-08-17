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
public class ServerResponse implements Serializable{
    private int operation;
    private Object parameter;
    private boolean isSuccess;
    private Exception e;

    public ServerResponse() {
    }

    public ServerResponse(int operacija, Object parametar, boolean uspesno, Exception greska) {
        this.operation = operacija;
        this.parameter = parametar;
        this.isSuccess = uspesno;
        this.e = greska;
    }

    public Object getParameter() {
        return parameter;
    }


    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }


    public boolean isIsSuccess() {
        return isSuccess;
    }


    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }


    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }


    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }
    
}
