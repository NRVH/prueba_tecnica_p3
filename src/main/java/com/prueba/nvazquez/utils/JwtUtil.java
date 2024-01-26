package com.prueba.nvazquez.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.function.Function;

@Component
// Esta anotación indica que JwtUtil es un componente de Spring y que será gestionado por el contenedor de Spring.
public class JwtUtil {

    // Inyecta el valor de 'jwt.secret' del archivo de propiedades de la aplicación en la variable 'secret'.
    @Value("${jwt.secret}")
    private String secret;

    // Genera el token JWT para un nombre de usuario.
    public String generateToken(String username) {
        return Jwts.builder() // Crea un nuevo JWT Builder para definir las propiedades del token JWT.
                .setSubject(username) // Establece el sujeto del token, en este caso el nombre de usuario.
                .setIssuedAt(new Date(System.currentTimeMillis())) // Define la fecha y hora en que se emitió el token.
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Establece la fecha de expiración del token (10 horas después de la emisión).
                .signWith(SignatureAlgorithm.HS256, secret) // Firma el token con el algoritmo HS256 y la clave secreta.
                .compact(); // Construye el JWT y lo serializa en una cadena compacta y URL-segura.
    }

    // Carga los detalles del usuario por nombre de usuario.
    // Este es un método simplificado que crea un nuevo usuario con una autoridad 'ROLE_USER'.
    public UserDetails loadUserByUsername(String username) {
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(username, "", authorities); // Retorna un objeto User con el nombre de usuario y la autoridad.
    }

    // Valida el token JWT comparando el nombre de usuario y comprobando si el token ha expirado.
    public Boolean validateToken(String token, String username) {
        final String usernameFromToken = getUsernameFromToken(token); // Extrae el nombre de usuario del token.
        return (usernameFromToken.equals(username) && !isTokenExpired(token)); // Compara el nombre de usuario y verifica que el token no haya expirado.
    }

    // Extrae el nombre de usuario del token.
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject); // Utiliza la función getClaimFromToken para obtener el sujeto del token.
    }

    // Obtiene la fecha de expiración del token.
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration); // Utiliza la función getClaimFromToken para obtener la fecha de expiración.
    }

    // Extrae información de un token JWT (Claims) utilizando un resolver de claims (una función que toma Claims como entrada).
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token); // Obtiene todos los claims del token.
        return claimsResolver.apply(claims); // Aplica la función claimsResolver a los claims para obtener la información deseada.
    }

    // Obtiene todos los claims a partir de un token JWT.
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody(); // Parsea el token y extrae el cuerpo (claims).
    }

    // Comprueba si el token JWT ha expirado.
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token); // Obtiene la fecha de expiración del token.
        return expiration.before(new Date()); // Compara si la fecha de expiración es anterior a la fecha actual.
    }
}


