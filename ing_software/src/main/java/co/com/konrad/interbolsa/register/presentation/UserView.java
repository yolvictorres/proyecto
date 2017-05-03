/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.register.presentation;

import com.register.boundary.UserFacade;
import com.register.entities.User;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Home
 */
@Named(value = "UserView")
@RequestScoped
public class UserView {

    @EJB
    private UserFacade userFacade;
    
    private User user;
    /**
     * Creates a new instance of UserView
     */
    public UserView() {
        this.user = new User();
    }
    public User getUser(){
           return this.user;
    }
    
    public String postUser(Integer opt){
        if (opt == 1){
            this.user.setIdTypeUser(1);
        }
         this.userFacade.create(user);
         return "creditcard";
    }
}
