/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services.impl;

import com.amclos.dao.Dao;

import com.amclos.model.Institucion;
import com.amclos.model.Tipos;
import com.amclos.model.ZonaGeografica;
import com.amclos.services.ConsecutivoManager;
import com.amclos.services.EstadoManager;
import com.amclos.services.InstitucionManager;
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
 * @author Curiel
 */
@Service("InstitucionManager")
public class InstitucionManagerImpl implements InstitucionManager {

    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;
    
    @Autowired
    @Qualifier("TiposManager")
    private TiposManager tm;
    
    @Autowired
    @Qualifier("ZonaGeograficaManager")
    private ZonaGeograficaManager zm;

    @Autowired
    @Qualifier("ConsecutivoManager")
    private ConsecutivoManager consecutivo;
            
    @Override
    public boolean save(Institucion instituto) {

     
          try {
            if(instituto.getCodigo() == null){
                String codigo = consecutivo.getConsecutivo("INSTITUCION", instituto.getUsuario(), instituto.getPrograma(), "A", "0");
                instituto.setCodigo(Long.parseLong(codigo));
            }
            
            instituto.setFecha(Calendar.getInstance().getTime());
            instituto.setEstado(tm.getTipo("AC001"));
            dao.persist(instituto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Institucion> getInstitucion() {

        String hql;
        hql = "Select p from Institucion p order by p.nombre";
        return dao.find(hql);

    }

@Override
    public List<Institucion> getInstitucion(String idZonas) {

        String hql;
        hql = "Select p from Institucion p"
                + " where p.codigoZona.codigo ='"+idZonas+"'"
                + "and p.estado ='"+EstadoManager.ACTIVO+"'"
                + " order by p.nombre";
        return dao.find(hql);

    }

    @Override
    public boolean delete(String usuario, String programa, Long codigo) {
        
        
        try{
            Institucion instituto = getInstitucions(String.valueOf(codigo));
            instituto.setFecha(Calendar.getInstance().getTime());
            instituto.setUsuario(usuario);
            instituto.setPrograma(programa);
            instituto.setEstado(tm.getTipo(EstadoManager.INACTIVO));
            dao.persist(instituto);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
   /*    
        String hql = "SELECT p FROM Institucion p WHERE p.codigo ='" + codigo + "'";
        Institucion instituto = dao.findObject(hql);
        try {
            dao.delete(instituto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }*/
        
    }

    @Override
    public Institucion getInstitucions(String institucioncodigo) {
         String hql = "select t from Institucion t where t.codigo = "+institucioncodigo;
          return dao.findObject(hql);     
    }

    @Override
    public List<ComboLista> getComboInstitucion() {
        List<Institucion> li = getInstitucion();
        List<ComboLista> lc = new ArrayList<>();
        for (Institucion t: li) {
            ComboLista cb = new ComboLista(String.valueOf(t.getCodigo()), t.getNombre());
            lc.add(cb);
        }
        return lc; 
    }

    @Override
    public List<ComboLista> getComboInstitucion(String codigoZo) {
          List<Institucion> li = getInstitucion(codigoZo);          
        List<ComboLista> lc = new ArrayList<>();
        for (Institucion t: li) {
            ComboLista cb = new ComboLista(String.valueOf(t.getCodigo()), t.getNombre());
            lc.add(cb);
        }
        return lc; 
    }
}
