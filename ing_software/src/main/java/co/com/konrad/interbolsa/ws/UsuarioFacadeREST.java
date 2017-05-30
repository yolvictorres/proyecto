/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.ws;

import co.com.konrad.interbolsa.entities.Empresa;
import co.com.konrad.interbolsa.entities.Empresa_;
import co.com.konrad.interbolsa.entities.Usuario;
import co.com.konrad.interbolsa.entities.Usuario_;
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
import com.google.gson.Gson;
/**
 *
 * @author ADGS
 */
@Stateless
@Path("usuario")
public class UsuarioFacadeREST extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "interbolsaPU")
    private EntityManager em;

    public UsuarioFacadeREST() {
        super(Usuario.class);
    }

    @POST
    @Override
    @Path("create")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String create(Usuario entity)throws Exception{
        JsonObject res = new JsonObject();
        Gson gson = new Gson();
        try {
             super.create(entity);
            //retorno
            res.addProperty("codigo", 200);
            res.addProperty("mensaje", "operacion exitosa");
            
            javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
            javax.persistence.criteria.Root<Usuario> rt = cq.from(Usuario.class);
            cq.where(
                    cb.equal(rt.get(Usuario_.usuarioCedula), entity.getUsuarioCedula())
            );
            javax.persistence.TypedQuery<Usuario> q = getEntityManager().createQuery(cq);
            Usuario usuario = q.getSingleResult();
            res.add("data", gson.toJsonTree(usuario));
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
    public void edit(@PathParam("id") Integer id, Usuario entity) {
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
    public Usuario find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
