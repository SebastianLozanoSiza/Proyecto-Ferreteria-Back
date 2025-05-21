package com.ferreteria.demo.Controllers;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.demo.DTO.RespuestaDTO;
import com.ferreteria.demo.DTO.Tercero.ListarTerceroDTO;
import com.ferreteria.demo.DTO.Tercero.RespuestaNuevoNombreUsuarioDTO;
import com.ferreteria.demo.DTO.Usuario.UsuarioDTO;
import com.ferreteria.demo.Repositories.Entities.Credenciales;
import com.ferreteria.demo.Services.JWTService;
import com.ferreteria.demo.Services.JWTUserDetailService;
import com.ferreteria.demo.Services.ServiceTercero;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/terceros")
public class TerceroController {

    private ServiceTercero serviceTercero;
    private JWTUserDetailService jwtUserDetailService;
    private JWTService jwtService;

    @GetMapping("/buscarTerceroPorUsuario/{nombreUsuario}")
    public ResponseEntity<ListarTerceroDTO> findByNombreUsuario(@PathVariable String nombreUsuario) {
        try {
            ListarTerceroDTO listarTerceroDTO = serviceTercero.findByNombreUsuario(nombreUsuario);
            listarTerceroDTO.setRespuesta(new RespuestaDTO(false, "200", "Tercero encontrado"));
            return new ResponseEntity<>(listarTerceroDTO, HttpStatus.OK);
        } catch (DataAccessException e) {
            ListarTerceroDTO listarTerceroDTO = new ListarTerceroDTO();
            listarTerceroDTO.setRespuesta(new RespuestaDTO(true, "500",
                    "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(listarTerceroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            ListarTerceroDTO listarTerceroDTO = new ListarTerceroDTO();
            listarTerceroDTO.setRespuesta(new RespuestaDTO(true, "500", "Error inesperado: " + e.getMessage()));
            return new ResponseEntity<>(listarTerceroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizarTercero/{id}")
    public ResponseEntity<RespuestaNuevoNombreUsuarioDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO usuarioDTO,
            BindingResult result) {

        if (result.hasErrors()) {
            String errores = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            RespuestaNuevoNombreUsuarioDTO respuesta = new RespuestaNuevoNombreUsuarioDTO();
            respuesta.setRespuesta(new RespuestaDTO(true, "400", "Error: " + errores));
            respuesta.setToken(null);
            return ResponseEntity.badRequest().body(respuesta);
        }

        try {
            // Obtener el nombre de usuario actual antes de actualizar
            String nombreUsuarioAnterior = null;
            Optional<Credenciales> credencialesOpt = serviceTercero
                    .findCredencialesByTerceroId(id); // Debes crear este método en tu servicio

            if (credencialesOpt.isPresent()) {
                nombreUsuarioAnterior = credencialesOpt.get().getNombreUsuario();
            }

            serviceTercero.update(id, usuarioDTO);

            String token = null;
            // Si el nombre de usuario cambió, genera el nuevo token
            if (nombreUsuarioAnterior != null && !nombreUsuarioAnterior.equals(usuarioDTO.getNombreUsuario())) {
                // Generar el nuevo token
                UserDetails userDetails = jwtUserDetailService
                        .loadUserByUsername(usuarioDTO.getNombreUsuario());
                token = jwtService.generateToken(userDetails);
            }

            RespuestaNuevoNombreUsuarioDTO respuesta = new RespuestaNuevoNombreUsuarioDTO();
            respuesta.setRespuesta(new RespuestaDTO(false, "200", "Datos actualizados correctamente"));
            respuesta.setToken(token);
            return ResponseEntity.ok(respuesta);

        } catch (IllegalArgumentException e) {
            RespuestaNuevoNombreUsuarioDTO respuesta = new RespuestaNuevoNombreUsuarioDTO();
            respuesta.setRespuesta(new RespuestaDTO(true, "400", "Error: " + e.getMessage()));
            respuesta.setToken(null);
            return ResponseEntity.badRequest().body(respuesta);
        } catch (Exception e) {
            RespuestaNuevoNombreUsuarioDTO respuesta = new RespuestaNuevoNombreUsuarioDTO();
            respuesta.setRespuesta(new RespuestaDTO(true, "500", "Error inesperado: " + e.getMessage()));
            respuesta.setToken(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }
}
