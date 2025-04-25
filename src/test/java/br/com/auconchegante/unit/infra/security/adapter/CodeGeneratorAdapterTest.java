package br.com.auconchegante.unit.infra.security.adapter;

import br.com.auconchegante.infra.security.adapter.CodeGeneratorAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CodeGeneratorAdapterTest {
    CodeGeneratorAdapter codeGeneratorAdapter = new CodeGeneratorAdapter();

    @Test
    @DisplayName("Should generate a six numbers code")
    void encryptText() {
        String code = codeGeneratorAdapter.generate();
        assertThat(code).hasSize(6);
    }

    @Test
    @DisplayName("Should generate different codes")
    void encryptTextDifferent() {
        String code1 = codeGeneratorAdapter.generate();
        String code2 = codeGeneratorAdapter.generate();
        assertThat(code1).isNotEqualTo(code2);
    }
}
