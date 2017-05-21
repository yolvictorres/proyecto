/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ADGS
 */
@Embeddable
public class UsuarioDAOPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "usuario_id")
    private int usuarioId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "acciones_accion_id")
    private int accionesAccionId;

    public UsuarioDAOPK() {
    }

    public UsuarioDAOPK(int usuarioId, int accionesAccionId) {
        this.usuarioId = usuarioId;
        this.accionesAccionId = accionesAccionId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getAccionesAccionId() {
        return accionesAccionId;
    }

    public void setAccionesAccionId(int accionesAccionId) {
        this.accionesAccionId = accionesAccionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usuarioId;
        hash += (int) accionesAccionId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioDAOPK)) {
            return false;
        }
        UsuarioDAOPK other = (UsuarioDAOPK) object;
        if (this.usuarioId != other.usuarioId) {
            return false;
        }
        if (this.accionesAccionId != other.accionesAccionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.konrad.interbolsa.dao.UsuarioDAOPK[ usuarioId=" + usuarioId + ", accionesAccionId=" + accionesAccionId + " ]";
    }
    
}
