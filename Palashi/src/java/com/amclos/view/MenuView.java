/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view;

import com.amclos.model.Menu;
import com.amclos.model.Usuario;
import com.amclos.services.MenuManager;
import com.amclos.services.SpringContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Administrador
 */

@ManagedBean
@SessionScoped
public class MenuView implements Serializable {
    private List<Menu> menus;    
    private SpringContext context;
    private MenuManager menuManager;
    private String usuario, nombre;
    public MenuView(){
        context = SpringContext.getInstance();
        // Obtenemos el servicio ArbolManager
        menuManager = (MenuManager) context.getBean("MenuManager");
        usuario = ((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getCodigo();
        nombre = ((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getNombre();
        
        menus = menuManager.getMenuPrincipal(usuario);
        /*Menu ini = new Menu(); 
        ini.setNombre("Init");
        ini.setUrl("init.xhtml");
        menus = new ArrayList<Menu>();
        menus.add(ini);*/
    }

    /**
     * @return the menus
     */
    public List<Menu> getMenus() {
        return menus;
    }

    /**
     * @param menus the menus to set
     */
    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
