package cokke.alura.challenge.forohub.domain.respuestas;

import cokke.alura.challenge.forohub.domain.topicos.Topico;
import cokke.alura.challenge.forohub.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    private Date fechaCreacion;
    private String solucion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_topico")
    private Topico topico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor")
    private Usuario autor;

    public Respuesta(String solucion, String mensaje, Usuario usuario, Topico topico) {
        this.solucion = solucion;
        this.mensaje = mensaje;
        this.autor = usuario;
        this.topico = topico;
        this.fechaCreacion = Date.from(Instant.now());
    }

    public void actualizarDatos(String solucion, String mensaje) {
        if (solucion != "") {
            this.solucion = solucion;
        }
        if (mensaje != "") {
            this.mensaje = mensaje;
        }
    }
}
