/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "OPCIONES_RESPUESTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OpcionesRespuesta.findAll", query = "SELECT o FROM OpcionesRespuesta o")})
public class OpcionesRespuesta implements Serializable {
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
    @Column(name = "CODIGO_NIVEL")
    private Long codigoNivel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ORDEN")
    private Integer orden;
    @JoinColumn(name = "ESTADO", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Tipos estado;
    @JoinColumn(name = "PREGUNTASCODIGO", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Preguntas preguntascodigo;

    public OpcionesRespuesta() {
    }

    public OpcionesRespuesta(Long codigoNivel) {
        this.codigoNivel = codigoNivel;
    }

    public OpcionesRespuesta(Long codigoNivel, String usuario, String programa, Date fecha, String descripcion) {
        this.codigoNivel = codigoNivel;
        this.usuario = usuario;
        this.programa = programa;
        this.fecha = fecha;
        this.descripcion = descripcion;
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

    public Long getCodigoNivel() {
        return codigoNivel;
    }

    public void setCodigoNivel(Long codigoNivel) {
        this.codigoNivel = codigoNivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Tipos getEstado() {
        return estado;
    }

    public void setEstado(Tipos estado) {
        this.estado = estado;
    }

    public Preguntas getPreguntascodigo() {
        return preguntascodigo;
    }

    public void setPreguntascodigo(Preguntas preguntascodigo) {
        this.preguntascodigo = preguntascodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoNivel != null ? codigoNivel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OpcionesRespuesta)) {
            return false;
        }
        OpcionesRespuesta other = (OpcionesRespuesta) object;
        if ((this.codigoNivel == null && other.codigoNivel != null) || (this.codigoNivel != null && !this.codigoNivel.equals(other.codigoNivel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amclos.model.OpcionesRespuesta[ codigoNivel=" + codigoNivel + " ]";
    }
    
}
