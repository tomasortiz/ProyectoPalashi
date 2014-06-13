/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services.impl;

import com.amclos.dao.Dao;
import com.amclos.model.Parametro;
import com.amclos.model.Tipos;
import com.amclos.services.ConsecutivoManager;
import com.amclos.services.EstadoManager;
import com.amclos.services.ParametroManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrador
 */
@Service("ParametroManager")
public class ParametroManagerImpl implements ParametroManager {

    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;
    @Autowired
    @Qualifier("ConsecutivoManager")
    private ConsecutivoManager consecutivo;

    @Override
    public String getValorParametro(String codigo) {
        Parametro p = getParametro(codigo);
        return p == null ? null : p.getValor();
    }

    @Override
    public Parametro getParametro(String codigo) {
        String hql = "Select i from Parametro i where i.idParametro = '" + codigo + "'";
        return dao.findObject(hql);
    }

    @Override
    public boolean save(Parametro parametro) {
        try {
            parametro.setFechaModif(Calendar.getInstance().getTime());
            dao.persist(parametro);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String codigo) {
        try {
            String hql = "SELECT p FROM Parametro p WHERE p.idParametro='" + codigo + "'";
            Parametro parametro = dao.findObject(hql);
            dao.delete(parametro);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Parametro> getParametro() {

        String hql;
        hql = "Select p from Parametro p order by p.nombre";
        return dao.find(hql);
    }
}
