/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services;

import com.amclos.model.Tipos;
import com.amclos.model.ZonaGeografica;
import com.amclos.view.dataModel.ComboLista;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface ZonaGeograficaManager {

    public boolean save(ZonaGeografica zonaG);    
     
    public boolean delete(String usuario, String programa, Long codigo);
    
    public ZonaGeografica getZonaGeografica(String codigo);
    
    public List<ZonaGeografica> getZonaGeograficas();
    
    public List<ComboLista> getComboZona();
    
    public List<ComboLista> getComboZona(String tipo);
    
}
