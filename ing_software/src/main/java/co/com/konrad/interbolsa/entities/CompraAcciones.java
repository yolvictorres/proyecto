/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ADGS
 */
@Entity
@Table(name = "compra_acciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompraAcciones.findAll", query = "SELECT c FROM CompraAcciones c"),
    @NamedQuery(name = "CompraAcciones.findByCompraAccionesId", query = "SELECT c FROM CompraAcciones c WHERE c.compraAccionesPK.compraAccionesId = :compraAccionesId"),
    @NamedQuery(name = "CompraAcciones.findByCompraAccionesFecha", query = "SELECT c FROM CompraAcciones c WHERE c.compraAccionesFecha = :compraAccionesFecha"),
    @NamedQuery(name = "CompraAcciones.findByCompraAccionesEstado", query = "SELECT c FROM CompraAcciones c WHERE c.compraAccionesEstado = :compraAccionesEstado"),
    @NamedQuery(name = "CompraAcciones.findByValorAccionValorAccionId", query = "SELECT c FROM CompraAcciones c WHERE c.compraAccionesPK.valorAccionValorAccionId = :valorAccionValorAccionId")})
public class CompraAcciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CompraAccionesPK compraAccionesPK;
    @Column(name = "compra_acciones_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date compraAccionesFecha;
    @Size(max = 2)
    @Column(name = "compra_acciones_estado")
    private String compraAccionesEstado;
    @JoinColumn(name = "usuario_usuario_id", referencedColumnName = "usuario_id")
    @ManyToOne(optional = false)
    private Usuario usuarioUsuarioId;
    @JoinColumn(name = "valor_accion_valor_accion_id", referencedColumnName = "valor_accion_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ValorAccion valorAccion;

    public CompraAcciones() {
    }

    public CompraAcciones(CompraAccionesPK compraAccionesPK) {
        this.compraAccionesPK = compraAccionesPK;
    }

    public CompraAcciones(int compraAccionesId, int valorAccionValorAccionId) {
        this.compraAccionesPK = new CompraAccionesPK(compraAccionesId, valorAccionValorAccionId);
    }

    public CompraAccionesPK getCompraAccionesPK() {
        return compraAccionesPK;
    }

    public void setCompraAccionesPK(CompraAccionesPK compraAccionesPK) {
        this.compraAccionesPK = compraAccionesPK;
    }

    public Date getCompraAccionesFecha() {
        return compraAccionesFecha;
    }

    public void setCompraAccionesFecha(Date compraAccionesFecha) {
        this.compraAccionesFecha = compraAccionesFecha;
    }

    public String getCompraAccionesEstado() {
        return compraAccionesEstado;
    }

    public void setCompraAccionesEstado(String compraAccionesEstado) {
        this.compraAccionesEstado = compraAccionesEstado;
    }

    public Usuario getUsuarioUsuarioId() {
        return usuarioUsuarioId;
    }

    public void setUsuarioUsuarioId(Usuario usuarioUsuarioId) {
        this.usuarioUsuarioId = usuarioUsuarioId;
    }

    public ValorAccion getValorAccion() {
        return valorAccion;
    }

    public void setValorAccion(ValorAccion valorAccion) {
        this.valorAccion = valorAccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (compraAccionesPK != null ? compraAccionesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompraAcciones)) {
            return false;
        }
        CompraAcciones other = (CompraAcciones) object;
        if ((this.compraAccionesPK == null && other.compraAccionesPK != null) || (this.compraAccionesPK != null && !this.compraAccionesPK.equals(other.compraAccionesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.konrad.interbolsa.entities.CompraAcciones[ compraAccionesPK=" + compraAccionesPK + " ]";
    }
    
}
