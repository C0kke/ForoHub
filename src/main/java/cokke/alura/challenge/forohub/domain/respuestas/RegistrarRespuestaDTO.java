package cokke.alura.challenge.forohub.domain.respuestas;

public record RegistrarRespuestaDTO(
        String mensaje,
        String solucion,
        Long idUsuario,
        String tituloTopico
) {
}
