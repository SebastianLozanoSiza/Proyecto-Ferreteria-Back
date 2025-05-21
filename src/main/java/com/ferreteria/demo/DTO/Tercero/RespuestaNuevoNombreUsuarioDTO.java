package com.ferreteria.demo.DTO.Tercero;

import com.ferreteria.demo.DTO.RespuestaDTO;

import lombok.Data;

@Data
public class RespuestaNuevoNombreUsuarioDTO {
    private RespuestaDTO respuesta;
    private String token;
}
