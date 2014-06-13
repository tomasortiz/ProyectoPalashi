/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view;

import com.amclos.model.Parametro;
import com.amclos.services.ParametroManager;
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
public class ParametroView {

    private ParametroManager parametroManager;
    private List<Parametro> listParametros;
    private SpringContext context;
    private Parametro parametroSelected, parametro;
    private String usuario = "Usuario", programa = "ParametroView.xhtml";
    private String idParametro, nombre, valor;

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
    
    public ParametroView() {
        context = SpringContext.getInstance();
        parametroManager = (ParametroManager) context.getBean("ParametroManager");
        listParametros = parametroManager.getParametro();
        parametro = new Parametro ();
    }

    public void guardar() {
        try {
            parametro.setUsuario(usuario);
            parametro.setPrograma(programa);
            boolean resultado = parametroManager.save(parametro);

            if (resultado) {
                listParametros = parametroManager.getParametro();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Parámetro creado Correctamente.", ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Error alintentar crear Parámetro", ""));
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
        parametro = new Parametro ();
    }

    public void eliminar() {
        boolean resultado = parametroManager.delete(parametroSelected.getIdParametro());
        if (resultado) {
            listParametros = parametroManager.getParametro();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
            "Se ha eliminado el parámetro con éxito.", "")); 
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
            "No se ha podido eliminar este parámetro.", "")); 
        }
    }

    public void actualizar() {
        boolean resultado = parametroManager.save(parametroSelected); //este último parametro, no lo tengo claro.
        if (resultado) {
            listParametros= parametroManager.getParametro();
            limpiarCampos();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
            "Se ha actualizado el parámetro con éxito.", ""));             
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
            "Error al intentar actualizar parámetro.", ""));   
        }
    }

    public List<Parametro> getListParametros() {
        return listParametros;
    }

    public void setListParametros(List<Parametro> listParametros) {
        this.listParametros = listParametros;
    }

    public Parametro getParametroSelected() {
        return parametroSelected;
    }

    public void setParametroSelected(Parametro parametroSelected) {
        this.parametroSelected = parametroSelected;
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

    public String getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(String idParametro) {
        this.idParametro = idParametro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Parametro getParametro() {
        return parametro;
    }

    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
    }
    
    
}
