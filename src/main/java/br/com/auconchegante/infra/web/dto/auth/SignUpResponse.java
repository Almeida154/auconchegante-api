package br.com.auconchegante.infra.web.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpResponse {
    private String accessToken;
}
