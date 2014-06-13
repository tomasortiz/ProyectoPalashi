/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view.dataModel;

/**
 *
 * @author amerino
 */
public class ComboLista {
    private String id;
    private String descripcion;
    private String valor;

    public ComboLista(String id,String descripcion){
        this.id = id;
        this.descripcion = descripcion;
    }
    
    public ComboLista(String id,String descripcion, String valor){
        this.id = id;
        this.descripcion = descripcion;
        this.valor = valor;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String toString(){
        return descripcion;
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


}
