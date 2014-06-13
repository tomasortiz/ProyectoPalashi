/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "USUARIO_PERFIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioPerfil.findAll", query = "SELECT u FROM UsuarioPerfil u")})
public class UsuarioPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuarioPerfilPK usuarioPerfilPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "PROGRAMA")
    private String programa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_MODIF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;
    @Column(name = "FECHA_BAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @JoinColumn(name = "CODIGO_USUARIO", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario1;
    @JoinColumn(name = "CODIGO_PERFIL", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Perfil codigoPerfil;

    public UsuarioPerfil() {
    }

    public UsuarioPerfil(UsuarioPerfilPK usuarioPerfilPK) {
        this.usuarioPerfilPK = usuarioPerfilPK;
    }

    public UsuarioPerfil(UsuarioPerfilPK usuarioPerfilPK, String usuario, String programa, Date fechaModif) {
        this.usuarioPerfilPK = usuarioPerfilPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
    }

    public UsuarioPerfil(String codigoUsuario, Date fechaAlta) {
        this.usuarioPerfilPK = new UsuarioPerfilPK(codigoUsuario, fechaAlta);
    }

    public UsuarioPerfilPK getUsuarioPerfilPK() {
        return usuarioPerfilPK;
    }

    public void setUsuarioPerfilPK(UsuarioPerfilPK usuarioPerfilPK) {
        this.usuarioPerfilPK = usuarioPerfilPK;
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

    public Date getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    public Perfil getCodigoPerfil() {
        return codigoPerfil;
    }

    public void setCodigoPerfil(Perfil codigoPerfil) {
        this.codigoPerfil = codigoPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioPerfilPK != null ? usuarioPerfilPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioPerfil)) {
            return false;
        }
        UsuarioPerfil other = (UsuarioPerfil) object;
        if ((this.usuarioPerfilPK == null && other.usuarioPerfilPK != null) || (this.usuarioPerfilPK != null && !this.usuarioPerfilPK.equals(other.usuarioPerfilPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amclos.model.UsuarioPerfil[ usuarioPerfilPK=" + usuarioPerfilPK + " ]";
    }
    
}
