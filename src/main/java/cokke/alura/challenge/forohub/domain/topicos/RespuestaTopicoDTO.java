package cokke.alura.challenge.forohub.domain.topicos;

import java.util.Date;

public record RespuestaTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        Date fechaCreacion
) {
}
