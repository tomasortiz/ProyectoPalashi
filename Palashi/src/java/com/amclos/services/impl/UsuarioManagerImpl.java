/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.amclos.services.impl;

import com.amclos.dao.Dao;
import com.amclos.model.Perfil;
import com.amclos.model.Tipos;
import com.amclos.model.Usuario;
import com.amclos.model.UsuarioPerfil;
import com.amclos.model.UsuarioPerfilPK;
import com.amclos.services.EstadoManager;
import com.amclos.services.InstitucionManager;
import com.amclos.services.TiposManager;
//import com.amclos.model.view.Fecha;
import com.amclos.services.UsuarioManager;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */
@Service("UsuarioManager")
public class UsuarioManagerImpl implements UsuarioManager{
    @Autowired
    @Qualifier ("DaoHibernate")
    private Dao dao ;
    private short zero=0;
    
    @Autowired
    @Qualifier("TiposManager")
    private TiposManager tm;
    
    @Autowired
    @Qualifier("InstitucionManager")
    private InstitucionManager im;

    public Usuario login(String codigo, String contrasena) {
        String hql = "SELECT u FROM Usuario u "
                + "WHERE u.codigo = '" + codigo + "' and "
                + "u.estado='AC001'";
//         RequestContext context = RequestContext.getCurrentInstance();
         
        Usuario user = dao.findObject(hql);        
        if (user == null) {
            return null;
        }
        if (user.getPass().equals(contrasena)) {           //borrar intentos en la tabal usuario
           // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", user);
            //user.setIntentos(zero);
            //dao.persist(user);
            return user;
        } else {
            //aumentar intentos en la tabla usuario
            /*short userIntento = user.getIntentos()!=null?user.getIntentos():zero;
            short cont = (short) (userIntento+1);
            user.setIntentos(cont);
            dao.persist(user);*/
            return null;
        }
    }

 /*   @Override
    public void save(String usuario, String contrasena) {
        Usuario user = new Usuario();
        user.setUsuario("MIGRACION");
        user.setPrograma("AUTOMATICO");        
        user.setPass(contrasena);
        user.setUsuario(usuario);
        dao.persist(user);
    }    */
   
    @Override
    public boolean save(String usuario, String programa, String codigo, String nombre,String apellidos, String tipoUsuario, String email, String institucioncodigo, String pass){
      /*  Usuario user = new Usuario();
        user.setUsuario(usuario);
        user.setPrograma(programa);
        Fecha date = new Fecha();
        user.setFechaModif(date.getFechaSistema());
        user.setCodigo(codigo);
        user.setPass(contrasena);
        user.setNombre(nombre);
        //user.setTelefono(tel);
       // user.setEstado(estado);
        user.setFechaAlta(fecha_alta);
        user.setEmail(email);        
        
        dao.persist(user);
        saveUsuarioPerfil(usuario, programa, user, perfil);*/
        
        try {
            Tipos estate = tm.getTipo(EstadoManager.ACTIVO);
            Usuario user = new Usuario();
            user.setUsuario(usuario);
            user.setPrograma(programa);
            user.setFecha(Calendar.getInstance().getTime());
            user.setCodigo(codigo);
            user.setNombre(nombre);
            user.setApellidos(apellidos);
            user.setTipoUsuario(tm.getTipo(tipoUsuario));
           
            user.setEmail(email);
           
            user.setEstado( estate);
            //Cuidado con ese set.
            user.setInstitucioncodigo(im.getInstitucions(institucioncodigo));
            
            user.setFechaAlta(Calendar.getInstance().getTime());
            user.setFechaBaja(Calendar.getInstance().getTime());
            user.setPass(pass);
            dao.persist(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
       
    }
    
    @Override
    public List<Usuario> getUsuariosAll() {    
        String hql ="Select u from Usuario u order by u.nombre";
        return dao.find(hql);    
    }

    @Override
    public void saveUsuarioPerfil(String usuarioLogueado, String programa, Usuario usuario, Perfil perfil) {
     /*   darBajaUsuarioPerfil(usuario.getCodigo());
        UsuarioPerfil usuarioPerfil=new UsuarioPerfil();
        usuarioPerfil.setUsuario(usuarioLogueado);
        usuarioPerfil.setPrograma(programa);       
        Fecha date = new Fecha();
        usuarioPerfil.setFechaModif(date.getFechaSistema());
        usuarioPerfil.setUsuario1(usuario);
        usuarioPerfil.setPerfil(perfil);     
       
        UsuarioPerfilPK usuarioPerfilPK=new UsuarioPerfilPK(usuario.getCodigo(), perfil.getCodigo(), date.getFechaSistema());
        usuarioPerfil.setUsuarioPerfilPK(usuarioPerfilPK);
        
        dao.persist(usuarioPerfil);    */    
    }

    @Override
    public Perfil getPerfilDeUsuario(String coduser) {
        String hql = "select u.perfil from UsuarioPerfil u where u.usuario1.codigo='" + coduser + "' AND u.fechaBaja IS NULL order by u.fechaBaja desc";              
        return dao.findObject(hql);
    }

    @Override
    public Usuario getUsuario(String codigo) {
        String hql = "SELECT u FROM Usuario u WHERE u.codigo = '" + codigo+"'";
        return dao.findObject(hql);
    }
    
    public boolean actualizarContrasena(String codigo,String contrasenaActual,String nuevaContrasena){
        try{
        if(login(codigo,contrasenaActual) instanceof Usuario){
            Usuario usuario = getUsuario(codigo);
            usuario.setPass(nuevaContrasena);
            dao.persist(usuario);
            return true;
        }else{
            return false;
        }
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void updateUsuario(Usuario usuario) {
        dao.persistUpdate(usuario);
    }
    
    public void darBajaUsuarioPerfil(String idUsuario){
     /*   
        String hql="SELECT u FROM UsuarioPerfil u "
                + "WHERE u.usuarioPerfilPK.codigoUsuario ='"+idUsuario+"' "
                + "and u.fechaBaja IS NULL";
        
        if (!dao.find(hql).isEmpty()){
         List<UsuarioPerfil> data = dao.find(hql);
         for (Iterator it = data.iterator(); it.hasNext();) {
             UsuarioPerfil up = (UsuarioPerfil) it.next(); 
             Fecha fecha=new Fecha();
             up.setFechaBaja(fecha.getFecha());
             dao.persist(up);
         }
        }*/
             
    }
    
    /*public void logout() {
  ExternalContext ctx = 
      FacesContext.getCurrentInstance().getExternalContext();
  String ctxPath = 
      ((ServletContext) ctx.getContext()).getContextPath();

  try {
    // Usar el contexto de JSF para invalidar la sesión,
    // NO EL DE SERVLETS (nada de HttpServletRequest)
    ((HttpSession) ctx.getSession(false)).invalidate();

    // Redirección de nuevo con el contexto de JSF,
    // si se usa una HttpServletResponse fallará.
    // Sin embargo, como ya está fuera del ciclo de vida 
    // de JSF se debe usar la ruta completa -_-U
    ctx.redirect(ctxPath + "/faces/index.xhtml");
  } catch (IOException ex) {
    ex.printStackTrace();
  }
}*/

    @Override
    public boolean existeUsuario(String coduser) {
        String hql="SELECT u FROM Usuario u WHERE u.codigo = '" + coduser+"'";
        boolean existe=dao.findObject(hql)!=null?true:false;
        return existe; 
    }

    @Override
    public boolean usuarioBloqueado(String usuario) {        
       /* String hql ="SELECT p.valor FROM Parametro p WHERE p.idParametro ='LOGIN_MAXIMO_INTENTO'";
        String valor = dao.findObject(hql).toString();
        short maxIntento = Short.valueOf(valor);
        Usuario user = getUsuario(usuario); 
        if (user == null) {
            return false;
        }
    //    short userIntento = user.getIntentos()!=null?user.getIntentos():zero;
      // boolean bloqueado = userIntento >= maxIntento?
        //        true:false;
        //return bloqueado; */  
        return false;
    }

    @Override
    public boolean validarUrl(String usuario, String pagina) {
        String hql="select  up from UsuarioPerfil up, MenuPerfil mp\n" +
                   "where up.usuario1.codigo = '"+usuario+"'\n" +
                   "and  mp.perfil.codigo =up.codigoPerfil.codigo\n" +
                   "and mp.menu.url like '%"+pagina+"%'";
       System.out.println(hql);
         boolean permiso=dao.findObject(hql)!=null?true:false;
         return permiso;
    }

    @Override
    public boolean delete(String usuario, String programa, String codigo) {
      /*  String hql = "SELECT p FROM Usuario p WHERE p.codigo ='" + codigo + "'";
        Usuario user = dao.findObject(hql);
        try {
            dao.delete(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }   */
                try{
            Usuario user = getUsuario(codigo);
            user.setFecha(Calendar.getInstance().getTime());
            user.setUsuario(usuario);
            user.setPrograma(programa);
            user.setEstado(tm.getTipo(EstadoManager.INACTIVO));
            dao.persist(user);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Usuario> getUsuarios(String idInstituciones) {
        String hql;
        hql = "Select p from Usuario p"
                + " where p.institucioncodigo.codigo ='"+idInstituciones+"'"
                + "and p.estado='"+EstadoManager.ACTIVO+"'"
                + " order by p.nombre";
        return dao.find(hql);
    
    }


    @Override
    public List<Usuario> getUsuarios() {
        String hql;
        hql = "Select p from Usuario p "
                + "order by p.nombre";
        return dao.find(hql);
    
    }

    @Override
    public void save(String usuario, String contrasena) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 




}
