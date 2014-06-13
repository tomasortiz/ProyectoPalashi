/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view;

import com.amclos.model.Usuario;
import com.amclos.services.SpringContext;
import com.amclos.services.UsuarioManager;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ultimate
 */
@ManagedBean (name = "bean_resetPass")
@ViewScoped
public class Bean_resetPass {

    /**
     * Creates a new instance of Bean_resetPass
     */
   private String passOld;
   private String pass1;
   private String pass2;
   private String usuario;
   private String nombre;
   private Usuario usuario_;
   private UsuarioManager user;
   private SpringContext context;
    public Bean_resetPass() {
        context = SpringContext.getInstance();
        user= (UsuarioManager)context.getBean("UsuarioManager");
         usuario = ((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getCodigo();
        nombre = ((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getNombre();
        usuario_ = ((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"));
    }

    public void actualizarPassOld()
    {
        
    if(pass1.equals(pass2))
    {
        if(passOld.equals(usuario_.getPass()))    
        {
            usuario_.setPass(pass2);
            try
            {
            user.updateUsuario(usuario_);
            FacesContext context = FacesContext.getCurrentInstance();            
            // COSTO DE RECURSOS
            Login login = (Login) context.getApplication().evaluateExpressionGet(context, "#{login}", Object.class);            
            login.setContrasena(pass2);            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Se ha cambiado la contraseña"));
            }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "Se produjo un error al cambiar la contraseña."));
            }
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "La Antigua contraseña no es correcta."));
            }
    }
    else{
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "No coincide la nueva contraseña."));
    }
        
    }
    public String getPassOld() {
        return passOld;
    }

    public void setPassOld(String passOld) {
        this.passOld = passOld;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getUsuario_() {
        return usuario_;
    }

    public void setUsuario_(Usuario usuario_) {
        this.usuario_ = usuario_;
    }
    
    
}
