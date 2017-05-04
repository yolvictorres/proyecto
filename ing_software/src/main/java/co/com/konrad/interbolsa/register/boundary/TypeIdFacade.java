/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.register.boundary;

import com.register.entities.TypeId;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Home
 */
@Stateless
public class TypeIdFacade extends AbstractFacade<TypeId> {

    @PersistenceContext(unitName = "interbolsaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypeIdFacade() {
        super(TypeId.class);
    }
    
}
