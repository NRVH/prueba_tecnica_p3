package com.prueba.nvazquez.filter;

import com.prueba.nvazquez.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
// Indica que esta clase es un componente de Spring y será detectado automáticamente durante el escaneo de componentes.
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Autowired
    // Constructor que inyecta la dependencia de JwtUtil cuando se crea una instancia de JwtRequestFilter.
    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    // Define la lógica del filtro que se ejecuta una vez por cada solicitud HTTP.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Obtiene el valor del encabezado de autorización (donde se espera que esté el token JWT).
        final String authorizationHeader = request.getHeader("Authorization");

        // Inicializa las variables para el nombre de usuario y el JWT.
        String username = null;
        String jwt = null;

        // Comprueba si el encabezado de autorización no es nulo y si comienza con "Bearer ".
        // Esto es típico de los tokens JWT en las solicitudes HTTP.
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extrae el token JWT del encabezado de autorización.
            jwt = authorizationHeader.substring(7);
            // Utiliza JwtUtil para extraer el nombre de usuario del token.
            username = jwtUtil.getUsernameFromToken(jwt);
        }

        // Si se encontró un nombre de usuario y no hay autenticación previa en el contexto de seguridad,
        // entonces procede a establecer la autenticación en el contexto.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Carga los detalles del usuario asociados con ese nombre de usuario.
            UserDetails userDetails = this.jwtUtil.loadUserByUsername(username);

            // Valida el token utilizando JwtUtil. Si el token es válido, procede a autenticar.
            if (Boolean.TRUE.equals(jwtUtil.validateToken(jwt, userDetails.getUsername()))) {
                // Crea una autenticación basada en el token, sin credenciales y con las autoridades (roles) del usuario.
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Establece el objeto de autenticación en el contexto de seguridad de Spring.
                // Esto efectivamente autoriza al usuario para la solicitud actual.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        // Continúa con el procesamiento de la solicitud HTTP.
        // Si no se llamara a este método, la solicitud no llegaría al controlador.
        chain.doFilter(request, response);
    }
}
