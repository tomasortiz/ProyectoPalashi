

package com.amclos.notificacion;

import java.util.Properties;
import java.util.Vector;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

/**
* <p>Title: EMail</p>
*
* <p>Description: Envio de correo con Java</p>
*
* <p>Copyright: Copyright (c) 2011</p>
*
* <p>Company: INDRA </p>
*
* @author AGUSTIN MERIÑO
* @version 1.0
*/

public class EMail {

    private Properties props;
    private Session session;
    private MimeMessage message;
    private Vector toAdress,ccAdress,bccAdress;
    private String fromAdress,subject;
    private String host;
    private String puerto;
    private String encoding = "ISO-8859-1";
    private String tipoMensaje = "html";
    private StringBuffer mensaje;

    public EMail() {
        init();
    }

    public EMail(Vector toAdress,String fromAdress,String subject,StringBuffer mensaje){
        this.toAdress = toAdress;
        this.fromAdress = fromAdress;
        this.subject = subject;
        this.mensaje = mensaje;
        init();
    }

   private void init() {
        host = "smtp.gmail.com";
        puerto = "587";
        props = new Properties();
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", puerto);
        props.setProperty("mail.smtp.user", "infopalashi@gmail.com");
        props.setProperty("mail.smtp.pass", "$P123456");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        session = Session.getDefaultInstance(getProps());
        session.setDebug(true);
        message = new MimeMessage(getSession());
   }
    

    public void send () throws AddressException, MessagingException{
            setToAdressMail();
            setCcAdressMail();
            setBccAdressMail();
            setFromAdressMail();
            message.setSubject(getSubject());
            message.setText(mensaje.toString(),encoding,tipoMensaje);
            Transport t = session.getTransport("smtp");
            t.connect("infopalashi@gmail.com", "$P123456");
            t.sendMessage(message,message.getAllRecipients());
            t.close();
            System.out.println("enviado");
    }
   
    /**
     * @return the props
     */
    public Properties getProps() {
        return props;
    }

    /**
     * @param props the props to set
     */
    public void setProps(Properties props) {
        this.props = props;
    }

    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * @return the message
     */
    public MimeMessage getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(MimeMessage message) {
        this.message = message;
    }

    /**
     * @return the toAdress
     */
    public Vector getToAdress() {
        return toAdress;
    }

    /**
     * @param toAdress the toAdress to set
     */
    private void setToAdressMail() throws MessagingException {
        for (int i = 0; i < toAdress.size(); i++) {
                getMessage().addRecipient(Message.RecipientType.TO,new InternetAddress((String) toAdress.get(i)));
            }
    }

    /**
     * @return the ccAdress
     */
    public Vector getCcAdress() {
        return ccAdress;
    }

    /**
     * @param ccAdress the ccAdress to set
     */
    private void setCcAdressMail() throws AddressException, MessagingException {
        if(ccAdress != null)
        for (int i = 0; i < ccAdress.size(); i++) {
            getMessage().addRecipient(Message.RecipientType.CC,new InternetAddress((String) ccAdress.get(i)));
        }
    }

    /**
     * @return the bccAdress
     */
    public Vector getBccAdress() {
        return bccAdress;
    }

    /**
     * @param bccAdress the bccAdress to set
     */
    private void setBccAdressMail() throws AddressException, MessagingException {
        if(bccAdress != null)
        for (int i = 0; i < bccAdress.size(); i++) {
            getMessage().addRecipient(Message.RecipientType.BCC,new InternetAddress((String) bccAdress.get(i)));
        }
    }

    /**
     * @return the fromAdress
     */
    public String getFromAdress() {
        return fromAdress;
    }

    /**
     * @param fromAdress the fromAdress to set
     */
    public void setFromAdress(String fromAdress) {
        this.fromAdress = fromAdress;
    }

    private void setFromAdressMail() throws MessagingException {
        getMessage().setFrom(new InternetAddress(fromAdress));
    }

    /**
     * @param toAdress the toAdress to set
     */
    public void setToAdress(Vector toAdress) {
        this.toAdress = toAdress;
    }

    /**
     * @param ccAdress the ccAdress to set
     */
    public void setCcAdress(Vector ccAdress) {
        this.ccAdress = ccAdress;
    }

    /**
     * @param bccAdress the bccAdress to set
     */
    public void setBccAdress(Vector bccAdress) {
        this.bccAdress = bccAdress;
    }

    /**
     * @return the mensaje
     */
    public StringBuffer getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(StringBuffer mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }


    public static void main(String arg[]) throws AddressException, MessagingException{
        Vector toAdress = new Vector();
        toAdress.add("larryobispo@gmail.com");
        String fromAdress = "infopalashi@gmail.com";
        String subject = " Correo de prueba";
        StringBuffer mensaje = new StringBuffer("<p> Notificador de palashi</p> ");
        EMail mail = new EMail(toAdress,fromAdress,subject,mensaje);
        mail.setBccAdress(null);
        mail.setCcAdress(null);
        mail.send ();
   }


}
