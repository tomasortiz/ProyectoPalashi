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
public class RespuestaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "PREGUNTASCODIGO")
    private long preguntascodigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_RESPUESTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRespuesta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "USUARIOCODIGO")
    private String usuariocodigo;

    public RespuestaPK() {
    }

    public RespuestaPK(long preguntascodigo, Date fechaRespuesta, String usuariocodigo) {
        this.preguntascodigo = preguntascodigo;
        this.fechaRespuesta = fechaRespuesta;
        this.usuariocodigo = usuariocodigo;
    }

    public long getPreguntascodigo() {
        return preguntascodigo;
    }

    public void setPreguntascodigo(long preguntascodigo) {
        this.preguntascodigo = preguntascodigo;
    }

    public Date getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(Date fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public String getUsuariocodigo() {
        return usuariocodigo;
    }

    public void setUsuariocodigo(String usuariocodigo) {
        this.usuariocodigo = usuariocodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) preguntascodigo;
        hash += (fechaRespuesta != null ? fechaRespuesta.hashCode() : 0);
        hash += (usuariocodigo != null ? usuariocodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RespuestaPK)) {
            return false;
        }
        RespuestaPK other = (RespuestaPK) object;
        if (this.preguntascodigo != other.preguntascodigo) {
            return false;
        }
        if ((this.fechaRespuesta == null && other.fechaRespuesta != null) || (this.fechaRespuesta != null && !this.fechaRespuesta.equals(other.fechaRespuesta))) {
            return false;
        }
        if ((this.usuariocodigo == null && other.usuariocodigo != null) || (this.usuariocodigo != null && !this.usuariocodigo.equals(other.usuariocodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amclos.model.RespuestaPK[ preguntascodigo=" + preguntascodigo + ", fechaRespuesta=" + fechaRespuesta + ", usuariocodigo=" + usuariocodigo + " ]";
    }
    
}
