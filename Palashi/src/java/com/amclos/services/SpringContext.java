/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.amclos.services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContext {
    private static ApplicationContext context;
    private static SpringContext instance;
    private final static String CONFIG_FILE = "applicationContext.xml";

    public static SpringContext getInstance() {
        try{
        if (null == instance) {
            instance = new SpringContext();
            context = new ClassPathXmlApplicationContext(CONFIG_FILE);
        }
        
        }catch(Exception e){
            e.printStackTrace();
        }
        return instance;
    }

    public Object getBean(String bean) {
        return context.getBean(bean);
    }
     public static void main(String [] agr){
        try{
            SpringContext.getInstance();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
