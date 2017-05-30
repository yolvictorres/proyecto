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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ADGS
 */
@Entity
@Table(name = "valor_accion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValorAccion.findAll", query = "SELECT v FROM ValorAccion v"),
    @NamedQuery(name = "ValorAccion.findByValorAccionInicio", query = "SELECT v FROM ValorAccion v WHERE v.valorAccionInicio = :valorAccionInicio"),
    @NamedQuery(name = "ValorAccion.findByValorAccionFin", query = "SELECT v FROM ValorAccion v WHERE v.valorAccionFin = :valorAccionFin"),
    @NamedQuery(name = "ValorAccion.findByValorAccionEstado", query = "SELECT v FROM ValorAccion v WHERE v.valorAccionEstado = :valorAccionEstado"),
    @NamedQuery(name = "ValorAccion.findByValorAccionValor", query = "SELECT v FROM ValorAccion v WHERE v.valorAccionValor = :valorAccionValor"),
    @NamedQuery(name = "ValorAccion.findByValorAccionId", query = "SELECT v FROM ValorAccion v WHERE v.valorAccionId = :valorAccionId")})
public class ValorAccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "valor_accion_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valorAccionInicio;
    @Column(name = "valor_accion_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valorAccionFin;
    @Size(max = 2)
    @Column(name = "valor_accion_estado")
    private String valorAccionEstado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_accion_valor")
    private Float valorAccionValor;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "valor_accion_id")
    private Integer valorAccionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "valorAccion")
    private Collection<CompraAcciones> compraAccionesCollection;
    @JoinColumn(name = "acciones_accion_id", referencedColumnName = "accion_id")
    @ManyToOne(optional = false)
    private Acciones accionesAccionId;

    public ValorAccion() {
    }

    public ValorAccion(Integer valorAccionId) {
        this.valorAccionId = valorAccionId;
    }

    public Date getValorAccionInicio() {
        return valorAccionInicio;
    }

    public void setValorAccionInicio(Date valorAccionInicio) {
        this.valorAccionInicio = valorAccionInicio;
    }

    public Date getValorAccionFin() {
        return valorAccionFin;
    }

    public void setValorAccionFin(Date valorAccionFin) {
        this.valorAccionFin = valorAccionFin;
    }

    public String getValorAccionEstado() {
        return valorAccionEstado;
    }

    public void setValorAccionEstado(String valorAccionEstado) {
        this.valorAccionEstado = valorAccionEstado;
    }

    public Float getValorAccionValor() {
        return valorAccionValor;
    }

    public void setValorAccionValor(Float valorAccionValor) {
        this.valorAccionValor = valorAccionValor;
    }

    public Integer getValorAccionId() {
        return valorAccionId;
    }

    public void setValorAccionId(Integer valorAccionId) {
        this.valorAccionId = valorAccionId;
    }

    @XmlTransient
    public Collection<CompraAcciones> getCompraAccionesCollection() {
        return compraAccionesCollection;
    }

    public void setCompraAccionesCollection(Collection<CompraAcciones> compraAccionesCollection) {
        this.compraAccionesCollection = compraAccionesCollection;
    }

    public Acciones getAccionesAccionId() {
        return accionesAccionId;
    }

    public void setAccionesAccionId(Acciones accionesAccionId) {
        this.accionesAccionId = accionesAccionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (valorAccionId != null ? valorAccionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValorAccion)) {
            return false;
        }
        ValorAccion other = (ValorAccion) object;
        if ((this.valorAccionId == null && other.valorAccionId != null) || (this.valorAccionId != null && !this.valorAccionId.equals(other.valorAccionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.konrad.interbolsa.entities.ValorAccion[ valorAccionId=" + valorAccionId + " ]";
    }
    
}
