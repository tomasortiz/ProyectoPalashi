/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services.impl;

import com.amclos.dao.Dao;
import com.amclos.model.Perfil;
import com.amclos.services.ConsecutivoManager;
import com.amclos.services.EstadoManager;
import com.amclos.services.PerfilManager;
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
@Service("PerfilManager")
public class PerfilManagerImpl implements PerfilManager {
    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;
    
    @Autowired
    @Qualifier("ConsecutivoManager")
    private ConsecutivoManager consecutivo;    
    
    @Override
    public boolean save(Perfil perfil) {
        try {
          if(perfil.getCodigo() == null){
                String codigo = consecutivo.getConsecutivo("PERFIL", perfil.getUsuario(), perfil.getPrograma(), "A", "0");
                perfil.setCodigo(codigo);
            }
            perfil.setFechaModif(Calendar.getInstance().getTime());
            perfil.setEstado(EstadoManager.ACTIVO);
            dao.persist(perfil);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }        
        
        
        
    }

    @Override
    public List<Perfil> getPerfil() {
        String hql;
        hql = "Select p from Perfil p "
                + "where p.estado ='"+EstadoManager.ACTIVO+"'"
                + "order by p.nombre";
        return dao.find(hql);

    }

    @Override
    public boolean delete(String usuario, String programa, String codigo) {
          try{
            Perfil perfil = getPerfils(codigo);
            perfil.setFechaModif(Calendar.getInstance().getTime());
            perfil.setUsuario(usuario);
            perfil.setPrograma(programa);
            perfil.setEstado(EstadoManager.INACTIVO);
            dao.persist(perfil);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }   
        
        
        
    /*    
        String hql = "SELECT p FROM Perfil p WHERE p.codigo ='" + codigo + "'";
        Perfil perfil = dao.findObject(hql);
        try {
            dao.delete(perfil);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }*/
    }

    @Override
    public List<Perfil> getTipos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Perfil getPerfils(String codigo) {
        String hql = "SELECT p FROM Perfil p WHERE p.codigo ='" + codigo + "'";
        return dao.findObject(hql);
    }
    
}
