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
@Table(name = "VARIABLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Variable.findAll", query = "SELECT v FROM Variable v")})
public class Variable implements Serializable {
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
    @Column(name = "CODIGO")
    private String codigo;
    @Size(max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "MEDICION")
    private String medicion;
    @JoinColumn(name = "ESTADO", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private Tipos estado;
    @JoinColumn(name = "COD_SUBINDICADOR", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false)
    private SubIndicador codSubindicador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "variablecod")
    private List<Preguntas> preguntasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "variable")
    private List<InstitucionVariable> institucionVariableList;

    public Variable() {
    }

    public Variable(String codigo) {
        this.codigo = codigo;
    }

    public Variable(String codigo, String usuario, String programa, Date fecha, String medicion) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.programa = programa;
        this.fecha = fecha;
        this.medicion = medicion;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMedicion() {
        return medicion;
    }

    public void setMedicion(String medicion) {
        this.medicion = medicion;
    }

    public Tipos getEstado() {
        return estado;
    }

    public void setEstado(Tipos estado) {
        this.estado = estado;
    }

    public SubIndicador getCodSubindicador() {
        return codSubindicador;
    }

    public void setCodSubindicador(SubIndicador codSubindicador) {
        this.codSubindicador = codSubindicador;
    }

    @XmlTransient
    public List<Preguntas> getPreguntasList() {
        return preguntasList;
    }

    public void setPreguntasList(List<Preguntas> preguntasList) {
        this.preguntasList = preguntasList;
    }

    @XmlTransient
    public List<InstitucionVariable> getInstitucionVariableList() {
        return institucionVariableList;
    }

    public void setInstitucionVariableList(List<InstitucionVariable> institucionVariableList) {
        this.institucionVariableList = institucionVariableList;
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
        if (!(object instanceof Variable)) {
            return false;
        }
        Variable other = (Variable) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amclos.model.Variable[ codigo=" + codigo + " ]";
    }
    
}
