/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services;

import com.amclos.model.Variable;
import com.amclos.view.dataModel.ComboLista;
import com.amclos.view.dataModel.NodoArbolIndicadores;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface VariableManager {
    public List<NodoArbolIndicadores> getVariablesArbol(String codigoIndicador);
    public boolean guardar(Variable variable);
    public boolean eliminar(String usuario, String programa, String codigo);
    public Variable getVariable(String codigo);
    public List<ComboLista> getTiposInstituciones(String codigo);
    public boolean guardarInstitucionVariable(String usuario, String programa, List<ComboLista> tiposInst, Variable variable);
    
    public List<ComboLista> getVariablesCombo(String codigoSubIndicador);
    
}
