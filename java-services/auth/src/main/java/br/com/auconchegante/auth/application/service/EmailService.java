package br.com.auconchegante.auth.application.service;

import br.com.auconchegante.auth.domain.exceptions.InternalException;
import br.com.auconchegante.auth.domain.port.outgoing.EmailProtocol;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class EmailService implements EmailProtocol {
    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender mailSender;
    private final Configuration configuration;

    @Override
    public void sendPasswordResetCode(String email, String name, String code) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setFrom(fromEmail);
            helper.setTo(email);
            helper.setSubject("Auconchegante - Password Reset Code");

            Map<String, Object> model = new HashMap<>();
            model.put("code", code);
            model.put("name", name);
            model.put("expirationMinutes", 30);

            Template template = configuration.getTemplate("password-reset.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setText(html, true);
            mailSender.send(msg);

        } catch (Exception e) {
            throw new InternalException("Error sending verification code.");
        }
    }
}
