package cokke.alura.challenge.forohub.infra.security;

import cokke.alura.challenge.forohub.domain.usuarios.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired private TokenService tokenService;
    @Autowired private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Obtiene el token del header
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            var subject = tokenService.getSubject(token); // Obtiene solo el correo del usuario
            if (subject != null) { // Se verifica que el token es válido
                var usuario = usuarioRepository.findByCorreo(subject);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); // Se inicia la sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
