package com.ferreteria.demo.Config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.ferreteria.demo.DTO.Rol.RolDTO;
import com.ferreteria.demo.Repositories.Entities.Rol;

@Component
public class RolConverterDTO {

    private final ModelMapper dbm;

    public RolConverterDTO(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.dbm = modelMapper;
    }

    public RolDTO convertToListarDTO(Rol rol) {
        RolDTO rolDTO = dbm.map(rol, RolDTO.class);
        return rolDTO;
    }
}
