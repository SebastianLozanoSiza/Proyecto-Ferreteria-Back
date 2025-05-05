package com.ferreteria.demo.DTO.Departamento;

import java.util.List;

import com.ferreteria.demo.DTO.RespuestaDTO;
import com.ferreteria.demo.Repositories.Entities.Departamento;

import lombok.Data;

@Data
public class ListarDepartamentoDTO {

    private RespuestaDTO respuesta;
    private List<Departamento> departamentos; 
}
