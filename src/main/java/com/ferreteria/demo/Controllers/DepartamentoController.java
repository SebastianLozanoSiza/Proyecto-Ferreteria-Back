package com.ferreteria.demo.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.demo.DTO.RespuestaDTO;
import com.ferreteria.demo.DTO.Departamento.DepartamentoDTO;
import com.ferreteria.demo.DTO.Departamento.ListarDepartamentoDTO;
import com.ferreteria.demo.Services.ServiceDepartamento;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/departamentos")
public class DepartamentoController {

    private ServiceDepartamento serviceDepartamento;

    @GetMapping("/listarDepartamentos")
    public ResponseEntity<ListarDepartamentoDTO> findAll(@RequestParam(required = false) String nombre) {
        try {
            ListarDepartamentoDTO response = serviceDepartamento.findAll(nombre);

            if (response.getDepartamentos() == null || response.getDepartamentos().isEmpty()) {
                response.setRespuesta(new RespuestaDTO(true, "204", "No hay departamentos registrados"));
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            }

            response.setRespuesta(new RespuestaDTO(false, "200", "Departamentos listados correctamente"));
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (DataAccessException e) {
            ListarDepartamentoDTO response = new ListarDepartamentoDTO();
            response.setRespuesta(new RespuestaDTO(true, "500",
                    "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
            response.setDepartamentos(List.of());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            ListarDepartamentoDTO response = new ListarDepartamentoDTO();
            response.setRespuesta(new RespuestaDTO(true, "500", "Error inesperado: " + e.getMessage()));
            response.setDepartamentos(List.of());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/crearDepartamento")
    public ResponseEntity<RespuestaDTO> save(@Valid @RequestBody DepartamentoDTO departamentoDTO,
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
            serviceDepartamento.save(departamentoDTO);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Departamento creado exitosamente"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", e.getMessage()));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaDTO(true, "500",
                            "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
        }
    }

    @PutMapping("/actualizarDepartamento")
    public ResponseEntity<RespuestaDTO> update(@Valid @RequestBody DepartamentoDTO departamentoDTO,
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
            serviceDepartamento.update(id, departamentoDTO);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Departamento actualizado exitosamente"));
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

    @DeleteMapping("/eliminarDepartamento")
    public ResponseEntity<RespuestaDTO> delete(@RequestParam Long id) {
        try {
            serviceDepartamento.delete(id);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Departamento eliminado exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new RespuestaDTO(true, "404", e.getMessage()));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaDTO(true, "500",
                            "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
        }
    }
}
