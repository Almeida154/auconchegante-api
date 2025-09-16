package br.com.auconchegante.auth.unit.infra.security.adapter;

import br.com.auconchegante.auth.infra.security.adapter.JasyptAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class JasyptAdapterTest {
    JasyptAdapter jasyptAdapter;

    private static final String TEST_SECRET = "any.secret";

    @BeforeEach
    void setUp() {
        jasyptAdapter = new JasyptAdapter();
        // application.properties key injection
        ReflectionTestUtils.setField(jasyptAdapter, "secret", TEST_SECRET);
        ReflectionTestUtils.invokeMethod(jasyptAdapter, "init");
    }

    @Test
    @DisplayName("Should encrypt a text")
    void encryptText() {
        String text = "Any Text";
        String encrypted = jasyptAdapter.encrypt(text);
        assertThat(text).isNotEqualTo(encrypted);
    }

    @Test
    @DisplayName("Should generate different encryption for a same text")
    void encryptTextDifferent() {
        String text = "Any Text";
        String encrypted1 = jasyptAdapter.encrypt(text);
        String encrypted2 = jasyptAdapter.encrypt(text);
        assertThat(encrypted1).isNotEqualTo(encrypted2);
    }

    @Test
    @DisplayName("Should decrypt a text")
    void decryptText() {
        String text = "Any Text";
        String encrypted = jasyptAdapter.encrypt(text);
        String decrypted = jasyptAdapter.decrypt(encrypted);
        assertThat(text).isEqualTo(decrypted);
    }
}
