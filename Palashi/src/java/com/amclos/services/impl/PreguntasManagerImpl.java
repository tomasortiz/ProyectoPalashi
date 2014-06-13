/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services.impl;

import com.amclos.dao.Dao;
import com.amclos.model.OpcionesRespuesta;
import com.amclos.model.Preguntas;
import com.amclos.model.Respuesta;
import com.amclos.services.ConsecutivoManager;
import com.amclos.services.OpcionRespuestaManager;
import com.amclos.view.IndicadorView;
import com.amclos.services.EstadoManager;
import com.amclos.services.PreguntasManager;
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

@Service("PreguntasManager")
public class PreguntasManagerImpl implements PreguntasManager{

    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;
    
    
     @Autowired
    @Qualifier("ConsecutivoManager")
    private ConsecutivoManager consecutivo;
    
    @Autowired
    @Qualifier("OpcionRespuestaManager")
    private OpcionRespuestaManager opm;
    
    
    
    @Override
    public List<NodoArbolIndicadores> getPreguntasArbol(String codigoVariable) {
        String hql = "select i from Preguntas i "
                   + " where i.estado='"+EstadoManager.ACTIVO+"'"
                   + "   and i.variablecod.codigo = '" + codigoVariable +"'"
                   + " order by i.tipoSituacion.codigo ";
        List<Preguntas> preguntas =  dao.find(hql);
        List<NodoArbolIndicadores> nodos = new ArrayList<>();
        int cont = 1;
        for (Preguntas p : preguntas) {
            NodoArbolIndicadores nodo = new NodoArbolIndicadores(String.valueOf(p.getCodigo()),p.getDescripcion(),IndicadorView.TIPO_PREGUNTA);
            nodo.setValor(cont++ +"_"+ p.getTipoSituacion().getDescripcion());
            nodos.add(nodo);
        }
        return nodos;
    }

    @Override
    public boolean guardar(Preguntas preguntas) {
        try {
            if(preguntas.getCodigo() == null){
                String codigo = consecutivo.getConsecutivo("PREGUNTAS", preguntas.getUsuario(), preguntas.getPrograma(), "A", "0");
                preguntas.setCodigo(Long.parseLong(codigo));
            }
            preguntas.setFecha(Calendar.getInstance().getTime());
            preguntas.setEstado(EstadoManager.ACTIVO);            
            dao.persist(preguntas);
            if(preguntas.getTipoSituacion().getCodigo().equals("TIS002")){
                OpcionesRespuesta si = new OpcionesRespuesta();
                si.setUsuario(preguntas.getUsuario());
                si.setPrograma(preguntas.getPrograma());
                si.setOrden(1);
                si.setDescripcion("SI");
                si.setPreguntascodigo(preguntas);
                opm.save(si);
                OpcionesRespuesta no = new OpcionesRespuesta();
                no.setUsuario(preguntas.getUsuario());
                no.setPrograma(preguntas.getPrograma());
                no.setOrden(2);
                no.setDescripcion("NO");
                no.setPreguntascodigo(preguntas);
                opm.save(no);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(String usuario, String programa, String codigo) {
        try {
            Preguntas preguntas = getPreguntas(codigo);
            preguntas.setFecha(Calendar.getInstance().getTime());
            preguntas.setUsuario(usuario);
            preguntas.setPrograma(programa);
            preguntas.setEstado(EstadoManager.INACTIVO);
            dao.persist(preguntas);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Preguntas getPreguntas(String codigo) {
        String hql = "Select i "
                + "from Preguntas i "
                + "where i.codigo = '" + codigo + "'";
        return dao.findObject(hql);
    }


@Override
public List<Preguntas> listaPreguntas_respondidas(String usuario,String code) {//listar preguntas totales

        String hql = " select DISTINCT r.preguntas \n" +
        " from Respuesta r \n" +
        " where r.preguntas.estado='AC001'"+
        " and r.usuario1.codigo = '"+usuario+ "' "+
        " and r.preguntas.tipoSituacion ='"+code+"'";

        //System.out.println("Rspondidas: "+hql); 
        return dao.find(hql);
}
    
    @Override
    public List<OpcionesRespuesta> opcionrespuesta(String codigo, String codPregunta) {
       String hql="select o from OpcionesRespuesta o "
               + "where o.preguntascodigo.codigo='"+codPregunta+"' "
               + "and o.estado.codigo ='"+codigo+"'"
               + "order by orden";
        //System.out.println(""+hql);        
        return dao.find(hql);
    }

    @Override
    public boolean guardar(Respuesta[] respuesta) {
         try {               
             
            dao.persist(respuesta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

     @Override
    public List<Preguntas> listaPreguntasMes(String usuario,String code) {//pendientes en el mes
        String hql="select distinct p \n" +
                    "from  Preguntas p, Usuario us, Institucion  it, InstitucionVariable iv\n" +
                    "where p NOT in\n" +
                        "            (select r.preguntas \n" +
                        "            from Respuesta r, Usuario u, Institucion  i, InstitucionVariable v \n" +
                        "            where r.usuario= us.codigo\n" +
                        "            and v.institucionVariablePK.variablecodigo=p.variablecod.codigo\n" +
                        "            and v.tipos.codigo=i.tipoInstitucion.codigo\n" +
                        "            and u.institucioncodigo.codigo=i.codigo\n" +
                        "            and p.estado ='AC001' \n" +
                        " and   p.tipoSituacion.codigo ='"+code+"')"+
                        " and p.tipoSituacion.codigo ='"+code+"'" +
                        "and  iv.institucionVariablePK.variablecodigo=p.variablecod.codigo\n" +
                        "and iv.tipos.codigo=it.tipoInstitucion.codigo\n" +
                        "and us.institucioncodigo.codigo=it.codigo\n" +
                        "and p.estado ='AC001' \n" +
                        "and us.codigo='"+usuario+"'"+
                        "and extract(month from p.fecha) <= extract(month from iv.fechaModificacion)";
      //  System.out.println(""+hql);        
        return dao.find(hql);
    }

    @Override
    public List<Preguntas> listaPreguntasSinResponder(String usuario,String code) {//preguntas sin responder
         String hql= " select distinct p from  Preguntas p, Usuario us, Institucion  it, InstitucionVariable iv "
                + " where p NOT in  (select r.preguntas from Respuesta r, Usuario u, Institucion  i, InstitucionVariable v "
                + " where r.usuario= us.codigo and v.institucionVariablePK.variablecodigo=p.variablecod.codigo "
                + " and v.tipos.codigo=i.tipoInstitucion.codigo  "
                + " and u.institucioncodigo.codigo=i.codigo"
                + " and p.estado ='AC001' "
                + " and   p.tipoSituacion.codigo ='"+code+"')"
                + " and p.tipoSituacion.codigo ='"+code+"'"
                + " and  iv.institucionVariablePK.variablecodigo=p.variablecod.codigo "
                + " and iv.tipos.codigo=it.tipoInstitucion.codigo "
                + " and us.institucioncodigo.codigo=it.codigo"
                + " and p.estado ='AC001' "
                + " and us.codigo='"+usuario+"'";
         return dao.find(hql);
    }

    @Override
    public Respuesta guardar(String codPregunta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Respuesta> obtenerRespuesta(String usuario, String code) {
       String hql="from Respuesta r "
               + "where r.preguntas.codigo='"+code+"' "
               + "and r.usuario='"+usuario+"' "
               + "order by r.fecha desc";
       // System.out.println(""+hql);        
        return dao.find(hql);
    }
    
}

