package com.autoui.fwk.notifiers;

import com.autoui.fwk.reporting.Logger;
//import com.jcraft.jsch.Session;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;
import javax.mail.Transport;


public class Mail {

    Session session = null;
    private static final Logger _logger = Logger.getLogger(Mail.class);

    public Mail(String smtpServer) {
        // Getting system properties
        Properties properties = System.getProperties();

        // Setting up mail server
        properties.setProperty("mail.smtp.host", smtpServer);

        // creating session object to get properties
        session = Session.getDefaultInstance(properties);
        _logger.info("SMTP server set as :" + smtpServer);
    }

    public void sendEmail(String recipient, String sender, String subject, String body) {
        try {

            _logger.info("Sending mail to " + recipient + ", from " + sender + ", subject " + subject + ", body " + body);
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));

            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: subject of the email
            message.setSubject(subject);

            // set body of the email.
            message.setText(body);

            // Send email.
            Transport.send(message);

            _logger.info("Email sent...");

        } catch (MessagingException mex) {
            _logger.error("Failed to send the email reason " + mex.toString());
        }

    }
}
