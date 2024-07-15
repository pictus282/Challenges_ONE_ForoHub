package com.alurachallenge.ForoHub.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroUsuarioUno(
        Long id, @NotNull
String nombre,

        @Email
        @NotNull
        String email,

        @NotNull(message = "Este campo no debe ser nulo")
        String clave
) {
    public String getNombre() {
        return "nombre";
    }

    public String getEmail() {
        return "email";
    }

    public String getClave() {
        return null;
    }

    public String getLogin() {
        return this.getLogin();
    }
}
