/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services.impl;

import com.amclos.dao.Dao;
import com.amclos.model.Riesgos;
import com.amclos.services.ReporteManager;
import com.amclos.view.IndicadorView;
import com.amclos.view.dataModel.ComboLista;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrador
 */
@Service("ReporteManager")
public class ReporteManagerImpl implements ReporteManager {

    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;

    @Override
    public List<ComboLista> getValoresHijos(String codigoPadre, String tipo, String codigoZona, String periodo) {
        String hql = null;
        switch (tipo) {
            case IndicadorView.TIPO_INDICE:
                hql = " select  r from Riesgos r "
                        + "  where  r.zonaGeografica.codigo = " + codigoZona
                        + "    and  r.riesgosPK.periodo = " + periodo
                        + "    and  r.tipos.codigo = '" + IndicadorView.TIPO_INDICADOR + "'";
                break;
            case IndicadorView.TIPO_INDICADOR:
                hql = " select  r from Riesgos r, SubIndicador s "
                        + "  where  s.codigoIndicador.codigo = '" + codigoPadre + "'"
                        + "    and  r.codigoIndicador = s.codigo"
                        + "    and  r.zonaGeografica.codigo = " + codigoZona
                        + "    and  r.riesgosPK.periodo = " + periodo
                        + "    and  r.tipos.codigo = '" + IndicadorView.TIPO_SUBINDICADOR + "'";
                break;
            case IndicadorView.TIPO_SUBINDICADOR:
                hql = " select  r from Riesgos r, Variable v "
                        + "  where  v.codSubindicador.codigo = '" + codigoPadre + "'"
                        + "    and  r.codigoIndicador = v.codigo"
                        + "    and  r.zonaGeografica.codigo = " + codigoZona
                        + "    and  r.riesgosPK.periodo = " + periodo
                        + "    and  r.tipos.codigo = '" + IndicadorView.TIPO_VARIABLE + "'";
                break;
        }
        List<Riesgos> lr = dao.find(hql);
        List<ComboLista> lCb = new ArrayList<>();
        for (Riesgos r : lr) {
            ComboLista cb = new ComboLista(r.getCodigoIndicador(), r.getRiesgosPK().getPeriodo() + "", r.getValor() + "");
            lCb.add(cb);
        }
        return lCb;
    }

    @Override
    public String getValor(String codigo, String codigoZona, String periodo) {
        String hql = " select  r from Riesgos r "
                + "  where  r.codigoIndicador = '" + codigo + "'"
                + "    and  r.zonaGeografica.codigo = " + codigoZona
                + "    and  r.riesgosPK.periodo = " + periodo;
        Riesgos r = dao.findObject(hql);
        return (r == null ? "0" : "" + r.getValor());
    }

    @Override
    public List<ComboLista> getValoresHistoricos(String codigo, String codigoZona) {
        String hql = " select  r from Riesgos r "
                + "  where  r.codigoIndicador = '" + codigo + "'"
                + "    and  r.zonaGeografica.codigo = " + codigoZona
                + "   order by r.riesgosPK.periodo desc";
        List<Riesgos> lr = dao.find(hql);
        List<ComboLista> lCb = new ArrayList<>();
        int cont = 1;
        for (Riesgos r : lr) {
            ComboLista cb = new ComboLista(r.getCodigoIndicador(), r.getRiesgosPK().getPeriodo() + "", r.getValor() + "");
            lCb.add(cb);
            cont++;
            if (cont > 6) {
                break;
            }
        }
        return lCb;
    }

    @Override
    public List<ComboLista> getPeriodos() {
        String hql = " select distinct r.riesgosPK.periodo from Riesgos r ";
        List periodo = dao.find(hql);
        List<ComboLista> lCb = new ArrayList<>();
        for (int i = 0; i < periodo.size(); i++) {
            String per = String.valueOf(periodo.get(i));
            ComboLista cb = new ComboLista(per, per);
            lCb.add(cb);
        }
        return lCb;
    }

    public List<ComboLista> getRanking(String periodo) {
        String hql = " select  r from Riesgos r "
                + "  where  r.riesgosPK.periodo = " + periodo
                + "    and  r.tipos.codigo = '" + IndicadorView.TIPO_INDICE + "'"
                + "    and  r.zonaGeografica.tipoZona = 'TZ003'"
                + "  order by r.valor desc";
        List<Riesgos> lr = dao.find(hql);
        List<ComboLista> lCb = new ArrayList<>();
        int cont = 1;
        for (Riesgos r : lr) {
            ComboLista cb = new ComboLista(String.valueOf(cont), r.getZonaGeografica().getDescripcion() + "", r.getValor() + "");
            lCb.add(cb);
            cont++;
        }
        return lCb;
    }

    @Override
    public List<ComboLista> getValoresMunicipios(List<ComboLista> lMunicipio, String tipo, String codigoElemento, String periodo) {
        String hql = " select  r from Riesgos r "
                + "  where  r.riesgosPK.periodo = " + periodo
                + "    and  r.codigoIndicador = '"+ codigoElemento + "'"
                + "    and  r.tipos.codigo = '" + tipo + "'"
                + "    and  r.zonaGeografica.tipoZona = 'TZ003'"
                + "    and  r.zonaGeografica.codigo in ("+ getMunicipios(lMunicipio) + ")"
                + "  order by r.valor desc";
        List<Riesgos> lr = dao.find(hql);
        List<ComboLista> lCb = new ArrayList<>();
        for (Riesgos r : lr) {
            ComboLista cb = new ComboLista(r.getCodigoIndicador(), r.getZonaGeografica().getDescripcion() + "", r.getValor() + "");
            lCb.add(cb);
        }
        return lCb;
    }
    
    public String getMunicipios(List<ComboLista> lMunicipio){
        String respuesta = "''";
        int cont = 0;
        for(ComboLista cb:lMunicipio){
            if (cont == 0)
                respuesta = "'"+cb.getId()+"'";
            else
                respuesta += ",'"+cb.getId()+"'";
            cont++;
        }
        return respuesta;
    }
}
