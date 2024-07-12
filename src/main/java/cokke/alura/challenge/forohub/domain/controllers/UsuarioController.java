package cokke.alura.challenge.forohub.domain.controllers;

import cokke.alura.challenge.forohub.domain.usuarios.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostMapping
    public ResponseEntity<VerUsuarioDTO> registrarUsuario(@RequestBody @Valid RegistroUsuarioDTO datos, UriComponentsBuilder uriComponentsBuilder) {

        var new_pass = bCryptPasswordEncoder.encode(datos.contrasena());
        Usuario usuario = usuarioRepository.save(new Usuario(datos.nombre(), datos.correo(), new_pass));
        VerUsuarioDTO respuestaUsuarioDTO =new VerUsuarioDTO(usuario.getId() ,datos.nombre(), datos.correo());
        URI uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(respuestaUsuarioDTO);
    }

    @GetMapping
    public ResponseEntity<Page<VerUsuarioDTO>> listarUsuarios(@PageableDefault Pageable paginacion) {

        return ResponseEntity.ok(usuarioRepository.findAll(paginacion).map(VerUsuarioDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaUsuarioDTO> verDetalleUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        var detallesUsuario = new RespuestaUsuarioDTO(usuario.getNombre(), usuario.getCorreo());
        return ResponseEntity.ok(detallesUsuario);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RespuestaUsuarioDTO> actualizarUsuario(@RequestBody @Valid ActualizarUsuarioDTO datos, @PathVariable Long id) {

        Usuario usuario = usuarioRepository.getReferenceById(id);

        var new_pass = bCryptPasswordEncoder.encode(datos.contrasena());
        usuario.actualizarDatos(datos.nombre(), datos.correo(), new_pass);
        RespuestaUsuarioDTO usuarioDTO =new RespuestaUsuarioDTO(usuario.getNombre(), usuario.getCorreo());
        return ResponseEntity.ok(usuarioDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Usuario> eliminarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        if (usuario != null) {
            usuarioRepository.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }

}
