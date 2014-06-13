/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services;

import com.amclos.model.OpcionesRespuesta;
import com.amclos.model.Preguntas;
import com.amclos.model.Respuesta;
import com.amclos.view.dataModel.NodoArbolIndicadores;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface PreguntasManager {

    public List<NodoArbolIndicadores> getPreguntasArbol(String codigoVariable);
    public boolean guardar(Preguntas pregunta);
    public boolean eliminar(String usuario, String programa, String codigo);
    public Preguntas getPreguntas(String codigo);    
    public List<Preguntas> listaPreguntas_respondidas(String usuario,String code);//preguntas respondidas en total
    public List<Preguntas> listaPreguntasMes(String usuario,String code);//preguntas en el mes actual
    public List<Respuesta> obtenerRespuesta(String usuario,String code);
    public List<OpcionesRespuesta> opcionrespuesta(String codigo,String codPregunta);
     public List<Preguntas> listaPreguntasSinResponder(String usuario,String code);//preguntas sin responder
    public boolean guardar(Respuesta[] respuesta);
    public Respuesta guardar(String codPregunta);


 
}
