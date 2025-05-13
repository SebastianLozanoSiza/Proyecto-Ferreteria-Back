package com.ferreteria.demo.DTO.Cliente;

import java.util.List;

import com.ferreteria.demo.DTO.RespuestaDTO;

import lombok.Data;

@Data
public class ListarClienteDTO {
    private RespuestaDTO respuesta;
    private List<ClienteDTO> clientes;
}
