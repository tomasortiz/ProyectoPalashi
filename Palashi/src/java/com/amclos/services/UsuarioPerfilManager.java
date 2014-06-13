/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services;

import com.amclos.model.Perfil;
import com.amclos.model.Usuario;
import com.amclos.model.UsuarioPerfil;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Curiel
 */
public interface UsuarioPerfilManager {
    
        public boolean save(String usuario, String programa, Usuario usuario1, Perfil codigoPerfil);

        public List<UsuarioPerfil> getUsuarioPerfil();
       // public List<UsuarioPerfil> getUsuarioPerfil(String idZonas);
        public boolean delete(String codigo); 
}
