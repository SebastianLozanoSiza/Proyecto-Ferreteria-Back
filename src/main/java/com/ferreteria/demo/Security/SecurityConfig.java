package com.ferreteria.demo.Security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ferreteria.demo.Config.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JWTValidationFilter jwtValidationFilter,
            CustomAccessDeniedHandler customAccessDeniedHandler) throws Exception {
        var requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(auth -> auth
                // Permitir acceso al login y registro sin autenticación
                .requestMatchers("/login/**", "/registrarNuevo/**").permitAll()

                // El cliente solo puede actualizar sus datos
                .requestMatchers(HttpMethod.PUT, "/clientes/**").hasAnyAuthority("CLIENTE", "Administrador", "Empleado")
                // Administrador y empleado tienen acceso completo
                .requestMatchers("/clientes/**").hasAnyAuthority("Administrador", "Empleado")
                // Solo administradores pueden gestionar empleados
                .requestMatchers("/empleados/**").hasAuthority("Administrador")

                // Solo clientes pueden listar ferreterias
                .requestMatchers(HttpMethod.GET, "/ferreterias/**")
                .hasAnyAuthority("CLIENTE", "Empleado", "Administrador")

                // Solo empleados y administradores pueden crear, editar y eliminar ferreterias
                .requestMatchers(HttpMethod.POST, "/ferreterias/**").hasAnyAuthority("Empleado", "Administrador")
                .requestMatchers(HttpMethod.PUT, "/ferreterias/**").hasAnyAuthority("Empleado", "Administrador")
                .requestMatchers(HttpMethod.DELETE, "/ferreterias/**").hasAnyAuthority("Empleado", "Administrador")

                // Solo empleados y administradores pueden ver departamentos
                .requestMatchers(HttpMethod.GET, "/departamentos/**").hasAnyAuthority("Empleado", "Administrador")

                // Solo clientes y administradores pueden listar productos
                .requestMatchers(HttpMethod.GET, "/productos/listarProductos")
                .hasAnyAuthority("CLIENTE", "Administrador")

                // Solo administradores pueden crear, actualizar y eliminar productos
                .requestMatchers(HttpMethod.POST, "/productos/**").hasAuthority("Administrador")
                .requestMatchers(HttpMethod.PUT, "/productos/**").hasAuthority("Administrador")
                .requestMatchers(HttpMethod.DELETE, "/productos/**").hasAuthority("Administrador")

                // Solo administradores pueden crear departamentos
                .requestMatchers(HttpMethod.POST, "/departamentos/**").hasAuthority("Administrador")

                // Permitir acceso a Swagger sin autenticación
                .requestMatchers(SWAGGER_WHILELIST).permitAll()
                // Bloquear cualquier otro acceso
                .anyRequest().authenticated()).addFilterAfter(jwtValidationFilter, BasicAuthenticationFilter.class);

        http.exceptionHandling(exceptions -> exceptions
                .accessDeniedHandler(customAccessDeniedHandler));
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.csrf(crsf -> crsf.disable());
        return http.build();
    }

    private static final String[] SWAGGER_WHILELIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-resources"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
