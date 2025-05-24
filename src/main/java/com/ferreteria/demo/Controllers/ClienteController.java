package com.ferreteria.demo.Controllers;

import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.demo.DTO.RespuestaDTO;
import com.ferreteria.demo.DTO.Cliente.ListarClienteDTO;
import com.ferreteria.demo.DTO.Usuario.UsuarioDTO;
import com.ferreteria.demo.Services.ServiceCliente;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@AllArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {

    private ServiceCliente serviceCliente;

    @GetMapping("/listarClientes")
    public ResponseEntity<ListarClienteDTO> findAll(@RequestParam(required = false) String identificacion,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String correo) {
        try {
            ListarClienteDTO listarClienteDTO = serviceCliente.findAll(identificacion, nombre, correo);
            listarClienteDTO.setRespuesta(new RespuestaDTO(false, "200", "Clientes listados correctamente"));
            return new ResponseEntity<>(listarClienteDTO, HttpStatus.OK);
        } catch (DataAccessException e) {
            ListarClienteDTO listarClienteDTO = new ListarClienteDTO();
            listarClienteDTO.setRespuesta(new RespuestaDTO(true, "500",
                    "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(listarClienteDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            ListarClienteDTO listarClienteDTO = new ListarClienteDTO();
            listarClienteDTO.setRespuesta(new RespuestaDTO(true, "500", "Error inesperado: " + e.getMessage()));
            return new ResponseEntity<>(listarClienteDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscarClientePorUsuario/{nombreUsuario}")
    public ResponseEntity<ListarClienteDTO> findByNombreUsuario(@PathVariable String nombreUsuario) {
        try {
            ListarClienteDTO listarClienteDTO = serviceCliente.findByNombreUsuario(nombreUsuario);
            listarClienteDTO.setRespuesta(new RespuestaDTO(false, "200", "Cliente encontrado"));
            return new ResponseEntity<>(listarClienteDTO, HttpStatus.OK);
        } catch (DataAccessException e) {
            ListarClienteDTO listarClienteDTO = new ListarClienteDTO();
            listarClienteDTO.setRespuesta(new RespuestaDTO(true, "500",
                    "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(listarClienteDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            ListarClienteDTO listarClienteDTO = new ListarClienteDTO();
            listarClienteDTO.setRespuesta(new RespuestaDTO(true, "500", "Error inesperado: " + e.getMessage()));
            return new ResponseEntity<>(listarClienteDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizarCliente/{id}")
    public ResponseEntity<RespuestaDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO usuarioDTO,
            BindingResult result) {

        if (result.hasErrors()) {
            String errores = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error: " + errores));
        }

        try {
            serviceCliente.update(id, usuarioDTO);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Cliente actualizado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaDTO(true, "500", "Error inesperado: " + e.getMessage()));
        }
    }

    @DeleteMapping("/eliminarCliente/{id}")
    public ResponseEntity<RespuestaDTO> delete(@PathVariable Long id) {
        try {
            serviceCliente.delete(id);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Cliente eliminado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaDTO(true, "500", "Error inesperado: " + e.getMessage()));
        }
    }

}