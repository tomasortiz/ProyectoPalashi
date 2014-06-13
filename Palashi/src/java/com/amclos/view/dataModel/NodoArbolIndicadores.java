/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view.dataModel;

import java.util.List;

/**
 *
 * @author Administrador
 */
public class NodoArbolIndicadores {
    private String codigo,nombre,tipo,valor;
    private boolean selected = false;
    private List<NodoArbolIndicadores> hijos;
    private String coordenadaX;
    private String coordenadaY;
    
    public NodoArbolIndicadores (String codigo,String nombre,String tipo){
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo   = tipo;
    }
    
        public NodoArbolIndicadores (String codigo,String nombre,String valor, String coordenadaX,String coordenadaY){
        this.codigo = codigo;
        this.nombre = nombre;
        this.valor   = valor;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
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

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the hijos
     */
    public List<NodoArbolIndicadores> getHijos() {
        return hijos;
    }

    /**
     * @param hijos the hijos to set
     */
    public void setHijos(List<NodoArbolIndicadores> hijos) {
        this.hijos = hijos;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
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
     * @return the coordenadaX
     */
    public String getCoordenadaX() {
        return coordenadaX;
    }

    /**
     * @param coordenadaX the coordenadaX to set
     */
    public void setCoordenadaX(String coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    /**
     * @return the coordenadaY
     */
    public String getCoordenadaY() {
        return coordenadaY;
    }

    /**
     * @param coordenadaY the coordenadaY to set
     */
    public void setCoordenadaY(String coordenadaY) {
        this.coordenadaY = coordenadaY;
    }
    
}
