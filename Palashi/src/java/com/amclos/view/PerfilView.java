/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view;

import com.amclos.model.Perfil;
import com.amclos.services.PerfilManager;
import com.amclos.services.SpringContext;
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
public class PerfilView {

    private String codigo, nombre;
    private String usuario = "Manual";
    private String programa = "PerfilView.xhtml";

    private PerfilManager perfilManager;
    private List<Perfil> listPerfiles;
    private SpringContext context;
    private Perfil perfilSelected, perfil;    

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
    
    public PerfilView() {
         context = SpringContext.getInstance();
        perfilManager = (PerfilManager) context.getBean("PerfilManager");
        listPerfiles = perfilManager.getPerfil();
        perfil = new Perfil ();
    }

    public void guardar() {
        try {
            perfil.setUsuario(usuario);
            perfil.setPrograma(programa);
            boolean resultado = perfilManager.save(perfil);

            if (resultado) {
                System.out.println("Tipo Creado");
                listPerfiles= perfilManager.getPerfil();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Perfil Creado Correctamente.", ""));
            } else {
              
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Error al Crear Perfil.", ""));
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
        perfil = new Perfil();
    }

    public void eliminar() {
        boolean resultado = perfilManager.delete(perfilSelected.getUsuario(), perfilSelected.getPrograma(), perfilSelected.getCodigo());
        if (resultado) {
            listPerfiles = perfilManager.getPerfil();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Perfil eliminado correctamente.", ""));            
        } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Error al intentar eliminar Perfil.", ""));            
        }
    }

    public void actualizar() {
        perfilSelected.setUsuario(usuario);
        boolean resultado = perfilManager.save(perfilSelected);
        if (resultado) {
            listPerfiles = perfilManager.getPerfil();
            limpiarCampos();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Perfil Actualizado con éxito!", ""));            
        } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Error al intentar actualizar Perfil.", ""));
        }
    }
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Institucion Selected", ((Perfil) event.getObject()).getNombre());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", ((Perfil) event.getObject()).getNombre());

        FacesContext.getCurrentInstance().addMessage(null, msg);
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

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public List<Perfil> getListPerfiles() {
        return listPerfiles;
    }

    public void setListPerfiles(List<Perfil> listPerfiles) {
        this.listPerfiles = listPerfiles;
    }

    public Perfil getPerfilSelected() {
        return perfilSelected;
    }

    public void setPerfilSelected(Perfil perfilSelected) {
        this.perfilSelected = perfilSelected;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    
}
