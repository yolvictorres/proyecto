/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.ws;

import co.com.konrad.interbolsa.dao.UsuarioDAO;
import co.com.konrad.interbolsa.dao.UsuarioDAOPK;
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
@Path("co.com.konrad.interbolsa.dao.usuariodao")
public class UsuarioDAOFacadeREST extends AbstractFacade<UsuarioDAO> {

    @PersistenceContext(unitName = "interbolsaPU")
    private EntityManager em;

    private UsuarioDAOPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;usuarioId=usuarioIdValue;accionesAccionId=accionesAccionIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        co.com.konrad.interbolsa.dao.UsuarioDAOPK key = new co.com.konrad.interbolsa.dao.UsuarioDAOPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> usuarioId = map.get("usuarioId");
        if (usuarioId != null && !usuarioId.isEmpty()) {
            key.setUsuarioId(new java.lang.Integer(usuarioId.get(0)));
        }
        java.util.List<String> accionesAccionId = map.get("accionesAccionId");
        if (accionesAccionId != null && !accionesAccionId.isEmpty()) {
            key.setAccionesAccionId(new java.lang.Integer(accionesAccionId.get(0)));
        }
        return key;
    }

    public UsuarioDAOFacadeREST() {
        super(UsuarioDAO.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(UsuarioDAO entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, UsuarioDAO entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        co.com.konrad.interbolsa.dao.UsuarioDAOPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public UsuarioDAO find(@PathParam("id") PathSegment id) {
        co.com.konrad.interbolsa.dao.UsuarioDAOPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UsuarioDAO> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UsuarioDAO> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
