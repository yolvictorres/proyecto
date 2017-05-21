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
public class CompraAccionesDAOPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "compra_acciones_id")
    private int compraAccionesId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_accion_id")
    private int valorAccionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usuario_id")
    private int usuarioId;

    public CompraAccionesDAOPK() {
    }

    public CompraAccionesDAOPK(int compraAccionesId, int valorAccionId, int usuarioId) {
        this.compraAccionesId = compraAccionesId;
        this.valorAccionId = valorAccionId;
        this.usuarioId = usuarioId;
    }

    public int getCompraAccionesId() {
        return compraAccionesId;
    }

    public void setCompraAccionesId(int compraAccionesId) {
        this.compraAccionesId = compraAccionesId;
    }

    public int getValorAccionId() {
        return valorAccionId;
    }

    public void setValorAccionId(int valorAccionId) {
        this.valorAccionId = valorAccionId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compraAccionesId;
        hash += (int) valorAccionId;
        hash += (int) usuarioId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompraAccionesDAOPK)) {
            return false;
        }
        CompraAccionesDAOPK other = (CompraAccionesDAOPK) object;
        if (this.compraAccionesId != other.compraAccionesId) {
            return false;
        }
        if (this.valorAccionId != other.valorAccionId) {
            return false;
        }
        if (this.usuarioId != other.usuarioId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.konrad.interbolsa.dao.CompraAccionesDAOPK[ compraAccionesId=" + compraAccionesId + ", valorAccionId=" + valorAccionId + ", usuarioId=" + usuarioId + " ]";
    }
    
}
