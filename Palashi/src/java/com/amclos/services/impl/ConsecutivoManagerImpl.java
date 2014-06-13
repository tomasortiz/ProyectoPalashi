/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services.impl;


import com.amclos.dao.Dao;
import com.amclos.services.ConsecutivoManager;
import com.amclos.services.SpringContext;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author 85154220
 */

@Service("ConsecutivoManager")
public class ConsecutivoManagerImpl implements ConsecutivoManager{
    
    @Autowired
    @Qualifier ("DaoHibernate")
    private Dao dao ;
    
    @Override
    public String getConsecutivo(String tabla, String usuario, String programa, String operacion, String valor) {
        try {
            Connection con = dao.getConnection();
            String sql = "{?=call GeneraCons(?,?,?,?,?)}";
            CallableStatement  statement = con.prepareCall(sql);
            statement.registerOutParameter(1,Types.INTEGER);
            statement.setString(2, usuario);
            statement.setString(3, programa);
            statement.setString(4, tabla);
            statement.setString(5, operacion);
            statement.setInt(6, Integer.parseInt(valor));
            statement.executeQuery();
            int conse = statement.getInt(1);
            con.close();
            return String.valueOf(conse);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ConsecutivoManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static void main(String [] agr){
        try{
            SpringContext context = SpringContext.getInstance();
            ConsecutivoManager app = (ConsecutivoManager) context.getBean("ConsecutivoManager");
            System.out.println(app.getConsecutivo("OPCIONES_RESPUESTA", "MIGRACION", "MANUAL", "A", "0"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
