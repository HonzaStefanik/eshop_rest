package org.hsnr.rest.util;

import com.sun.mail.smtp.SMTPTransport;
import com.sun.net.ssl.internal.ssl.Provider;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.Security;
import java.util.Properties;

public class EmailUtil {

  private static final Logger LOG = LoggerFactory.getLogger(EmailUtil.class);

  private static final String SSLSOCKET_FACTORY = "SSLSocketFactory";

  private EmailUtil() {} // util class

  public static void newPasswordEmail(String recipient, String newPassword) {
    String content = "Hello, dear customer,\nyour new password is: " + newPassword;
    String subject = "EshopMsc - New Password";
    send(recipient, subject, content);
  }

  private static void send(String recipient, String subject, String content) {
    Security.addProvider(new Provider());

    Session session = Session.getInstance(getProperties(), null);

    final MimeMessage msg = new MimeMessage(session);

    try {
      msg.setFrom(new InternetAddress("eshopmsc@gmail.com"));
      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
      msg.setSubject(subject);
      msg.setText(content, "utf-8");
      SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
      t.connect("smtp.gmail.com", "eshopMsc@gmail.com", "Eshop1Mail2");
      t.sendMessage(msg, msg.getAllRecipients());
      t.close();
    } catch (MessagingException e) {
      LOG.error("Email failed to send for recipient '{}'", recipient, e);
    }
  }

  public static boolean validateAddress(String email) {
    boolean allowLocal = false;
    return EmailValidator.getInstance(allowLocal).isValid(email);
  }

  private static Properties getProperties() {
    Properties props = System.getProperties();
    props.setProperty("mail.smtps.host", "smtp.gmail.com");
    props.setProperty("mail.smtp.socketFactory.class", SSLSOCKET_FACTORY);
    props.setProperty("mail.smtp.socketFactory.fallback", "false");
    props.setProperty("mail.smtp.port", "465");
    props.setProperty("mail.smtp.socketFactory.port", "465");
    props.setProperty("mail.smtps.auth", "true");

    props.put("mail.smtps.quitwait", "false");
    return props;
  }
}