/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services.impl;

import com.amclos.dao.Dao;
import com.amclos.model.SubIndicador;
import com.amclos.services.EstadoManager;
import com.amclos.services.SubIndicadorManager;
import com.amclos.view.IndicadorView;
import com.amclos.view.dataModel.ComboLista;
import com.amclos.view.dataModel.NodoArbolIndicadores;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrador
 */

@Service("SubIndicadorManager")
public class SubIndicadorManagerImpl implements SubIndicadorManager{

    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;
    
    public List<SubIndicador> getSubIndicadores(String codigoIndicador){
        String hql = "select i from SubIndicador i "
                   + " where i.estado='"+EstadoManager.ACTIVO+"'"
                   + "   and i.codigoIndicador.codigo = '" + codigoIndicador +"'"
                   + " order by i.codigo ";
        return dao.find(hql);
    }
    
    @Override
    public List<NodoArbolIndicadores> getSubIndicadoresArbol(String codigoIndicador) {
        List<SubIndicador> subIndicadores =  getSubIndicadores(codigoIndicador);
        List<NodoArbolIndicadores> nodos = new ArrayList<>();
        for (SubIndicador subInd : subIndicadores) {
            NodoArbolIndicadores nodo = new NodoArbolIndicadores(subInd.getCodigo(),subInd.getNombre(),IndicadorView.TIPO_SUBINDICADOR);
            nodos.add(nodo);
        }
        return nodos;
    }

    @Override
    public boolean guardar(SubIndicador subindicador) {
        try {
            subindicador.setFecha(Calendar.getInstance().getTime());
            subindicador.setEstado(EstadoManager.ACTIVO);            
            dao.persist(subindicador);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(String usuario, String programa, String codigo) {
        try {
            SubIndicador subIndicador = getSubIndicador(codigo);
            subIndicador.setFecha(Calendar.getInstance().getTime());
            subIndicador.setUsuario(usuario);
            subIndicador.setPrograma(programa);
            subIndicador.setEstado(EstadoManager.INACTIVO);
            dao.persist(subIndicador);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public SubIndicador getSubIndicador(String codigo) {
        String hql = "Select i from SubIndicador i where i.codigo = '" + codigo + "'";
        return dao.findObject(hql);
    }
    
    @Override
    public List<ComboLista> getSubIndicadoresCombo(String codigoIndicador) {
        List<SubIndicador> subIndicadores =  getSubIndicadores(codigoIndicador);
        List<ComboLista> lCb = new ArrayList<>();
        for (SubIndicador i : subIndicadores) {
            ComboLista cb = new ComboLista(i.getCodigo(), i.getNombre());
            lCb.add(cb);
        }
        return lCb;
    }
}
