/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Curiel
 */
@ManagedBean
@SessionScoped
public class UsuarioPerfilView {

    @ManagedProperty("#{login}")
    private Login login;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String init() {
       // usuario = login.getUsuario();
        return "";
    }
    public UsuarioPerfilView() {
        
    }
}
