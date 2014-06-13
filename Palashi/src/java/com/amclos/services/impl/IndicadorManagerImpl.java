/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services.impl;

import com.amclos.dao.Dao;
import com.amclos.model.Indicador;
import com.amclos.services.EstadoManager;
import com.amclos.services.IndicadorManager;
import com.amclos.services.TiposManager;
import com.amclos.view.IndicadorView;
import com.amclos.view.dataModel.ComboLista;
import com.amclos.view.dataModel.NodoArbolIndicadores;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Agustin Meriño
 */
@Service("IndicadorManager")
public class IndicadorManagerImpl implements IndicadorManager {

    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;
    @Autowired
    @Qualifier("TiposManager")
    private TiposManager tm;

    public List<Indicador> getIndicadores(){
        String hql = "select i from Indicador i where i.estado='" + EstadoManager.ACTIVO + "' order by i.codigo ";
        return dao.find(hql);
    }
    
    @Override
    public List<NodoArbolIndicadores> getIndicadoresArbol() {
        List<Indicador> indicadores = getIndicadores();
        List<NodoArbolIndicadores> nodos = new ArrayList<NodoArbolIndicadores>();
        for (Iterator it = indicadores.iterator(); it.hasNext();) {
            Indicador ind = (Indicador) it.next();
            NodoArbolIndicadores nodo = new NodoArbolIndicadores(ind.getCodigo(), ind.getNombre(), IndicadorView.TIPO_INDICADOR);
            nodos.add(nodo);
        }
        return nodos;
    }

    @Override
    public boolean guardar(String usuario, String programa, String codigo, String nombre, String descripcion, String relevancia, String peso, String tipoEscala) {
        try {
            Indicador indicador = getIndicador(codigo);
            indicador = indicador != null ? indicador : new Indicador();
            indicador.setUsuario(usuario);
            indicador.setPrograma(programa);
            indicador.setFecha(Calendar.getInstance().getTime());
            indicador.setEstado(EstadoManager.ACTIVO);
            indicador.setCodigo(codigo);
            indicador.setNombre(nombre);
            indicador.setDescripcion(descripcion);
            indicador.setRelevancia(relevancia);
            indicador.setPeso(BigDecimal.valueOf(Double.parseDouble(peso)));
            indicador.setTipoEscala(tm.getTipo(tipoEscala));
            dao.persist(indicador);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(String usuario, String programa, String codigo) {
        try {
            Indicador indicador = getIndicador(codigo);
            indicador.setFecha(Calendar.getInstance().getTime());
            indicador.setUsuario(usuario);
            indicador.setPrograma(programa);
            indicador.setEstado(EstadoManager.INACTIVO);
            dao.persist(indicador);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Indicador getIndicador(String codigo) {
        String hql = "Select i from Indicador i where i.codigo = '" + codigo + "'";
        return dao.findObject(hql);
    }

    //--LOB.20140319.INI--
    @Override
    public  List<NodoArbolIndicadores> getIndicadorRiesgo(String periodo, String tipoInd){


        List<NodoArbolIndicadores> nodos = new ArrayList<NodoArbolIndicadores>();

        try{
            String hql = "select  r.zonaGeografica.codigo, r.zonaGeografica.descripcion,r.zonaGeografica.coordX, " +
                            "r.zonaGeografica.coordY, r.valor, r.codigoIndicador\n" +
                            "from  Riesgos r " +
                            "where  r.riesgosPK.periodo=" +periodo +
                            "and  r.tipos.codigo='"+ tipoInd +"'";
            List<Object[]> registros = dao.find(hql);

        for (Iterator it  = registros.iterator(); it.hasNext();) {
                Object[] ind = (Object[]) it.next();
            NodoArbolIndicadores nodo = new NodoArbolIndicadores( String.valueOf(ind[0]),String.valueOf(ind[1])
                                                                  ,String.valueOf(ind[4]),String.valueOf(ind[2])
                                                                   ,String.valueOf(ind[3]));
                nodos.add(nodo);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return nodos;


    }

    
    @Override
    public List getPeriodo(){
        String hql = " SELECT distinct  b.riesgosPK.periodo  from Riesgos  b \n" +
                     "order by  b.riesgosPK.periodo desc" ;
        List periodo =  dao.find(hql);
        List listCombo = new ArrayList();
        for (int i = 0; i < periodo.size(); i++) {
            String per = String.valueOf(periodo.get(i));
            listCombo.add(per);
        }
        return listCombo;
    }

    
    @Override
    public String getZoomMapa() {
        String zoom= "";

        try{
            String hql = " SELECT t.valor FROM Parametro t "
                + "    where  t.idParametro='ZOOM'" ;
         zoom =  dao.findObject(hql);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return zoom;
    }

         @Override
    public List<Indicador> getIndicador() {
        String hql="from Tipos t where t.grupo='TIPO_INDICADOR'";
        return dao.find(hql);
    }
    //LOB.20140319.FIN
    
   @Override
    public List<ComboLista> getIndicadoresCombo() {
        List<Indicador> indicadores = getIndicadores();
        List<ComboLista> lCb = new ArrayList<>();
        for (Indicador i : indicadores) {
            ComboLista cb = new ComboLista(i.getCodigo(), i.getNombre());
            lCb.add(cb);
        }
        return lCb;
    }
}
