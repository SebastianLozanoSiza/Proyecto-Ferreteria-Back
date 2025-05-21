package com.ferreteria.demo.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.demo.DTO.RespuestaDTO;
import com.ferreteria.demo.DTO.Login.LoginDTO;
import com.ferreteria.demo.DTO.Usuario.UsuarioDTO;
import com.ferreteria.demo.Repositories.Models.JWTRequest;
import com.ferreteria.demo.Services.JWTService;
import com.ferreteria.demo.Services.JWTUserDetailService;
import com.ferreteria.demo.Services.ServiceCredenciales;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private ServiceCredenciales serviceCredenciales;
    private final AuthenticationManager authenticationManager;
    private final JWTUserDetailService jwtUserDetailService;
    private final JWTService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> postToken(@RequestBody JWTRequest request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LoginDTO(new RespuestaDTO(true, "400", "El campo 'Usuario' es obligatorio."),
                            null, null));
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LoginDTO(new RespuestaDTO(true, "400", "El campo 'Contraseña' es obligatorio."), null,
                            null));
        }

        try {
            this.authenticate(request);
            final var userDetails = jwtUserDetailService.loadUserByUsername(request.getUsername());
            final var token = jwtService.generateToken(userDetails);

            // Obtener el nombre del usuario desde las credenciales
            String nombre = serviceCredenciales
                    .findByNombreUsuario(request.getUsername())
                    .map(cred -> cred.getNombreUsuario() != null ? cred.getNombreUsuario() : null)
                    .orElse(null);
            return ResponseEntity.ok(new LoginDTO(new RespuestaDTO(false, "200", "Inicio se sesion exitoso"), token, nombre));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginDTO(new RespuestaDTO(true, "401",
                            "Credenciales incorrectas. Verifica tu usuario y contraseña."), null, null));
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginDTO(
                            new RespuestaDTO(true, "401", "Cuenta deshabilitada. Contacta al administrador."), null,
                            null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginDTO(
                            new RespuestaDTO(true, "500", "Error en el servidor. Intenta nuevamente."), null, null));
        }
    }

    private void authenticate(JWTRequest request) {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciales incorrectas"); 
        } catch (DisabledException e) {
            throw new DisabledException("Cuenta deshabilitada"); 
        }
    }

    @PostMapping("/registrarNuevo")
    public ResponseEntity<RespuestaDTO> registrarUsuario(
            @Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest()
                    .body(new RespuestaDTO(true, "400", "Error: " + String.join(", ", errores)));
        }

        try {
            serviceCredenciales.save(usuarioDTO);
            return ResponseEntity
                    .ok(new RespuestaDTO(false, "200", "Usuario registrado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error al registrar usuario: " + e.getMessage()));
        }
    }
}
