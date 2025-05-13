package com.ferreteria.demo.Config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.ferreteria.demo.DTO.Ferreteria.FerreteriaDTO;
import com.ferreteria.demo.Repositories.Entities.Ferreteria;

@Component
public class FerreteriaConverterDTO {

    private final ModelMapper dbm;

    public FerreteriaConverterDTO(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.dbm = modelMapper;
    }

    public FerreteriaDTO convertToListarDTO(Ferreteria ferreteria){
        FerreteriaDTO ferreteriaDTO = dbm.map(ferreteria, FerreteriaDTO.class);
        return ferreteriaDTO;
    }


}
