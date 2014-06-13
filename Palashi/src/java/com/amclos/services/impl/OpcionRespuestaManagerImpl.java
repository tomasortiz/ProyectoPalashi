/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services.impl;

import com.amclos.dao.Dao;
import com.amclos.model.OpcionesRespuesta;
import com.amclos.services.ConsecutivoManager;
import com.amclos.services.EstadoManager;
import com.amclos.services.OpcionRespuestaManager;
import com.amclos.services.TiposManager;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrador
 */
@Service("OpcionRespuestaManager")
public class OpcionRespuestaManagerImpl implements OpcionRespuestaManager{

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
    public boolean save(OpcionesRespuesta op) {
        try {
            if(op.getCodigoNivel() == null){
                String codigo = consecutivo.getConsecutivo("OPCIONES_RESPUESTA", op.getUsuario(), op.getPrograma(), "A", "0");
                op.setCodigoNivel(Long.parseLong(codigo));
            }
            op.setFecha(Calendar.getInstance().getTime());
            op.setEstado(tm.getTipo(EstadoManager.ACTIVO));            
            dao.persist(op);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OpcionesRespuesta> getOpcionesRespuesta(String codigoPregunta) {
        String hql;
        hql = "Select op from OpcionesRespuesta op"
                + " where op.preguntascodigo.codigo ="+codigoPregunta 
                + "  and op.estado.codigo = '"+ EstadoManager.ACTIVO+ "'"
                + " order by op.orden";
        return dao.find(hql);
    }
    
    @Override
    public OpcionesRespuesta getOpcionRespuesta(String codigoOP) {
        String hql;
        hql = "Select op from OpcionesRespuesta op"
                + " where op.codigoNivel ="+codigoOP;
        return dao.findObject(hql);
    }

    @Override
    public boolean delete(String usuario, String programa,String codigo) {
        try {
            OpcionesRespuesta op = getOpcionRespuesta(codigo);
            op.setFecha(Calendar.getInstance().getTime());
            op.setUsuario(usuario);
            op.setPrograma(programa);
            op.setEstado(tm.getTipo(EstadoManager.INACTIVO));    
            dao.persist(op);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
}
