/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view;

import com.amclos.model.Institucion;
import com.amclos.model.Usuario;
import com.amclos.model.ZonaGeografica;
import com.amclos.services.InstitucionManager;
import com.amclos.services.SpringContext;
import com.amclos.services.UsuarioManager;
import com.amclos.services.ZonaGeograficaManager;
import com.amclos.view.dataModel.ComboLista;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Curiel
 */
@ManagedBean
@SessionScoped
public class UsuarioView {
    private UsuarioManager usuarioManager;
     private SpringContext context;
     private String usuario ="Usuario";
     private String programa ="UsuarioView.xhtm";
     private Date fecha;
     private String codigo,  nombre,  apellidos,  tipoUsuario="TUS001",    email,  institucioncodigo;
     private Date fechaAlta,  fechaBaja;
     private String pass;
     private List<Usuario> listUsuarios;
     private String idInstituciones;
     private List<Institucion> clInstituciones;
     private List<ZonaGeografica> clZonaGeo;
     private Usuario usuarioSelected;
     private  InstitucionManager institucionManager;
     private  ZonaGeograficaManager zonaGeografica;
     private String idZongeo;
     private String pasNuew;
     private String password3;
     private boolean activeBotonCalcular;





    @ManagedProperty("#{login}")
    private Login login;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
    public String init() {
        usuario = login.getUsuario();
        return "";
    }
    
    

    /**
     * Creates a new instance of UsuarioView
     */
    public UsuarioView() {
        context = SpringContext.getInstance();
        // Obtenemos el servicio ArbolManager
        listUsuarios= new ArrayList<>();
        clInstituciones= new ArrayList<>();
        clZonaGeo= new ArrayList<>();
        usuarioManager = (UsuarioManager) context.getBean("UsuarioManager");        
        institucionManager = (InstitucionManager) context.getBean("InstitucionManager");
        zonaGeografica=(ZonaGeograficaManager) context.getBean("ZonaGeograficaManager");
        clZonaGeo = zonaGeografica.getZonaGeograficas();//obtenemos la lista de zona geografica    
        activeBotonCalcular=true;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstitucioncodigo() {
        return institucioncodigo;
    }

    public void setInstitucioncodigo(String institucioncodigo) {
        this.institucioncodigo = institucioncodigo;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<Usuario> getListUsuarios() {
        return listUsuarios;
    }

    public void setListUsuarios(List<Usuario> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    public String getIdInstituciones() {
        return idInstituciones;
    }

    public void setIdInstituciones(String idInstituciones) {
        this.idInstituciones = idInstituciones;
    }

    public List<Institucion> getClZonas() {
        return clInstituciones;
    }

    public void setClZonas(List<Institucion> clInstituciones) {
        this.clInstituciones = clInstituciones;
    }

    public List<Institucion> getClInstituciones() {
        return clInstituciones;
    }

    public void setClInstituciones(List<Institucion> clInstituciones) {
        this.clInstituciones = clInstituciones;
    }

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }
    
   
    
  public void guardar() {
        try {                  
           boolean resultado = usuarioManager.save("Usuario", "usuarioVire.xhtml",   codigo, nombre, apellidos,  tipoUsuario,  email, idInstituciones,  pass);
            if (resultado) {// esto es de momento
                System.out.println("UsuarioCreado");
                listUsuarios = usuarioManager.getUsuarios(idInstituciones);
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "El Usuario ha sido creado correctamente.", ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                "Error al intentar crear usuario.", ""));                
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema.", ""));
        } finally {
            limpiarCampos();
        }

    }

    public void limpiarCampos() {
        
        codigo = "0";
        nombre = "";
        pass = "";
        apellidos = "";
        email = "";

       // subIndicadorNuevo = new SubIndicador();
        //variableNueva = new Variable();
        
        
        
    }    
    public void eliminar() {
        boolean resultado = usuarioManager.delete(usuarioSelected.getUsuario(), usuarioSelected.getPrograma(), usuarioSelected.getCodigo());
        if (resultado) {
            System.out.println("institucion Eliminado");
            listUsuarios = usuarioManager.getUsuarios(idInstituciones);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
            "Se ha eliminado el usuario con éxito.", "")); 
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
            "No se ha podido eliminar este usuario.", "")); 
        }
    }

    public void buscarInstitucion()
    {
    if(idZongeo!=null && !idZongeo.equals(""))    
    {   
        Object obj= institucionManager.getInstitucion(idZongeo);//Cargamos las instituciones por codgo de zona
        if(obj instanceof List){
           // idInstituciones = clInstituciones.get(0).getCodigo();
          //  listUsuarios = usuarioManager.getUsuarios(idInstituciones);
            clInstituciones = (List<Institucion>)obj;
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Exitoso", "encontraron "+clInstituciones.size()+" Instituciones."));      
        }   
        else
        {
        idInstituciones="";
        listUsuarios= new ArrayList<>();
        clInstituciones= new ArrayList<>();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "No hay registros", ""));   
        }
    }
    }

     public void listarUsuarios()
    {
    if(idInstituciones!=null && !idInstituciones.equals(""))       
    {   
        Object obj=usuarioManager.getUsuarios(idInstituciones);
      
        if(obj instanceof List){
            listUsuarios = (List<Usuario>) obj;          
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Exitoso", "encontraron "+listUsuarios.size()+" usuarios"));                
        }
        else
        {
        listUsuarios= new ArrayList<>();
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "No hay registros.", "No se encontraron usuarios"));
        }
    }
    }
     
    public void actualizar_() {
        boolean resultado;
        try
        {                                 
            usuarioManager.updateUsuario(usuarioSelected);  
            resultado=true;                      
        }catch(Exception e){
        resultado=false;
        }
        if (resultado) {
            listUsuarios = usuarioManager.getUsuarios(idInstituciones);
            limpiarCampos();
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Actualizado Correctamente.", usuarioSelected.getNombre()));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error al Actualizar.", usuarioSelected.getNombre()));
        }
    }

    

    public String getIdZongeo() {
        return idZongeo;
    }

    public void setIdZongeo(String idZongeo) {
        this.idZongeo = idZongeo;
    }

    

    public List<ZonaGeografica> getClZonaGeo() {        
		return clZonaGeo;
    }

    public void setClZonaGeo(List<ZonaGeografica> clZonaGeo) {
        this.clZonaGeo = clZonaGeo;
    }

    /**
     * @return the pasNuew
     */
    public String getPasNuew() {
        return pasNuew;
    }

    /**
     * @param pasNuew the pasNuew to set
     */
    public void setPasNuew(String pasNuew) {
        this.pasNuew = pasNuew;
    }

   
    
public String getPassword3() {
        return password3;
    }
 
    public void setPassword3(String password3) {
        this.password3 = password3;
    }    
  public boolean isActiveBotonCalcular() {
        return activeBotonCalcular;
    }

    public void setActiveBotonCalcular(boolean activeBotonCalcular) {
        this.activeBotonCalcular = activeBotonCalcular;
    } 
}
