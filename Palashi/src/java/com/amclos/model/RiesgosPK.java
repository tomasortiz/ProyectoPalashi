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
public class RiesgosPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERIODO")
    private int periodo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_ZONA")
    private long codigoZona;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "TIPO_INDICADOR")
    private String tipoIndicador;

    public RiesgosPK() {
    }

    public RiesgosPK(int periodo, String codigo, long codigoZona, String tipoIndicador) {
        this.periodo = periodo;
        this.codigo = codigo;
        this.codigoZona = codigoZona;
        this.tipoIndicador = tipoIndicador;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public long getCodigoZona() {
        return codigoZona;
    }

    public void setCodigoZona(long codigoZona) {
        this.codigoZona = codigoZona;
    }

    public String getTipoIndicador() {
        return tipoIndicador;
    }

    public void setTipoIndicador(String tipoIndicador) {
        this.tipoIndicador = tipoIndicador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) periodo;
        hash += (codigo != null ? codigo.hashCode() : 0);
        hash += (int) codigoZona;
        hash += (tipoIndicador != null ? tipoIndicador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RiesgosPK)) {
            return false;
        }
        RiesgosPK other = (RiesgosPK) object;
        if (this.periodo != other.periodo) {
            return false;
        }
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        if (this.codigoZona != other.codigoZona) {
            return false;
        }
        if ((this.tipoIndicador == null && other.tipoIndicador != null) || (this.tipoIndicador != null && !this.tipoIndicador.equals(other.tipoIndicador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amclos.model.RiesgosPK[ periodo=" + periodo + ", codigo=" + codigo + ", codigoZona=" + codigoZona + ", tipoIndicador=" + tipoIndicador + " ]";
    }
    
}
