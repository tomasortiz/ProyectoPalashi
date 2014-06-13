/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services;

import com.amclos.model.Perfil;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Curiel
 */
public interface PerfilManager {
    
        public boolean save(Perfil perfil);

        public List<Perfil> getPerfil();
       // public List<UsuarioPerfil> getUsuarioPerfil(String idZonas);
        public boolean delete(String usuario, String programa, String codigo);
        public Perfil getPerfils (String codigo);
        public List<Perfil> getTipos();
}
