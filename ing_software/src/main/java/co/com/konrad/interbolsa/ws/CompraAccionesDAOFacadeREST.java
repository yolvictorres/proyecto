/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.ws;

import co.com.konrad.interbolsa.dao.CompraAccionesDAO;
import co.com.konrad.interbolsa.dao.CompraAccionesDAOPK;
import java.util.List;
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
@Path("co.com.konrad.interbolsa.dao.compraaccionesdao")
public class CompraAccionesDAOFacadeREST extends AbstractFacade<CompraAccionesDAO> {

    @PersistenceContext(unitName = "interbolsaPU")
    private EntityManager em;

    private CompraAccionesDAOPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;compraAccionesId=compraAccionesIdValue;valorAccionId=valorAccionIdValue;usuarioId=usuarioIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        co.com.konrad.interbolsa.dao.CompraAccionesDAOPK key = new co.com.konrad.interbolsa.dao.CompraAccionesDAOPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> compraAccionesId = map.get("compraAccionesId");
        if (compraAccionesId != null && !compraAccionesId.isEmpty()) {
            key.setCompraAccionesId(new java.lang.Integer(compraAccionesId.get(0)));
        }
        java.util.List<String> valorAccionId = map.get("valorAccionId");
        if (valorAccionId != null && !valorAccionId.isEmpty()) {
            key.setValorAccionId(new java.lang.Integer(valorAccionId.get(0)));
        }
        java.util.List<String> usuarioId = map.get("usuarioId");
        if (usuarioId != null && !usuarioId.isEmpty()) {
            key.setUsuarioId(new java.lang.Integer(usuarioId.get(0)));
        }
        return key;
    }

    public CompraAccionesDAOFacadeREST() {
        super(CompraAccionesDAO.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(CompraAccionesDAO entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, CompraAccionesDAO entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        co.com.konrad.interbolsa.dao.CompraAccionesDAOPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CompraAccionesDAO find(@PathParam("id") PathSegment id) {
        co.com.konrad.interbolsa.dao.CompraAccionesDAOPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CompraAccionesDAO> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CompraAccionesDAO> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
