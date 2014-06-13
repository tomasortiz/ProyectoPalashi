/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Administrador
 */
@Embeddable
public class UsuarioPerfilPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CODIGO_USUARIO")
    private String codigoUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_ALTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;

    public UsuarioPerfilPK() {
    }

    public UsuarioPerfilPK(String codigoUsuario, Date fechaAlta) {
        this.codigoUsuario = codigoUsuario;
        this.fechaAlta = fechaAlta;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoUsuario != null ? codigoUsuario.hashCode() : 0);
        hash += (fechaAlta != null ? fechaAlta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioPerfilPK)) {
            return false;
        }
        UsuarioPerfilPK other = (UsuarioPerfilPK) object;
        if ((this.codigoUsuario == null && other.codigoUsuario != null) || (this.codigoUsuario != null && !this.codigoUsuario.equals(other.codigoUsuario))) {
            return false;
        }
        if ((this.fechaAlta == null && other.fechaAlta != null) || (this.fechaAlta != null && !this.fechaAlta.equals(other.fechaAlta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amclos.model.UsuarioPerfilPK[ codigoUsuario=" + codigoUsuario + ", fechaAlta=" + fechaAlta + " ]";
    }
    
}
