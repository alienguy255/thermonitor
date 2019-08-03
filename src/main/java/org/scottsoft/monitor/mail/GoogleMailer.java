package org.scottsoft.monitor.mail;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class GoogleMailer {

    private Properties mailServerProperties;
    private List<InternetAddress> recipients;
    private String subject;
    private String content;

    public static GoogleMailer createMailer() {
        return new GoogleMailer();
    }

    private GoogleMailer() {
        this.mailServerProperties = System.getProperties();
        this.recipients = Lists.newArrayList();
    }

    public GoogleMailer addRecipient(String recipient) {
        try {
            this.recipients.add(new InternetAddress(recipient));
        } catch (AddressException e) {
            throw new IllegalStateException("An error occurred adding recipient " + recipient, e);
        }
        return this;
    }

    public GoogleMailer setSubject(final String subject) {
        this.subject = subject;
        return this;
    }

    public GoogleMailer setContent(final String content) {
        this.content = content;
        return this;
    }

    public void sendMail() {
        Preconditions.checkArgument(this.subject != null, "A subject must be provided before sending mail.");
        Preconditions.checkArgument(this.content != null, "Content must be provided before sending mail.");
        try {
            this.mailServerProperties.put("mail.smtp.port", "587");
            this.mailServerProperties.put("mail.smtp.auth", "true");
            this.mailServerProperties.put("mail.smtp.starttls.enable", "true");
            final Session getMailSession = Session.getDefaultInstance(this.mailServerProperties, null);
            final MimeMessage generateMailMessage = new MimeMessage(getMailSession);
            for (final InternetAddress recipient : this.recipients) {
                generateMailMessage.addRecipient(Message.RecipientType.TO, recipient);
            }
            generateMailMessage.setSubject(this.subject);
            generateMailMessage.setContent((Object)this.content, "text/html");
            Transport transport = getMailSession.getTransport("smtp");
            transport.connect("smtp.gmail.com", "<username here>", "<passwd here>");
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();
        }
        catch (MessagingException e) {
            throw new IllegalStateException("An error occurred sending email.", e);
        }
    }

}
