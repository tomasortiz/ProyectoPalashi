/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view;

import com.amclos.model.Institucion;
import com.amclos.model.ZonaGeografica;

import com.amclos.services.InstitucionManager;
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
public class InstitucionView {

    private String usuario = "usuario", programa = "IntitucionView.xhtml", repLegal;
    private String nombre, direccion, telefono, email;
    private Date fecha;
    private Long codigo, numEmpleados;
    private String codigoZona = "1";
    private String tipoInstitucion;
    private InstitucionManager institucionManager;
    private ZonaGeograficaManager zonaManager;
    private TiposManager tm;
    private List<Institucion> listInstituciones;
    private String idZonas;
    private List<ComboLista> clZonas,tiposInstitucion;
    private List<Institucion> institucionesParameter;
    private Institucion instituto;
    private SpringContext context;
    private Institucion institucionSelected;
    
    
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

    public InstitucionView() {
        context = SpringContext.getInstance();
        // Obtenemos el servicio ArbolManager
        institucionManager = (InstitucionManager) context.getBean("InstitucionManager");
        zonaManager = (ZonaGeograficaManager) context.getBean("ZonaGeograficaManager");
        tm = (TiposManager) context.getBean("TiposManager");
        clZonas = zonaManager.getComboZona();
        if (clZonas.size() > 0) {
            idZonas = ((ComboLista) clZonas.get(0)).getId();
        }
        listInstituciones = institucionManager.getInstitucion(idZonas);
        tiposInstitucion = tm.getTiposGrupoCombo("TIPO_INSTITUCION");
        instituto = new Institucion();

    }

    public void guardar() {
        try {
            instituto.setUsuario(usuario);
            instituto.setPrograma(programa);
            instituto.setCodigoZona(zonaManager.getZonaGeografica(idZonas));
            instituto.setTipoInstitucion(tm.getTipo(tipoInstitucion));
            boolean resultado = institucionManager.save(instituto);

            if (resultado) {
                System.out.println("Institucion Creada");
                listInstituciones = institucionManager.getInstitucion(idZonas);
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
        instituto = new Institucion();
    }

    public void eliminar() {
        boolean resultado = institucionManager.delete(institucionSelected.getUsuario(), institucionSelected.getPrograma(), institucionSelected.getCodigo());
        if (resultado) {
            System.out.println("institucion Eliminado");
            listInstituciones = institucionManager.getInstitucion(idZonas);
        } else {
            System.out.println("Error alinstitucionor favor contactar al administrador del sistema");
        }
    }

    public void handleChange(ValueChangeEvent event) {
        System.out.println("New value: " + event.getNewValue());
        listInstituciones = institucionManager.getInstitucion(String.valueOf(event.getNewValue()));
    }

    public void motrarInstitucion() {
    }

    public void actualizar() {
        institucionSelected.setUsuario(usuario);
        institucionSelected.setPrograma(programa);
        boolean resultado = institucionManager.save(institucionSelected);
        if (resultado) {
            System.out.println("Empleado Actualizado");
            listInstituciones = institucionManager.getInstitucion(idZonas);
            limpiar();
        } else {
            System.out.println("Erinstitucionpor favor contactar al administrador del sistema");
        }
    }

    public void limpiar() {
        setNombre(null);
        setDireccion(null);
        setEmail(null);
        setNumEmpleados(null);
        setRepLegal(null);
        setTelefono(null);

    }

    public Institucion getInstitucionSelected() {
        return institucionSelected;
    }

    public void setInstitucionSelected(Institucion institucionSelected) {
        this.institucionSelected = institucionSelected;
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

    public String getRepLegal() {
        return repLegal;
    }

    public void setRepLegal(String repLegal) {
        this.repLegal = repLegal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Long getNumEmpleados() {
        return numEmpleados;
    }

    public void setNumEmpleados(Long numEmpleados) {
        this.numEmpleados = numEmpleados;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Institucion Selected", ((Institucion) event.getObject()).getNombre());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", ((Institucion) event.getObject()).getNombre());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * @return the codigoZona
     */
    public String getCodigoZona() {
        return codigoZona;
    }

    /**
     * @param codigoZona the codigoZona to set
     */
    public void setCodigoZona(String codigoZona) {
        this.codigoZona = codigoZona;
    }

    /**
     * @param tipoInstitucion the tipoInstitucion to set
     */
    public void setTipoInstitucion(String tipoInstitucion) {
        this.tipoInstitucion = tipoInstitucion;
    }

    /**
     * @return the listInstituciones
     */
    public List<Institucion> getListInstituciones() {
        return listInstituciones;
    }

    /**
     * @param listInstituciones the listInstituciones to set
     */
    public void setListInstituciones(List<Institucion> listInstituciones) {
        this.listInstituciones = listInstituciones;
    }

    public Institucion getInstituto() {
        return instituto;
    }

    public void setInstituto(Institucion instituto) {
        this.instituto = instituto;
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

    /**
     * @return the tiposInstitucion
     */
    public List<ComboLista> getTiposInstitucion() {
        return tiposInstitucion;
    }

    /**
     * @param tiposInstitucion the tiposInstitucion to set
     */
    public void setTiposInstitucion(List<ComboLista> tiposInstitucion) {
        this.tiposInstitucion = tiposInstitucion;
    }

    /**
     * @return the tipoInstitucion
     */
    public String getTipoInstitucion() {
        return tipoInstitucion;
    }
}
