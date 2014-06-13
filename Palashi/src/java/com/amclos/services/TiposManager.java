/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services;

import com.amclos.model.Tipos;
import com.amclos.view.dataModel.ComboLista;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface TiposManager {

     
 
    public boolean save(Tipos tipos);
    public boolean delete(String codigo);
    

    public Tipos getTipo(String codigoTipo);
    public List<Tipos> getTipos();
    public List<Tipos> getTiposGrupo(String grupo);
    public List<ComboLista> getTiposGrupoCombo(String grupo);
    
}
