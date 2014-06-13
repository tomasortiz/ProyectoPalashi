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
@Table(name = "MENU_PERFIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MenuPerfil.findAll", query = "SELECT m FROM MenuPerfil m")})
public class MenuPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MenuPerfilPK menuPerfilPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "PROGRAMA")
    private String programa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_MODIF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;
    @Size(max = 5)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "CODIGO_PERFIL", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Perfil perfil;
    @JoinColumn(name = "CODIGO_MENU", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Menu menu;

    public MenuPerfil() {
    }

    public MenuPerfil(MenuPerfilPK menuPerfilPK) {
        this.menuPerfilPK = menuPerfilPK;
    }

    public MenuPerfil(MenuPerfilPK menuPerfilPK, String usuario, String programa, Date fechaModif) {
        this.menuPerfilPK = menuPerfilPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
    }

    public MenuPerfil(String codigoMenu, String codigoPerfil) {
        this.menuPerfilPK = new MenuPerfilPK(codigoMenu, codigoPerfil);
    }

    public MenuPerfilPK getMenuPerfilPK() {
        return menuPerfilPK;
    }

    public void setMenuPerfilPK(MenuPerfilPK menuPerfilPK) {
        this.menuPerfilPK = menuPerfilPK;
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

    public Date getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menuPerfilPK != null ? menuPerfilPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MenuPerfil)) {
            return false;
        }
        MenuPerfil other = (MenuPerfil) object;
        if ((this.menuPerfilPK == null && other.menuPerfilPK != null) || (this.menuPerfilPK != null && !this.menuPerfilPK.equals(other.menuPerfilPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amclos.model.MenuPerfil[ menuPerfilPK=" + menuPerfilPK + " ]";
    }
    
}
