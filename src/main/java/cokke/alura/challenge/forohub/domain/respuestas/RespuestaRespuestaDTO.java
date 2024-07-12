package cokke.alura.challenge.forohub.domain.respuestas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record RespuestaRespuestaDTO(
        @NotNull
        Long id,
        @NotBlank
        String nombreAutor,
        @NotBlank
        String mensaje,
        @NotBlank
        String solucion,
        @NotNull
        Date fechaCreacion
) {
    public RespuestaRespuestaDTO(Respuesta respuesta) {
        this(
            respuesta.getId(),
            respuesta.getAutor().getNombre(),
            respuesta.getMensaje(),
            respuesta.getSolucion(),
            respuesta.getFechaCreacion()
        );
    }
}
