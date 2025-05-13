package com.ferreteria.demo.Controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.demo.DTO.RespuestaDTO;
import com.ferreteria.demo.DTO.Cliente.ListarClienteDTO;
import com.ferreteria.demo.Services.ServiceCliente;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {

    private ServiceCliente serviceCliente;

    @GetMapping("/listarClientes")
    public ResponseEntity<ListarClienteDTO> findAll() {
        try {
            ListarClienteDTO listarClienteDTO = serviceCliente.findAll();
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

}