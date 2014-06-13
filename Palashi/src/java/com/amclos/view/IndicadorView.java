/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view;

import com.amclos.model.Indicador;
import com.amclos.model.OpcionesRespuesta;
import com.amclos.model.Preguntas;
import com.amclos.model.SubIndicador;
import com.amclos.model.Variable;
import com.amclos.services.IndicadorManager;
import com.amclos.services.OpcionRespuestaManager;
import com.amclos.services.ParametroManager;
import com.amclos.services.PreguntasManager;
import com.amclos.services.SpringContext;
import com.amclos.services.SubIndicadorManager;
import com.amclos.services.TiposManager;
import com.amclos.services.VariableManager;
import com.amclos.view.dataModel.ComboLista;
import com.amclos.view.dataModel.NodoArbolIndicadores;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Administrador
 */
@ManagedBean
@SessionScoped
public class IndicadorView {

    private TreeNode root;
    private TreeNode selectedNode;
    private IndicadorManager im;
    private SubIndicadorManager sim;
    private VariableManager vm;
    private TiposManager tm;
    private PreguntasManager pm;
    private ParametroManager param;
    private OpcionRespuestaManager orm;
    private SpringContext context;
    private HashMap data, dataObject;
    private String usuario, programa = "IndicadoresView.xhtml", codigo, nombre, peso, descripcion, relevancia, tipoEscala;
    private List<ComboLista> tiposEscala, tiposSituacion, tiposInstitucionesTot, tiposInstitucionesAsig, tiposInstitucionesNoAsig;
    private Indicador indicador;
    private SubIndicador subIndicadorNuevo, subIndicadorEdit;
    private Variable variableNueva, variableEdit;
    private Preguntas preguntasNueva, preguntasEdit;
    private OpcionesRespuesta orNueva, opcionRSelected;
    private List<OpcionesRespuesta> listOR;
    private String disableIndicador = "true";
    private String preguntaDefecto, tipoSeleccionado, tipoSituacion, url, tipoInstitucion, tipoInstitucionAsig, disabledGuardarIntVar;
    public static final String TIPO_INDICE = "IND000",
            TIPO_INDICADOR = "IND001",
            TIPO_SUBINDICADOR = "IND002",
            TIPO_VARIABLE = "IND003",
            TIPO_PREGUNTA = "IND004";

    public IndicadorView() {
        context = SpringContext.getInstance();
        im = (IndicadorManager) context.getBean("IndicadorManager");
        tm = (TiposManager) context.getBean("TiposManager");
        sim = (SubIndicadorManager) context.getBean("SubIndicadorManager");
        vm = (VariableManager) context.getBean("VariableManager");
        pm = (PreguntasManager) context.getBean("PreguntasManager");
        param = (ParametroManager) context.getBean("ParametroManager");
        orm = (OpcionRespuestaManager) context.getBean("OpcionRespuestaManager");
        root = new DefaultTreeNode("I", null);
        TreeNode nodoInicial = new DefaultTreeNode("I", root);
        NodoArbolIndicadores n0 = new NodoArbolIndicadores("I", "Indice de gobernabilidad para la gestión del riesgo costero", TIPO_INDICE);
        tipoSeleccionado = TIPO_INDICE;
        data = new HashMap();
        data.put(n0.getCodigo(), n0);
        tiposEscala = tm.getTiposGrupoCombo("TIPO_ESCALA");
        tiposInstitucionesTot = tm.getTiposGrupoCombo("TIPO_INSTITUCION");
        tiposSituacion = tm.getTiposGrupoCombo("TIPO_SITUACION");
        selectedNode = root;
        subIndicadorNuevo = new SubIndicador();
        variableNueva = new Variable();
        preguntasNueva = new Preguntas();
        preguntaDefecto = param.getValorParametro("PREGUNTA_DEFECTO");
        preguntasNueva.setDescripcion(preguntaDefecto);

        url = "Indice.xhtml";
        dataObject = new HashMap();
        orNueva = new OpcionesRespuesta();
    }
    
    public String init(){
        usuario = login.getUsuario();
        return "";
    }
    
    @ManagedProperty("#{login}")
    private Login login;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String nombreNodo(String nodo) {
        NodoArbolIndicadores n = (NodoArbolIndicadores) data.get(nodo);
        if (n.getTipo().equals(TIPO_PREGUNTA)) {
            return n.getValor();
        }
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
        if (getTipoSeleccionado().equals(TIPO_VARIABLE)) {
            nodoHijo.setValor((nodoData.getHijos().size() + 1) + "_" + preguntasNueva.getTipoSituacion().getDescripcion());
        }
        TreeNode nodeView = new DefaultTreeNode(nodoHijo.getCodigo() + nodoHijo.getTipo(), getSelectedNode());
        getSelectedNode().setSelected(false);
        nodeView.setSelected(true);
        this.selectedNode = nodeView;
        data.put(nodoHijo.getCodigo() + nodoHijo.getTipo(), nodoHijo);

    }

    public void onNodeSelect(NodeSelectEvent event) {
        try {
            disableIndicador = "true";
            setSelectedNode(event.getTreeNode());
            String idNodo = (String) getSelectedNode().getData();
            obtenerInfo(idNodo);
            agregarNodos(idNodo, getSelectedNode());
            getSelectedNode().setExpanded(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    NodoArbolIndicadores nodoData;

    public void obtenerInfo(String clave) {
        nodoData = (NodoArbolIndicadores) data.get(clave);
        setTipoSeleccionado(nodoData.getTipo());
        List hijos = null;
        switch (getTipoSeleccionado()) {
            case TIPO_INDICE:
                url = "Indice.xhtml";
                break;
            case TIPO_INDICADOR:
                indicador = (Indicador) dataObject.get(nodoData.getCodigo());
                url = "Indicador.xhtml";
                break;
            case TIPO_SUBINDICADOR:
                subIndicadorEdit = (SubIndicador) dataObject.get(nodoData.getCodigo());
                url = "SubIndicador.xhtml";
                break;
            case TIPO_VARIABLE:
                variableEdit = (Variable) dataObject.get(nodoData.getCodigo());
                url = "Variable.xhtml";
                tiposInstitucionesAsig = vm.getTiposInstituciones(nodoData.getCodigo());
                setTiposInstitucionesNoAsig();
                disabledGuardarIntVar = "true";
                break;
            case TIPO_PREGUNTA:
                setPreguntasEdit((Preguntas) dataObject.get(nodoData.getCodigo()));
                if (preguntasEdit != null) {
                    listOR = orm.getOpcionesRespuesta(String.valueOf(preguntasEdit.getCodigo()));
                }
                url = "Preguntas.xhtml";
                break;
        }
        if (!nodoData.isSelected()) {
            if (nodoData.getTipo().equals(TIPO_INDICE)) {
                hijos = im.getIndicadoresArbol();
            } else if (nodoData.getTipo().equals(TIPO_INDICADOR)) {
                hijos = sim.getSubIndicadoresArbol(nodoData.getCodigo());
                indicador = im.getIndicador(nodoData.getCodigo());
                dataObject.put(nodoData.getCodigo(), indicador);
            } else if (nodoData.getTipo().equals(TIPO_SUBINDICADOR)) {
                hijos = vm.getVariablesArbol(nodoData.getCodigo());
                subIndicadorEdit = sim.getSubIndicador(nodoData.getCodigo());
                dataObject.put(nodoData.getCodigo(), subIndicadorEdit);
            } else if (nodoData.getTipo().equals(TIPO_VARIABLE)) {
                hijos = pm.getPreguntasArbol(nodoData.getCodigo());
                variableEdit = vm.getVariable(nodoData.getCodigo());
                dataObject.put(nodoData.getCodigo(), variableEdit);

            } else if (nodoData.getTipo().equals(TIPO_PREGUNTA)) {
                //hijos = pm.getPreguntasArbol(nodoData.getCodigo());
                preguntasEdit = pm.getPreguntas(nodoData.getCodigo());
                dataObject.put(nodoData.getCodigo(), preguntasEdit);
                listOR = orm.getOpcionesRespuesta(String.valueOf(preguntasEdit.getCodigo()));
            }
            nodoData.setHijos(hijos);
            if (hijos != null) {
                for (int i = 0; i < hijos.size(); i++) {
                    NodoArbolIndicadores nodoDataHijo = (NodoArbolIndicadores) hijos.get(i);
                    data.put(nodoDataHijo.getCodigo() + nodoDataHijo.getTipo(), nodoDataHijo);
                }
            }
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

    public void guardar() {
        try {
            switch (getTipoSeleccionado()) {
                case TIPO_INDICE:
                    if (im.guardar(usuario, programa, codigo, nombre, descripcion, relevancia, peso, tipoEscala)) {
                        agregarNuevoNodo(codigo, nombre, TIPO_INDICADOR);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Indicador creado exitosamente.", ""));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Error al momento de crear el Indicador, Contacte al administrador del sistema", ""));
                    }
                    break;
                case TIPO_INDICADOR:
                    subIndicadorNuevo.setUsuario(usuario);
                    subIndicadorNuevo.setPrograma(programa);
                    subIndicadorNuevo.setCodigoIndicador(indicador);
                    if (sim.guardar(subIndicadorNuevo)) {
                        agregarNuevoNodo(subIndicadorNuevo.getCodigo(), subIndicadorNuevo.getNombre(), TIPO_SUBINDICADOR);
                        dataObject.put(subIndicadorNuevo.getCodigo(), subIndicadorNuevo);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Subindicador creado exitosamente.", ""));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Error al momento de crear el Subindicador, Contacte al administrador del sistema", ""));
                    }
                    break;
                case TIPO_SUBINDICADOR:
                    variableNueva.setUsuario(usuario);
                    variableNueva.setPrograma(programa);
                    variableNueva.setCodSubindicador(subIndicadorEdit);
                    if (vm.guardar(variableNueva)) {
                        agregarNuevoNodo(variableNueva.getCodigo(), variableNueva.getDescripcion(), TIPO_VARIABLE);
                        dataObject.put(variableNueva.getCodigo(), variableNueva);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Variable creada exitosamente.", ""));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Error al momento de crear la variable, Contacte al administrador del sistema", ""));
                    }
                    break;

                case TIPO_VARIABLE:
                    getPreguntasNueva().setUsuario(usuario);
                    getPreguntasNueva().setPrograma(programa);
                    getPreguntasNueva().setVariablecod(variableEdit);
                    getPreguntasNueva().setTipoSituacion(tm.getTipo(tipoSituacion));
                    if (pm.guardar(getPreguntasNueva())) {
                        agregarNuevoNodo(String.valueOf(getPreguntasNueva().getCodigo()), getPreguntasNueva().getDescripcion(), TIPO_PREGUNTA);
                        dataObject.put(String.valueOf(getPreguntasNueva().getCodigo()), getPreguntasNueva());
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Pregunta creada exitosamente.", ""));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Error al momento de crear la pregunta, Contacte al administrador del sistema", ""));
                    }
                    break;

                case TIPO_PREGUNTA:
                    orNueva.setUsuario(usuario);
                    orNueva.setPrograma(programa);
                    orNueva.setPreguntascodigo(preguntasEdit);
                    if (orm.save(orNueva)) {
                        listOR.add(orNueva);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Opcion de Respuesta creada exitosamente.", ""));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Error al momento de crear la Opcion de Respuesta, Contacte al administrador del sistema", ""));
                    }
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema.", ""));
        } finally {
            limpiarCampos();
        }
    }

    public void limpiarCampos() {
        codigo = "";
        nombre = "";
        peso = "";
        descripcion = "";
        relevancia = "";
        tipoEscala = "";
        subIndicadorNuevo = new SubIndicador();
        variableNueva = new Variable();
        preguntasNueva = new Preguntas();
        preguntasNueva.setDescripcion(preguntaDefecto);
        orNueva = new OpcionesRespuesta();
    }

    public void editar() {
        disableIndicador = "false";
        System.out.println(disableIndicador);
    }

    public void actualizar() {
        try {
            switch (getTipoSeleccionado()) {
                case TIPO_INDICADOR:
                    String codigo = indicador.getCodigo();
                    String nombre = indicador.getNombre();
                    String descripcion = indicador.getDescripcion();
                    String relevancia = indicador.getRelevancia();
                    String peso = String.valueOf(indicador.getPeso());
                    String tipoEscala = indicador.getTipoEscala().getCodigo();
                    indicador.getTipoEscala().setDescripcion(tm.getTipo(tipoEscala).getDescripcion());
                    if (im.guardar(usuario, programa, codigo, nombre, descripcion, relevancia, peso, tipoEscala)) {
                        disableIndicador = "true";
                        dataObject.put(indicador.getCodigo(), indicador);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Indicador actualizado exitosamente.", ""));

                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Error al momento de actualizar el Indicador, Contacte al administrador del sistema", ""));
                    }
                    break;
                case TIPO_SUBINDICADOR:
                    subIndicadorEdit.setUsuario(usuario);
                    subIndicadorEdit.setPrograma(programa);
                    if (sim.guardar(subIndicadorEdit)) {
                        dataObject.put(subIndicadorEdit.getCodigo(), subIndicadorEdit);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Subindicador actualizado exitosamente.", ""));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Error al momento de actualizar el Subindicador, Contacte al administrador del sistema", ""));
                    }
                    break;
                case TIPO_VARIABLE:
                    variableEdit.setUsuario(usuario);
                    variableEdit.setPrograma(programa);
                    if (vm.guardar(variableEdit)) {
                        disableIndicador = "true";
                        dataObject.put(variableEdit.getCodigo(), variableEdit);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Variable actualizada exitosamente.", ""));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Error al momento de actualizar la variable, Contacte al administrador del sistema", ""));
                    }
                    break;
                case TIPO_PREGUNTA:
                    getPreguntasEdit().setUsuario(usuario);
                    getPreguntasEdit().setPrograma(programa);
                    getPreguntasEdit().setTipoSituacion(tm.getTipo(tipoSituacion));
                    if (pm.guardar(getPreguntasEdit())) {
                        disableIndicador = "true";
                        dataObject.put(String.valueOf(getPreguntasEdit().getCodigo()), getPreguntasEdit());
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Pregunta actualizada exitosamente.", ""));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Error al momento de actualizar la pregunta, Contacte al administrador del sistema", ""));
                    }
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema.", ""));
        }
    }

    public void eliminar() {
        try {
            String clave, codigo;
            switch (getTipoSeleccionado()) {
                case TIPO_INDICADOR:
                    clave = (String) getSelectedNode().getData();
                    codigo = (String) ((NodoArbolIndicadores) data.get(clave)).getCodigo();
                    if (im.eliminar(usuario, programa, codigo)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Indicador eliminado exitosamente.", ""));
                        TreeNode padre = getSelectedNode().getParent();
                        padre.getChildren().remove(getSelectedNode());
                        setSelectedNode(padre);
                        padre.setSelected(true);
                        data.remove(clave);
                        dataObject.remove(codigo);
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al momento de eliminar el Indicador, Contacte al administrador del sistema", ""));
                    }
                    break;
                case TIPO_SUBINDICADOR:
                    clave = (String) getSelectedNode().getData();
                    codigo = (String) ((NodoArbolIndicadores) data.get(clave)).getCodigo();
                    if (sim.eliminar(usuario, programa, codigo)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Subindicador eliminado exitosamente.", ""));
                        TreeNode padre = getSelectedNode().getParent();
                        padre.getChildren().remove(getSelectedNode());
                        setSelectedNode(padre);
                        padre.setSelected(true);
                        data.remove(clave);
                        dataObject.remove(codigo);
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Error al momento de eliminar el Subndicador, Contacte al administrador del sistema", ""));
                    }
                    break;

                case TIPO_VARIABLE:
                    clave = (String) getSelectedNode().getData();
                    codigo = (String) ((NodoArbolIndicadores) data.get(clave)).getCodigo();
                    if (vm.eliminar(usuario, programa, codigo)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Variable eliminada exitosamente.", ""));
                        TreeNode padre = getSelectedNode().getParent();
                        padre.getChildren().remove(getSelectedNode());
                        setSelectedNode(padre);
                        padre.setSelected(true);
                        data.remove(clave);
                        dataObject.remove(codigo);
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Error al momento de eliminar la variable, Contacte al administrador del sistema", ""));
                    }
                    break;
                case TIPO_PREGUNTA:
                    clave = (String) getSelectedNode().getData();
                    codigo = String.valueOf(((NodoArbolIndicadores) data.get(clave)).getCodigo());
                    if (pm.eliminar(usuario, programa, codigo)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Pregunta eliminada exitosamente.", ""));
                        TreeNode padre = getSelectedNode().getParent();
                        padre.getChildren().remove(getSelectedNode());
                        setSelectedNode(padre);
                        padre.setSelected(true);
                        data.remove(clave);
                        dataObject.remove(codigo);
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Error al momento de eliminar la pregunta, Contacte al administrador del sistema", ""));
                    }
                    break;


            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema.", ""));
        }
    }

    public void eliminarOR() {
        try {
            if (orm.delete(usuario, programa, String.valueOf(this.opcionRSelected.getCodigoNivel()))) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Opcion de Respuesta eliminada exitosamente.", ""));
                listOR.remove(opcionRSelected);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Error al momento de eliminar la opción de respuesta, Contacte al administrador del sistema", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema.", ""));
        }
    }

    public void actualizarOR() {
        try {
            if (orm.save(opcionRSelected)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Opcion de Respuesta actualizada exitosamente.", ""));
                
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Error al momento de actualizada la opción de respuesta, Contacte al administrador del sistema", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema.", ""));
        }
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the peso
     */
    public String getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(String peso) {
        this.peso = peso;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the relevancia
     */
    public String getRelevancia() {
        return relevancia;
    }

    /**
     * @param relevancia the relevancia to set
     */
    public void setRelevancia(String relevancia) {
        this.relevancia = relevancia;
    }

    /**
     * @return the tipoEscala
     */
    public String getTipoEscala() {
        return tipoEscala;
    }

    /**
     * @param tipoEscala the tipoEscala to set
     */
    public void setTipoEscala(String tipoEscala) {
        this.tipoEscala = tipoEscala;
    }

    /**
     * @return the tiposEscala
     */
    public List<ComboLista> getTiposEscala() {
        return tiposEscala;
    }

    /**
     * @param tiposEscala the tiposEscala to set
     */
    public void setTiposEscala(List<ComboLista> tiposEscala) {
        this.tiposEscala = tiposEscala;
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
     * @return the enableIndicador
     */
    public String getDisableIndicador() {
        return disableIndicador;
    }

    /**
     * @param enableIndicador the enableIndicador to set
     */
    public void setIsableIndicador(String disableIndicador) {
        this.disableIndicador = disableIndicador;
    }

    /**
     * @return the subIndicadorNuevo
     */
    public SubIndicador getSubIndicadorNuevo() {
        return subIndicadorNuevo;
    }

    /**
     * @param subIndicadorNuevo the subIndicadorNuevo to set
     */
    public void setSubIndicadorNuevo(SubIndicador subIndicadorNuevo) {
        this.subIndicadorNuevo = subIndicadorNuevo;
    }

    /**
     * @return the subIndicadorEdit
     */
    public SubIndicador getSubIndicadorEdit() {
        return subIndicadorEdit;
    }

    /**
     * @param subIndicadorEdit the subIndicadorEdit to set
     */
    public void setSubIndicadorEdit(SubIndicador subIndicadorEdit) {
        this.subIndicadorEdit = subIndicadorEdit;
    }

    /**
     * @return the variableEdit
     */
    public Variable getVariableEdit() {
        return variableEdit;
    }

    /**
     * @param variableEdit the variableEdit to set
     */
    public void setVariableEdit(Variable variableEdit) {
        this.variableEdit = variableEdit;
    }

    /**
     * @return the variableNueva
     */
    public Variable getVariableNueva() {
        return variableNueva;
    }

    /**
     * @param variableNueva the variableNueva to set
     */
    public void setVariableNueva(Variable variableNueva) {
        this.variableNueva = variableNueva;
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
     * @return the tiposInstitucionesTot
     */
    public List<ComboLista> getTiposInstitucionesTot() {
        return tiposInstitucionesTot;
    }

    /**
     * @param tiposInstitucionesTot the tiposInstitucionesTot to set
     */
    public void setTiposInstitucionesTot(List<ComboLista> tiposInstitucionesTot) {
        this.tiposInstitucionesTot = tiposInstitucionesTot;
    }

    /**
     * @return the tipoInstitucion
     */
    public String getTipoInstitucion() {
        return tipoInstitucion;
    }

    /**
     * @param tipoInstitucion the tipoInstitucion to set
     */
    public void setTipoInstitucion(String tipoInstitucion) {
        this.tipoInstitucion = tipoInstitucion;
    }

    /**
     * @return the tiposInstitucionesAsig
     */
    public List<ComboLista> getTiposInstitucionesAsig() {
        return tiposInstitucionesAsig;
    }

    /**
     * @param tiposInstitucionesAsig the tiposInstitucionesAsig to set
     */
    public void setTiposInstitucionesAsig(List<ComboLista> tiposInstitucionesAsig) {
        this.tiposInstitucionesAsig = tiposInstitucionesAsig;
    }

    /**
     * @return the tipoInstitucionAsig
     */
    public String getTipoInstitucionAsig() {
        return tipoInstitucionAsig;
    }

    /**
     * @param tipoInstitucionAsig the tipoInstitucionAsig to set
     */
    public void setTipoInstitucionAsig(String tipoInstitucionAsig) {
        this.tipoInstitucionAsig = tipoInstitucionAsig;
    }

    /**
     * @return the tiposInstitucionesNoAsig
     */
    public List<ComboLista> getTiposInstitucionesNoAsig() {
        return tiposInstitucionesNoAsig;
    }

    /**
     * @param tiposInstitucionesNoAsig the tiposInstitucionesNoAsig to set
     */
    public void setTiposInstitucionesNoAsig() {
        tiposInstitucionesNoAsig = new ArrayList<>();
        for (ComboLista cbT : tiposInstitucionesTot) {
            boolean asignado = false;
            for (ComboLista cbA : tiposInstitucionesAsig) {
                if (cbT.getId().equals(cbA.getId())) {
                    asignado = true;
                }
            }
            if (!asignado) {
                tiposInstitucionesNoAsig.add(cbT);
            }
        }
    }

    public void desAsignar() {
        for (ComboLista cbA : tiposInstitucionesAsig) {
            if (cbA.getId().equals(tipoInstitucionAsig)) {
                tiposInstitucionesNoAsig.add(cbA);
                tiposInstitucionesAsig.remove(cbA);
                break;
            }
        }
        disabledGuardarIntVar = "false";
    }

    public void asignar() {
        for (ComboLista cbt : tiposInstitucionesNoAsig) {
            if (cbt.getId().equals(tipoInstitucion)) {
                tiposInstitucionesAsig.add(cbt);
                tiposInstitucionesNoAsig.remove(cbt);
                break;
            }
        }
        disabledGuardarIntVar = "false";
    }

    public void desAsignarTodo() {
        tiposInstitucionesNoAsig = new ArrayList<>();
        for (ComboLista cbT : tiposInstitucionesTot) {
            tiposInstitucionesNoAsig.add(cbT);
        }
        tiposInstitucionesAsig = new ArrayList<>();
        disabledGuardarIntVar = "false";
    }

    public void asignarTodo() {
        tiposInstitucionesAsig = new ArrayList<>();
        for (ComboLista cbT : tiposInstitucionesTot) {
            tiposInstitucionesAsig.add(cbT);
        }
        tiposInstitucionesNoAsig = new ArrayList<>();
        disabledGuardarIntVar = "false";
    }

    /**
     * @return the disabledGuardarIntVar
     */
    public String getDisabledGuardarIntVar() {
        return disabledGuardarIntVar;
    }

    public void guardarInsVar() {
        try {
            if (vm.guardarInstitucionVariable(usuario, programa, tiposInstitucionesAsig, variableEdit)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Cambios guardados exitosamente.", ""));
                disabledGuardarIntVar = "true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Error al momento de guardar los cambios, Contacte al administrador del sistema", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema.", ""));
        }
    }

    /**
     * @return the preguntasNueva
     */
    public Preguntas getPreguntasNueva() {
        return preguntasNueva;
    }

    /**
     * @param preguntasNueva the preguntasNueva to set
     */
    public void setPreguntasNueva(Preguntas preguntasNueva) {
        this.preguntasNueva = preguntasNueva;
    }

    /**
     * @return the preguntasEdit
     */
    public Preguntas getPreguntasEdit() {
        return preguntasEdit;
    }

    /**
     * @param preguntasEdit the preguntasEdit to set
     */
    public void setPreguntasEdit(Preguntas preguntasEdit) {
        this.preguntasEdit = preguntasEdit;
    }

    /**
     * @return the tiposSituacion
     */
    public List<ComboLista> getTiposSituacion() {
        return tiposSituacion;
    }

    /**
     * @param tiposSituacion the tiposSituacion to set
     */
    public void setTiposSituacion(List<ComboLista> tiposSituacion) {
        this.tiposSituacion = tiposSituacion;
    }

    /**
     * @return the tipoSituacion
     */
    public String getTipoSituacion() {
        return tipoSituacion;
    }

    /**
     * @param tipoSituacion the tipoSituacion to set
     */
    public void setTipoSituacion(String tipoSituacion) {
        this.tipoSituacion = tipoSituacion;
    }

    /**
     * @return the orNueva
     */
    public OpcionesRespuesta getOrNueva() {
        return orNueva;
    }

    /**
     * @param orNueva the orNueva to set
     */
    public void setOrNueva(OpcionesRespuesta orNueva) {
        this.orNueva = orNueva;
    }

    /**
     * @return the opcionRSelected
     */
    public OpcionesRespuesta getOpcionRSelected() {
        return opcionRSelected;
    }

    /**
     * @param opcionRSelected the opcionRSelected to set
     */
    public void setOpcionRSelected(OpcionesRespuesta opcionRSelected) {
        this.opcionRSelected = opcionRSelected;
    }

    /**
     * @return the listOR
     */
    public List<OpcionesRespuesta> getListOR() {
        return listOR;
    }

    /**
     * @param listOR the listOR to set
     */
    public void setListOR(List<OpcionesRespuesta> listOR) {
        this.listOR = listOR;
    }
}
