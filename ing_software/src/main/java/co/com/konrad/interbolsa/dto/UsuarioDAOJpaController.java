/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.dto;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.konrad.interbolsa.dao.AccionesDAO;
import co.com.konrad.interbolsa.dao.EmpresaDAO;
import co.com.konrad.interbolsa.dao.CompraAccionesDAO;
import co.com.konrad.interbolsa.dao.UsuarioDAO;
import co.com.konrad.interbolsa.dao.UsuarioDAOPK;
import co.com.konrad.interbolsa.dto.exceptions.IllegalOrphanException;
import co.com.konrad.interbolsa.dto.exceptions.NonexistentEntityException;
import co.com.konrad.interbolsa.dto.exceptions.PreexistingEntityException;
import co.com.konrad.interbolsa.dto.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ADGS
 */
public class UsuarioDAOJpaController implements Serializable {

    public UsuarioDAOJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioDAO usuarioDAO) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (usuarioDAO.getUsuarioDAOPK() == null) {
            usuarioDAO.setUsuarioDAOPK(new UsuarioDAOPK());
        }
        if (usuarioDAO.getCompraAccionesDAOCollection() == null) {
            usuarioDAO.setCompraAccionesDAOCollection(new ArrayList<CompraAccionesDAO>());
        }
        usuarioDAO.getUsuarioDAOPK().setAccionesAccionId(usuarioDAO.getAccionesDAO().getAccionId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AccionesDAO accionesDAO = usuarioDAO.getAccionesDAO();
            if (accionesDAO != null) {
                accionesDAO = em.getReference(accionesDAO.getClass(), accionesDAO.getAccionId());
                usuarioDAO.setAccionesDAO(accionesDAO);
            }
            EmpresaDAO empresaEmpresaId = usuarioDAO.getEmpresaEmpresaId();
            if (empresaEmpresaId != null) {
                empresaEmpresaId = em.getReference(empresaEmpresaId.getClass(), empresaEmpresaId.getEmpresaId());
                usuarioDAO.setEmpresaEmpresaId(empresaEmpresaId);
            }
            Collection<CompraAccionesDAO> attachedCompraAccionesDAOCollection = new ArrayList<CompraAccionesDAO>();
            for (CompraAccionesDAO compraAccionesDAOCollectionCompraAccionesDAOToAttach : usuarioDAO.getCompraAccionesDAOCollection()) {
                compraAccionesDAOCollectionCompraAccionesDAOToAttach = em.getReference(compraAccionesDAOCollectionCompraAccionesDAOToAttach.getClass(), compraAccionesDAOCollectionCompraAccionesDAOToAttach.getCompraAccionesDAOPK());
                attachedCompraAccionesDAOCollection.add(compraAccionesDAOCollectionCompraAccionesDAOToAttach);
            }
            usuarioDAO.setCompraAccionesDAOCollection(attachedCompraAccionesDAOCollection);
            em.persist(usuarioDAO);
            if (accionesDAO != null) {
                accionesDAO.getUsuarioDAOCollection().add(usuarioDAO);
                accionesDAO = em.merge(accionesDAO);
            }
            if (empresaEmpresaId != null) {
                empresaEmpresaId.getUsuarioDAOCollection().add(usuarioDAO);
                empresaEmpresaId = em.merge(empresaEmpresaId);
            }
            for (CompraAccionesDAO compraAccionesDAOCollectionCompraAccionesDAO : usuarioDAO.getCompraAccionesDAOCollection()) {
                UsuarioDAO oldUsuarioDAOOfCompraAccionesDAOCollectionCompraAccionesDAO = compraAccionesDAOCollectionCompraAccionesDAO.getUsuarioDAO();
                compraAccionesDAOCollectionCompraAccionesDAO.setUsuarioDAO(usuarioDAO);
                compraAccionesDAOCollectionCompraAccionesDAO = em.merge(compraAccionesDAOCollectionCompraAccionesDAO);
                if (oldUsuarioDAOOfCompraAccionesDAOCollectionCompraAccionesDAO != null) {
                    oldUsuarioDAOOfCompraAccionesDAOCollectionCompraAccionesDAO.getCompraAccionesDAOCollection().remove(compraAccionesDAOCollectionCompraAccionesDAO);
                    oldUsuarioDAOOfCompraAccionesDAOCollectionCompraAccionesDAO = em.merge(oldUsuarioDAOOfCompraAccionesDAOCollectionCompraAccionesDAO);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsuarioDAO(usuarioDAO.getUsuarioDAOPK()) != null) {
                throw new PreexistingEntityException("UsuarioDAO " + usuarioDAO + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioDAO usuarioDAO) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        usuarioDAO.getUsuarioDAOPK().setAccionesAccionId(usuarioDAO.getAccionesDAO().getAccionId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuarioDAO persistentUsuarioDAO = em.find(UsuarioDAO.class, usuarioDAO.getUsuarioDAOPK());
            AccionesDAO accionesDAOOld = persistentUsuarioDAO.getAccionesDAO();
            AccionesDAO accionesDAONew = usuarioDAO.getAccionesDAO();
            EmpresaDAO empresaEmpresaIdOld = persistentUsuarioDAO.getEmpresaEmpresaId();
            EmpresaDAO empresaEmpresaIdNew = usuarioDAO.getEmpresaEmpresaId();
            Collection<CompraAccionesDAO> compraAccionesDAOCollectionOld = persistentUsuarioDAO.getCompraAccionesDAOCollection();
            Collection<CompraAccionesDAO> compraAccionesDAOCollectionNew = usuarioDAO.getCompraAccionesDAOCollection();
            List<String> illegalOrphanMessages = null;
            for (CompraAccionesDAO compraAccionesDAOCollectionOldCompraAccionesDAO : compraAccionesDAOCollectionOld) {
                if (!compraAccionesDAOCollectionNew.contains(compraAccionesDAOCollectionOldCompraAccionesDAO)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CompraAccionesDAO " + compraAccionesDAOCollectionOldCompraAccionesDAO + " since its usuarioDAO field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (accionesDAONew != null) {
                accionesDAONew = em.getReference(accionesDAONew.getClass(), accionesDAONew.getAccionId());
                usuarioDAO.setAccionesDAO(accionesDAONew);
            }
            if (empresaEmpresaIdNew != null) {
                empresaEmpresaIdNew = em.getReference(empresaEmpresaIdNew.getClass(), empresaEmpresaIdNew.getEmpresaId());
                usuarioDAO.setEmpresaEmpresaId(empresaEmpresaIdNew);
            }
            Collection<CompraAccionesDAO> attachedCompraAccionesDAOCollectionNew = new ArrayList<CompraAccionesDAO>();
            for (CompraAccionesDAO compraAccionesDAOCollectionNewCompraAccionesDAOToAttach : compraAccionesDAOCollectionNew) {
                compraAccionesDAOCollectionNewCompraAccionesDAOToAttach = em.getReference(compraAccionesDAOCollectionNewCompraAccionesDAOToAttach.getClass(), compraAccionesDAOCollectionNewCompraAccionesDAOToAttach.getCompraAccionesDAOPK());
                attachedCompraAccionesDAOCollectionNew.add(compraAccionesDAOCollectionNewCompraAccionesDAOToAttach);
            }
            compraAccionesDAOCollectionNew = attachedCompraAccionesDAOCollectionNew;
            usuarioDAO.setCompraAccionesDAOCollection(compraAccionesDAOCollectionNew);
            usuarioDAO = em.merge(usuarioDAO);
            if (accionesDAOOld != null && !accionesDAOOld.equals(accionesDAONew)) {
                accionesDAOOld.getUsuarioDAOCollection().remove(usuarioDAO);
                accionesDAOOld = em.merge(accionesDAOOld);
            }
            if (accionesDAONew != null && !accionesDAONew.equals(accionesDAOOld)) {
                accionesDAONew.getUsuarioDAOCollection().add(usuarioDAO);
                accionesDAONew = em.merge(accionesDAONew);
            }
            if (empresaEmpresaIdOld != null && !empresaEmpresaIdOld.equals(empresaEmpresaIdNew)) {
                empresaEmpresaIdOld.getUsuarioDAOCollection().remove(usuarioDAO);
                empresaEmpresaIdOld = em.merge(empresaEmpresaIdOld);
            }
            if (empresaEmpresaIdNew != null && !empresaEmpresaIdNew.equals(empresaEmpresaIdOld)) {
                empresaEmpresaIdNew.getUsuarioDAOCollection().add(usuarioDAO);
                empresaEmpresaIdNew = em.merge(empresaEmpresaIdNew);
            }
            for (CompraAccionesDAO compraAccionesDAOCollectionNewCompraAccionesDAO : compraAccionesDAOCollectionNew) {
                if (!compraAccionesDAOCollectionOld.contains(compraAccionesDAOCollectionNewCompraAccionesDAO)) {
                    UsuarioDAO oldUsuarioDAOOfCompraAccionesDAOCollectionNewCompraAccionesDAO = compraAccionesDAOCollectionNewCompraAccionesDAO.getUsuarioDAO();
                    compraAccionesDAOCollectionNewCompraAccionesDAO.setUsuarioDAO(usuarioDAO);
                    compraAccionesDAOCollectionNewCompraAccionesDAO = em.merge(compraAccionesDAOCollectionNewCompraAccionesDAO);
                    if (oldUsuarioDAOOfCompraAccionesDAOCollectionNewCompraAccionesDAO != null && !oldUsuarioDAOOfCompraAccionesDAOCollectionNewCompraAccionesDAO.equals(usuarioDAO)) {
                        oldUsuarioDAOOfCompraAccionesDAOCollectionNewCompraAccionesDAO.getCompraAccionesDAOCollection().remove(compraAccionesDAOCollectionNewCompraAccionesDAO);
                        oldUsuarioDAOOfCompraAccionesDAOCollectionNewCompraAccionesDAO = em.merge(oldUsuarioDAOOfCompraAccionesDAOCollectionNewCompraAccionesDAO);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                UsuarioDAOPK id = usuarioDAO.getUsuarioDAOPK();
                if (findUsuarioDAO(id) == null) {
                    throw new NonexistentEntityException("The usuarioDAO with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(UsuarioDAOPK id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuarioDAO usuarioDAO;
            try {
                usuarioDAO = em.getReference(UsuarioDAO.class, id);
                usuarioDAO.getUsuarioDAOPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioDAO with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CompraAccionesDAO> compraAccionesDAOCollectionOrphanCheck = usuarioDAO.getCompraAccionesDAOCollection();
            for (CompraAccionesDAO compraAccionesDAOCollectionOrphanCheckCompraAccionesDAO : compraAccionesDAOCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UsuarioDAO (" + usuarioDAO + ") cannot be destroyed since the CompraAccionesDAO " + compraAccionesDAOCollectionOrphanCheckCompraAccionesDAO + " in its compraAccionesDAOCollection field has a non-nullable usuarioDAO field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            AccionesDAO accionesDAO = usuarioDAO.getAccionesDAO();
            if (accionesDAO != null) {
                accionesDAO.getUsuarioDAOCollection().remove(usuarioDAO);
                accionesDAO = em.merge(accionesDAO);
            }
            EmpresaDAO empresaEmpresaId = usuarioDAO.getEmpresaEmpresaId();
            if (empresaEmpresaId != null) {
                empresaEmpresaId.getUsuarioDAOCollection().remove(usuarioDAO);
                empresaEmpresaId = em.merge(empresaEmpresaId);
            }
            em.remove(usuarioDAO);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuarioDAO> findUsuarioDAOEntities() {
        return findUsuarioDAOEntities(true, -1, -1);
    }

    public List<UsuarioDAO> findUsuarioDAOEntities(int maxResults, int firstResult) {
        return findUsuarioDAOEntities(false, maxResults, firstResult);
    }

    private List<UsuarioDAO> findUsuarioDAOEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioDAO.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public UsuarioDAO findUsuarioDAO(UsuarioDAOPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioDAO.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioDAOCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioDAO> rt = cq.from(UsuarioDAO.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
