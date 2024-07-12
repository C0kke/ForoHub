package cokke.alura.challenge.forohub.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RespuestaUsuarioDTO(
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String correo
) {
        public RespuestaUsuarioDTO(Usuario usuario) {
                this(
                        usuario.getNombre(),
                        usuario.getCorreo()
                );
        }
}
