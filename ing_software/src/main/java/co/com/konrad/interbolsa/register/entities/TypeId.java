/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.register.entities;

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
@Table(name = "type_id")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeId.findAll", query = "SELECT t FROM TypeId t")
    , @NamedQuery(name = "TypeId.findByIdTypeId", query = "SELECT t FROM TypeId t WHERE t.idTypeId = :idTypeId")
    , @NamedQuery(name = "TypeId.findByTypeId", query = "SELECT t FROM TypeId t WHERE t.typeId = :typeId")})
public class TypeId implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_type_id")
    private Integer idTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "type_id")
    private String typeId;

    public TypeId() {
    }

    public TypeId(Integer idTypeId) {
        this.idTypeId = idTypeId;
    }

    public TypeId(Integer idTypeId, String typeId) {
        this.idTypeId = idTypeId;
        this.typeId = typeId;
    }

    public Integer getIdTypeId() {
        return idTypeId;
    }

    public void setIdTypeId(Integer idTypeId) {
        this.idTypeId = idTypeId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTypeId != null ? idTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeId)) {
            return false;
        }
        TypeId other = (TypeId) object;
        if ((this.idTypeId == null && other.idTypeId != null) || (this.idTypeId != null && !this.idTypeId.equals(other.idTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.interbolsa.register.entities.TypeId[ idTypeId=" + idTypeId + " ]";
    }
    
}
