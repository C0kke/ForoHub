package cokke.alura.challenge.forohub.domain.usuarios;

import jakarta.validation.constraints.Email;

public record ActualizarUsuarioDTO(
        String nombre,
        @Email
        String correo,
        String contrasena
) {
}
