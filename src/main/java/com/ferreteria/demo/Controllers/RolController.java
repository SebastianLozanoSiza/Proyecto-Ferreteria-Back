package com.ferreteria.demo.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.demo.DTO.RespuestaDTO;
import com.ferreteria.demo.DTO.Rol.ListarRolDTO;
import com.ferreteria.demo.Services.ServiceRol;

import lombok.AllArgsConstructor;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RolController {

    private ServiceRol serviceRol;

    @GetMapping("/listarRoles")
    public ResponseEntity<ListarRolDTO> findAll() {
        try {
            ListarRolDTO listarRolDTO = serviceRol.findAll();
            listarRolDTO.setRespuesta(new RespuestaDTO(false, "200", "Roles listados correctamente"));
            return new ResponseEntity<>(listarRolDTO, HttpStatus.OK);
        } catch (DataAccessException e) {
            ListarRolDTO listarRolDTO = new ListarRolDTO();
            listarRolDTO.setRespuesta(new RespuestaDTO(true, "500",
                    "Error en la base de datos: " + e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(listarRolDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            ListarRolDTO listarRolDTO = new ListarRolDTO();
            listarRolDTO.setRespuesta(new RespuestaDTO(true, "500", "Error inesperado: " + e.getMessage()));
            return new ResponseEntity<>(listarRolDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
