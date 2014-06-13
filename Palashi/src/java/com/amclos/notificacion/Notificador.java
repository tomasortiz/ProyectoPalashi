/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.notificacion;

import com.amclos.dao.SqlHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;


/**
 *
 * @author Administrador
 */
public class Notificador {
    private SqlHandler sqlHandler;
    private List tiposInstituciones;
    private HashMap hCantVariables, hVariablesAsociadas;
    private StringBuffer mensaje;
    
    public Notificador() throws SQLException{
        sqlHandler = new SqlHandler();
        sqlHandler.establishConnection();
    }
    
    public static void main(String [] argv){
        Notificador notificador;
        try {
            notificador = new Notificador();
            notificador.setVariablesMes();
            notificador.enviarMensaje("");
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(Notificador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setVariablesMes() throws SQLException{
        setTiposInstituciones();
        setVariablesAsociadas();
        for (int i = 0; i < tiposInstituciones.size(); i++) {
            String tipoInstitucion = (String) tiposInstituciones.get(i);
            List variables = (List) hVariablesAsociadas.get(tipoInstitucion);
            if(variables instanceof List)
            for (int j = 0; j < variables.size(); j++) {
                String variable = (String) variables.get(j);
                String update =  " UPDATE INSTITUCION_VARIABLE SET FECHA = TO_DATE(TO_CHAR(SYSDATE,'YYYY/MM/')||'01', 'YYYY/MM/DD') " +
                                 "  WHERE TIPO_INSTITUCION = '"+tipoInstitucion+"' " +
                                 "   AND VARIABLECODIGO = '"+variable+"'";
                sqlHandler.updateStatement(update);
            }
        }
        sqlHandler.commitTransactions();
    }
    
    public void setTiposInstituciones() throws SQLException{
        String select = " SELECT CODIGO FROM TIPOS " +
                        "  WHERE GRUPO = 'TIPO_INSTITUCION' ";
        tiposInstituciones = new ArrayList();
        ResultSet rs = sqlHandler.selectStatement(select);
        while(rs.next()){
            tiposInstituciones.add(rs.getString("CODIGO"));
        }
    }
    /**
     * Creamos un HashMap que contiene la relacion entre tipo de institucion y variables
     * @throws SQLException 
     */
    public void setVariablesAsociadas() throws SQLException{
        String select = " SELECT TIPO_INSTITUCION, VARIABLECODIGO FROM INSTITUCION_VARIABLE " 
                       +"  WHERE FECHA IS NULL ";
        ResultSet rs = sqlHandler.selectStatement(select);
        hVariablesAsociadas = new HashMap();
        setCantidadVariablesAsig();
        while(rs.next()){
            List variables = (List) (hVariablesAsociadas.get(rs.getString("TIPO_INSTITUCION")) == null? new ArrayList(): hVariablesAsociadas.get(rs.getString("TIPO_INSTITUCION")));
            int cantidadVariables = Integer.parseInt((String)hCantVariables.get(rs.getString("TIPO_INSTITUCION")));
            if(!variables.contains(rs.getString("VARIABLECODIGO")) && cantidadVariables > variables.size() ){
                variables.add(rs.getString("VARIABLECODIGO"));
            }
            hVariablesAsociadas.put(rs.getString("TIPO_INSTITUCION"),variables);
        }
    }
    
    public void setCantidadVariablesAsig() throws SQLException{
        String select = " SELECT TIPO_INSTITUCION, "
                      + "       CASE "
                      + "       WHEN TRUNC(NUM_VARIABLES/TO_NUMBER(P1.VALOR)) >  TO_NUMBER(P2.VALOR) THEN TRUNC(NUM_VARIABLES/TO_NUMBER(P1.VALOR))   "
                      + "       ELSE TO_NUMBER(P2.VALOR) "                               
                      + "        END CANTIDAD "
                      + " FROM  PARAMETRO P1 ,  "
                      + "       PARAMETRO P2 ,"
                      + "       (SELECT TIPO_INSTITUCION, COUNT(*) NUM_VARIABLES FROM INSTITUCION_VARIABLE IV "
                      + "         GROUP BY TIPO_INSTITUCION) IV "
                      + " WHERE P1.ID_PARAMETRO = 'NUM_MESES_PERIODO' "
                      + "   AND P2.ID_PARAMETRO = 'NUM_VARIABLES_ASIG' ";
        ResultSet rs = sqlHandler.selectStatement(select);
        hCantVariables = new HashMap();
        while(rs.next()){            
            hCantVariables.put(rs.getString("TIPO_INSTITUCION"),rs.getString("CANTIDAD"));
        }
    }
    public void enviarMensaje(String tipoInstitucion) throws AddressException, MessagingException{
        Vector toAdress = new Vector();
        toAdress.add("ingeniero.agustin@gmail.com");
        toAdress.add("amcaro@indracompany.com");
        String fromAdress = "infopalashi@gmail.com";
        prepararMensaje(tipoInstitucion);
        String subject = " Correo de prueba";
        EMail mail = new EMail(toAdress,fromAdress,subject,mensaje);
        mail.setBccAdress(null);
        mail.setCcAdress(null);
        mail.send ();
    }
    public void prepararMensaje(String tipoInstitucion){
        
        mensaje = new StringBuffer("<!DOCTYPE html>");
        mensaje.append("<html>");
        mensaje.append("<head>");
        mensaje.append("<title>Notificacion Palashi </title>");
        mensaje.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        mensaje.append("<link href=\"http://54.187.36.111/Palashi/css/bootstrap.css\" rel=\"stylesheet\"/>");
        mensaje.append("</head>");
        mensaje.append("<body>");
        mensaje.append("<div class=\"navbar navbar-inverse navbar-fixed-top\" role=\"navigation\">");
        mensaje.append("<div class=\"container\">");
        mensaje.append("<div class=\"navbar-header\">");
        mensaje.append("<a href=\"http://54.187.36.111/Palashi\" class=\" navbar-brand\" >Palashi</a>");
        mensaje.append("</div>");
        mensaje.append("</div>");
        mensaje.append("</div>");
        mensaje.append("<br><br>");
        mensaje.append("<div class=\"container\">");
        mensaje.append("<h2> Variables asignadas del mes : </h2>");
        mensaje.append("<ul>");
        
        mensaje.append("<li> Variable</li>");
        
        mensaje.append("</ul>");
        mensaje.append("<br><br>");
        mensaje.append("Presione click en el siguiente link para <a href=\"http://54.187.36.111/Palashi\" class=\" navbar-brand\" >Actualizar las Variables</a>");
        mensaje.append("</div>");
        mensaje.append("<br><br>");
        mensaje.append("<div class=\"container\">");
        mensaje.append("<footer>");
        mensaje.append("<table>");
        mensaje.append(" <tr>");
        mensaje.append("<td> <img src=\"http://54.187.36.111/Palashi/images/sergio.jpg\" width=\"80\" height=\"80\"/></td>");
        mensaje.append("<td> <img src=\"http://54.187.36.111/Palashi/images/corpo.jpg\" width=\"80\" height=\"80\"/></td>");
        mensaje.append("<td> <img src=\"http://54.187.36.111/Palashi/images/playascorp.png\" width=\"80\" height=\"80\"/></td>");
        mensaje.append("<td> <img src=\"http://54.187.36.111/Palashi/images/oriente.jpg\" width=\"80\" height=\"80\"/></td>");
        mensaje.append("<td> <img src=\"http://54.187.36.111/Palashi/images/iemp.png\" width=\"80\" height=\"80\"/></td>");
        mensaje.append("<td> <img src=\"http://54.187.36.111/Palashi/images/colciencias.jpg\" width=\"80\" height=\"80\"/></td>");
        mensaje.append("<td> <img src=\"http://54.187.36.111/Palashi/images/esap.jpg\" width=\"80\" height=\"80\"/></td>");
        mensaje.append("</tr>");
        mensaje.append("</table>");
        mensaje.append("<br></br>");
        mensaje.append("<p>Diseñado por  Amclos 2014</p>");
        mensaje.append("</footer>");
        mensaje.append("</div>");
        mensaje.append("</body>");
        mensaje.append("</html>");
    }
    
    
    
}


