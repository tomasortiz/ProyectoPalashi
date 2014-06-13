/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.map.LatLng;

/**
 *
 * @author Ultimate
 */
@ManagedBean
@ViewScoped
public class Latitud {

    /**
     * Creates a new instance of Latitud
     */
    LatLng coord1;
    String Nombre;
    public Latitud() {
    }

    public Latitud(LatLng coord1, String Nombre) {
        this.coord1 = coord1;
        this.Nombre = Nombre;
    }
    

    public LatLng getCoord1() {
        return coord1;
    }

    public void setCoord1(LatLng coord1) {
        this.coord1 = coord1;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
}
