/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services;

import com.amclos.model.SubIndicador;
import com.amclos.view.dataModel.ComboLista;
import com.amclos.view.dataModel.NodoArbolIndicadores;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface SubIndicadorManager {
    public List<NodoArbolIndicadores> getSubIndicadoresArbol(String codigoIndicador);
    public boolean guardar(SubIndicador subindicador);
    public boolean eliminar(String usuario, String programa, String codigo);
    public SubIndicador getSubIndicador(String codigo);
    
    public List<ComboLista> getSubIndicadoresCombo(String codigoIndicador);
}
