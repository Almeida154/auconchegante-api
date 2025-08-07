package br.com.auconchegante.unit.application.service;

import br.com.auconchegante.application.service.EmailService;
import br.com.auconchegante.domain.exceptions.InternalException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @Mock
    private JavaMailSender mailSender;

    @Mock
    private Configuration configuration;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() throws Exception {
        var field = EmailService.class.getDeclaredField("fromEmail");
        field.setAccessible(true);
        field.set(emailService, "test@auconchegante.com");
    }

    @Test
    @DisplayName("Should call JavaMailSender to send a reset code for provided e-mail")
    void callSendToResetCode() throws Exception {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        Template template = new Template("name", new StringReader("Código: ${code}"), null);
        when(configuration.getTemplate("password-reset.ftl")).thenReturn(template);

        emailService.sendPasswordResetCode("any@email.com", "John Doe", "123456");

        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    @DisplayName("Should throw InternalException when mailSender throws")
    void throwNotFoundException() {
        assertThatThrownBy(() -> {
            MimeMessage mimeMessage = mock(MimeMessage.class);
            when(mailSender.createMimeMessage()).thenThrow();

            emailService.sendPasswordResetCode("any@email.com", "John Doe", "123456");
        })
                .isInstanceOf(InternalException.class)
                .hasMessage("Error sending verification code.");
    }
}
