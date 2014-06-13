/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view;

import com.amclos.services.ReporteManager;
import com.amclos.services.SpringContext;
import com.amclos.view.dataModel.ComboLista;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Administrador
 */

@ManagedBean
@SessionScoped
public class RankingView {
    private ReporteManager rm;
    private SpringContext context;
    private List<ComboLista> lPeriodos, lRanking;
    private String periodo;
    public RankingView(){
        context = SpringContext.getInstance();
        rm = (ReporteManager) context.getBean("ReporteManager");
        lPeriodos = rm.getPeriodos();
        if (lPeriodos.size() > 0) {
            periodo = ((ComboLista) lPeriodos.get(0)).getId();
        }
        lRanking = rm.getRanking(periodo);
    }
    
    public void periodoHandleChange(ValueChangeEvent event) {
        System.out.println("New value: " + event.getNewValue());
        periodo = String.valueOf(event.getNewValue());
        lRanking = rm.getRanking(periodo);
    }

    /**
     * @return the lPeriodos
     */
    public List<ComboLista> getlPeriodos() {
        return lPeriodos;
    }

    /**
     * @param lPeriodos the lPeriodos to set
     */
    public void setlPeriodos(List<ComboLista> lPeriodos) {
        this.lPeriodos = lPeriodos;
    }

    /**
     * @return the lRanking
     */
    public List<ComboLista> getlRanking() {
        return lRanking;
    }

    /**
     * @param lRanking the lRanking to set
     */
    public void setlRanking(List<ComboLista> lRanking) {
        this.lRanking = lRanking;
    }

    /**
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
}
