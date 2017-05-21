/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.dao;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ADGS
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioDAO.findAll", query = "SELECT u FROM UsuarioDAO u"),
    @NamedQuery(name = "UsuarioDAO.findByUsuarioId", query = "SELECT u FROM UsuarioDAO u WHERE u.usuarioDAOPK.usuarioId = :usuarioId"),
    @NamedQuery(name = "UsuarioDAO.findByUsuarioNombre", query = "SELECT u FROM UsuarioDAO u WHERE u.usuarioNombre = :usuarioNombre"),
    @NamedQuery(name = "UsuarioDAO.findByUsuarioApellido", query = "SELECT u FROM UsuarioDAO u WHERE u.usuarioApellido = :usuarioApellido"),
    @NamedQuery(name = "UsuarioDAO.findByUsuarioCedula", query = "SELECT u FROM UsuarioDAO u WHERE u.usuarioCedula = :usuarioCedula"),
    @NamedQuery(name = "UsuarioDAO.findByUsuarioCorreo", query = "SELECT u FROM UsuarioDAO u WHERE u.usuarioCorreo = :usuarioCorreo"),
    @NamedQuery(name = "UsuarioDAO.findByUsuarioTelefono", query = "SELECT u FROM UsuarioDAO u WHERE u.usuarioTelefono = :usuarioTelefono"),
    @NamedQuery(name = "UsuarioDAO.findByUsuairoClave", query = "SELECT u FROM UsuarioDAO u WHERE u.usuairoClave = :usuairoClave"),
    @NamedQuery(name = "UsuarioDAO.findByUsuarioTipo", query = "SELECT u FROM UsuarioDAO u WHERE u.usuarioTipo = :usuarioTipo"),
    @NamedQuery(name = "UsuarioDAO.findByUsuarioPersona", query = "SELECT u FROM UsuarioDAO u WHERE u.usuarioPersona = :usuarioPersona"),
    @NamedQuery(name = "UsuarioDAO.findByAccionesAccionId", query = "SELECT u FROM UsuarioDAO u WHERE u.usuarioDAOPK.accionesAccionId = :accionesAccionId"),
    @NamedQuery(name = "UsuarioDAO.findByUsuarioPersonaTipo", query = "SELECT u FROM UsuarioDAO u WHERE u.usuarioPersonaTipo = :usuarioPersonaTipo")})
public class UsuarioDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuarioDAOPK usuarioDAOPK;
    @Size(max = 100)
    @Column(name = "usuario_nombre")
    private String usuarioNombre;
    @Size(max = 100)
    @Column(name = "usuario_apellido")
    private String usuarioApellido;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "usuario_cedula")
    private Float usuarioCedula;
    @Size(max = 45)
    @Column(name = "usuario_correo")
    private String usuarioCorreo;
    @Column(name = "usuario_telefono")
    private Float usuarioTelefono;
    @Size(max = 45)
    @Column(name = "usuairo_clave")
    private String usuairoClave;
    @Size(max = 3)
    @Column(name = "usuario_tipo")
    private String usuarioTipo;
    @Size(max = 2)
    @Column(name = "usuario_persona")
    private String usuarioPersona;
    @Size(max = 3)
    @Column(name = "usuario_persona_tipo")
    private String usuarioPersonaTipo;
    @JoinColumn(name = "acciones_accion_id", referencedColumnName = "accion_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AccionesDAO accionesDAO;
    @JoinColumn(name = "empresa_empresa_id", referencedColumnName = "empresa_id")
    @ManyToOne(optional = false)
    private EmpresaDAO empresaEmpresaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioDAO")
    private Collection<CompraAccionesDAO> compraAccionesDAOCollection;

    public UsuarioDAO() {
    }

    public UsuarioDAO(UsuarioDAOPK usuarioDAOPK) {
        this.usuarioDAOPK = usuarioDAOPK;
    }

    public UsuarioDAO(int usuarioId, int accionesAccionId) {
        this.usuarioDAOPK = new UsuarioDAOPK(usuarioId, accionesAccionId);
    }

    public UsuarioDAOPK getUsuarioDAOPK() {
        return usuarioDAOPK;
    }

    public void setUsuarioDAOPK(UsuarioDAOPK usuarioDAOPK) {
        this.usuarioDAOPK = usuarioDAOPK;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getUsuarioApellido() {
        return usuarioApellido;
    }

    public void setUsuarioApellido(String usuarioApellido) {
        this.usuarioApellido = usuarioApellido;
    }

    public Float getUsuarioCedula() {
        return usuarioCedula;
    }

    public void setUsuarioCedula(Float usuarioCedula) {
        this.usuarioCedula = usuarioCedula;
    }

    public String getUsuarioCorreo() {
        return usuarioCorreo;
    }

    public void setUsuarioCorreo(String usuarioCorreo) {
        this.usuarioCorreo = usuarioCorreo;
    }

    public Float getUsuarioTelefono() {
        return usuarioTelefono;
    }

    public void setUsuarioTelefono(Float usuarioTelefono) {
        this.usuarioTelefono = usuarioTelefono;
    }

    public String getUsuairoClave() {
        return usuairoClave;
    }

    public void setUsuairoClave(String usuairoClave) {
        this.usuairoClave = usuairoClave;
    }

    public String getUsuarioTipo() {
        return usuarioTipo;
    }

    public void setUsuarioTipo(String usuarioTipo) {
        this.usuarioTipo = usuarioTipo;
    }

    public String getUsuarioPersona() {
        return usuarioPersona;
    }

    public void setUsuarioPersona(String usuarioPersona) {
        this.usuarioPersona = usuarioPersona;
    }

    public String getUsuarioPersonaTipo() {
        return usuarioPersonaTipo;
    }

    public void setUsuarioPersonaTipo(String usuarioPersonaTipo) {
        this.usuarioPersonaTipo = usuarioPersonaTipo;
    }

    public AccionesDAO getAccionesDAO() {
        return accionesDAO;
    }

    public void setAccionesDAO(AccionesDAO accionesDAO) {
        this.accionesDAO = accionesDAO;
    }

    public EmpresaDAO getEmpresaEmpresaId() {
        return empresaEmpresaId;
    }

    public void setEmpresaEmpresaId(EmpresaDAO empresaEmpresaId) {
        this.empresaEmpresaId = empresaEmpresaId;
    }

    @XmlTransient
    public Collection<CompraAccionesDAO> getCompraAccionesDAOCollection() {
        return compraAccionesDAOCollection;
    }

    public void setCompraAccionesDAOCollection(Collection<CompraAccionesDAO> compraAccionesDAOCollection) {
        this.compraAccionesDAOCollection = compraAccionesDAOCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioDAOPK != null ? usuarioDAOPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioDAO)) {
            return false;
        }
        UsuarioDAO other = (UsuarioDAO) object;
        if ((this.usuarioDAOPK == null && other.usuarioDAOPK != null) || (this.usuarioDAOPK != null && !this.usuarioDAOPK.equals(other.usuarioDAOPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.konrad.interbolsa.dao.UsuarioDAO[ usuarioDAOPK=" + usuarioDAOPK + " ]";
    }
    
}
