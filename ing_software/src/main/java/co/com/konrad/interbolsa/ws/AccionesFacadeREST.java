/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.ws;

import co.com.konrad.interbolsa.entities.Acciones;
import com.google.gson.Gson;
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

/**
 *
 * @author ADGS
 */
@Stateless
@Path("acciones")
public class AccionesFacadeREST extends AbstractFacade<Acciones> {

    @PersistenceContext(unitName = "interbolsaPU")
    private EntityManager em;

    public AccionesFacadeREST() {
        super(Acciones.class);
    }

    @POST
    @Override
    @Path("create")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String create(Acciones entity) throws Exception{
                //VAriable json
        JsonObject res = new JsonObject();
        Gson gson = new Gson();
        try {
            //retorno
            res.addProperty("codigo", 200);
            res.addProperty("mensaje", "operacion exitosa");
            super.create(entity);
            
            
            javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
            javax.persistence.criteria.Root<Acciones> rt = cq.from(Acciones.class);
            /*cq.where(
                    cb.equal(rt.get(Empresa_.empresaNit), entity.getEmpresaNit())
            );*/
            javax.persistence.TypedQuery<Acciones> q = getEntityManager().createQuery(cq);
            Acciones acciones = q.getSingleResult();
            res.add("data", gson.toJsonTree(acciones));
            
        } catch (Exception ex) {
              java.util.logging.Logger.getLogger(AbstractFacade.class.getName()).
                    log(Level.SEVERE, "Erro guardando entidad", ex);
                                     res.addProperty("codigo", 400);
            res.addProperty("mensaje", "operacion fallida");
        }
        //return res.toString();
        return "{'hola':1}";
        
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Acciones entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Acciones find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Acciones> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Acciones> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
