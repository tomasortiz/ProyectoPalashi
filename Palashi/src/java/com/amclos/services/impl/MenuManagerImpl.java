/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amclos.services.impl;

import com.amclos.dao.Dao;
import com.amclos.model.Menu;
import com.amclos.services.MenuManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrador
 */
@Service("MenuManager")
public class MenuManagerImpl implements MenuManager {

    @Autowired
    @Qualifier("DaoHibernate")
    private Dao dao;

    @Override
    public List<Menu> getMenuPrincipal(String codigoUsuario) {
        String hql = "select p.menu "
                + " from  MenuPerfil p,UsuarioPerfil u"
                + " where p.perfil.codigo= u.codigoPerfil.codigo "
                + " and   p.estado = 'AC001'  "
                + " and   u.usuario1.codigo = '"+codigoUsuario+"'"
                + " and   u.fechaBaja is null"
                + " and   p.menu.codPadre is null"
                + " order by p.menu.orden";
        return dao.find(hql);
    }

    @Override
    public boolean save(String usuario, String programa, String codigo, String codPadre, String nombre, Long orden, String icono, String url) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String usuario, String programa, String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Menu getMenu(String codigo) {
                String hql = "select m from Menu m where m.codigo = "+codigo;
          return dao.findObject(hql);    
    }
}
