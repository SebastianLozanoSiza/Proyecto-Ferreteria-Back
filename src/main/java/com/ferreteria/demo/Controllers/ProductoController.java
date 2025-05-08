package com.ferreteria.demo.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.demo.DTO.RespuestaDTO;
import com.ferreteria.demo.DTO.Producto.CrearProductoDTO;
import com.ferreteria.demo.DTO.Producto.ListarProductoDTO;
import com.ferreteria.demo.Services.ServiceProducto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@AllArgsConstructor
@RequestMapping("/productos")
public class ProductoController {

    private ServiceProducto serviceProducto;

    @GetMapping("/listarProductos")
    public ResponseEntity<ListarProductoDTO> findAll(@RequestParam(required = false) String nombreProducto,
            @RequestParam(required = false) String categoria, @RequestParam(required = false) String razonSocial) {

        try {
            ListarProductoDTO listarProductoDTO = serviceProducto.findAll(nombreProducto, categoria, razonSocial);
            listarProductoDTO.setRespuesta(new RespuestaDTO(false, "200", "Productos listados correctamente"));
            return new ResponseEntity<>(listarProductoDTO, HttpStatus.OK);
        } catch (DataAccessException e) {
            ListarProductoDTO listarProductoDTO = new ListarProductoDTO();
            listarProductoDTO.setRespuesta(new RespuestaDTO(true, "500",
                    "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(listarProductoDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            ListarProductoDTO listarProductoDTO = new ListarProductoDTO();
            listarProductoDTO.setRespuesta(new RespuestaDTO(true, "500", "Error inesperado: " + e.getMessage()));
            return new ResponseEntity<>(listarProductoDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/crearProductos")
    public ResponseEntity<RespuestaDTO> save(@Valid @RequestBody CrearProductoDTO crearProductoDTO,
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
            serviceProducto.save(crearProductoDTO);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Producto creado exitosamente"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(
                    new RespuestaDTO(true, "400", e.getMessage()));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaDTO(true, "500",
                            "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
        }
    }

    @PutMapping("/actualizarProducto")
    public ResponseEntity<RespuestaDTO> update(@Valid @RequestBody CrearProductoDTO crearProductoDTO,
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
            serviceProducto.update(id, crearProductoDTO);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Prducto actualizado exitosamente"));
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

    @DeleteMapping("/eliminarProducto/{id}")
    public ResponseEntity<RespuestaDTO> delete(@PathVariable Long id) {
        try {
            serviceProducto.delete(id);
            return ResponseEntity.ok(
                    new RespuestaDTO(false, "200", "Producto eliminado exitosamente"));
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
