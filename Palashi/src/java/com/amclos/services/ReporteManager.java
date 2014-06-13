/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services;

import com.amclos.view.dataModel.ComboLista;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface ReporteManager {
    
    public List<ComboLista> getValoresHijos(String codigoPadre, String tipo, String codigoZona, String periodo);
    
    public String getValor(String codigo , String codigoZona, String periodo);
    
    public List<ComboLista> getValoresHistoricos(String codigo, String codigoZona);
    
    public List<ComboLista> getPeriodos();
    
    public List<ComboLista> getRanking(String Periodo);
    
    public List<ComboLista> getValoresMunicipios(List<ComboLista> lMunicipio, String tipo,String codigoElemento,String periodo);

}
