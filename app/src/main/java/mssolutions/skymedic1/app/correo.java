package mssolutions.skymedic1.app;
import android.os.StrictMode;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//import java.util.function.Function;


/**
 * Created by marci on 6/7/2017.
 */

public class correo {
    public String respuesta="";
public String enviarcorreo(String receptor, String asunto, String mensaje, String telefono, String nombre){

//username password receptor asunto mensaje
try {final String username = "biodiagsantaelena@gmail.com";
    final String password = "n7608440";
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
    try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("biodiagsantaelena@gmail.com"));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(receptor));
        message.setSubject(asunto);
        mensaje = nombre+'\n' +telefono+ '\n'+'\n' + mensaje;
        message.setContent(mensaje,"text/html; charset=utf-8");

        Transport.send(message);

        System.out.println("Done");
        respuesta="Su mensaje ha sido enviado";
    } catch (MessagingException e) {
        respuesta="Error al enviar";
        throw new RuntimeException(e);

    }

} catch (Exception e) {
        respuesta="Error al enviar, verifique su conexión";
        throw new RuntimeException(e);

    }


        return respuesta;
    }
}
