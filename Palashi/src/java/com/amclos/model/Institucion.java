/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
 * @author Administrador
 */
@Entity
@Table(name = "INSTITUCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Institucion.findAll", query = "SELECT i FROM Institucion i")})
public class Institucion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PROGRAMA")
    private String programa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private Long codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 150)
    @Column(name = "REP_LEGAL")
    private String repLegal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TELEFONO")
    private String telefono;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 150)
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "NUM_EMPLEADOS")
    private Long numEmpleados;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "institucioncodigo")
    private List<Usuario> usuarioList;
    @JoinColumn(name = "CODIGO_ZONA", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private ZonaGeografica codigoZona;
    @JoinColumn(name = "TIPO_INSTITUCION", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Tipos tipoInstitucion;
    @JoinColumn(name = "ESTADO", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Tipos estado;

    public Institucion() {
    }

    public Institucion(Long codigo) {
        this.codigo = codigo;
    }

    public Institucion(Long codigo, String usuario, String programa, Date fecha, String nombre, String telefono, String email) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.programa = programa;
        this.fecha = fecha;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRepLegal() {
        return repLegal;
    }

    public void setRepLegal(String repLegal) {
        this.repLegal = repLegal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getNumEmpleados() {
        return numEmpleados;
    }

    public void setNumEmpleados(Long numEmpleados) {
        this.numEmpleados = numEmpleados;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public ZonaGeografica getCodigoZona() {
        return codigoZona;
    }

    public void setCodigoZona(ZonaGeografica codigoZona) {
        this.codigoZona = codigoZona;
    }

    public Tipos getTipoInstitucion() {
        return tipoInstitucion;
    }

    public void setTipoInstitucion(Tipos tipoInstitucion) {
        this.tipoInstitucion = tipoInstitucion;
    }

    public Tipos getEstado() {
        return estado;
    }

    public void setEstado(Tipos estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Institucion)) {
            return false;
        }
        Institucion other = (Institucion) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amclos.model.Institucion[ codigo=" + codigo + " ]";
    }
    
}
