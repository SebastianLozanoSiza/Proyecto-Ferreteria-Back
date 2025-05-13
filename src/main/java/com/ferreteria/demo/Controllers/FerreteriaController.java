package com.ferreteria.demo.Controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.demo.DTO.RespuestaDTO;
import com.ferreteria.demo.DTO.Ferreteria.ListarFerreteriaDTO;
import com.ferreteria.demo.Services.ServiceFerreteria;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/ferreterias")
public class FerreteriaController {

    private ServiceFerreteria serviceFerreteria;

    @GetMapping("/listarFerreterias")
    public ResponseEntity<ListarFerreteriaDTO> findAll() {
        try {
            ListarFerreteriaDTO listarFerreteriaDTO = serviceFerreteria.findAll();
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
}
