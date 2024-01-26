package com.prueba.nvazquez.config;

import com.prueba.nvazquez.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtRequestFilter jwtRequestFilter;

    // Inyecta el filtro JWT en la configuración de seguridad.
    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter){
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // Configuración de las reglas de seguridad HTTP para las peticiones.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Desactiva la protección CSRF, no es ncesaria para APIs REST.
                .cors().and()
                .authorizeRequests() // Autoriza peticiones basadas en las siguientes reglas.
                .antMatchers("/api/auth/**").permitAll() // Permite todas las peticiones a /api/auth/** sin autenticación.
                .anyRequest().authenticated() // Cualquier otra petición requiere autenticación.
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Configura la gestión de sesiones para ser sin estado.

        // Añade el filtro JWT antes del filtro de autenticación de usuario y contraseña.
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // Configuración del AuthenticationManager para definir cómo se autentican los usuarios.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication() // Autenticación en memoria (para propósitos de prueba).
                .passwordEncoder(NoOpPasswordEncoder.getInstance()) // Codificador de contraseñas (NoOp para pruebas).
                .withUser("admin") // Nombre de usuario "admin".
                .password("123456") // Contraseña "123456".
                .roles("ADMIN"); // Rol "ADMIN".
    }

    // Bean para el AuthenticationManager.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
