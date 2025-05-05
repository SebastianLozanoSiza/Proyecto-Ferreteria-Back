package com.ferreteria.demo.Config;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.ferreteria.demo.DTO.Departamento.DepartamentoDTO;
import com.ferreteria.demo.DTO.Departamento.ListarDepartamentoDTO;
import com.ferreteria.demo.Repositories.Entities.Departamento;

@Component
public class DepartamentoDTOConverter {

    private final ModelMapper dbm;

    public DepartamentoDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.dbm = modelMapper;
    }

    public DepartamentoDTO convertToDTO(Departamento departamento) {
        DepartamentoDTO departamentoDTO = dbm.map(departamento, DepartamentoDTO.class);
        return departamentoDTO;
    }

    public Departamento convertToEntity(DepartamentoDTO departamentoDTO) {
        Departamento departamento = dbm.map(departamentoDTO, Departamento.class);
        return departamento;
    }

    public ListarDepartamentoDTO convertListarToDTO(List<Departamento> departamento) {
        ListarDepartamentoDTO listarDepartamentoDTO = new ListarDepartamentoDTO();
        listarDepartamentoDTO.setDepartamentos(departamento);
        return listarDepartamentoDTO;
    }
}
