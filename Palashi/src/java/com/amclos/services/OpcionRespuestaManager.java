/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services;

import com.amclos.model.OpcionesRespuesta;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface OpcionRespuestaManager {

    public boolean save(OpcionesRespuesta op);

    public List<OpcionesRespuesta> getOpcionesRespuesta(String codigoPregunta);
    
    public OpcionesRespuesta getOpcionRespuesta(String codigoOP);

    public boolean delete(String usuario, String programa,String codigo) ;
}
