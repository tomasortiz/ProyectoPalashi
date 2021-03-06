/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view;

import com.amclos.model.Indicador;
import com.amclos.model.ZonaGeografica;
import com.amclos.services.IndicadorManager;
import com.amclos.services.PreguntasManager;
import com.amclos.services.SpringContext;
import com.amclos.services.ZonaGeograficaManager;
import com.amclos.services.impl.IndicadorManagerImpl;
import com.amclos.view.dataModel.NodoArbolIndicadores;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
 import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
/**
 *
 * @author Ultimate
 */
@ManagedBean (name = "mapBean1")
@ViewScoped
public class MapBean1 {
    private int count;
    private String zoom ;
    private MapModel advancedModel;
    private ZonaGeograficaManager zg;
    private Marker marker;
    private SpringContext context;
    List<ZonaGeografica> zgeo;
    List<Latitud> coordenadas;
    private IndicadorManager im;
    List<String> periodos;
    private double x;
    private double y;
     private MapBean mapa;
     private String periodoSeleccionado;
    private List<NodoArbolIndicadores> mapaRiesgos;
    private List<NodoArbolIndicadores> mapaRiesgosSelec;
    private List<Indicador> Indicador;
    private String indicadorSeleccionado;
    private boolean pintar;
    public MapBean1() {
        count=0;
       
        periodos= new ArrayList<>();
        coordenadas= new ArrayList<>();
        mapaRiesgosSelec= new ArrayList<>();
        context = SpringContext.getInstance();       
        im = (IndicadorManager) context.getBean("IndicadorManager");
        periodos= im.getPeriodo(); 
        Indicador= im.getIndicador();
         pintar= false;
    }
    
    public void ajaxInit()
    {  
         mapa= new MapBean();
          advancedModel = new DefaultMapModel();
          
     mapaRiesgos=im.getIndicadorRiesgo(periodoSeleccionado, indicadorSeleccionado);
        zoom =  im.getZoomMapa();         
         
        for (int i = 0; i < mapaRiesgos.size(); i++) {
            if(mapaRiesgos.get(i).getCoordenadaX()!=null || mapaRiesgos.get(i).getCoordenadaY()!=null)
            {
              LatLng coord_ = new LatLng(Double.valueOf(mapaRiesgos.get(i).getCoordenadaY()), Double.valueOf(mapaRiesgos.get(i).getCoordenadaX()));
              coordenadas.add(new Latitud(coord_, mapaRiesgos.get(i).getNombre()));
            }            
        }
                                                
        //Shared coordinates
        //LatLng coord1 = new LatLng(36.879466, 30.667648);
        //LatLng coord2 = new LatLng(36.883707, 30.689216);
        //LatLng coord3 = new LatLng(36.879703, 30.706707);
        //LatLng coord4 = new LatLng(36.885233, 30.702323);
         
        for (int i = 0; i < coordenadas.size(); i++) {
            if(i%2==0)
            advancedModel.addOverlay(new Marker(coordenadas.get(i).getCoord1(), coordenadas.get(i).getNombre(), "karaalioglu.png", "http://maps.google.com/mapfiles/ms/micons/yellow-dot.png"));  
            else
                if(i%3==0)
                    advancedModel.addOverlay(new Marker(coordenadas.get(i).getCoord1(), coordenadas.get(i).getNombre(), "kaleici.png", "http://maps.google.com/mapfiles/ms/micons/pink-dot.png"));  
                    else
                    advancedModel.addOverlay(new Marker(coordenadas.get(i).getCoord1(), coordenadas.get(i).getNombre(), "konyaalti.png", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));  
        }
        //Icons and Data
        //advancedModel.addOverlay(new Marker(coord1, "Konyaalti", "konyaalti.png", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
        //advancedModel.addOverlay(new Marker(coord2, "Ataturk Parki", "ataturkparki.png"));
        //advancedModel.addOverlay(new Marker(coord4, "Kaleici", "kaleici.png", "http://maps.google.com/mapfiles/ms/micons/pink-dot.png"));
        //advancedModel.addOverlay(new Marker(coord3, "Karaalioglu Parki", "karaalioglu.png", "http://maps.google.com/mapfiles/ms/micons/yellow-dot.png"));
     pintar= true;
    }
    
    
    public MapModel getAdvancedModel() {
        return advancedModel;
    }
     
    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        for (int i = 0; i < mapaRiesgos.size(); i++) {
            if(mapaRiesgos.get(i).getNombre().equals(marker.getTitle()))
            {
                mapaRiesgosSelec.add(mapaRiesgos.get(i));
            }
        }
    }
     
    public Marker getMarker() {
        return marker;
    }

    public List<ZonaGeografica> getZgeo() {
        return zgeo;
    }

    public void setZgeo(List<ZonaGeografica> zgeo) {
        this.zgeo = zgeo;
    }

    public List<Latitud> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(List<Latitud> coordenadas) {
        this.coordenadas = coordenadas;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public ZonaGeograficaManager getZg() {
        return zg;
    }

    public void setZg(ZonaGeograficaManager zg) {
        this.zg = zg;
    }

    public IndicadorManager getIm() {
        return im;
    }

    public void setIm(IndicadorManager im) {
        this.im = im;
    }

    public MapBean getMapa() {
        return mapa;
    }

    public void setMapa(MapBean mapa) {
        this.mapa = mapa;
    }

    public List<NodoArbolIndicadores> getMapaRiesgos() {
        return mapaRiesgos;
    }

    public void setMapaRiesgos(List<NodoArbolIndicadores> mapaRiesgos) {
        this.mapaRiesgos = mapaRiesgos;
    }

    public List<String> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<String> periodos) {
        this.periodos = periodos;
    }

    public String getPeriodoSeleccionado() {
        return periodoSeleccionado;
    }

    public void setPeriodoSeleccionado(String periodoSeleccionado) {
        this.periodoSeleccionado = periodoSeleccionado;
    }

    public List<Indicador> getIndicador() {
        return Indicador;
    }

    public void setIndicador(List<Indicador> Indicador) {
        this.Indicador = Indicador;
    }

    public String getIndicadorSeleccionado() {
                return indicadorSeleccionado;
    }

    public void setIndicadorSeleccionado(String indicadorSeleccionado) {
        this.indicadorSeleccionado = indicadorSeleccionado;
    }

    public List<NodoArbolIndicadores> getMapaRiesgosSelec() {
        return mapaRiesgosSelec;
    }

    public void setMapaRiesgosSelec(List<NodoArbolIndicadores> mapaRiesgosSelec) {
        this.mapaRiesgosSelec = mapaRiesgosSelec;
    }

   

    public boolean isPintar() {
        return pintar;
    }

    public void setPintar(boolean pintar) {
        this.pintar = pintar;
    }

    
}
