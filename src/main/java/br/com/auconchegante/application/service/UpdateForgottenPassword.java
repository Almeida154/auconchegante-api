package br.com.auconchegante.application.service;

import br.com.auconchegante.domain.port.incoming.UpdateForgottenPasswordUseCase;

public class UpdateForgottenPassword implements UpdateForgottenPasswordUseCase {

    @Override
    public void execute(String code, String newPassword) {
        // 1. Check if the code keeps valid

        // 2. Check if the e-mail exists in our database

        // 3. Update the user password with the new one
    }
}
