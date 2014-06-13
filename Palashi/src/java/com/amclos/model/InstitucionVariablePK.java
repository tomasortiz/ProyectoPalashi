/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Administrador
 */
@Embeddable
public class InstitucionVariablePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "TIPO_INSTITUCION")
    private String tipoInstitucion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "VARIABLECODIGO")
    private String variablecodigo;

    public InstitucionVariablePK() {
    }

    public InstitucionVariablePK(String tipoInstitucion, String variablecodigo) {
        this.tipoInstitucion = tipoInstitucion;
        this.variablecodigo = variablecodigo;
    }

    public String getTipoInstitucion() {
        return tipoInstitucion;
    }

    public void setTipoInstitucion(String tipoInstitucion) {
        this.tipoInstitucion = tipoInstitucion;
    }

    public String getVariablecodigo() {
        return variablecodigo;
    }

    public void setVariablecodigo(String variablecodigo) {
        this.variablecodigo = variablecodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoInstitucion != null ? tipoInstitucion.hashCode() : 0);
        hash += (variablecodigo != null ? variablecodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InstitucionVariablePK)) {
            return false;
        }
        InstitucionVariablePK other = (InstitucionVariablePK) object;
        if ((this.tipoInstitucion == null && other.tipoInstitucion != null) || (this.tipoInstitucion != null && !this.tipoInstitucion.equals(other.tipoInstitucion))) {
            return false;
        }
        if ((this.variablecodigo == null && other.variablecodigo != null) || (this.variablecodigo != null && !this.variablecodigo.equals(other.variablecodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amclos.model.InstitucionVariablePK[ tipoInstitucion=" + tipoInstitucion + ", variablecodigo=" + variablecodigo + " ]";
    }
    
}
