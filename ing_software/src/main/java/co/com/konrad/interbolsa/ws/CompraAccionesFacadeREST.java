/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.ws;

import co.com.konrad.interbolsa.entities.CompraAcciones;
import co.com.konrad.interbolsa.entities.CompraAccionesPK;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author ADGS
 */
@Stateless
@Path("co.com.konrad.interbolsa.entities.compraacciones")
public class CompraAccionesFacadeREST extends AbstractFacade<CompraAcciones> {

    @PersistenceContext(unitName = "interbolsaPU")
    private EntityManager em;

    private CompraAccionesPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;compraAccionesId=compraAccionesIdValue;valorAccionValorAccionId=valorAccionValorAccionIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        co.com.konrad.interbolsa.entities.CompraAccionesPK key = new co.com.konrad.interbolsa.entities.CompraAccionesPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> compraAccionesId = map.get("compraAccionesId");
        if (compraAccionesId != null && !compraAccionesId.isEmpty()) {
            key.setCompraAccionesId(new java.lang.Integer(compraAccionesId.get(0)));
        }
        java.util.List<String> valorAccionValorAccionId = map.get("valorAccionValorAccionId");
        if (valorAccionValorAccionId != null && !valorAccionValorAccionId.isEmpty()) {
            key.setValorAccionValorAccionId(new java.lang.Integer(valorAccionValorAccionId.get(0)));
        }
        return key;
    }

    public CompraAccionesFacadeREST() {
        super(CompraAcciones.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String create(CompraAcciones entity)throws Exception{
       //VAriable json
        JsonObject res = new JsonObject();
        try {
            //retorno
            res.addProperty("codigo", 200);
            res.addProperty("mensaje", "operacion exitosa");
            super.create(entity);
        } catch (Throwable ex) {
              java.util.logging.Logger.getLogger(AbstractFacade.class.getName()).
                    log(Level.SEVERE, "Erro guardando entidad", ex);
                                     res.addProperty("codigo", 400);
            res.addProperty("mensaje", "operacion fallida");
        }
        return res.toString();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, CompraAcciones entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        co.com.konrad.interbolsa.entities.CompraAccionesPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CompraAcciones find(@PathParam("id") PathSegment id) {
        co.com.konrad.interbolsa.entities.CompraAccionesPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CompraAcciones> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CompraAcciones> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
