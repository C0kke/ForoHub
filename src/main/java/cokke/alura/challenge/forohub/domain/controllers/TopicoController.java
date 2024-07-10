package cokke.alura.challenge.forohub.domain.controllers;

import cokke.alura.challenge.forohub.domain.cursos.Curso;
import cokke.alura.challenge.forohub.domain.cursos.CursoRepository;
import cokke.alura.challenge.forohub.domain.topicos.*;
import cokke.alura.challenge.forohub.domain.usuarios.Usuario;
import cokke.alura.challenge.forohub.domain.usuarios.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired private TopicoRepository topicoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<RespuestaTopicoDTO> registrarTopico(@RequestBody @Valid RegistroTopicoDTO datosRegistroTopico,
                                                              UriComponentsBuilder uriComponentsBuilder) {

        Optional<Usuario> autor = usuarioRepository.findById(datosRegistroTopico.idUsuario());
        Optional<Curso> curso = cursoRepository.findByNombre(datosRegistroTopico.nombreCurso());

        if (autor.isEmpty() || curso.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Guarda el Tópico ingresado tanto en la base de datos como en la variable
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje(), autor.get(), curso.get()));

        // Se genera la respuesta que se envía al API
        RespuestaTopicoDTO topicoDTO =new RespuestaTopicoDTO(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion());
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(topicoDTO);
    }

    @GetMapping
    public ResponseEntity<Page<VerTopicosDTO>> listarTopicos(@PageableDefault Pageable paginacion) {

        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(VerTopicosDTO::new));

        //Ver los primeros dias por fecha de cracion (No funciona)
        // return ResponseEntity.ok(topicoRepository.findTop10ByOrderByFechaCreacionAsc(paginacion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VerTopicosDTO> verDetalleTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        var datosDetalleTopico = new VerTopicosDTO(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(),
                                                    topico.getEstado(), topico.getAutor().getNombre(), topico.getCurso().getNombre());

        return ResponseEntity.ok(datosDetalleTopico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RespuestaTopicoDTO> actualizarTopico(@RequestBody @Valid ActualizarTopicoDTO datos, @PathVariable Long id) {

        Topico topico = topicoRepository.getReferenceById(id);

        topico.actualizarDatos(datos.titulo(), datos.mensaje(), datos.estado());
        RespuestaTopicoDTO topicoDTO =new RespuestaTopicoDTO(topico.getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getFechaCreacion());
        return ResponseEntity.ok(topicoDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        if (topico != null) {
            topicoRepository.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }
}
