/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.ws;

import co.com.konrad.interbolsa.dao.ValorAccionDAO;
import co.com.konrad.interbolsa.dao.ValorAccionDAOPK;
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
@Path("co.com.konrad.interbolsa.dao.valoracciondao")
public class ValorAccionDAOFacadeREST extends AbstractFacade<ValorAccionDAO> {

    @PersistenceContext(unitName = "interbolsaPU")
    private EntityManager em;

    private ValorAccionDAOPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;accionesAccionId=accionesAccionIdValue;valorAccionId=valorAccionIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        co.com.konrad.interbolsa.dao.ValorAccionDAOPK key = new co.com.konrad.interbolsa.dao.ValorAccionDAOPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> accionesAccionId = map.get("accionesAccionId");
        if (accionesAccionId != null && !accionesAccionId.isEmpty()) {
            key.setAccionesAccionId(new java.lang.Integer(accionesAccionId.get(0)));
        }
        java.util.List<String> valorAccionId = map.get("valorAccionId");
        if (valorAccionId != null && !valorAccionId.isEmpty()) {
            key.setValorAccionId(new java.lang.Integer(valorAccionId.get(0)));
        }
        return key;
    }

    public ValorAccionDAOFacadeREST() {
        super(ValorAccionDAO.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(ValorAccionDAO entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, ValorAccionDAO entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        co.com.konrad.interbolsa.dao.ValorAccionDAOPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ValorAccionDAO find(@PathParam("id") PathSegment id) {
        co.com.konrad.interbolsa.dao.ValorAccionDAOPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ValorAccionDAO> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ValorAccionDAO> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
