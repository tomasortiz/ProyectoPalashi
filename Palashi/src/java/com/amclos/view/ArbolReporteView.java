/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view;

import com.amclos.model.Indicador;
import com.amclos.model.SubIndicador;
import com.amclos.model.Variable;
import com.amclos.services.IndicadorManager;
import com.amclos.services.ReporteManager;
import com.amclos.services.SpringContext;
import com.amclos.services.SubIndicadorManager;
import com.amclos.services.TiposManager;
import com.amclos.services.VariableManager;
import com.amclos.services.ZonaGeograficaManager;
import com.amclos.view.dataModel.ComboLista;
import com.amclos.view.dataModel.NodoArbolIndicadores;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DonutChartModel;

/**
 *
 * @author Administrador
 */
@ManagedBean
@SessionScoped
public class ArbolReporteView {

    private TreeNode root;
    private TreeNode selectedNode;
    private IndicadorManager im;
    private SubIndicadorManager sim;
    private VariableManager vm;
    private TiposManager tm;
    private ZonaGeograficaManager zm;
    private ReporteManager rm;
    private SpringContext context;
    private HashMap data, dataObject;
    private Indicador indicador;
    private SubIndicador subIndicador;
    private Variable variable;
    private String tipoSeleccionado, url, idZona,idSituacion, periodo, nombreZona, tituloNivelDesempeno, tituloComparativo, tituloHistorico, municipioSel, municipioComp;
    public static final String TIPO_INDICE = "IND000",
            TIPO_INDICADOR = "IND001",
            TIPO_SUBINDICADOR = "IND002",
            TIPO_VARIABLE = "IND003",
            TIPO_PREGUNTA = "IND004",
            NIVEL_DESEMPEÑO = "Nivel desempeño",
            RESTANTE = "Restante para una situacion ideal";
    private DonutChartModel anillo;
    private CartesianChartModel barra, linea,barraComparativa;
    private List<ComboLista> lMunicipios, lPeriodos,lMunicipiosDisp, lMunicipiosComp, lMunicipioTotal,lSituacion;

    public ArbolReporteView() {
        context = SpringContext.getInstance();
        im = (IndicadorManager) context.getBean("IndicadorManager");
        tm = (TiposManager) context.getBean("TiposManager");
        sim = (SubIndicadorManager) context.getBean("SubIndicadorManager");
        vm = (VariableManager) context.getBean("VariableManager");
        rm = (ReporteManager) context.getBean("ReporteManager");
        zm = (ZonaGeograficaManager) context.getBean("ZonaGeograficaManager");
        root = new DefaultTreeNode("I", null);
        TreeNode nodoInicial = new DefaultTreeNode("I", root);
        NodoArbolIndicadores n0 = new NodoArbolIndicadores("I", "Indice de Gestión del Riesgo", TIPO_INDICE);
        tipoSeleccionado = TIPO_INDICE;
        data = new HashMap();
        data.put(n0.getCodigo(), n0);
        selectedNode = root;
        url = "ReporteIndice.xhtml";
        dataObject = new HashMap();
        lMunicipios = zm.getComboZona("TZ003");
        lMunicipioTotal = zm.getComboZona("TZ003");
        lMunicipiosComp = new ArrayList<>();
        desAsignarTodo();
        if (lMunicipios.size() > 0) {
            nombreZona = ((ComboLista) lMunicipios.get(0)).getDescripcion();
            idZona = ((ComboLista) lMunicipios.get(0)).getId();
            lMunicipiosComp.add((ComboLista) lMunicipios.get(0));
        }
        lPeriodos = rm.getPeriodos();
        if (lPeriodos.size() > 0) {
            periodo = ((ComboLista) lPeriodos.get(0)).getId();
        }
        lSituacion = tm.getTiposGrupoCombo("TIPO_SITUACION");
        if (lSituacion.size() > 0) {
            idSituacion = ((ComboLista) lSituacion.get(0)).getId();
        }
        graficasIniciales();


    }

    public void crearAnillo(double valor) {
        anillo = new DonutChartModel();
        Map<String, Number> circle = new LinkedHashMap<String, Number>();
        circle.put(NIVEL_DESEMPEÑO, valor);
        circle.put(RESTANTE, (double) (5 - valor));
        anillo.addCircle(circle);
    }

    public void crearBarra(List<ComboLista> lista) {
        barra = new CartesianChartModel();
        for (ComboLista cb : lista) {
            ChartSeries ind1 = new ChartSeries();
            ind1.setLabel(cb.getId());
            ind1.set(cb.getDescripcion(), Double.parseDouble(cb.getValor()));
            barra.addSeries(ind1);
        }
    }
    
    public void crearBarraComparativo(List<ComboLista> lista) {
        barraComparativa = new CartesianChartModel();
        for (ComboLista cb : lista) {
            ChartSeries ind1 = new ChartSeries();
            ind1.setLabel(cb.getDescripcion());
            ind1.set(tituloComparativo, Double.parseDouble(cb.getValor()));
            barraComparativa.addSeries(ind1);
        }
    }

    public void crearLinea(List<ComboLista> lista, String titulo) {
        linea = new CartesianChartModel();
        ChartSeries ind1 = new ChartSeries();
        ind1.setLabel(titulo);
        for (int i = lista.size() - 1; i >= 0; i--) {
            ComboLista cb = lista.get(i);
            ind1.set(cb.getDescripcion(), Double.parseDouble(cb.getValor()));
        }
        linea.addSeries(ind1);
    }

    public String nombreNodo(String nodo) {
        NodoArbolIndicadores n = (NodoArbolIndicadores) data.get(nodo);
        return n.getNombre();
    }

    public void agregarNodos(String clave, TreeNode padre) {
        NodoArbolIndicadores nodoData = (NodoArbolIndicadores) data.get(clave);
        if (!nodoData.isSelected()) {
            List hijos = nodoData.getHijos();
            if (hijos != null) {
                for (int i = 0; i < hijos.size(); i++) {
                    NodoArbolIndicadores nodoHijo = (NodoArbolIndicadores) hijos.get(i);
                    TreeNode nodeView = new DefaultTreeNode(nodoHijo.getCodigo() + nodoHijo.getTipo(), padre);
                }
            }
            nodoData.setSelected(true);
            data.put(clave, nodoData);
        }
    }

    public void agregarNuevoNodo(String codigo, String nombre, String tipo) {
        NodoArbolIndicadores nodoHijo = new NodoArbolIndicadores(codigo, nombre, tipo);
        TreeNode nodeView = new DefaultTreeNode(nodoHijo.getCodigo() + nodoHijo.getTipo(), getSelectedNode());
        getSelectedNode().setSelected(false);
        nodeView.setSelected(true);
        this.selectedNode = nodeView;
        data.put(nodoHijo.getCodigo() + nodoHijo.getTipo(), nodoHijo);

    }

    public void onNodeSelect(NodeSelectEvent event) {
        try {
            setSelectedNode(event.getTreeNode());
            String idNodo = (String) getSelectedNode().getData();
            obtenerInfo(idNodo);
            agregarNodos(idNodo, getSelectedNode());
            getSelectedNode().setExpanded(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void comparar(){
        switch (getTipoSeleccionado()) {
            case TIPO_INDICE:
                crearBarraComparativo(rm.getValoresMunicipios(lMunicipiosComp, TIPO_INDICE, "0", periodo));
                break;
            case TIPO_INDICADOR:
                indicador = (Indicador) dataObject.get(nodoData.getCodigo());
                crearBarraComparativo(rm.getValoresMunicipios(lMunicipiosComp, TIPO_INDICADOR, indicador.getCodigo(), periodo));
                tituloNivelDesempeno = indicador.getNombre();
                break;
            case TIPO_SUBINDICADOR:
                subIndicador = (SubIndicador) dataObject.get(nodoData.getCodigo());
                crearBarraComparativo(rm.getValoresMunicipios(lMunicipiosComp, TIPO_SUBINDICADOR, subIndicador.getCodigo(), periodo));
                tituloNivelDesempeno = subIndicador.getNombre();
                break;
            case TIPO_VARIABLE:
                variable = (Variable) dataObject.get(nodoData.getCodigo());
                crearBarraComparativo(rm.getValoresMunicipios(lMunicipiosComp, TIPO_VARIABLE, variable.getCodigo(), periodo));
                tituloNivelDesempeno = variable.getDescripcion();
                break;
        }
    }
    
    
    NodoArbolIndicadores nodoData;

    public void obtenerInfo(String clave) {
        nodoData = (NodoArbolIndicadores) data.get(clave);
        setTipoSeleccionado(nodoData.getTipo());
        List hijos = null;
        if (!nodoData.isSelected()) {
            if (nodoData.getTipo().equals(TIPO_INDICE)) {
                hijos = im.getIndicadoresArbol();
            } else if (nodoData.getTipo().equals(TIPO_INDICADOR)) {
                hijos = sim.getSubIndicadoresArbol(nodoData.getCodigo());
                indicador = im.getIndicador(nodoData.getCodigo());
                dataObject.put(nodoData.getCodigo(), indicador);
            } else if (nodoData.getTipo().equals(TIPO_SUBINDICADOR)) {
                hijos = vm.getVariablesArbol(nodoData.getCodigo());
                subIndicador = sim.getSubIndicador(nodoData.getCodigo());
                dataObject.put(nodoData.getCodigo(), subIndicador);
            } else if (nodoData.getTipo().equals(TIPO_VARIABLE)) {
                variable = vm.getVariable(nodoData.getCodigo());
                dataObject.put(nodoData.getCodigo(), variable);

            }
            nodoData.setHijos(hijos);
            if (hijos != null) {
                for (int i = 0; i < hijos.size(); i++) {
                    NodoArbolIndicadores nodoDataHijo = (NodoArbolIndicadores) hijos.get(i);
                    data.put(nodoDataHijo.getCodigo() + nodoDataHijo.getTipo(), nodoDataHijo);
                }
            }
        }
        switch (getTipoSeleccionado()) {
            case TIPO_INDICE:
                graficasIniciales();
                break;
            case TIPO_INDICADOR:
                indicador = (Indicador) dataObject.get(nodoData.getCodigo());
                crearAnillo(Double.parseDouble(rm.getValor(indicador.getCodigo(), getIdZona(), getPeriodo())));
                crearBarra(rm.getValoresHijos(indicador.getCodigo(), TIPO_INDICADOR, getIdZona(), getPeriodo()));
                crearLinea(rm.getValoresHistoricos(indicador.getCodigo(), getIdZona()), indicador.getNombre());
                crearBarraComparativo(rm.getValoresMunicipios(lMunicipiosComp, TIPO_INDICADOR, indicador.getCodigo(), periodo));

                tituloNivelDesempeno = indicador.getNombre();
                tituloComparativo = "Comparativo de SubIndicadores";
                tituloHistorico = "Histórico " + indicador.getNombre();
                break;
            case TIPO_SUBINDICADOR:
                subIndicador = (SubIndicador) dataObject.get(nodoData.getCodigo());
                crearAnillo(Double.parseDouble(rm.getValor(subIndicador.getCodigo(), getIdZona(), getPeriodo())));
                crearBarra(rm.getValoresHijos(subIndicador.getCodigo(), TIPO_SUBINDICADOR, getIdZona(), getPeriodo()));
                crearLinea(rm.getValoresHistoricos(subIndicador.getCodigo(), getIdZona()), subIndicador.getNombre());
                crearBarraComparativo(rm.getValoresMunicipios(lMunicipiosComp, TIPO_SUBINDICADOR, subIndicador.getCodigo(), periodo));

                tituloNivelDesempeno = subIndicador.getNombre();
                tituloComparativo = "Comparativo de Variables";
                tituloHistorico = "Histórico " + subIndicador.getNombre();
                break;
            case TIPO_VARIABLE:
                variable = (Variable) dataObject.get(nodoData.getCodigo());
                crearAnillo(Double.parseDouble(rm.getValor(variable.getCodigo(), getIdZona(), getPeriodo())));
                crearLinea(rm.getValoresHistoricos(variable.getCodigo(), getIdZona()), variable.getDescripcion());
                crearBarraComparativo(rm.getValoresMunicipios(lMunicipiosComp, TIPO_VARIABLE, variable.getCodigo(), periodo));

                tituloNivelDesempeno = variable.getDescripcion();
                tituloHistorico = "Histórico " + variable.getDescripcion();
                break;
        }

    }

    public void onNodeExpanded(NodeExpandEvent event) {//cuando expande en el arbol
        TreeNode nodoSeleccionado = event.getTreeNode();
        if (nodoSeleccionado.getParent() != null) { //si tiene padre para buscar hnos
            List<TreeNode> hermanos = nodoSeleccionado.getParent().getChildren();
            for (Iterator it = hermanos.iterator(); it.hasNext();) {
                TreeNode n = (TreeNode) it.next();
                if (!n.isSelected()) {
                    n.setExpanded(false);
                }
            }
        }
    }

    public void onNodeCollapse(NodeCollapseEvent event) {//cuando contrae en el arbol
        TreeNode nodoSeleccionado = event.getTreeNode();
        nodoSeleccionado.setExpanded(false);
    }

    /**
     * @return the root
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     * @return the selectedNode
     */
    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    /**
     * @param selectedNode the selectedNode to set
     */
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public String getTipo() {
        if (getSelectedNode() == null) {
            return TIPO_INDICE;
        }
        String idNodo = (String) getSelectedNode().getData();
        NodoArbolIndicadores nodo = (NodoArbolIndicadores) data.get(idNodo);
        return nodo.getTipo();
    }

    /**
     * @return the indicador
     */
    public Indicador getIndicador() {
        return indicador;
    }

    /**
     * @param indicador the indicador to set
     */
    public void setIndicador(Indicador indicador) {
        this.indicador = indicador;
    }

    /**
     * @return the subIndicador
     */
    public SubIndicador getSubIndicador() {
        return subIndicador;
    }

    /**
     * @param subIndicadorNuevo the subIndicador to set
     */
    public void setSubIndicador(SubIndicador subIndicador) {
        this.subIndicador = subIndicador;
    }

    /**
     * @return the variable
     */
    public Variable getVariable() {
        return variable;
    }

    /**
     * @param variable the variable to set
     */
    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    /**
     * @return the tipoSeleccionado
     */
    public String getTipoSeleccionado() {
        return tipoSeleccionado;
    }

    /**
     * @param tipoSeleccionado the tipoSeleccionado to set
     */
    public void setTipoSeleccionado(String tipoSeleccionado) {
        this.tipoSeleccionado = tipoSeleccionado;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the anillo
     */
    public DonutChartModel getAnillo() {
        return anillo;
    }

    /**
     * @param anillo the anillo to set
     */
    public void setAnillo(DonutChartModel anillo) {
        this.anillo = anillo;
    }

    /**
     * @return the barra
     */
    public CartesianChartModel getBarra() {
        return barra;
    }

    /**
     * @param barra the barra to set
     */
    public void setBarra(CartesianChartModel barra) {
        this.barra = barra;
    }

    /**
     * @return the linea
     */
    public CartesianChartModel getLinea() {
        return linea;
    }

    /**
     * @param linea the linea to set
     */
    public void setLinea(CartesianChartModel linea) {
        this.linea = linea;
    }

    /**
     * @return the nombreZona
     */
    public String getNombreZona() {
        return nombreZona;
    }

    /**
     * @param nombreZona the nombreZona to set
     */
    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    /**
     * @return the tituloNivelDesempeno
     */
    public String getTituloNivelDesempeno() {
        return tituloNivelDesempeno;
    }

    /**
     * @param tituloNivelDesempeno the tituloNivelDesempeno to set
     */
    public void setTituloNivelDesempeno(String tituloNivelDesempeno) {
        this.tituloNivelDesempeno = tituloNivelDesempeno;
    }

    /**
     * @return the tituloComparativo
     */
    public String getTituloComparativo() {
        return tituloComparativo;
    }

    /**
     * @param tituloComparativo the tituloComparativo to set
     */
    public void setTituloComparativo(String tituloComparativo) {
        this.tituloComparativo = tituloComparativo;
    }

    /**
     * @return the tituloHistorico
     */
    public String getTituloHistorico() {
        return tituloHistorico;
    }

    /**
     * @param tituloHistorico the tituloHistorico to set
     */
    public void setTituloHistorico(String tituloHistorico) {
        this.tituloHistorico = tituloHistorico;
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

    /**
     * @return the lMunicipios
     */
    public List<ComboLista> getlMunicipios() {
        return lMunicipios;
    }

    /**
     * @param lMunicipios the lMunicipios to set
     */
    public void setlMunicipios(List<ComboLista> lMunicipios) {
        this.lMunicipios = lMunicipios;
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
     * @return the idZona
     */
    public String getIdZona() {
        return idZona;
    }

    /**
     * @param idZona the idZona to set
     */
    public void setIdZona(String idZona) {
        this.idZona = idZona;
    }

    public void municipioHandleChange(ValueChangeEvent event) {
        System.out.println("New value: " + event.getNewValue());
        idZona = String.valueOf(event.getNewValue());
        for (ComboLista cb : lMunicipios) {
            if (cb.getId().equals(idZona)) {
                nombreZona = cb.getDescripcion();
                lMunicipiosComp.add(cb);
                break;
            }
        }
        graficasIniciales();
    }

    public void periodoHandleChange(ValueChangeEvent event) {
        System.out.println("New value: " + event.getNewValue());
        periodo = String.valueOf(event.getNewValue());
        graficasIniciales();
    }

    public void graficasIniciales() {
        crearAnillo(Double.parseDouble(rm.getValor("0", getIdZona(), getPeriodo())));
        crearBarra(rm.getValoresHijos("0", TIPO_INDICE, getIdZona(), getPeriodo()));
        crearLinea(rm.getValoresHistoricos("0", getIdZona()), "Indice de Gestión del Riesgo");
        crearBarraComparativo(rm.getValoresMunicipios(lMunicipiosComp, TIPO_INDICE, "0", periodo));

        tituloNivelDesempeno = "Indice de Gestión del Riesgo";
        tituloComparativo = "Comparativo de Indicadores";
        tituloHistorico = "Histórico Indice de Gestión del Riesgo";
    }

    /**
     * @return the municipioSel
     */
    public String getMunicipioSel() {
        return municipioSel;
    }

    /**
     * @param municipioSel the municipioSel to set
     */
    public void setMunicipioSel(String municipioSel) {
        this.municipioSel = municipioSel;
    }

    /**
     * @return the municipioComp
     */
    public String getMunicipioComp() {
        return municipioComp;
    }

    /**
     * @param municipioComp the municipioComp to set
     */
    public void setMunicipioComp(String municipioComp) {
        this.municipioComp = municipioComp;
    }

    /**
     * @return the barraComparativa
     */
    public CartesianChartModel getBarraComparativa() {
        return barraComparativa;
    }

    /**
     * @param barraComparativa the barraComparativa to set
     */
    public void setBarraComparativa(CartesianChartModel barraComparativa) {
        this.barraComparativa = barraComparativa;
    }

    /**
     * @return the lMunicipiosDisp
     */
    public List<ComboLista> getlMunicipiosDisp() {
        return lMunicipiosDisp;
    }

    /**
     * @param lMunicipiosDisp the lMunicipiosDisp to set
     */
    public void setlMunicipiosDisp(List<ComboLista> lMunicipiosDisp) {
        this.lMunicipiosDisp = lMunicipiosDisp;
    }

    /**
     * @return the lMunicipiosComp
     */
    public List<ComboLista> getlMunicipiosComp() {
        return lMunicipiosComp;
    }

    /**
     * @param lMunicipiosComp the lMunicipiosComp to set
     */
    public void setlMunicipiosComp(List<ComboLista> lMunicipiosComp) {
        this.lMunicipiosComp = lMunicipiosComp;
    }

    /**
     * @return the lMunicipioTotal
     */
    public List<ComboLista> getlMunicipioTotal() {
        return lMunicipioTotal;
    }

    /**
     * @param lMunicipioTotal the lMunicipioTotal to set
     */
    public void setlMunicipioTotal(List<ComboLista> lMunicipioTotal) {
        this.lMunicipioTotal = lMunicipioTotal;
    }
    
    public void desAsignar() {
        for (ComboLista cbA : lMunicipiosComp) {
            if (cbA.getId().equals(municipioComp)) {
                lMunicipiosDisp.add(cbA);
                lMunicipiosComp.remove(cbA);
                break;
            }
        }
    }

    public void asignar() {
        for (ComboLista cbt : lMunicipiosDisp) {
            if (cbt.getId().equals(municipioSel)) {
                lMunicipiosComp.add(cbt);
                lMunicipiosDisp.remove(cbt);
                break;
            }
        }
    }

    public void desAsignarTodo() {
        lMunicipiosDisp = new ArrayList<>();
        for (ComboLista cbT : lMunicipioTotal) {
            lMunicipiosDisp.add(cbT);
        }
        lMunicipiosComp = new ArrayList<>();
    }

    public void asignarTodo() {
        lMunicipiosComp = new ArrayList<>();
        for (ComboLista cbT : lMunicipioTotal) {
            lMunicipiosComp.add(cbT);
        }
        lMunicipiosDisp = new ArrayList<>();
    }

    /**
     * @return the idSituacion
     */
    public String getIdSituacion() {
        return idSituacion;
    }

    /**
     * @param idSituacion the idSituacion to set
     */
    public void setIdSituacion(String idSituacion) {
        this.idSituacion = idSituacion;
    }

    /**
     * @return the lSituacion
     */
    public List<ComboLista> getlSituacion() {
        return lSituacion;
    }

    /**
     * @param lSituacion the lSituacion to set
     */
    public void setlSituacion(List<ComboLista> lSituacion) {
        this.lSituacion = lSituacion;
    }
}
