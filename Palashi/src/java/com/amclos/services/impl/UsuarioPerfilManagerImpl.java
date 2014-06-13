/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services.impl;

import com.amclos.model.Perfil;
import com.amclos.model.Usuario;
import com.amclos.model.UsuarioPerfil;
import com.amclos.services.UsuarioPerfilManager;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Curiel
 */
public class UsuarioPerfilManagerImpl implements UsuarioPerfilManager{

    @Override
    public boolean save(String usuario,String programa, Usuario usuario1, Perfil codigoPerfil) {
      
        return false;
    }

    @Override
    public List<UsuarioPerfil> getUsuarioPerfil() {
      return null;
    }

    @Override
    public boolean delete(String codigo) {
        return false;
    }
    
}
