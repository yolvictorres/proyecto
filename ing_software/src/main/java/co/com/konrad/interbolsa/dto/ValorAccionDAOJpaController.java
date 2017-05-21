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
import co.com.konrad.interbolsa.dao.CompraAccionesDAO;
import co.com.konrad.interbolsa.dao.ValorAccionDAO;
import co.com.konrad.interbolsa.dao.ValorAccionDAOPK;
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
public class ValorAccionDAOJpaController implements Serializable {

    public ValorAccionDAOJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ValorAccionDAO valorAccionDAO) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (valorAccionDAO.getValorAccionDAOPK() == null) {
            valorAccionDAO.setValorAccionDAOPK(new ValorAccionDAOPK());
        }
        if (valorAccionDAO.getCompraAccionesDAOCollection() == null) {
            valorAccionDAO.setCompraAccionesDAOCollection(new ArrayList<CompraAccionesDAO>());
        }
        valorAccionDAO.getValorAccionDAOPK().setAccionesAccionId(valorAccionDAO.getAccionesDAO().getAccionId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AccionesDAO accionesDAO = valorAccionDAO.getAccionesDAO();
            if (accionesDAO != null) {
                accionesDAO = em.getReference(accionesDAO.getClass(), accionesDAO.getAccionId());
                valorAccionDAO.setAccionesDAO(accionesDAO);
            }
            Collection<CompraAccionesDAO> attachedCompraAccionesDAOCollection = new ArrayList<CompraAccionesDAO>();
            for (CompraAccionesDAO compraAccionesDAOCollectionCompraAccionesDAOToAttach : valorAccionDAO.getCompraAccionesDAOCollection()) {
                compraAccionesDAOCollectionCompraAccionesDAOToAttach = em.getReference(compraAccionesDAOCollectionCompraAccionesDAOToAttach.getClass(), compraAccionesDAOCollectionCompraAccionesDAOToAttach.getCompraAccionesDAOPK());
                attachedCompraAccionesDAOCollection.add(compraAccionesDAOCollectionCompraAccionesDAOToAttach);
            }
            valorAccionDAO.setCompraAccionesDAOCollection(attachedCompraAccionesDAOCollection);
            em.persist(valorAccionDAO);
            if (accionesDAO != null) {
                accionesDAO.getValorAccionDAOCollection().add(valorAccionDAO);
                accionesDAO = em.merge(accionesDAO);
            }
            for (CompraAccionesDAO compraAccionesDAOCollectionCompraAccionesDAO : valorAccionDAO.getCompraAccionesDAOCollection()) {
                ValorAccionDAO oldValorAccionDAOOfCompraAccionesDAOCollectionCompraAccionesDAO = compraAccionesDAOCollectionCompraAccionesDAO.getValorAccionDAO();
                compraAccionesDAOCollectionCompraAccionesDAO.setValorAccionDAO(valorAccionDAO);
                compraAccionesDAOCollectionCompraAccionesDAO = em.merge(compraAccionesDAOCollectionCompraAccionesDAO);
                if (oldValorAccionDAOOfCompraAccionesDAOCollectionCompraAccionesDAO != null) {
                    oldValorAccionDAOOfCompraAccionesDAOCollectionCompraAccionesDAO.getCompraAccionesDAOCollection().remove(compraAccionesDAOCollectionCompraAccionesDAO);
                    oldValorAccionDAOOfCompraAccionesDAOCollectionCompraAccionesDAO = em.merge(oldValorAccionDAOOfCompraAccionesDAOCollectionCompraAccionesDAO);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findValorAccionDAO(valorAccionDAO.getValorAccionDAOPK()) != null) {
                throw new PreexistingEntityException("ValorAccionDAO " + valorAccionDAO + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ValorAccionDAO valorAccionDAO) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        valorAccionDAO.getValorAccionDAOPK().setAccionesAccionId(valorAccionDAO.getAccionesDAO().getAccionId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ValorAccionDAO persistentValorAccionDAO = em.find(ValorAccionDAO.class, valorAccionDAO.getValorAccionDAOPK());
            AccionesDAO accionesDAOOld = persistentValorAccionDAO.getAccionesDAO();
            AccionesDAO accionesDAONew = valorAccionDAO.getAccionesDAO();
            Collection<CompraAccionesDAO> compraAccionesDAOCollectionOld = persistentValorAccionDAO.getCompraAccionesDAOCollection();
            Collection<CompraAccionesDAO> compraAccionesDAOCollectionNew = valorAccionDAO.getCompraAccionesDAOCollection();
            List<String> illegalOrphanMessages = null;
            for (CompraAccionesDAO compraAccionesDAOCollectionOldCompraAccionesDAO : compraAccionesDAOCollectionOld) {
                if (!compraAccionesDAOCollectionNew.contains(compraAccionesDAOCollectionOldCompraAccionesDAO)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CompraAccionesDAO " + compraAccionesDAOCollectionOldCompraAccionesDAO + " since its valorAccionDAO field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (accionesDAONew != null) {
                accionesDAONew = em.getReference(accionesDAONew.getClass(), accionesDAONew.getAccionId());
                valorAccionDAO.setAccionesDAO(accionesDAONew);
            }
            Collection<CompraAccionesDAO> attachedCompraAccionesDAOCollectionNew = new ArrayList<CompraAccionesDAO>();
            for (CompraAccionesDAO compraAccionesDAOCollectionNewCompraAccionesDAOToAttach : compraAccionesDAOCollectionNew) {
                compraAccionesDAOCollectionNewCompraAccionesDAOToAttach = em.getReference(compraAccionesDAOCollectionNewCompraAccionesDAOToAttach.getClass(), compraAccionesDAOCollectionNewCompraAccionesDAOToAttach.getCompraAccionesDAOPK());
                attachedCompraAccionesDAOCollectionNew.add(compraAccionesDAOCollectionNewCompraAccionesDAOToAttach);
            }
            compraAccionesDAOCollectionNew = attachedCompraAccionesDAOCollectionNew;
            valorAccionDAO.setCompraAccionesDAOCollection(compraAccionesDAOCollectionNew);
            valorAccionDAO = em.merge(valorAccionDAO);
            if (accionesDAOOld != null && !accionesDAOOld.equals(accionesDAONew)) {
                accionesDAOOld.getValorAccionDAOCollection().remove(valorAccionDAO);
                accionesDAOOld = em.merge(accionesDAOOld);
            }
            if (accionesDAONew != null && !accionesDAONew.equals(accionesDAOOld)) {
                accionesDAONew.getValorAccionDAOCollection().add(valorAccionDAO);
                accionesDAONew = em.merge(accionesDAONew);
            }
            for (CompraAccionesDAO compraAccionesDAOCollectionNewCompraAccionesDAO : compraAccionesDAOCollectionNew) {
                if (!compraAccionesDAOCollectionOld.contains(compraAccionesDAOCollectionNewCompraAccionesDAO)) {
                    ValorAccionDAO oldValorAccionDAOOfCompraAccionesDAOCollectionNewCompraAccionesDAO = compraAccionesDAOCollectionNewCompraAccionesDAO.getValorAccionDAO();
                    compraAccionesDAOCollectionNewCompraAccionesDAO.setValorAccionDAO(valorAccionDAO);
                    compraAccionesDAOCollectionNewCompraAccionesDAO = em.merge(compraAccionesDAOCollectionNewCompraAccionesDAO);
                    if (oldValorAccionDAOOfCompraAccionesDAOCollectionNewCompraAccionesDAO != null && !oldValorAccionDAOOfCompraAccionesDAOCollectionNewCompraAccionesDAO.equals(valorAccionDAO)) {
                        oldValorAccionDAOOfCompraAccionesDAOCollectionNewCompraAccionesDAO.getCompraAccionesDAOCollection().remove(compraAccionesDAOCollectionNewCompraAccionesDAO);
                        oldValorAccionDAOOfCompraAccionesDAOCollectionNewCompraAccionesDAO = em.merge(oldValorAccionDAOOfCompraAccionesDAOCollectionNewCompraAccionesDAO);
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
                ValorAccionDAOPK id = valorAccionDAO.getValorAccionDAOPK();
                if (findValorAccionDAO(id) == null) {
                    throw new NonexistentEntityException("The valorAccionDAO with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ValorAccionDAOPK id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ValorAccionDAO valorAccionDAO;
            try {
                valorAccionDAO = em.getReference(ValorAccionDAO.class, id);
                valorAccionDAO.getValorAccionDAOPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The valorAccionDAO with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CompraAccionesDAO> compraAccionesDAOCollectionOrphanCheck = valorAccionDAO.getCompraAccionesDAOCollection();
            for (CompraAccionesDAO compraAccionesDAOCollectionOrphanCheckCompraAccionesDAO : compraAccionesDAOCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ValorAccionDAO (" + valorAccionDAO + ") cannot be destroyed since the CompraAccionesDAO " + compraAccionesDAOCollectionOrphanCheckCompraAccionesDAO + " in its compraAccionesDAOCollection field has a non-nullable valorAccionDAO field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            AccionesDAO accionesDAO = valorAccionDAO.getAccionesDAO();
            if (accionesDAO != null) {
                accionesDAO.getValorAccionDAOCollection().remove(valorAccionDAO);
                accionesDAO = em.merge(accionesDAO);
            }
            em.remove(valorAccionDAO);
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

    public List<ValorAccionDAO> findValorAccionDAOEntities() {
        return findValorAccionDAOEntities(true, -1, -1);
    }

    public List<ValorAccionDAO> findValorAccionDAOEntities(int maxResults, int firstResult) {
        return findValorAccionDAOEntities(false, maxResults, firstResult);
    }

    private List<ValorAccionDAO> findValorAccionDAOEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ValorAccionDAO.class));
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

    public ValorAccionDAO findValorAccionDAO(ValorAccionDAOPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ValorAccionDAO.class, id);
        } finally {
            em.close();
        }
    }

    public int getValorAccionDAOCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ValorAccionDAO> rt = cq.from(ValorAccionDAO.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
