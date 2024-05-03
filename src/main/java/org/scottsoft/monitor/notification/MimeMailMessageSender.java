package org.scottsoft.monitor.notification;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.List;

public class MimeMailMessageSender {

    private final JavaMailSender mailSender;
    private final List<InternetAddress> recipients;
    private String subject;
    private String content;

    public static MimeMailMessageSender createMailer(JavaMailSender mailSender) {
        return new MimeMailMessageSender(mailSender);
    }

    private MimeMailMessageSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.recipients = Lists.newArrayList();
    }

    public MimeMailMessageSender addRecipients(String... recipients) {
        for (String recipient : recipients) {
            try {
                this.recipients.add(new InternetAddress(recipient));
            } catch (AddressException e) {
                throw new IllegalStateException("An error occurred adding recipient " + recipient, e);
            }
        }

        return this;
    }

    public MimeMailMessageSender setSubject(final String subject) {
        this.subject = subject;
        return this;
    }

    public MimeMailMessageSender setContent(final String content) {
        this.content = content;
        return this;
    }

    public void sendMail() {
        Preconditions.checkArgument(this.subject != null, "A subject must be provided before sending mail.");
        Preconditions.checkArgument(this.content != null, "Content must be provided before sending mail.");
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            for (final InternetAddress recipient : this.recipients) {
                mimeMessageHelper.addTo(recipient);
            }
            mimeMessageHelper.setSubject(this.subject);
            mimeMessageHelper.setText(this.content, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("An error occurred sending email.", e);
        }
    }

}
