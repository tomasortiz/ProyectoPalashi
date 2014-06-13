/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services;


import com.amclos.model.Institucion;
import com.amclos.view.dataModel.ComboLista;
import com.amclos.view.dataModel.ComboLista;

import com.amclos.model.ZonaGeografica;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Curiel
 */
public interface InstitucionManager {
    
        public boolean save(Institucion instituto);
        
        public List<Institucion> getInstitucion();
        
        public List<Institucion> getInstitucion(String idZonas);
        
        public boolean delete(String usuario, String programa, Long codigo);   
        
        public Institucion getInstitucions(String institucioncodigo);
        
        public List<ComboLista> getComboInstitucion();
        public List<ComboLista> getComboInstitucion(String codigoZo);
}
