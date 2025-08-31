/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * REST Web Service
 *
 * @author ANDJELA
 */
@Path("generickikontrolerserverport")
public class GenerickiKontrolerServerPort {

    private Server_client.GenerickiKontrolerServer port;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenerickiKontrolerServerPort
     */
    public GenerickiKontrolerServerPort() {
        port = getPort();
    }

    /**
     * Invokes the SOAP method registrujKorisnika
     * @param arg0 resource URI parameter
     * @param arg1 resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<java.lang.Long>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("registrujkorisnika/")
    public JAXBElement<Long> getRegistrujKorisnika(@QueryParam("arg0") String arg0, @QueryParam("arg1") String arg1) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.Long result = port.registrujKorisnika(arg0, arg1);
                return new JAXBElement<java.lang.Long>(new QName("http//lang.java/", "long"), java.lang.Long.class, result);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     *
     */
    private Server_client.GenerickiKontrolerServer getPort() {
        try {
            // Call Web Service Operation
            Server_client.GenerickiKontrolerServer_Service service = new Server_client.GenerickiKontrolerServer_Service();
            Server_client.GenerickiKontrolerServer p = service.getGenerickiKontrolerServerPort();
            return p;
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }
}
