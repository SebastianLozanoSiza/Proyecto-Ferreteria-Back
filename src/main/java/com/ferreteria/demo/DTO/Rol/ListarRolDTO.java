package com.ferreteria.demo.DTO.Rol;

import java.util.List;

import com.ferreteria.demo.DTO.RespuestaDTO;

import lombok.Data;

@Data
public class ListarRolDTO {

    private RespuestaDTO respuesta;
    private List<RolDTO> roles;
}
