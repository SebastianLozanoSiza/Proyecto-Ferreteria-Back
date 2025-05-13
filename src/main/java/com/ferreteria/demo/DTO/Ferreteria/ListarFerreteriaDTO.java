package com.ferreteria.demo.DTO.Ferreteria;

import java.util.List;

import com.ferreteria.demo.DTO.RespuestaDTO;

import lombok.Data;

@Data
public class ListarFerreteriaDTO {
    private RespuestaDTO respuesta;
    private List<FerreteriaDTO> ferreteria;
}
