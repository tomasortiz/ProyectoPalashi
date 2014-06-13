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
@Table(name = "ZONA_GEOGRAFICA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ZonaGeografica.findAll", query = "SELECT z FROM ZonaGeografica z")})
public class ZonaGeografica implements Serializable {
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
    @Column(name = "CODIGO")
    private Long codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "ESTADO")
    private String estado;
    @Size(max = 30)
    @Column(name = "COORD_X")
    private String coordX;
    @Size(max = 30)
    @Column(name = "COORD_Y")
    private String coordY;
    @Column(name = "COD_PADRE")
    private Long codPadre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zonaGeografica")
    private List<Riesgos> riesgosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoZona")
    private List<Institucion> institucionList;
    @JoinColumn(name = "TIPO_ZONA", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Tipos tipoZona;

    public ZonaGeografica() {
    }

    public ZonaGeografica(Long codigo) {
        this.codigo = codigo;
    }

    public ZonaGeografica(Long codigo, String usuario, String programa, Date fecha, String descripcion, String estado) {
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

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCoordX() {
        return coordX;
    }

    public void setCoordX(String coordX) {
        this.coordX = coordX;
    }

    public String getCoordY() {
        return coordY;
    }

    public void setCoordY(String coordY) {
        this.coordY = coordY;
    }

    public Long getCodPadre() {
        return codPadre;
    }

    public void setCodPadre(Long codPadre) {
        this.codPadre = codPadre;
    }

    @XmlTransient
    public List<Riesgos> getRiesgosList() {
        return riesgosList;
    }

    public void setRiesgosList(List<Riesgos> riesgosList) {
        this.riesgosList = riesgosList;
    }

    @XmlTransient
    public List<Institucion> getInstitucionList() {
        return institucionList;
    }

    public void setInstitucionList(List<Institucion> institucionList) {
        this.institucionList = institucionList;
    }

    public Tipos getTipoZona() {
        return tipoZona;
    }

    public void setTipoZona(Tipos tipoZona) {
        this.tipoZona = tipoZona;
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
        if (!(object instanceof ZonaGeografica)) {
            return false;
        }
        ZonaGeografica other = (ZonaGeografica) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amclos.model.ZonaGeografica[ codigo=" + codigo + " ]";
    }
    
}
