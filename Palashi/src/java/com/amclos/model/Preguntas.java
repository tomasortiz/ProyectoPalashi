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
@Table(name = "PREGUNTAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preguntas.findAll", query = "SELECT p FROM Preguntas p")})
public class Preguntas implements Serializable {
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO")
    private Long codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "VARIABLECOD", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Variable variablecod;
    @JoinColumn(name = "TIPO_SITUACION", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Tipos tipoSituacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preguntascodigo")
    private List<OpcionesRespuesta> opcionesRespuestaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preguntas")
    private List<Respuesta> respuestaList;

    public Preguntas() {
    }

    public Preguntas(Long codigo) {
        this.codigo = codigo;
    }

    public Preguntas(Long codigo, String usuario, String programa, Date fecha, String descripcion, String estado) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.programa = programa;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.estado = estado;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Variable getVariablecod() {
        return variablecod;
    }

    public void setVariablecod(Variable variablecod) {
        this.variablecod = variablecod;
    }

    public Tipos getTipoSituacion() {
        return tipoSituacion;
    }

    public void setTipoSituacion(Tipos tipoSituacion) {
        this.tipoSituacion = tipoSituacion;
    }

    @XmlTransient
    public List<OpcionesRespuesta> getOpcionesRespuestaList() {
        return opcionesRespuestaList;
    }

    public void setOpcionesRespuestaList(List<OpcionesRespuesta> opcionesRespuestaList) {
        this.opcionesRespuestaList = opcionesRespuestaList;
    }

    @XmlTransient
    public List<Respuesta> getRespuestaList() {
        return respuestaList;
    }

    public void setRespuestaList(List<Respuesta> respuestaList) {
        this.respuestaList = respuestaList;
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
        if (!(object instanceof Preguntas)) {
            return false;
        }
        Preguntas other = (Preguntas) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amclos.model.Preguntas[ codigo=" + codigo + " ]";
    }
    
}
