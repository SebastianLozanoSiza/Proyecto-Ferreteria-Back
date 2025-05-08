package com.ferreteria.demo.DTO.Empleado;

import java.util.List;

import com.ferreteria.demo.DTO.RespuestaDTO;

import lombok.Data;

@Data
public class ListarEmpleadoDTO {
    
    private RespuestaDTO respuesta;
    private List<EmpleadoDTO> empleados;
}
