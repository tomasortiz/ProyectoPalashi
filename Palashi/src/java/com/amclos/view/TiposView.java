/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view;

import com.amclos.model.Tipos;
import com.amclos.services.SpringContext;
import com.amclos.services.TiposManager;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Curiel
 */
@ManagedBean
@SessionScoped
public class TiposView {

    private String usuario = "Manual";
    private String programa = "TiposView.xhtml";
    private String codigo;
    private String descripcion;
    private String grupo;
    private Short valor;
    
    private TiposManager tiposManager;
    private List<Tipos> listTipos;
    private SpringContext context;
    private Tipos tiposSelected, tipos;

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
    public TiposView() {
          
        context = SpringContext.getInstance();
        tiposManager = (TiposManager) context.getBean("TiposManager");
        listTipos = tiposManager.getTipos();
        tipos = new Tipos();
    }
    public void guardar() {
        try {
            tipos.setUsuario(usuario);
            tipos.setPrograma(programa);
            boolean resultado = tiposManager.save(tipos);

            if (resultado) {
    
                listTipos = tiposManager.getTipos();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Tipo Creado Correctamente.", ""));
            } else {
                System.out.println("Error al crear ");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                "Error al Crear Tipo.", ""));                
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
        tipos = new Tipos();
    } 
    public void eliminar() {
        boolean resultado = tiposManager.delete(tiposSelected.getCodigo());
        if (resultado) {
            listTipos = tiposManager.getTipos();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
            "Tipo ha sido aeliminado con éxito..", ""));            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
            "Error al intentar eliminar Tipo..", ""));     
        }
    } 
    public void actualizar() {
        tiposSelected.setUsuario(usuario);        
        boolean resultado = tiposManager.save(tiposSelected);
        if (resultado) {
            listTipos = tiposManager.getTipos();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
            "Tipo ha sido actualizado con éxito..", ""));                 
            limpiarCampos();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
            "Error al intentar actualizar Tipo..", ""));    
        }
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public List<Tipos> getListTipos() {
        return listTipos;
    }

    public void setListTipos(List<Tipos> listTipos) {
        this.listTipos = listTipos;
    }

    public Tipos getTiposSelected() {
        return tiposSelected;
    }

    public void setTiposSelected(Tipos tiposSelected) {
        this.tiposSelected = tiposSelected;
    }

    public Short getValor() {
        return valor;
    }

    public void setValor(Short valor) {
        this.valor = valor;
    }

    public Tipos getTipos() {
        return tipos;
    }

    public void setTipos(Tipos tipos) {
        this.tipos = tipos;
    }
    
    
}
