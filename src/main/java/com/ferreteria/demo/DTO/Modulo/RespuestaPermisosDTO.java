package com.ferreteria.demo.DTO.Modulo;

import java.util.List;

import com.ferreteria.demo.DTO.RespuestaDTO;

import lombok.Data;

@Data
public class RespuestaPermisosDTO {
    private RespuestaDTO respuesta;
    private List<ModuloPermisoDTO> modulos;
}