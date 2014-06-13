/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services.impl;

import com.amclos.dao.Dao;
import com.amclos.model.InstitucionVariable;
import com.amclos.model.InstitucionVariablePK;
import com.amclos.model.Tipos;
import com.amclos.model.Variable;
import com.amclos.services.EstadoManager;
import com.amclos.services.TiposManager;
import com.amclos.services.VariableManager;
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
@Service("VariableManager")
public class VariableManagerImpl implements VariableManager {

    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;
    @Autowired
    @Qualifier("TiposManager")
    private TiposManager tm;

    public List<Variable> getVariables(String codigoSubIndicador){
        String hql = "select i from Variable i "
                + " where i.estado='" + EstadoManager.ACTIVO + "'"
                + "   and i.codSubindicador.codigo = '" + codigoSubIndicador + "'"
                + " order by i.codigo ";
        return dao.find(hql);
    }
    
    @Override
    public List<NodoArbolIndicadores> getVariablesArbol(String codigoSubIndicador) {
        List<Variable> variables = getVariables(codigoSubIndicador);
        List<NodoArbolIndicadores> nodos = new ArrayList<>();
        for (Variable variable : variables) {
            NodoArbolIndicadores nodo = new NodoArbolIndicadores(variable.getCodigo(), variable.getDescripcion(), IndicadorView.TIPO_VARIABLE);
            nodos.add(nodo);
        }
        return nodos;
    }

    @Override
    public boolean guardar(Variable variable) {
        try {
            variable.setFecha(Calendar.getInstance().getTime());
            variable.setEstado(tm.getTipo(EstadoManager.ACTIVO));
            dao.persist(variable);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(String usuario, String programa, String codigo) {
        try {
            Variable variable = getVariable(codigo);
            variable.setFecha(Calendar.getInstance().getTime());
            variable.setUsuario(usuario);
            variable.setPrograma(programa);
            variable.setEstado(tm.getTipo(EstadoManager.INACTIVO));
            dao.persist(variable);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Variable getVariable(String codigo) {
        String hql = "Select i from Variable i where i.codigo = '" + codigo + "'";
        return dao.findObject(hql);
    }
    
    public List<InstitucionVariable> getInstitucionesVariables(String codigo) {
        String hql = "select i from InstitucionVariable i "
                + " where i.variable.codigo = '" + codigo + "'"
                + " order by i.tipos.codigo ";
        return dao.find(hql);
    }

    @Override
    public List<ComboLista> getTiposInstituciones(String codigo) {
        List<InstitucionVariable> ivs = getInstitucionesVariables(codigo);
        List<ComboLista> nodos = new ArrayList<>();
        for (InstitucionVariable iv : ivs) {
            ComboLista nodo = new ComboLista(iv.getTipos().getCodigo(), iv.getTipos().getDescripcion());
            nodos.add(nodo);
        }
        return nodos;
    }

    @Override
    public boolean guardarInstitucionVariable(String usuario, String programa, List<ComboLista> tiposInst, Variable variable) {
        try {
            List<InstitucionVariable> ivs = getInstitucionesVariables(variable.getCodigo());
            for(InstitucionVariable instVar: ivs){
                dao.delete(instVar);
            }
            for (ComboLista cb : tiposInst) {
                Tipos ti = tm.getTipo(cb.getId());
                InstitucionVariable iv = new InstitucionVariable();
                InstitucionVariablePK pk = new InstitucionVariablePK();
                pk.setTipoInstitucion(ti.getCodigo());
                pk.setVariablecodigo(variable.getCodigo());
                iv.setInstitucionVariablePK(pk);
                iv.setUsuario(usuario);
                iv.setPrograma(programa);
                iv.setFechaModificacion(Calendar.getInstance().getTime());
                iv.setTipos(ti);
                iv.setVariable(variable);
                dao.persist(iv);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<ComboLista> getVariablesCombo(String codigoSubIndicador) {
        List<Variable> variables =  getVariables(codigoSubIndicador);
        List<ComboLista> lCb = new ArrayList<>();
        for (Variable i : variables) {
            ComboLista cb = new ComboLista(i.getCodigo(), i.getDescripcion());
            lCb.add(cb);
        }
        return lCb;
    }
    
}
