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
@Table(name = "INSTITUCION_VARIABLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InstitucionVariable.findAll", query = "SELECT i FROM InstitucionVariable i")})
public class InstitucionVariable implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InstitucionVariablePK institucionVariablePK;
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
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "VARIABLECODIGO", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Variable variable;
    @JoinColumn(name = "TIPO_INSTITUCION", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tipos tipos;

    public InstitucionVariable() {
    }

    public InstitucionVariable(InstitucionVariablePK institucionVariablePK) {
        this.institucionVariablePK = institucionVariablePK;
    }

    public InstitucionVariable(InstitucionVariablePK institucionVariablePK, String usuario, String programa, Date fechaModificacion) {
        this.institucionVariablePK = institucionVariablePK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModificacion = fechaModificacion;
    }

    public InstitucionVariable(String tipoInstitucion, String variablecodigo) {
        this.institucionVariablePK = new InstitucionVariablePK(tipoInstitucion, variablecodigo);
    }

    public InstitucionVariablePK getInstitucionVariablePK() {
        return institucionVariablePK;
    }

    public void setInstitucionVariablePK(InstitucionVariablePK institucionVariablePK) {
        this.institucionVariablePK = institucionVariablePK;
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

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public Tipos getTipos() {
        return tipos;
    }

    public void setTipos(Tipos tipos) {
        this.tipos = tipos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (institucionVariablePK != null ? institucionVariablePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InstitucionVariable)) {
            return false;
        }
        InstitucionVariable other = (InstitucionVariable) object;
        if ((this.institucionVariablePK == null && other.institucionVariablePK != null) || (this.institucionVariablePK != null && !this.institucionVariablePK.equals(other.institucionVariablePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amclos.model.InstitucionVariable[ institucionVariablePK=" + institucionVariablePK + " ]";
    }
    
}
