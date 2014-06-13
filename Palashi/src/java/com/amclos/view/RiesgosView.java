/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.amclos.view;

import com.amclos.services.IndicadorManager;
import com.amclos.services.SpringContext;
import com.amclos.services.impl.IndicadorManagerImpl;
import com.amclos.view.dataModel.NodoArbolIndicadores;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author larry.obispo
 */

@ManagedBean
@SessionScoped

public class RiesgosView{

  private MapBean mapa;
  private String zoom ;
   private IndicadorManager im;
  private SpringContext context;
  private NodoArbolIndicadores nodoIndica;
  private List<NodoArbolIndicadores> mapaRiesgos ;
    
  public void riesgosView(){
        context = SpringContext.getInstance();
        im = (IndicadorManager) context.getBean("IndicadorManager");
        
        setMapaRiesgos(im.getIndicadorRiesgo("201402", "IND001"));
                
        setMapa(new MapBean());
        zoom =  im.getZoomMapa();
        try{
            if(getMapaRiesgos() !=null){
                 for (int i = 0; i < getMapaRiesgos().size(); i++) {
                    NodoArbolIndicadores nodoData =  getMapaRiesgos().get(i) ;           
                    getMapa().setZoom(zoom);
                    getMapa().setNodo(nodoData);
                 }
             }
        }catch (Exception e) {
            e.printStackTrace();
        }      
           
    }
  
    public String obtenerNivel(String titulo){
        String [] aux = titulo.split(":");
         
        for(int j=0; j< getMapaRiesgos().size(); j++){
            NodoArbolIndicadores nodoData = getMapaRiesgos().get(j);
            if (nodoData.getCodigo().equals(aux[1])){
                setNodoIndica(nodoData);
            }
        }
         return "";
        
    }

    public MapBean getMapa() {
        return mapa;
    }

    public void setMapa(MapBean mapa) {
        this.mapa = mapa;
    }

    /**
     * @return the mapaRiesgos
     */
    public List<NodoArbolIndicadores> getMapaRiesgos() {
        return mapaRiesgos;
    }

    /**
     * @param mapaRiesgos the mapaRiesgos to set
     */
    public void setMapaRiesgos(List<NodoArbolIndicadores> mapaRiesgos) {
        this.mapaRiesgos = mapaRiesgos;
    }

    /**
     * @return the nodoIndica
     */
    public NodoArbolIndicadores getNodoIndica() {
        return nodoIndica;
    }

    /**
     * @param nodoIndica the nodoIndica to set
     */
    public void setNodoIndica(NodoArbolIndicadores nodoIndica) {
        this.nodoIndica = nodoIndica;
    }
    
    
}
