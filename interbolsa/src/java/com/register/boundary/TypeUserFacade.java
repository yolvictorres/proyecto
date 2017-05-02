/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.register.boundary;

import com.register.entities.TypeUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Home
 */
@Stateless
public class TypeUserFacade extends AbstractFacade<TypeUser> {

    @PersistenceContext(unitName = "interbolsaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypeUserFacade() {
        super(TypeUser.class);
    }
    
}
