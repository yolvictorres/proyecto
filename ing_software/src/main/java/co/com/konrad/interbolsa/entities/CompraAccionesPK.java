/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.entities;

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
public class CompraAccionesPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "compra_acciones_id")
    private int compraAccionesId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_accion_valor_accion_id")
    private int valorAccionValorAccionId;

    public CompraAccionesPK() {
    }

    public CompraAccionesPK(int compraAccionesId, int valorAccionValorAccionId) {
        this.compraAccionesId = compraAccionesId;
        this.valorAccionValorAccionId = valorAccionValorAccionId;
    }

    public int getCompraAccionesId() {
        return compraAccionesId;
    }

    public void setCompraAccionesId(int compraAccionesId) {
        this.compraAccionesId = compraAccionesId;
    }

    public int getValorAccionValorAccionId() {
        return valorAccionValorAccionId;
    }

    public void setValorAccionValorAccionId(int valorAccionValorAccionId) {
        this.valorAccionValorAccionId = valorAccionValorAccionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compraAccionesId;
        hash += (int) valorAccionValorAccionId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompraAccionesPK)) {
            return false;
        }
        CompraAccionesPK other = (CompraAccionesPK) object;
        if (this.compraAccionesId != other.compraAccionesId) {
            return false;
        }
        if (this.valorAccionValorAccionId != other.valorAccionValorAccionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.konrad.interbolsa.entities.CompraAccionesPK[ compraAccionesId=" + compraAccionesId + ", valorAccionValorAccionId=" + valorAccionValorAccionId + " ]";
    }
    
}
