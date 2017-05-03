/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.register.presentation;

import co.com.konrad.interbolsa.register.boundary.TypeUserFacade;
import com.register.entities.TypeUser;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Home
 */
@Named(value = "TypeUserView")
@RequestScoped
public class TypeUserView {

    @EJB
    private TypeUserFacade typeUserFacade;
    private TypeUser typeUserId;
    /**
     * Creates a new instance of TypeUserView
     */
    public TypeUserView() {
        this.typeUserId = new TypeUser();
    }
     public TypeUser getTypeUserId() {
       return typeUserId;
    }
    public String postMessage(){
       this.typeUserFacade.create(typeUserId);
       return "creditcard";
    }
}
