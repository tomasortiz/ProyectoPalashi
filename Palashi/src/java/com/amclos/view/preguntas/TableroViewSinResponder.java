/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view.preguntas;

import com.amclos.model.OpcionesRespuesta;
import com.amclos.model.Preguntas;
import com.amclos.model.Respuesta;
import com.amclos.model.RespuestaPK;
import com.amclos.model.Usuario;
import com.amclos.services.PreguntasManager;
import com.amclos.services.SpringContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Ultimate
 */
@ManagedBean(name = "TableroViewSinResponder")
@ViewScoped
public class TableroViewSinResponder {

    /**
     * Creates a new instance of TableroView
     */
    //select p.descripcion, p.tipoSituacion.descripcion, p.codigo, p.variablecod from Preguntas p,  Usuario u, Institucion  i, InstitucionVariable v where v.institucionVariablePK.variablecodigo=p.variablecod and v.tipos.codigo=i.tipoInstitucion.codigo
//and u.institucioncodigo.codigo=i.codigo and u.codigo='SISLOS'  and p.estado in ('AC001') and p.tipoSituacion.codigo in ('TIS001')
    private PreguntasManager preguntasmanager;
    private List<Preguntas> preguntas;
    private List<Preguntas> preguntasGuardar;
    private Preguntas pre;
    private List<OpcionesRespuesta> opcionrespuesta;
    private Respuesta[] respuestaGuardar;
    private RespuestaPK pk;
    private OpcionesRespuesta select;
     private List<Preguntas> preguntasAC002;
    private List<Preguntas> preguntasAC001;
    private String numeroPreguntasTotalAC002;
    private String numeroPreguntasTotalAC001;
    private PreguntasManager pm;
  //  private ParametroManager param;
    private SpringContext context;
    private String respuestaSeleccionada;
    private String codigoPregunta;
    private int index;
    private List<Integer> valor;
    private Date fecha;
    private Respuesta respuesta;
    private String evidencia;
    private List<String> evidenciaList;
    private boolean footer;
    private boolean actualizarPermit;
    private String usuario;
    private String nombre;
    private Usuario usuario_;
    private String programa;
    private String varSesionPregunta;
    private String numeroDePreguntasMes;
     FacesContext ctx;
    HttpServletRequest request;
    String ruta;
    private String param;
    public TableroViewSinResponder() {
        //buscarPreguntas();
        footer=true;
        usuario_ = new Usuario();
        actualizarPermit= false;
        fecha = new Date();
        valor = new ArrayList<>();
        evidenciaList = new ArrayList<>();
        pk = new RespuestaPK();
        respuesta = new Respuesta();
        preguntas = new ArrayList<>();
        preguntasGuardar = new ArrayList<>();
        opcionrespuesta = new ArrayList<>();
        select = new OpcionesRespuesta();
         preguntasAC002= new ArrayList<>();
        preguntasAC001= new ArrayList<>();
        //respuestaGuardar=new Respuesta[index];
        usuario = ((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getCodigo();
        nombre = ((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario")).getNombre();
        usuario_ = ((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario"));
        context = SpringContext.getInstance();  
        pm = (PreguntasManager) context.getBean("PreguntasManager");
        ctx = FacesContext.getCurrentInstance();
        request = (HttpServletRequest) ctx.getExternalContext().getRequest();
        ruta  = request.getRequestURL().toString().replace(request.getRequestURI(), "") + request.getContextPath();
        init();
        
         try
        {
         param=((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("tipo");
        if(param!=null)
        {
            System.out.println("parametro "+param);
            initPreguntas(param);
        }
        else
        {
          param=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipo");
          System.out.println("parametro 2 "+param);            
           initPreguntas(param);
        }
        }catch(Exception e){}
         
    }

     public void init()
    {
        //preguntas
      preguntasAC001 = pm.listaPreguntasSinResponder(usuario,"TIS001");    
        numeroPreguntasTotalAC001=String.valueOf(preguntasAC001.size());
        
        preguntasAC002=pm.listaPreguntasSinResponder(usuario,"TIS002");    
        numeroPreguntasTotalAC002=String.valueOf(preguntasAC002.size());
        
                if(numeroPreguntasTotalAC002==null || numeroPreguntasTotalAC002.equals(""))
            numeroPreguntasTotalAC002="0";
        if(numeroPreguntasTotalAC001==null || numeroPreguntasTotalAC001.equals(""))
            numeroPreguntasTotalAC001="0";
        
        //preguntas=preguntasAC001;
        
        programa = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        programa = programa.substring(programa.lastIndexOf("/") + 1, programa.length());

    }
     
     
    public void initPreguntas(String pregunta)
    {
    if(pregunta.equals("TIS001"))
        preguntas=preguntasAC001;
    if(pregunta.equals("TIS002"))
        preguntas=preguntasAC002;
    
    
        try {
            for (int i = 0; i < preguntas.size(); i++) {
                opcionrespuesta = pm.opcionrespuesta("AC001",preguntas.get(i).getCodigo().toString());
                preguntas.get(i).setOpcionesRespuestaList(opcionrespuesta);
            }
        } catch (Exception e) {
        }
    
    }
     
    public void onTabChange(TabChangeEvent event) {
        initPreguntas(event.getTab().getId().toString());
        
    }


  
   
    public void guardarRespuesta() {
        respuestaGuardar = new Respuesta[preguntasGuardar.size()];
        System.out.println("Vamos a guardar" + preguntasGuardar.size());   
        for (int i = 0; i < preguntasGuardar.size(); i++) {
            respuesta= new Respuesta();
            pk=new RespuestaPK();
            respuesta.setFecha(fecha);
            respuesta.setPreguntas(preguntasGuardar.get(i));
            respuesta.setPrograma(programa);
            respuesta.setUrl(ruta + "/" + programa);
            respuesta.setUsuario(usuario);
            respuesta.setUsuario1(usuario_);
            pk.setFechaRespuesta(respuesta.getFecha());
            pk.setPreguntascodigo(preguntasGuardar.get(i).getCodigo());
            pk.setUsuariocodigo(usuario);
            respuesta.setRespuestaPK(pk);
            respuesta.setEvidencia(evidenciaList.get(i));
            respuesta.setValor(valor.get(i).longValue());
            respuestaGuardar[i] = respuesta;
        }
          if (pm.guardar(respuestaGuardar) == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Exitoso", "Se han Guardado Correctamente"));
            evidencia=null;
            actualizarPermit=true;
            RequestContext.getCurrentInstance().execute("window.location.reload(true)");
            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "no se han Guardado"));
        }
    }

    /**
     * @return the preguntasmanager
     */
    public PreguntasManager getPreguntasmanager() {
        return preguntasmanager;
    }

    /**
     * @param preguntasmanager the preguntasmanager to set
     */
    public void setPreguntasmanager(PreguntasManager preguntasmanager) {
        this.preguntasmanager = preguntasmanager;
    }

    /**
     * @return the preguntas
     */
    public List<Preguntas> getPreguntas() {
        return preguntas;
    }

    /**
     * @param preguntas the preguntas to set
     */
    public void setPreguntas(List<Preguntas> preguntas) {
        this.preguntas = preguntas;
    }

    /**
     * @return the context
     */
    public SpringContext getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(SpringContext context) {
        this.context = context;
    }

    /**
     * @return the opcionrespuesta
     */
    public List<OpcionesRespuesta> getOpcionrespuesta() {
        return opcionrespuesta;
    }

    /**
     * @param opcionrespuesta the opcionrespuesta to set
     */
    public void setOpcionrespuesta(List<OpcionesRespuesta> opcionrespuesta) {
        this.opcionrespuesta = opcionrespuesta;
    }

    /**
     * @return the respuestaSeleccionada
     */
    public String getRespuestaSeleccionada() {
        return respuestaSeleccionada;
    }

    /**
     * @param respuestaSeleccionada the respuestaSeleccionada to set
     */
    public void setRespuestaSeleccionada(String respuestaSeleccionada) {
        this.respuestaSeleccionada = respuestaSeleccionada;
    }

    /**
     * @return the select
     */
    public OpcionesRespuesta getSelect() {
        return select;
    }

    /**
     * @param select the select to set
     */
    public void setSelect(OpcionesRespuesta select) {
        this.select = select;
    }

    /**
     * @return the codigoPregunta
     */
    public String getCodigoPregunta() {
        return codigoPregunta;
    }

    /**
     * @param codigoPregunta the codigoPregunta to set
     */
    public void setCodigoPregunta(String codigoPregunta) {
        this.codigoPregunta = codigoPregunta;
    }

    public Respuesta[] getRespuestaGuardar() {
        return respuestaGuardar;
    }

    public void setRespuestaGuardar(Respuesta[] respuestaGuardar) {
        this.respuestaGuardar = respuestaGuardar;
    }

    public RespuestaPK getPk() {
        return pk;
    }

    public void setPk(RespuestaPK pk) {
        this.pk = pk;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void onRowSelect(SelectEvent event) {
        footer = false;
        pre = ((OpcionesRespuesta) event.getObject()).getPreguntascodigo();
        int cont = 0;
        if (preguntasGuardar.size() == 0) {
            preguntasGuardar.add(pre);
            valor.add(((OpcionesRespuesta) event.getObject()).getOrden());
           // evidenciaList.add(evidencia);
        } else {
            for (int i = 0; i < preguntasGuardar.size(); i++) {
                if (preguntasGuardar.get(i).getCodigo() == pre.getCodigo())//pregunta existe
                {
                    cont++;
                    break;
                }
            }
            if (cont == 0) {
                preguntasGuardar.add(pre);
                valor.add(((OpcionesRespuesta) event.getObject()).getOrden());
             //   evidenciaList.add(evidencia);
            } else {
                cont = 0;
            }

         //   System.out.println("entro selec" + pre.getCodigo());
           // FacesMessage msg = new FacesMessage("Evento: ", ((OpcionesRespuesta) event.getObject()).getDescripcion());

            //FacesContext.getCurrentInstance().addMessage(null, msg);
        }
       // pre=null;
    }

    public void onRowUnselect(UnselectEvent event) {
        footer = false;
        pre = ((OpcionesRespuesta) event.getObject()).getPreguntascodigo();
        int cont = 0;
        for (int i = 0; i < preguntasGuardar.size(); i++) {
            if (preguntasGuardar.get(i).getCodigo() == pre.getCodigo()) {
                preguntasGuardar.remove(pre);
                valor.remove(valor.get(i));
             //   evidenciaList.remove(evidenciaList.get(i));
                break;
            }
        }
        System.out.println("un select " + pre.getCodigo());
        //FacesMessage msg = new FacesMessage("", ((OpcionesRespuesta) event.getObject()).getDescripcion());

//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void stringTablero()
    {
    
    }
    public void redir() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
            String redireccionar = request.getRequestURL().toString().replace(request.getRequestURI(), "") + request.getContextPath();
            externalContext.redirect(redireccionar + "/preguntas.xhtml");

        } catch (IOException ex) {
            Logger.getLogger(TableroViewMes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Preguntas getPre() {
        return pre;
    }

    public void setPre(Preguntas pre) {
        this.pre = pre;
    }

    public List<Preguntas> getPreguntasGuardar() {
        return preguntasGuardar;
    }

    public void setPreguntasGuardar(List<Preguntas> preguntasGuardar) {
        this.preguntasGuardar = preguntasGuardar;
    }

    public List<Integer> getValor() {
        return valor;
    }

    public void setValor(List<Integer> valor) {
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {             
        if(pre!=null)
        {
        for (int i = 0; i < preguntasGuardar.size(); i++) {
            if (preguntasGuardar.get(i).getCodigo() == pre.getCodigo()) {
                if(evidenciaList!=null)                    
                evidenciaList.add(i,evidencia);
                else
                   if(evidenciaList.get(i)!=null)
                   {
                   evidenciaList.set(i, evidencia);
                   }else
                   evidenciaList.add(i,evidencia);
                                   
                 // evidencia=null;
                break;
            }
            }
        }
       // pre=null;
        this.evidencia = evidencia;
    }

    public List<String> getEvidenciaList() {
        return evidenciaList;
    }

    public void setEvidenciaList(List<String> evidenciaList) {
        this.evidenciaList = evidenciaList;
    }

    public boolean isFooter() {
        return footer;
    }

    public void setFooter(boolean footer) {
        this.footer = footer;
    }

  

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getUsuario_() {
        return usuario_;
    }

    public void setUsuario_(Usuario usuario_) {
        this.usuario_ = usuario_;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getVarSesionPregunta() {
        return varSesionPregunta;
    }

    public void setVarSesionPregunta(String varSesionPregunta) {
        this.varSesionPregunta = varSesionPregunta;
    }

    public String getNumeroDePreguntasMes() {
        return numeroDePreguntasMes;
    }

    public void setNumeroDePreguntasMes(String numeroDePreguntasMes) {
        this.numeroDePreguntasMes = numeroDePreguntasMes;
    }

    public List<Preguntas> getPreguntasAC002() {
        return preguntasAC002;
    }

    public void setPreguntasAC002(List<Preguntas> preguntasAC002) {
        this.preguntasAC002 = preguntasAC002;
    }

    public List<Preguntas> getPreguntasAC001() {
        return preguntasAC001;
    }

    public void setPreguntasAC001(List<Preguntas> preguntasAC001) {
        this.preguntasAC001 = preguntasAC001;
    }

    public String getNumeroPreguntasTotalAC002() {
        return numeroPreguntasTotalAC002;
    }

    public void setNumeroPreguntasTotalAC002(String numeroPreguntasTotalAC002) {
        this.numeroPreguntasTotalAC002 = numeroPreguntasTotalAC002;
    }

    public String getNumeroPreguntasTotalAC001() {
        return numeroPreguntasTotalAC001;
    }

    public void setNumeroPreguntasTotalAC001(String numeroPreguntasTotalAC001) {
        this.numeroPreguntasTotalAC001 = numeroPreguntasTotalAC001;
    }

    public boolean isActualizarPermit() {
        return actualizarPermit;
    }

    public void setActualizarPermit(boolean actualizarPermit) {
        this.actualizarPermit = actualizarPermit;
    }
    
    
}
