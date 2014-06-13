/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.amclos.services;

import com.amclos.model.Perfil;
import com.amclos.model.Tipos;
import com.amclos.model.Usuario;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 85154220
 */
public interface UsuarioManager {

    public Usuario login(String usuario,String contrasena);

    //  public Usuario loginUser(String usuario,String contrasena);
    
    public void save(String usuario,String contrasena);

	
    //public boolean save(String usuario, String programa,String codigo,String nombre,String apellidos,String tipoUsuario,String  email,String idInstituciones,String  pass);
    //public boolean save(Usuario user);

    public boolean save(String usuario, String programa, String codigo, String nombre,String apellidos, String tipoUsuario, String email, String institucioncodigo, String pass);    
    
    public List<Usuario> getUsuariosAll();
    
    public void saveUsuarioPerfil(String usuarioLogueado, String programa, Usuario usuario, Perfil perfil);
    
    public Perfil getPerfilDeUsuario(String coduser);
    
    public Usuario getUsuario(String codigo);
    
    public void updateUsuario(Usuario usuario);
    
    public boolean usuarioBloqueado(String usuario); 
    
    public boolean existeUsuario (String coduser);
    
    public boolean validarUrl (String usuario, String pagina);

    public List<Usuario> getUsuarios(String idInstituciones);

    public boolean delete(String usuario, String programa, String codigo);
    
    public List<Usuario> getUsuarios();
}
