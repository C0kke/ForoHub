package cokke.alura.challenge.forohub.domain.topicos;

import java.util.Date;

public record VerTopicosDTO(
        Long id,
        String titulo,
        String mensaje,
        Date fechaCreacion,
        String estado,
        String usuario,
        String curso
) {

    public VerTopicosDTO(Topico topico) {
        this(
            topico.getId(),
            topico.getTitulo(),
            topico.getMensaje(),
            topico.getFechaCreacion(),
            topico.getEstado(),
            topico.getAutor().getNombre(),
            topico.getCurso().getNombre()
        );
    }
}
