/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "RIESGOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Riesgos.findAll", query = "SELECT r FROM Riesgos r")})
public class Riesgos implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RiesgosPK riesgosPK;
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CODIGO_INDICADOR")
    private String codigoIndicador;
    @JoinColumn(name = "CODIGO_ZONA", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ZonaGeografica zonaGeografica;
    @JoinColumn(name = "TIPO_INDICADOR", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tipos tipos;

    public Riesgos() {
    }

    public Riesgos(RiesgosPK riesgosPK) {
        this.riesgosPK = riesgosPK;
    }

    public Riesgos(RiesgosPK riesgosPK, String usuario, String programa, Date fecha, String codigoIndicador) {
        this.riesgosPK = riesgosPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fecha = fecha;
        this.codigoIndicador = codigoIndicador;
    }

    public Riesgos(int periodo, String codigo, long codigoZona, String tipoIndicador) {
        this.riesgosPK = new RiesgosPK(periodo, codigo, codigoZona, tipoIndicador);
    }

    public RiesgosPK getRiesgosPK() {
        return riesgosPK;
    }

    public void setRiesgosPK(RiesgosPK riesgosPK) {
        this.riesgosPK = riesgosPK;
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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCodigoIndicador() {
        return codigoIndicador;
    }

    public void setCodigoIndicador(String codigoIndicador) {
        this.codigoIndicador = codigoIndicador;
    }

    public ZonaGeografica getZonaGeografica() {
        return zonaGeografica;
    }

    public void setZonaGeografica(ZonaGeografica zonaGeografica) {
        this.zonaGeografica = zonaGeografica;
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
        hash += (riesgosPK != null ? riesgosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Riesgos)) {
            return false;
        }
        Riesgos other = (Riesgos) object;
        if ((this.riesgosPK == null && other.riesgosPK != null) || (this.riesgosPK != null && !this.riesgosPK.equals(other.riesgosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amclos.model.Riesgos[ riesgosPK=" + riesgosPK + " ]";
    }
    
}
