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
public class ValorAccionDAOPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "acciones_accion_id")
    private int accionesAccionId;
    @Basic(optional = false)
    @Column(name = "valor_accion_id")
    private int valorAccionId;

    public ValorAccionDAOPK() {
    }

    public ValorAccionDAOPK(int accionesAccionId, int valorAccionId) {
        this.accionesAccionId = accionesAccionId;
        this.valorAccionId = valorAccionId;
    }

    public int getAccionesAccionId() {
        return accionesAccionId;
    }

    public void setAccionesAccionId(int accionesAccionId) {
        this.accionesAccionId = accionesAccionId;
    }

    public int getValorAccionId() {
        return valorAccionId;
    }

    public void setValorAccionId(int valorAccionId) {
        this.valorAccionId = valorAccionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) accionesAccionId;
        hash += (int) valorAccionId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValorAccionDAOPK)) {
            return false;
        }
        ValorAccionDAOPK other = (ValorAccionDAOPK) object;
        if (this.accionesAccionId != other.accionesAccionId) {
            return false;
        }
        if (this.valorAccionId != other.valorAccionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.konrad.interbolsa.dao.ValorAccionDAOPK[ accionesAccionId=" + accionesAccionId + ", valorAccionId=" + valorAccionId + " ]";
    }
    
}
