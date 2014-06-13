/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view;

import com.amclos.model.Tipos;
import com.amclos.model.ZonaGeografica;
import com.amclos.services.SpringContext;
import com.amclos.services.TiposManager;
import com.amclos.services.ZonaGeograficaManager;
import com.amclos.view.dataModel.ComboLista;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Curiel
 */
@ManagedBean
@SessionScoped
public class ZonaGeograficaView {

    private String usuario = "Usuario";
    private String programa = "ZonaGeograficaView.xhtml";
    private String tipoZona = "TZ001";
    private String idZonas;
    private Long codPadre;
    private List<ComboLista> clZonas;
    private ZonaGeograficaManager zonaManager;
    private TiposManager tm;
    private List<ZonaGeografica> listZonas;
    private ZonaGeografica zonaSelected, zonaG;
    private SpringContext context;
    
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
    public ZonaGeograficaView() {
        context = SpringContext.getInstance();
        zonaManager = (ZonaGeograficaManager) context.getBean("ZonaGeograficaManager");
        tm = (TiposManager) context.getBean("TiposManager");
        listZonas = zonaManager.getZonaGeograficas();

        clZonas = zonaManager.getComboZona();
        if (clZonas.size() > 0) {
            codPadre = Long.parseLong(((ComboLista) clZonas.get(0)).getId());
        }
        
        zonaG = new ZonaGeografica ();
    }

    public List<ZonaGeografica> getListZonas() {
        return listZonas;
    }

    public void setListZonas(List<ZonaGeografica> listZonas) {
        this.listZonas = listZonas;
    }

    public ZonaGeografica getZonaSelected() {
        return zonaSelected;
    }

    public void setZonaSelected(ZonaGeografica zonaSelected) {
        this.zonaSelected = zonaSelected;
    }

    public void guardar() {


        try {
            zonaG.setUsuario(usuario);
            zonaG.setPrograma(programa);
            zonaG.setTipoZona(tm.getTipo(tipoZona));
            
            boolean resultado = zonaManager.save(zonaG);

            if (resultado) {
                System.out.println("Zona Geográfica  Creada Satisfactoriamente");
                listZonas = zonaManager.getZonaGeograficas();
                clZonas = zonaManager.getComboZona();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Institución Creada Correctamente.", ""));
            } else {
                System.out.println("Error al crear ");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Error al Crear Institución.", ""));
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
        zonaG = new ZonaGeografica();        
        codPadre = null;
        if (clZonas.size() > 0) {
            codPadre = Long.parseLong(((ComboLista) clZonas.get(0)).getId());
        }
    }

    public void eliminar() {
        boolean resultado = zonaManager.delete(zonaSelected.getUsuario(), zonaSelected.getPrograma(), zonaSelected.getCodigo());
        if (resultado) {
            listZonas = zonaManager.getZonaGeograficas();
            clZonas = zonaManager.getComboZona();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Zona eliminada con éxito!.", ""));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema, no se ha podido eliminar la Zona.", ""));
        }
    }

    public void reiniciar() {

        if (clZonas.size() > 0) {
            idZonas = ((ComboLista) clZonas.get(0)).getId();
        }
    }

    public void actualizar() {
        zonaSelected.setUsuario(usuario);
        zonaSelected.setPrograma(programa);
        boolean resultado = zonaManager.save(zonaSelected);

        if (resultado) {
            listZonas = zonaManager.getZonaGeograficas();
            clZonas = zonaManager.getComboZona();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La Zona Geográfica ha sido actualizada con éxito!", ""));

         
        } else {
          
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al intentar actualizar zona", ""));
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

    public String getTipoZona() {
        return tipoZona;
    }

    public void setTipoZona(String tipoZona) {
        this.tipoZona = tipoZona;
    }

    
    public Long getCodPadre() {
        return codPadre;
    }

    public void setCodPadre(Long codPadre) {
        this.codPadre = codPadre;
    }

    public String getIdZonas() {
        return idZonas;
    }

    public void setIdZonas(String idZonas) {
        this.idZonas = idZonas;
    }

    public List<ComboLista> getClZonas() {
        return clZonas;
    }

    public void setClZonas(List<ComboLista> clZonas) {
        this.clZonas = clZonas;
    }

    public ZonaGeografica getZonaG() {
        return zonaG;
    }

    public void setZonaG(ZonaGeografica zonaG) {
        this.zonaG = zonaG;
    }
    
    
    
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Zona Selected", ((ZonaGeografica) event.getObject()).getDescripcion());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("ZonaUnselected", ((ZonaGeografica) event.getObject()).getDescripcion());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleChange(ValueChangeEvent event) {
        if (event != null) {
            System.out.println("New value: " + event.getNewValue());
            //listZonas = zonaManager.getZonaGeograficas(String.valueOf(event.getNewValue()));

            codPadre = Long.parseLong(String.valueOf(event.getNewValue()));
        }
    }
}
