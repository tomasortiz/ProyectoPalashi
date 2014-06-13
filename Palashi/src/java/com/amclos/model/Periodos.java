/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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

/**
 *
 * @author larry.obispo
 */
@Entity
@Table(name = "PERIODOS")
@NamedQueries({
    @NamedQuery(name = "Periodos.findAll", query = "SELECT p FROM Periodos p"),
    @NamedQuery(name = "Periodos.findByUsuario", query = "SELECT p FROM Periodos p WHERE p.usuario = :usuario"),
    @NamedQuery(name = "Periodos.findByPrograma", query = "SELECT p FROM Periodos p WHERE p.programa = :programa"),
    @NamedQuery(name = "Periodos.findByFecha", query = "SELECT p FROM Periodos p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "Periodos.findByPeriodo", query = "SELECT p FROM Periodos p WHERE p.periodo = :periodo"),
    @NamedQuery(name = "Periodos.findByFechaInicial", query = "SELECT p FROM Periodos p WHERE p.fechaInicial = :fechaInicial"),
    @NamedQuery(name = "Periodos.findByFechaFinal", query = "SELECT p FROM Periodos p WHERE p.fechaFinal = :fechaFinal"),
    @NamedQuery(name = "Periodos.findByDescripcion", query = "SELECT p FROM Periodos p WHERE p.descripcion = :descripcion")})
public class Periodos implements Serializable {
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
    @Size(min = 1, max = 10)
    @Column(name = "PERIODO")
    private String periodo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_INICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_FINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal;
    @Size(max = 500)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "TIPO_PERIODO", referencedColumnName = "CODIGO")
    @ManyToOne
    private Tipos tipoPeriodo;

    public Periodos() {
    }

    public Periodos(String periodo) {
        this.periodo = periodo;
    }

    public Periodos(String periodo, String usuario, String programa, Date fecha, Date fechaInicial, Date fechaFinal) {
        this.periodo = periodo;
        this.usuario = usuario;
        this.programa = programa;
        this.fecha = fecha;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
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

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tipos getTipoPeriodo() {
        return tipoPeriodo;
    }

    public void setTipoPeriodo(Tipos tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (periodo != null ? periodo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Periodos)) {
            return false;
        }
        Periodos other = (Periodos) object;
        if ((this.periodo == null && other.periodo != null) || (this.periodo != null && !this.periodo.equals(other.periodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amclos.model.Periodos[ periodo=" + periodo + " ]";
    }
    
}
