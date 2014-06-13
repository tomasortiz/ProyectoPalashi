/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.view.dataModel;



import com.amclos.model.Institucion;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Curiel
 */
public class InstitucionDataModel extends ListDataModel<Institucion> implements SelectableDataModel<Institucion>{
    
    public InstitucionDataModel (){
    
    }
    public InstitucionDataModel(List<Institucion> data) {  
        super(data);  
    } 
    
    
    
  

    @Override
    public Institucion getRowData(String rowKey) {
        
          List<Institucion> institucions = (List<Institucion>) getWrappedData();           
       // for (Iterator<Institucion> it = institucions.iterator(); it.hasNext();) {        
          for (Institucion institucion :institucions){          
              //Institucion institucion = it.next();  
            if(String.valueOf(institucion.getCodigo()).equals(rowKey))  
                return institucion;
          }  
          
        return null;       
        
        
    }
    
    @Override
    public Object getRowKey(Institucion institucion) {
           return institucion.getCodigo(); 
    }
    
    
}
