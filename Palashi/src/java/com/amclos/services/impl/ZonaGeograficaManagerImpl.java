/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services.impl;

import com.amclos.dao.Dao;
import com.amclos.model.Tipos;
import com.amclos.services.EstadoManager;
import com.amclos.model.ZonaGeografica;
import com.amclos.services.ConsecutivoManager;
import com.amclos.services.TiposManager;
import com.amclos.services.ZonaGeograficaManager;
import com.amclos.view.dataModel.ComboLista;
import java.util.ArrayList;
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
@Service("ZonaGeograficaManager")
public class ZonaGeograficaManagerImpl implements ZonaGeograficaManager{

    
    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;
    
    @Autowired
    @Qualifier("TiposManager")
    private TiposManager tm;

    @Autowired
    @Qualifier("ConsecutivoManager")
    private ConsecutivoManager consecutivo;
    
    @Override
    public ZonaGeografica getZonaGeografica(String codigo) {
        String hql = "select t from ZonaGeografica t where t.codigo = "+codigo;
        return dao.findObject(hql);
    }
    
    @Override
    public List<ZonaGeografica> getZonaGeograficas() {
        String hql = "select t from ZonaGeografica t "
                + "where t.estado ='"+EstadoManager.ACTIVO+"'"
                + "order by t.codigo ";
        return dao.find(hql);
    }

    @Override
    public boolean save(ZonaGeografica zonaG) {
         try {
            if(zonaG.getCodigo() == null){
                String codigo = consecutivo.getConsecutivo("ZONA_GEOGRAFICA", zonaG.getUsuario(), zonaG.getPrograma(), "A", "0");
                zonaG.setCodigo(Long.parseLong(codigo));
            }
            zonaG.setFecha(Calendar.getInstance().getTime());
            zonaG.setEstado(EstadoManager.ACTIVO);
            dao.persist(zonaG);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

 /*   @Override
    public boolean save(String usuario, String programa, Date fecha, Long codigo, String descripcion, Tipos tipoZona, String estado, String coordX, String coordY) {
         try {
            Date utilDate = new Date();
            ZonaGeografica zonaG = new ZonaGeografica(codigo);
            zonaG.setCodigo(codigo);
            zonaG.setUsuario(usuario);
            zonaG.setPrograma(programa);
            zonaG.setFecha(utilDate);
            zonaG.setDescripcion(descripcion);
           // zonaG.setTipoZona(tm.getTipos(tipoZona));
            zonaG.setEstado(estado);
            zonaG.setCoordX(coordX);
            zonaG.setCoordY(coordY);
            dao.persist(zonaG);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }*/

    @Override
    public boolean delete(String usuario, String programa, Long codigo) {
        try{
            ZonaGeografica zona = getZonaGeografica(String.valueOf(codigo));
            zona.setFecha(Calendar.getInstance().getTime());
            zona.setUsuario(usuario);
            zona.setPrograma(programa);
            zona.setEstado(EstadoManager.INACTIVO);
            dao.persist(zona);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        /*String hql = "SELECT p FROM ZonaGeografica p WHERE p.codigo ='" + codigo + "'";
        ZonaGeografica zonaG = dao.findObject(hql);
        try {
            dao.delete(zonaG);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }*/
    }

    @Override
    public List<ComboLista> getComboZona() {
        List<ZonaGeografica> lz = getZonaGeograficas();
        List<ComboLista> lc = new ArrayList<>();
        for (ZonaGeografica t: lz) {
            ComboLista cb = new ComboLista(String.valueOf(t.getCodigo()), t.getDescripcion());
            lc.add(cb);
        }
        return lc; 
    }
    
    @Override
    public List<ComboLista> getComboZona(String tipo) {
        String hql = " select t from ZonaGeografica t "
                   + "  where t.tipoZona = '"+tipo+"'";
        List<ZonaGeografica> lz = dao.find(hql);
        List<ComboLista> lc = new ArrayList<>();
        for (ZonaGeografica t: lz) {
            ComboLista cb = new ComboLista(String.valueOf(t.getCodigo()), t.getDescripcion());
            lc.add(cb);
        }
        return lc; 
    }
    
}
