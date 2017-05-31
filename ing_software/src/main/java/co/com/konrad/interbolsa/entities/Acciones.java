/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ADGS
 */
@Entity
@Table(name = "acciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acciones.findAll", query = "SELECT a FROM Acciones a"),
    @NamedQuery(name = "Acciones.findByAccionId", query = "SELECT a FROM Acciones a WHERE a.accionId = :accionId"),
    @NamedQuery(name = "Acciones.findByAccionCantidad", query = "SELECT a FROM Acciones a WHERE a.accionCantidad = :accionCantidad"),
    @NamedQuery(name = "Acciones.findByAccionFecha", query = "SELECT a FROM Acciones a WHERE a.accionFecha = :accionFecha"),
    @NamedQuery(name = "Acciones.findByAccionvenderA", query = "SELECT a FROM Acciones a WHERE a.accionvenderA = :accionvenderA"),
    @NamedQuery(name = "Acciones.findByAccionCondicion", query = "SELECT a FROM Acciones a WHERE a.accionCondicion = :accionCondicion")})
public class Acciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "accion_id")
    private Integer accionId;
    @Column(name = "accion_cantidad")
    private Integer accionCantidad;
    @Column(name = "accion_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date accionFecha;
    @Size(max = 2)
    @Column(name = "accion_vender_A")
    private String accionvenderA;
    @Column(name = "accion_condicion")
    private Integer accionCondicion;
    @JoinColumn(name = "usuario_usuario_id", referencedColumnName = "usuario_id")
    @ManyToOne(optional = false)
    private Usuario usuarioUsuarioId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accionesAccionId")
    private Collection<ValorAccion> valorAccionCollection;

    public Acciones() {
    }

    public Acciones(Integer accionId) {
        this.accionId = accionId;
    }

    public Integer getAccionId() {
        return accionId;
    }

    public void setAccionId(Integer accionId) {
        this.accionId = accionId;
    }

    public Integer getAccionCantidad() {
        return accionCantidad;
    }

    public void setAccionCantidad(Integer accionCantidad) {
        this.accionCantidad = accionCantidad;
    }

    public Date getAccionFecha() {
        return accionFecha;
    }

    public void setAccionFecha(Date accionFecha) {
        this.accionFecha = accionFecha;
    }

    public String getAccionvenderA() {
        return accionvenderA;
    }

    public void setAccionvenderA(String accionvenderA) {
        this.accionvenderA = accionvenderA;
    }

    public Integer getAccionCondicion() {
        return accionCondicion;
    }

    public void setAccionCondicion(Integer accionCondicion) {
        this.accionCondicion = accionCondicion;
    }

    public Usuario getUsuarioUsuarioId() {
        return usuarioUsuarioId;
    }

    public void setUsuarioUsuarioId(Usuario usuarioUsuarioId) {
        this.usuarioUsuarioId = usuarioUsuarioId;
    }

    @XmlTransient
    public Collection<ValorAccion> getValorAccionCollection() {
        return valorAccionCollection;
    }

    public void setValorAccionCollection(Collection<ValorAccion> valorAccionCollection) {
        this.valorAccionCollection = valorAccionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accionId != null ? accionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Acciones)) {
            return false;
        }
        Acciones other = (Acciones) object;
        if ((this.accionId == null && other.accionId != null) || (this.accionId != null && !this.accionId.equals(other.accionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.konrad.interbolsa.entities.Acciones[ accionId=" + accionId + " ]";
    }
    
}
