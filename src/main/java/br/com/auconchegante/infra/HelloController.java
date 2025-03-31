package br.com.auconchegante.infra;

import br.com.auconchegante.domain.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class HelloController {
    @GetMapping
    public String login() {
        User user = new User("Abcdef", "romeu@gmail.com", "123");
        return user.getName();
    }
}
