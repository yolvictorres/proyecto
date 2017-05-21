/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.dto;

import co.com.konrad.interbolsa.dao.AccionesDAO;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.konrad.interbolsa.dao.UsuarioDAO;
import java.util.ArrayList;
import java.util.Collection;
import co.com.konrad.interbolsa.dao.ValorAccionDAO;
import co.com.konrad.interbolsa.dto.exceptions.IllegalOrphanException;
import co.com.konrad.interbolsa.dto.exceptions.NonexistentEntityException;
import co.com.konrad.interbolsa.dto.exceptions.PreexistingEntityException;
import co.com.konrad.interbolsa.dto.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ADGS
 */
public class AccionesDAOJpaController implements Serializable {

    public AccionesDAOJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AccionesDAO accionesDAO) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (accionesDAO.getUsuarioDAOCollection() == null) {
            accionesDAO.setUsuarioDAOCollection(new ArrayList<UsuarioDAO>());
        }
        if (accionesDAO.getValorAccionDAOCollection() == null) {
            accionesDAO.setValorAccionDAOCollection(new ArrayList<ValorAccionDAO>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<UsuarioDAO> attachedUsuarioDAOCollection = new ArrayList<UsuarioDAO>();
            for (UsuarioDAO usuarioDAOCollectionUsuarioDAOToAttach : accionesDAO.getUsuarioDAOCollection()) {
                usuarioDAOCollectionUsuarioDAOToAttach = em.getReference(usuarioDAOCollectionUsuarioDAOToAttach.getClass(), usuarioDAOCollectionUsuarioDAOToAttach.getUsuarioDAOPK());
                attachedUsuarioDAOCollection.add(usuarioDAOCollectionUsuarioDAOToAttach);
            }
            accionesDAO.setUsuarioDAOCollection(attachedUsuarioDAOCollection);
            Collection<ValorAccionDAO> attachedValorAccionDAOCollection = new ArrayList<ValorAccionDAO>();
            for (ValorAccionDAO valorAccionDAOCollectionValorAccionDAOToAttach : accionesDAO.getValorAccionDAOCollection()) {
                valorAccionDAOCollectionValorAccionDAOToAttach = em.getReference(valorAccionDAOCollectionValorAccionDAOToAttach.getClass(), valorAccionDAOCollectionValorAccionDAOToAttach.getValorAccionDAOPK());
                attachedValorAccionDAOCollection.add(valorAccionDAOCollectionValorAccionDAOToAttach);
            }
            accionesDAO.setValorAccionDAOCollection(attachedValorAccionDAOCollection);
            em.persist(accionesDAO);
            for (UsuarioDAO usuarioDAOCollectionUsuarioDAO : accionesDAO.getUsuarioDAOCollection()) {
                AccionesDAO oldAccionesDAOOfUsuarioDAOCollectionUsuarioDAO = usuarioDAOCollectionUsuarioDAO.getAccionesDAO();
                usuarioDAOCollectionUsuarioDAO.setAccionesDAO(accionesDAO);
                usuarioDAOCollectionUsuarioDAO = em.merge(usuarioDAOCollectionUsuarioDAO);
                if (oldAccionesDAOOfUsuarioDAOCollectionUsuarioDAO != null) {
                    oldAccionesDAOOfUsuarioDAOCollectionUsuarioDAO.getUsuarioDAOCollection().remove(usuarioDAOCollectionUsuarioDAO);
                    oldAccionesDAOOfUsuarioDAOCollectionUsuarioDAO = em.merge(oldAccionesDAOOfUsuarioDAOCollectionUsuarioDAO);
                }
            }
            for (ValorAccionDAO valorAccionDAOCollectionValorAccionDAO : accionesDAO.getValorAccionDAOCollection()) {
                AccionesDAO oldAccionesDAOOfValorAccionDAOCollectionValorAccionDAO = valorAccionDAOCollectionValorAccionDAO.getAccionesDAO();
                valorAccionDAOCollectionValorAccionDAO.setAccionesDAO(accionesDAO);
                valorAccionDAOCollectionValorAccionDAO = em.merge(valorAccionDAOCollectionValorAccionDAO);
                if (oldAccionesDAOOfValorAccionDAOCollectionValorAccionDAO != null) {
                    oldAccionesDAOOfValorAccionDAOCollectionValorAccionDAO.getValorAccionDAOCollection().remove(valorAccionDAOCollectionValorAccionDAO);
                    oldAccionesDAOOfValorAccionDAOCollectionValorAccionDAO = em.merge(oldAccionesDAOOfValorAccionDAOCollectionValorAccionDAO);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findAccionesDAO(accionesDAO.getAccionId()) != null) {
                throw new PreexistingEntityException("AccionesDAO " + accionesDAO + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AccionesDAO accionesDAO) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AccionesDAO persistentAccionesDAO = em.find(AccionesDAO.class, accionesDAO.getAccionId());
            Collection<UsuarioDAO> usuarioDAOCollectionOld = persistentAccionesDAO.getUsuarioDAOCollection();
            Collection<UsuarioDAO> usuarioDAOCollectionNew = accionesDAO.getUsuarioDAOCollection();
            Collection<ValorAccionDAO> valorAccionDAOCollectionOld = persistentAccionesDAO.getValorAccionDAOCollection();
            Collection<ValorAccionDAO> valorAccionDAOCollectionNew = accionesDAO.getValorAccionDAOCollection();
            List<String> illegalOrphanMessages = null;
            for (UsuarioDAO usuarioDAOCollectionOldUsuarioDAO : usuarioDAOCollectionOld) {
                if (!usuarioDAOCollectionNew.contains(usuarioDAOCollectionOldUsuarioDAO)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioDAO " + usuarioDAOCollectionOldUsuarioDAO + " since its accionesDAO field is not nullable.");
                }
            }
            for (ValorAccionDAO valorAccionDAOCollectionOldValorAccionDAO : valorAccionDAOCollectionOld) {
                if (!valorAccionDAOCollectionNew.contains(valorAccionDAOCollectionOldValorAccionDAO)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ValorAccionDAO " + valorAccionDAOCollectionOldValorAccionDAO + " since its accionesDAO field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<UsuarioDAO> attachedUsuarioDAOCollectionNew = new ArrayList<UsuarioDAO>();
            for (UsuarioDAO usuarioDAOCollectionNewUsuarioDAOToAttach : usuarioDAOCollectionNew) {
                usuarioDAOCollectionNewUsuarioDAOToAttach = em.getReference(usuarioDAOCollectionNewUsuarioDAOToAttach.getClass(), usuarioDAOCollectionNewUsuarioDAOToAttach.getUsuarioDAOPK());
                attachedUsuarioDAOCollectionNew.add(usuarioDAOCollectionNewUsuarioDAOToAttach);
            }
            usuarioDAOCollectionNew = attachedUsuarioDAOCollectionNew;
            accionesDAO.setUsuarioDAOCollection(usuarioDAOCollectionNew);
            Collection<ValorAccionDAO> attachedValorAccionDAOCollectionNew = new ArrayList<ValorAccionDAO>();
            for (ValorAccionDAO valorAccionDAOCollectionNewValorAccionDAOToAttach : valorAccionDAOCollectionNew) {
                valorAccionDAOCollectionNewValorAccionDAOToAttach = em.getReference(valorAccionDAOCollectionNewValorAccionDAOToAttach.getClass(), valorAccionDAOCollectionNewValorAccionDAOToAttach.getValorAccionDAOPK());
                attachedValorAccionDAOCollectionNew.add(valorAccionDAOCollectionNewValorAccionDAOToAttach);
            }
            valorAccionDAOCollectionNew = attachedValorAccionDAOCollectionNew;
            accionesDAO.setValorAccionDAOCollection(valorAccionDAOCollectionNew);
            accionesDAO = em.merge(accionesDAO);
            for (UsuarioDAO usuarioDAOCollectionNewUsuarioDAO : usuarioDAOCollectionNew) {
                if (!usuarioDAOCollectionOld.contains(usuarioDAOCollectionNewUsuarioDAO)) {
                    AccionesDAO oldAccionesDAOOfUsuarioDAOCollectionNewUsuarioDAO = usuarioDAOCollectionNewUsuarioDAO.getAccionesDAO();
                    usuarioDAOCollectionNewUsuarioDAO.setAccionesDAO(accionesDAO);
                    usuarioDAOCollectionNewUsuarioDAO = em.merge(usuarioDAOCollectionNewUsuarioDAO);
                    if (oldAccionesDAOOfUsuarioDAOCollectionNewUsuarioDAO != null && !oldAccionesDAOOfUsuarioDAOCollectionNewUsuarioDAO.equals(accionesDAO)) {
                        oldAccionesDAOOfUsuarioDAOCollectionNewUsuarioDAO.getUsuarioDAOCollection().remove(usuarioDAOCollectionNewUsuarioDAO);
                        oldAccionesDAOOfUsuarioDAOCollectionNewUsuarioDAO = em.merge(oldAccionesDAOOfUsuarioDAOCollectionNewUsuarioDAO);
                    }
                }
            }
            for (ValorAccionDAO valorAccionDAOCollectionNewValorAccionDAO : valorAccionDAOCollectionNew) {
                if (!valorAccionDAOCollectionOld.contains(valorAccionDAOCollectionNewValorAccionDAO)) {
                    AccionesDAO oldAccionesDAOOfValorAccionDAOCollectionNewValorAccionDAO = valorAccionDAOCollectionNewValorAccionDAO.getAccionesDAO();
                    valorAccionDAOCollectionNewValorAccionDAO.setAccionesDAO(accionesDAO);
                    valorAccionDAOCollectionNewValorAccionDAO = em.merge(valorAccionDAOCollectionNewValorAccionDAO);
                    if (oldAccionesDAOOfValorAccionDAOCollectionNewValorAccionDAO != null && !oldAccionesDAOOfValorAccionDAOCollectionNewValorAccionDAO.equals(accionesDAO)) {
                        oldAccionesDAOOfValorAccionDAOCollectionNewValorAccionDAO.getValorAccionDAOCollection().remove(valorAccionDAOCollectionNewValorAccionDAO);
                        oldAccionesDAOOfValorAccionDAOCollectionNewValorAccionDAO = em.merge(oldAccionesDAOOfValorAccionDAOCollectionNewValorAccionDAO);
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
                Integer id = accionesDAO.getAccionId();
                if (findAccionesDAO(id) == null) {
                    throw new NonexistentEntityException("The accionesDAO with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AccionesDAO accionesDAO;
            try {
                accionesDAO = em.getReference(AccionesDAO.class, id);
                accionesDAO.getAccionId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accionesDAO with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UsuarioDAO> usuarioDAOCollectionOrphanCheck = accionesDAO.getUsuarioDAOCollection();
            for (UsuarioDAO usuarioDAOCollectionOrphanCheckUsuarioDAO : usuarioDAOCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AccionesDAO (" + accionesDAO + ") cannot be destroyed since the UsuarioDAO " + usuarioDAOCollectionOrphanCheckUsuarioDAO + " in its usuarioDAOCollection field has a non-nullable accionesDAO field.");
            }
            Collection<ValorAccionDAO> valorAccionDAOCollectionOrphanCheck = accionesDAO.getValorAccionDAOCollection();
            for (ValorAccionDAO valorAccionDAOCollectionOrphanCheckValorAccionDAO : valorAccionDAOCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AccionesDAO (" + accionesDAO + ") cannot be destroyed since the ValorAccionDAO " + valorAccionDAOCollectionOrphanCheckValorAccionDAO + " in its valorAccionDAOCollection field has a non-nullable accionesDAO field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(accionesDAO);
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

    public List<AccionesDAO> findAccionesDAOEntities() {
        return findAccionesDAOEntities(true, -1, -1);
    }

    public List<AccionesDAO> findAccionesDAOEntities(int maxResults, int firstResult) {
        return findAccionesDAOEntities(false, maxResults, firstResult);
    }

    private List<AccionesDAO> findAccionesDAOEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AccionesDAO.class));
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

    public AccionesDAO findAccionesDAO(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AccionesDAO.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccionesDAOCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AccionesDAO> rt = cq.from(AccionesDAO.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
