/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view;

/**
 *
 * @author Larry
 */
import com.amclos.view.dataModel.NodoArbolIndicadores;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class MapBean implements Serializable{

    private MapModel advancedModel;
    private Marker marker;
    private String cordX;
    private String cordY;
    private String nombre;
    private String zoom;
    private String center;
    private String valor;
    private String codigo;
    private Double valorRiesgo;
   // private String zoomTipoindicador;
    

    public MapBean() {
        advancedModel = new DefaultMapModel();
    }

    public void createMapa() {
        
        LatLng coord = new LatLng(Double.parseDouble(cordY), Double.parseDouble(cordX));
        advancedModel.addOverlay(new Marker(coord,"Codigo:"+ getCodigo()+":"+getNombre()+ " : " + getValorRiesgo() + "%"));

    }

    public MapModel getAdvancedModel() {
        return advancedModel;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }

    public Marker getMarker() {
        return marker;
    }

    public void setNodo(NodoArbolIndicadores node) {
        this.cordX            = node.getCoordenadaX();
        this.cordY            = node.getCoordenadaY();
        this.nombre           = node.getNombre();
        this.setValor(node.getValor());
        this.setCodigo(node.getCodigo());
        setZoom(zoom); 
        setCenter(node);
        createMapa();
    }
    

    
      public void setNodoList(NodoArbolIndicadores node ) {
        advancedModel = new DefaultMapModel();
         List nodeList = node.getHijos() ;
         setZoom(zoom); 
         setCenter(node);
        if (nodeList != null) {
            
            for (int i = 0; i < nodeList.size(); i++) {
                 NodoArbolIndicadores nodoData = (NodoArbolIndicadores) nodeList.get(i);
                  this.cordX            = nodoData.getCoordenadaX();
                    this.cordY            = nodoData.getCoordenadaY();
                    this.nombre           = nodoData.getNombre();
                    this.setValor(nodoData.getValor());
                    this.setCodigo(nodoData.getCodigo());
            }
        }
      }
     
    

    public void setZoom(String mapa) {
        zoom = mapa;
    }

    public String getZoom() {
        return zoom;
    }
    
    /*
    public HashMap getZoomTipoindicador() {
        return zoomTipoindicador;
    }


    public void setZoomTipoindicador(HashMap zoomTipoindicador) {
        this.zoomTipoindicador = zoomTipoindicador;
    }
   */

    public String getCenter() {
        return center;
    }


    public void setCenter(NodoArbolIndicadores node) {
        this.center = node.getCoordenadaY() + "," + node.getCoordenadaX();
    }


    public String getNombre() {
        return nombre;
    }

    public Double getValorRiesgo() {
        return valorRiesgo;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


}