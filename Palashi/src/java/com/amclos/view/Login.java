/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.amclos.view;

import com.amclos.model.Usuario;
import com.amclos.services.SpringContext;
import com.amclos.services.UsuarioManager;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import javax.faces.application.FacesMessage;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author 85154220
 */

@ManagedBean
@SessionScoped

public class Login implements Serializable{
    private SpringContext context ;
    private String usuario ,contrasena;
    private UsuarioManager usuarioManager;
    private boolean validarSession;
    
    /*Para mantener filtros del Modulo de Gestion en el Arbol E|G 
     durante la sesion*/
    private HashMap gestionTrafo;
    private String tipoRed;
    private String tipoUso;
    private String rango;
    private Usuario usuario_;
    
    public Login(){
        validarSession=true;
        context = SpringContext.getInstance();
        usuarioManager = (UsuarioManager) context.getBean("UsuarioManager");        
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
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
/**
 * Validacion de usuarios de la aplicacion
 * @return
 */
    public String validarLogin(){
      try{  
        if (!usuario.equals("") && usuario != null && !contrasena.equals("") && contrasena != null) {
            if (!usuarioManager.usuarioBloqueado(usuario)) {
                 usuario_ = usuarioManager.login(usuario, contrasena);
                if (usuario_ instanceof Usuario) {
                    validarSession = false;                    
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario_);
                    ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
                    String ctxPath =((ServletContext) ctx.getContext()).getContextPath();
                    ctx.redirect(ctxPath + "/Reportes/mapaRiesgo.xhtml");                    
                    return "inicio/home";
                } else {
                    validarSession = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Usuario y/o Contraseña Errada", "Intentelo otra vez!"));
                    return "index";
                }
            } else {
                validarSession = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Usuario bloqueado", "Pongase en contacto con el Administrador!"));
                    return "index";
            }

        } else {
            validarSession = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Debe ingresar su Usuario y Contraseña", ""));
            return "index";
        }
      }catch(Exception e){
            validarSession = true;
            usuario=null;
            contrasena=null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema", ""));
            return "index";
        }
    }

    public boolean isValidarSession() {
        return validarSession;
    }

    public void setValidarSession(boolean validarSession) {
        this.validarSession = validarSession;
    }    
    
    public void goInicio(){
        ExternalContext ctx = 
        FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
        try{
            ctx.redirect(ctxPath + "/index.xhtml");  
        }catch(IOException e){       
        }
    }
    
    public void goVariableSession(String variable)
    {
    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("variable", variable);
    }
    
    
    public void validateSession(String pagina) {
        //FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");            
        ExternalContext ctx =FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath =((ServletContext) ctx.getContext()).getContextPath();
        try {                      
            if (!usuarioManager.validarUrl(usuario, pagina)){
                ctx.redirect(ctxPath + "/index.xhtml");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }    
     public void logout() throws IOException {                
         ExternalContext ctx =FacesContext.getCurrentInstance().getExternalContext();
      //  String ctxPath =((ServletContext) ctx.getContext()).getContextPath();
        RequestContext context1 = RequestContext.getCurrentInstance();		                  
        String ctxPath =((ServletContext) ctx.getContext()).getContextPath();

            // Usar el contexto de JSF para invalidar la sesión,
            // NO EL DE SERVLETS (nada de HttpServletRequest)
            ((HttpSession) ctx.getSession(false)).invalidate();

            // Redirección de nuevo con el contexto de JSF,
            // si se usa una HttpServletResponse fallará.
            // Sin embargo, como ya está fuera del ciclo de vida 
            // de JSF se debe usar la ruta completa -_-U
            validarSession = true;
            ctx.redirect(ctxPath + "/index.xhtml");                          
    }
     
    public void gestionarTrafos() {
        gestionTrafo = new HashMap();
        gestionTrafo.put("TIPO_USO", tipoUso);
        gestionTrafo.put("TIPO_RED", tipoRed);
        gestionTrafo.put("RANGO", rango);
    }
     
    public HashMap getGestionTrafo() {
        return gestionTrafo;
    }

    public void setGestionTrafo(HashMap gestionTrafo) {
        this.gestionTrafo = gestionTrafo;
    }

    public String getTipoRed() {
        return tipoRed;
    }

    public void setTipoRed(String tipoRed) {
        this.tipoRed = tipoRed;
    }

    public String getTipoUso() {
        return tipoUso;
    }

    public void setTipoUso(String tipoUso) {
        this.tipoUso = tipoUso;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }


 /*public String salir(){         
  try {
    ExternalContext ctx = 
      FacesContext.getCurrentInstance().getExternalContext();
    ((HttpSession) ctx.getSession(false)).invalidate();
    validarSession=true;
    return "/login.xhtml";
  } catch (Exception ex) {
    return "/inicio.xhtml";
  }
  
 }
 
 public String salir(){
        validarSession = true;
        ExternalContext ctx =  FacesContext.getCurrentInstance().getExternalContext();
        ((HttpSession) ctx.getSession(false)).invalidate();
        return null;
    }*/

}
