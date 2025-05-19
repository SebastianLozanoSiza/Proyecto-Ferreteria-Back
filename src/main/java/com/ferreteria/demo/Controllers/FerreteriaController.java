package com.ferreteria.demo.Controllers;

import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.demo.DTO.RespuestaDTO;
import com.ferreteria.demo.DTO.Ferreteria.CrearFerreteriaDTO;
import com.ferreteria.demo.DTO.Ferreteria.ListarFerreteriaDTO;
import com.ferreteria.demo.Services.ServiceFerreteria;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
@RequestMapping("/ferreterias")
public class FerreteriaController {

    private ServiceFerreteria serviceFerreteria;

    @GetMapping("/listarFerreterias")
    public ResponseEntity<ListarFerreteriaDTO> findAll(@RequestParam(required = false) String nit,
            @RequestParam(required = false) String razonSocial, @RequestParam(required = false) String representante) {
        try {
            ListarFerreteriaDTO listarFerreteriaDTO = serviceFerreteria.findAll(nit, razonSocial, representante);
            listarFerreteriaDTO.setRespuesta(new RespuestaDTO(false, "200", "Ferreterias listadas correctamente"));
            return new ResponseEntity<>(listarFerreteriaDTO, HttpStatus.OK);
        } catch (DataAccessException e) {
            ListarFerreteriaDTO listarFerreteriaDTO = new ListarFerreteriaDTO();
            listarFerreteriaDTO.setRespuesta(new RespuestaDTO(true, "500",
                    "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(listarFerreteriaDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            ListarFerreteriaDTO listarFerreteriaDTO = new ListarFerreteriaDTO();
            listarFerreteriaDTO.setRespuesta(new RespuestaDTO(true, "500", "Error inesperado: " + e.getMessage()));
            return new ResponseEntity<>(listarFerreteriaDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/crearFerreteria")
    public ResponseEntity<RespuestaDTO> save(@Valid @RequestBody CrearFerreteriaDTO crearFerreteriaDTO,
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
            serviceFerreteria.save(crearFerreteriaDTO);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Ferretería creada exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error: " + e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", e.getMessage()));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaDTO(true, "500",
                            "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "500", e.getMessage()));
        }
    }

    @PutMapping("/actualizarFerreteria")
    public ResponseEntity<RespuestaDTO> update(@Valid @RequestBody CrearFerreteriaDTO crearFerreteriaDTO,
            BindingResult result, @RequestParam Long id) {
        if (result.hasErrors()) {
            String errores = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error: " + errores));
        }

        try {
            serviceFerreteria.update(id, crearFerreteriaDTO);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Ferreteria actualizada exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error: " + e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", e.getMessage()));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaDTO(true, "500",
                            "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
        }
    }

    @DeleteMapping("/eliminarFerreteria/{id}")
    public ResponseEntity<RespuestaDTO> delete(@PathVariable Long id) {
        try {
            serviceFerreteria.delete(id);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Ferretería eliminada exitosamente"));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaDTO(true, "500",
                            "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaDTO(true, "500", "Error inesperado: " + e.getMessage()));
        }
    }
}
