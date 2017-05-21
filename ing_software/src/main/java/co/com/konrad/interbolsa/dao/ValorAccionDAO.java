/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
    @NamedQuery(name = "ValorAccionDAO.findAll", query = "SELECT v FROM ValorAccionDAO v"),
    @NamedQuery(name = "ValorAccionDAO.findByValorAccionInicio", query = "SELECT v FROM ValorAccionDAO v WHERE v.valorAccionInicio = :valorAccionInicio"),
    @NamedQuery(name = "ValorAccionDAO.findByValorAccionFin", query = "SELECT v FROM ValorAccionDAO v WHERE v.valorAccionFin = :valorAccionFin"),
    @NamedQuery(name = "ValorAccionDAO.findByValorAccionEstado", query = "SELECT v FROM ValorAccionDAO v WHERE v.valorAccionEstado = :valorAccionEstado"),
    @NamedQuery(name = "ValorAccionDAO.findByValorAccionValor", query = "SELECT v FROM ValorAccionDAO v WHERE v.valorAccionValor = :valorAccionValor"),
    @NamedQuery(name = "ValorAccionDAO.findByAccionesAccionId", query = "SELECT v FROM ValorAccionDAO v WHERE v.valorAccionDAOPK.accionesAccionId = :accionesAccionId"),
    @NamedQuery(name = "ValorAccionDAO.findByValorAccionId", query = "SELECT v FROM ValorAccionDAO v WHERE v.valorAccionDAOPK.valorAccionId = :valorAccionId")})
public class ValorAccionDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ValorAccionDAOPK valorAccionDAOPK;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "valorAccionDAO")
    private Collection<CompraAccionesDAO> compraAccionesDAOCollection;
    @JoinColumn(name = "acciones_accion_id", referencedColumnName = "accion_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AccionesDAO accionesDAO;

    public ValorAccionDAO() {
    }

    public ValorAccionDAO(ValorAccionDAOPK valorAccionDAOPK) {
        this.valorAccionDAOPK = valorAccionDAOPK;
    }

    public ValorAccionDAO(int accionesAccionId, int valorAccionId) {
        this.valorAccionDAOPK = new ValorAccionDAOPK(accionesAccionId, valorAccionId);
    }

    public ValorAccionDAOPK getValorAccionDAOPK() {
        return valorAccionDAOPK;
    }

    public void setValorAccionDAOPK(ValorAccionDAOPK valorAccionDAOPK) {
        this.valorAccionDAOPK = valorAccionDAOPK;
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

    @XmlTransient
    public Collection<CompraAccionesDAO> getCompraAccionesDAOCollection() {
        return compraAccionesDAOCollection;
    }

    public void setCompraAccionesDAOCollection(Collection<CompraAccionesDAO> compraAccionesDAOCollection) {
        this.compraAccionesDAOCollection = compraAccionesDAOCollection;
    }

    public AccionesDAO getAccionesDAO() {
        return accionesDAO;
    }

    public void setAccionesDAO(AccionesDAO accionesDAO) {
        this.accionesDAO = accionesDAO;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (valorAccionDAOPK != null ? valorAccionDAOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValorAccionDAO)) {
            return false;
        }
        ValorAccionDAO other = (ValorAccionDAO) object;
        if ((this.valorAccionDAOPK == null && other.valorAccionDAOPK != null) || (this.valorAccionDAOPK != null && !this.valorAccionDAOPK.equals(other.valorAccionDAOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.konrad.interbolsa.dao.ValorAccionDAO[ valorAccionDAOPK=" + valorAccionDAOPK + " ]";
    }
    
}
