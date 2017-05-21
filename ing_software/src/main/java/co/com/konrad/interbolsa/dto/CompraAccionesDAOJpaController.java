/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.dto;

import co.com.konrad.interbolsa.dao.CompraAccionesDAO;
import co.com.konrad.interbolsa.dao.CompraAccionesDAOPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.konrad.interbolsa.dao.UsuarioDAO;
import co.com.konrad.interbolsa.dao.ValorAccionDAO;
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
public class CompraAccionesDAOJpaController implements Serializable {

    public CompraAccionesDAOJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CompraAccionesDAO compraAccionesDAO) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (compraAccionesDAO.getCompraAccionesDAOPK() == null) {
            compraAccionesDAO.setCompraAccionesDAOPK(new CompraAccionesDAOPK());
        }
        compraAccionesDAO.getCompraAccionesDAOPK().setUsuarioId(compraAccionesDAO.getUsuarioDAO().getUsuarioDAOPK().getUsuarioId());
        compraAccionesDAO.getCompraAccionesDAOPK().setValorAccionId(compraAccionesDAO.getValorAccionDAO().getValorAccionDAOPK().getValorAccionId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuarioDAO usuarioDAO = compraAccionesDAO.getUsuarioDAO();
            if (usuarioDAO != null) {
                usuarioDAO = em.getReference(usuarioDAO.getClass(), usuarioDAO.getUsuarioDAOPK());
                compraAccionesDAO.setUsuarioDAO(usuarioDAO);
            }
            ValorAccionDAO valorAccionDAO = compraAccionesDAO.getValorAccionDAO();
            if (valorAccionDAO != null) {
                valorAccionDAO = em.getReference(valorAccionDAO.getClass(), valorAccionDAO.getValorAccionDAOPK());
                compraAccionesDAO.setValorAccionDAO(valorAccionDAO);
            }
            em.persist(compraAccionesDAO);
            if (usuarioDAO != null) {
                usuarioDAO.getCompraAccionesDAOCollection().add(compraAccionesDAO);
                usuarioDAO = em.merge(usuarioDAO);
            }
            if (valorAccionDAO != null) {
                valorAccionDAO.getCompraAccionesDAOCollection().add(compraAccionesDAO);
                valorAccionDAO = em.merge(valorAccionDAO);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCompraAccionesDAO(compraAccionesDAO.getCompraAccionesDAOPK()) != null) {
                throw new PreexistingEntityException("CompraAccionesDAO " + compraAccionesDAO + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CompraAccionesDAO compraAccionesDAO) throws NonexistentEntityException, RollbackFailureException, Exception {
        compraAccionesDAO.getCompraAccionesDAOPK().setUsuarioId(compraAccionesDAO.getUsuarioDAO().getUsuarioDAOPK().getUsuarioId());
        compraAccionesDAO.getCompraAccionesDAOPK().setValorAccionId(compraAccionesDAO.getValorAccionDAO().getValorAccionDAOPK().getValorAccionId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CompraAccionesDAO persistentCompraAccionesDAO = em.find(CompraAccionesDAO.class, compraAccionesDAO.getCompraAccionesDAOPK());
            UsuarioDAO usuarioDAOOld = persistentCompraAccionesDAO.getUsuarioDAO();
            UsuarioDAO usuarioDAONew = compraAccionesDAO.getUsuarioDAO();
            ValorAccionDAO valorAccionDAOOld = persistentCompraAccionesDAO.getValorAccionDAO();
            ValorAccionDAO valorAccionDAONew = compraAccionesDAO.getValorAccionDAO();
            if (usuarioDAONew != null) {
                usuarioDAONew = em.getReference(usuarioDAONew.getClass(), usuarioDAONew.getUsuarioDAOPK());
                compraAccionesDAO.setUsuarioDAO(usuarioDAONew);
            }
            if (valorAccionDAONew != null) {
                valorAccionDAONew = em.getReference(valorAccionDAONew.getClass(), valorAccionDAONew.getValorAccionDAOPK());
                compraAccionesDAO.setValorAccionDAO(valorAccionDAONew);
            }
            compraAccionesDAO = em.merge(compraAccionesDAO);
            if (usuarioDAOOld != null && !usuarioDAOOld.equals(usuarioDAONew)) {
                usuarioDAOOld.getCompraAccionesDAOCollection().remove(compraAccionesDAO);
                usuarioDAOOld = em.merge(usuarioDAOOld);
            }
            if (usuarioDAONew != null && !usuarioDAONew.equals(usuarioDAOOld)) {
                usuarioDAONew.getCompraAccionesDAOCollection().add(compraAccionesDAO);
                usuarioDAONew = em.merge(usuarioDAONew);
            }
            if (valorAccionDAOOld != null && !valorAccionDAOOld.equals(valorAccionDAONew)) {
                valorAccionDAOOld.getCompraAccionesDAOCollection().remove(compraAccionesDAO);
                valorAccionDAOOld = em.merge(valorAccionDAOOld);
            }
            if (valorAccionDAONew != null && !valorAccionDAONew.equals(valorAccionDAOOld)) {
                valorAccionDAONew.getCompraAccionesDAOCollection().add(compraAccionesDAO);
                valorAccionDAONew = em.merge(valorAccionDAONew);
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
                CompraAccionesDAOPK id = compraAccionesDAO.getCompraAccionesDAOPK();
                if (findCompraAccionesDAO(id) == null) {
                    throw new NonexistentEntityException("The compraAccionesDAO with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CompraAccionesDAOPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CompraAccionesDAO compraAccionesDAO;
            try {
                compraAccionesDAO = em.getReference(CompraAccionesDAO.class, id);
                compraAccionesDAO.getCompraAccionesDAOPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compraAccionesDAO with id " + id + " no longer exists.", enfe);
            }
            UsuarioDAO usuarioDAO = compraAccionesDAO.getUsuarioDAO();
            if (usuarioDAO != null) {
                usuarioDAO.getCompraAccionesDAOCollection().remove(compraAccionesDAO);
                usuarioDAO = em.merge(usuarioDAO);
            }
            ValorAccionDAO valorAccionDAO = compraAccionesDAO.getValorAccionDAO();
            if (valorAccionDAO != null) {
                valorAccionDAO.getCompraAccionesDAOCollection().remove(compraAccionesDAO);
                valorAccionDAO = em.merge(valorAccionDAO);
            }
            em.remove(compraAccionesDAO);
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

    public List<CompraAccionesDAO> findCompraAccionesDAOEntities() {
        return findCompraAccionesDAOEntities(true, -1, -1);
    }

    public List<CompraAccionesDAO> findCompraAccionesDAOEntities(int maxResults, int firstResult) {
        return findCompraAccionesDAOEntities(false, maxResults, firstResult);
    }

    private List<CompraAccionesDAO> findCompraAccionesDAOEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CompraAccionesDAO.class));
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

    public CompraAccionesDAO findCompraAccionesDAO(CompraAccionesDAOPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CompraAccionesDAO.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompraAccionesDAOCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CompraAccionesDAO> rt = cq.from(CompraAccionesDAO.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
