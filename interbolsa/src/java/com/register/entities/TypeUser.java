/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.register.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Home
 */
@Entity
@Table(name = "type_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeUser.findAll", query = "SELECT t FROM TypeUser t")
    , @NamedQuery(name = "TypeUser.findByIdTypeUser", query = "SELECT t FROM TypeUser t WHERE t.idTypeUser = :idTypeUser")
    , @NamedQuery(name = "TypeUser.findByTypeUserName", query = "SELECT t FROM TypeUser t WHERE t.typeUserName = :typeUserName")})
public class TypeUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_type_user")
    private Integer idTypeUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "type_user_name")
    private String typeUserName;

    public TypeUser() {
    }

    public TypeUser(Integer idTypeUser) {
        this.idTypeUser = idTypeUser;
    }

    public TypeUser(Integer idTypeUser, String typeUserName) {
        this.idTypeUser = idTypeUser;
        this.typeUserName = typeUserName;
    }

    public Integer getIdTypeUser() {
        return idTypeUser;
    }

    public void setIdTypeUser(Integer idTypeUser) {
        this.idTypeUser = idTypeUser;
    }

    public String getTypeUserName() {
        return typeUserName;
    }

    public void setTypeUserName(String typeUserName) {
        this.typeUserName = typeUserName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTypeUser != null ? idTypeUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeUser)) {
            return false;
        }
        TypeUser other = (TypeUser) object;
        if ((this.idTypeUser == null && other.idTypeUser != null) || (this.idTypeUser != null && !this.idTypeUser.equals(other.idTypeUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.interbolsa.register.entities.TypeUser[ idTypeUser=" + idTypeUser + " ]";
    }
    
}
