/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services;

import com.amclos.model.Indicador;
import com.amclos.view.dataModel.ComboLista;
import com.amclos.view.dataModel.NodoArbolIndicadores;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface IndicadorManager {
    public List<NodoArbolIndicadores> getIndicadoresArbol();
    public boolean guardar(String usuario, String programa, String codigo , String nombre,String descripcion, String relevancia, String peso,String tipoEscala);
    public boolean eliminar(String usuario, String programa, String codigo);
    public Indicador getIndicador(String codigo);
    
    //LOB.201403.20.INI
    public List<NodoArbolIndicadores> getIndicadorRiesgo(String periodo, String tipoInd);
    public List getPeriodo();
    public String getZoomMapa();
    public List<Indicador> getIndicador();
    //LOB.201403.20.FIN
    
    public List<ComboLista> getIndicadoresCombo();
    
}
