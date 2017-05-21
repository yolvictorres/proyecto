/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.dao;

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
    @NamedQuery(name = "CompraAccionesDAO.findAll", query = "SELECT c FROM CompraAccionesDAO c"),
    @NamedQuery(name = "CompraAccionesDAO.findByCompraAccionesId", query = "SELECT c FROM CompraAccionesDAO c WHERE c.compraAccionesDAOPK.compraAccionesId = :compraAccionesId"),
    @NamedQuery(name = "CompraAccionesDAO.findByCompraAccionesFecha", query = "SELECT c FROM CompraAccionesDAO c WHERE c.compraAccionesFecha = :compraAccionesFecha"),
    @NamedQuery(name = "CompraAccionesDAO.findByCompraAccionesEstado", query = "SELECT c FROM CompraAccionesDAO c WHERE c.compraAccionesEstado = :compraAccionesEstado"),
    @NamedQuery(name = "CompraAccionesDAO.findByValorAccionId", query = "SELECT c FROM CompraAccionesDAO c WHERE c.compraAccionesDAOPK.valorAccionId = :valorAccionId"),
    @NamedQuery(name = "CompraAccionesDAO.findByUsuarioId", query = "SELECT c FROM CompraAccionesDAO c WHERE c.compraAccionesDAOPK.usuarioId = :usuarioId")})
public class CompraAccionesDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CompraAccionesDAOPK compraAccionesDAOPK;
    @Column(name = "compra_acciones_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date compraAccionesFecha;
    @Size(max = 2)
    @Column(name = "compra_acciones_estado")
    private String compraAccionesEstado;
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UsuarioDAO usuarioDAO;
    @JoinColumn(name = "valor_accion_id", referencedColumnName = "valor_accion_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ValorAccionDAO valorAccionDAO;

    public CompraAccionesDAO() {
    }

    public CompraAccionesDAO(CompraAccionesDAOPK compraAccionesDAOPK) {
        this.compraAccionesDAOPK = compraAccionesDAOPK;
    }

    public CompraAccionesDAO(int compraAccionesId, int valorAccionId, int usuarioId) {
        this.compraAccionesDAOPK = new CompraAccionesDAOPK(compraAccionesId, valorAccionId, usuarioId);
    }

    public CompraAccionesDAOPK getCompraAccionesDAOPK() {
        return compraAccionesDAOPK;
    }

    public void setCompraAccionesDAOPK(CompraAccionesDAOPK compraAccionesDAOPK) {
        this.compraAccionesDAOPK = compraAccionesDAOPK;
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

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public ValorAccionDAO getValorAccionDAO() {
        return valorAccionDAO;
    }

    public void setValorAccionDAO(ValorAccionDAO valorAccionDAO) {
        this.valorAccionDAO = valorAccionDAO;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (compraAccionesDAOPK != null ? compraAccionesDAOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompraAccionesDAO)) {
            return false;
        }
        CompraAccionesDAO other = (CompraAccionesDAO) object;
        if ((this.compraAccionesDAOPK == null && other.compraAccionesDAOPK != null) || (this.compraAccionesDAOPK != null && !this.compraAccionesDAOPK.equals(other.compraAccionesDAOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.konrad.interbolsa.dao.CompraAccionesDAO[ compraAccionesDAOPK=" + compraAccionesDAOPK + " ]";
    }
    
}
