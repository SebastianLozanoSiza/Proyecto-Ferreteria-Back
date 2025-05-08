package com.ferreteria.demo.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.demo.DTO.RespuestaDTO;
import com.ferreteria.demo.DTO.Empleado.CrearEmpleadoDTO;
import com.ferreteria.demo.DTO.Empleado.ListarEmpleadoDTO;
import com.ferreteria.demo.Services.ServiceEmpleado;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
@RequestMapping("/empleados")
public class EmpleadoController {

    private ServiceEmpleado serviceEmpleado;

    @GetMapping("/listarEmpleados")
    public ResponseEntity<ListarEmpleadoDTO> findAll() {
        try {
            ListarEmpleadoDTO listarEmpleadoDTO = serviceEmpleado.findAll();
            listarEmpleadoDTO.setRespuesta(new RespuestaDTO(false, "200", "Empleados listados correctamente"));
            return new ResponseEntity<>(listarEmpleadoDTO, HttpStatus.OK);
        } catch (DataAccessException e) {
            ListarEmpleadoDTO listarEmpleadoDTO = new ListarEmpleadoDTO();
            listarEmpleadoDTO.setRespuesta(new RespuestaDTO(true, "500",
                    "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(listarEmpleadoDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            ListarEmpleadoDTO listarEmpleadoDTO = new ListarEmpleadoDTO();
            listarEmpleadoDTO.setRespuesta(new RespuestaDTO(true, "500", "Error inesperado: " + e.getMessage()));
            return new ResponseEntity<>(listarEmpleadoDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/crearEmpleado")
    public ResponseEntity<RespuestaDTO> registrarUsuario(
            @Valid @RequestBody CrearEmpleadoDTO crearEmpleadoDTO, BindingResult result) {

        // Validar si hay errores en los campos
        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest()
                    .body(new RespuestaDTO(true, "400", "Error: " + String.join(", ", errores)));
        }

        try {
            serviceEmpleado.save(crearEmpleadoDTO);
            return ResponseEntity
                    .ok(new RespuestaDTO(false, "200", "Usuario registrado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", "Error al registrar usuario: " + e.getMessage()));
        }
    }

}
