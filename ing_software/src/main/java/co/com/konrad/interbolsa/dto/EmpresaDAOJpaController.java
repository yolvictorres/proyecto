/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.dto;

import co.com.konrad.interbolsa.dao.EmpresaDAO;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.konrad.interbolsa.dao.UsuarioDAO;
import co.com.konrad.interbolsa.dto.exceptions.IllegalOrphanException;
import co.com.konrad.interbolsa.dto.exceptions.NonexistentEntityException;
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
public class EmpresaDAOJpaController implements Serializable {

    public EmpresaDAOJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmpresaDAO empresaDAO) throws RollbackFailureException, Exception {
        if (empresaDAO.getUsuarioDAOCollection() == null) {
            empresaDAO.setUsuarioDAOCollection(new ArrayList<UsuarioDAO>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<UsuarioDAO> attachedUsuarioDAOCollection = new ArrayList<UsuarioDAO>();
            for (UsuarioDAO usuarioDAOCollectionUsuarioDAOToAttach : empresaDAO.getUsuarioDAOCollection()) {
                usuarioDAOCollectionUsuarioDAOToAttach = em.getReference(usuarioDAOCollectionUsuarioDAOToAttach.getClass(), usuarioDAOCollectionUsuarioDAOToAttach.getUsuarioDAOPK());
                attachedUsuarioDAOCollection.add(usuarioDAOCollectionUsuarioDAOToAttach);
            }
            empresaDAO.setUsuarioDAOCollection(attachedUsuarioDAOCollection);
            em.persist(empresaDAO);
            for (UsuarioDAO usuarioDAOCollectionUsuarioDAO : empresaDAO.getUsuarioDAOCollection()) {
                EmpresaDAO oldEmpresaEmpresaIdOfUsuarioDAOCollectionUsuarioDAO = usuarioDAOCollectionUsuarioDAO.getEmpresaEmpresaId();
                usuarioDAOCollectionUsuarioDAO.setEmpresaEmpresaId(empresaDAO);
                usuarioDAOCollectionUsuarioDAO = em.merge(usuarioDAOCollectionUsuarioDAO);
                if (oldEmpresaEmpresaIdOfUsuarioDAOCollectionUsuarioDAO != null) {
                    oldEmpresaEmpresaIdOfUsuarioDAOCollectionUsuarioDAO.getUsuarioDAOCollection().remove(usuarioDAOCollectionUsuarioDAO);
                    oldEmpresaEmpresaIdOfUsuarioDAOCollectionUsuarioDAO = em.merge(oldEmpresaEmpresaIdOfUsuarioDAOCollectionUsuarioDAO);
                }
            }
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

    public void edit(EmpresaDAO empresaDAO) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EmpresaDAO persistentEmpresaDAO = em.find(EmpresaDAO.class, empresaDAO.getEmpresaId());
            Collection<UsuarioDAO> usuarioDAOCollectionOld = persistentEmpresaDAO.getUsuarioDAOCollection();
            Collection<UsuarioDAO> usuarioDAOCollectionNew = empresaDAO.getUsuarioDAOCollection();
            List<String> illegalOrphanMessages = null;
            for (UsuarioDAO usuarioDAOCollectionOldUsuarioDAO : usuarioDAOCollectionOld) {
                if (!usuarioDAOCollectionNew.contains(usuarioDAOCollectionOldUsuarioDAO)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioDAO " + usuarioDAOCollectionOldUsuarioDAO + " since its empresaEmpresaId field is not nullable.");
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
            empresaDAO.setUsuarioDAOCollection(usuarioDAOCollectionNew);
            empresaDAO = em.merge(empresaDAO);
            for (UsuarioDAO usuarioDAOCollectionNewUsuarioDAO : usuarioDAOCollectionNew) {
                if (!usuarioDAOCollectionOld.contains(usuarioDAOCollectionNewUsuarioDAO)) {
                    EmpresaDAO oldEmpresaEmpresaIdOfUsuarioDAOCollectionNewUsuarioDAO = usuarioDAOCollectionNewUsuarioDAO.getEmpresaEmpresaId();
                    usuarioDAOCollectionNewUsuarioDAO.setEmpresaEmpresaId(empresaDAO);
                    usuarioDAOCollectionNewUsuarioDAO = em.merge(usuarioDAOCollectionNewUsuarioDAO);
                    if (oldEmpresaEmpresaIdOfUsuarioDAOCollectionNewUsuarioDAO != null && !oldEmpresaEmpresaIdOfUsuarioDAOCollectionNewUsuarioDAO.equals(empresaDAO)) {
                        oldEmpresaEmpresaIdOfUsuarioDAOCollectionNewUsuarioDAO.getUsuarioDAOCollection().remove(usuarioDAOCollectionNewUsuarioDAO);
                        oldEmpresaEmpresaIdOfUsuarioDAOCollectionNewUsuarioDAO = em.merge(oldEmpresaEmpresaIdOfUsuarioDAOCollectionNewUsuarioDAO);
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
                Integer id = empresaDAO.getEmpresaId();
                if (findEmpresaDAO(id) == null) {
                    throw new NonexistentEntityException("The empresaDAO with id " + id + " no longer exists.");
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
            EmpresaDAO empresaDAO;
            try {
                empresaDAO = em.getReference(EmpresaDAO.class, id);
                empresaDAO.getEmpresaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresaDAO with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UsuarioDAO> usuarioDAOCollectionOrphanCheck = empresaDAO.getUsuarioDAOCollection();
            for (UsuarioDAO usuarioDAOCollectionOrphanCheckUsuarioDAO : usuarioDAOCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EmpresaDAO (" + empresaDAO + ") cannot be destroyed since the UsuarioDAO " + usuarioDAOCollectionOrphanCheckUsuarioDAO + " in its usuarioDAOCollection field has a non-nullable empresaEmpresaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empresaDAO);
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

    public List<EmpresaDAO> findEmpresaDAOEntities() {
        return findEmpresaDAOEntities(true, -1, -1);
    }

    public List<EmpresaDAO> findEmpresaDAOEntities(int maxResults, int firstResult) {
        return findEmpresaDAOEntities(false, maxResults, firstResult);
    }

    private List<EmpresaDAO> findEmpresaDAOEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmpresaDAO.class));
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

    public EmpresaDAO findEmpresaDAO(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmpresaDAO.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresaDAOCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmpresaDAO> rt = cq.from(EmpresaDAO.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
