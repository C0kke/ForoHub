package cokke.alura.challenge.forohub.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VerUsuarioDTO(
        @NotNull
        Long id,
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String correo
) {
        public VerUsuarioDTO(Usuario usuario) {
                this(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getCorreo()
                );
        }
}
