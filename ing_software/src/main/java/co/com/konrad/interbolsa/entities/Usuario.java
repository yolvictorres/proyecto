/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.konrad.interbolsa.entities;

import java.io.Serializable;
import java.util.Collection;
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
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsuarioId", query = "SELECT u FROM Usuario u WHERE u.usuarioId = :usuarioId"),
    @NamedQuery(name = "Usuario.findByUsuarioNombre", query = "SELECT u FROM Usuario u WHERE u.usuarioNombre = :usuarioNombre"),
    @NamedQuery(name = "Usuario.findByUsuarioApellido", query = "SELECT u FROM Usuario u WHERE u.usuarioApellido = :usuarioApellido"),
    @NamedQuery(name = "Usuario.findByUsuarioCedula", query = "SELECT u FROM Usuario u WHERE u.usuarioCedula = :usuarioCedula"),
    @NamedQuery(name = "Usuario.findByUsuarioCorreo", query = "SELECT u FROM Usuario u WHERE u.usuarioCorreo = :usuarioCorreo"),
    @NamedQuery(name = "Usuario.findByUsuarioTelefono", query = "SELECT u FROM Usuario u WHERE u.usuarioTelefono = :usuarioTelefono"),
    @NamedQuery(name = "Usuario.findByUsuairoClave", query = "SELECT u FROM Usuario u WHERE u.usuairoClave = :usuairoClave"),
    @NamedQuery(name = "Usuario.findByUsuarioTipo", query = "SELECT u FROM Usuario u WHERE u.usuarioTipo = :usuarioTipo"),
    @NamedQuery(name = "Usuario.findByUsuarioPersona", query = "SELECT u FROM Usuario u WHERE u.usuarioPersona = :usuarioPersona")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usuario_id")
    private Integer usuarioId;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioUsuarioId")
    private Collection<Acciones> accionesCollection;
    @JoinColumn(name = "empresa_empresa_id", referencedColumnName = "empresa_id")
    @ManyToOne(optional = false)
    private Empresa empresaEmpresaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioUsuarioId")
    private Collection<CompraAcciones> compraAccionesCollection;

    public Usuario() {
    }

    public Usuario(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
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

    @XmlTransient
    public Collection<Acciones> getAccionesCollection() {
        return accionesCollection;
    }

    public void setAccionesCollection(Collection<Acciones> accionesCollection) {
        this.accionesCollection = accionesCollection;
    }

    public Empresa getEmpresaEmpresaId() {
        return empresaEmpresaId;
    }

    public void setEmpresaEmpresaId(Empresa empresaEmpresaId) {
        this.empresaEmpresaId = empresaEmpresaId;
    }

    @XmlTransient
    public Collection<CompraAcciones> getCompraAccionesCollection() {
        return compraAccionesCollection;
    }

    public void setCompraAccionesCollection(Collection<CompraAcciones> compraAccionesCollection) {
        this.compraAccionesCollection = compraAccionesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioId != null ? usuarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuarioId == null && other.usuarioId != null) || (this.usuarioId != null && !this.usuarioId.equals(other.usuarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.konrad.interbolsa.entities.Usuario[ usuarioId=" + usuarioId + " ]";
    }
    
}
