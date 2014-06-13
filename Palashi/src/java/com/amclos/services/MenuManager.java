/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services;

import com.amclos.model.Menu;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface MenuManager {
    
    public List<Menu> getMenuPrincipal(String codigoUsuario);
    public boolean save (String usuario,String programa, String codigo, String codPadre, String nombre, Long orden, String icono,String url); 
    public boolean delete (String usuario, String programa, String codigo);
    public Menu getMenu(String codigo);
}
