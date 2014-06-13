/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services.impl;

import com.amclos.dao.Dao;
import com.amclos.model.Tipos;
import com.amclos.services.ConsecutivoManager;
import com.amclos.services.TiposManager;
import com.amclos.view.dataModel.ComboLista;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrador
 */
@Service("TiposManager")
public class TiposManagerImpl implements TiposManager {

    
    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;

    @Autowired
    @Qualifier("ConsecutivoManager")
    private ConsecutivoManager consecutivo;
    
    @Override
    public Tipos getTipo(String codigo) {
        String hql = "select t from Tipos t where t.codigo = '"+codigo+"'";
        return dao.findObject(hql);
    }
    
    @Override
    public List<Tipos> getTipos() {
        String hql = "select t from Tipos t ";
        return dao.find(hql);
    }
    
    @Override
    public List<Tipos> getTiposGrupo(String grupo) {
        String hql = "select t from Tipos t where t.grupo = '"+ grupo + "'";
        return dao.find(hql);
    }
    
    @Override
    public List<ComboLista> getTiposGrupoCombo(String grupo) {
        List<Tipos> lt = getTiposGrupo(grupo);
        List<ComboLista> lc = new ArrayList<>();
        for (int i = 0; i < lt.size(); i++) {
            Tipos t = lt.get(i);
            ComboLista cb = new ComboLista(t.getCodigo(),t.getDescripcion());
            lc.add(cb);
        }
        return lc;
    }

    @Override
    public boolean save(Tipos tipos) {

        try {            
            tipos.setFecha(Calendar.getInstance().getTime());
            dao.persist(tipos);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public boolean delete(String codigo) {
        String hql = "SELECT p FROM Tipos p WHERE p.codigo ='" + codigo + "'";
        Tipos tipos = dao.findObject(hql);
        try {
            dao.delete(tipos);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
