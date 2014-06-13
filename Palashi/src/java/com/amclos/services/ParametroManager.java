/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services;

import com.amclos.model.Parametro;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface ParametroManager {
    public String getValorParametro(String codigo);
    public Parametro getParametro(String codigo);
    
    public boolean save(Parametro parametro);
    public boolean delete(String codigo);
    public List<Parametro> getParametro();
}
