package com.ferreteria.demo.Config;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ferreteria.demo.DTO.Tercero.TerceroDTO;
import com.ferreteria.demo.Repositories.RepositoryCredenciales;
import com.ferreteria.demo.Repositories.Entities.Credenciales;
import com.ferreteria.demo.Repositories.Entities.Tercero;

@Component
public class TerceroConverterDTO {

    private final ModelMapper dbm;

    @Autowired
    private RepositoryCredenciales repositoryCredenciales;

    public TerceroConverterDTO(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.dbm = modelMapper;
    }

    public TerceroDTO convertToListarDTO(Tercero tercero) {
        TerceroDTO terceroDTO = dbm.map(tercero, TerceroDTO.class);
        if (tercero.getIdTercero() != null) {
            Optional<Credenciales> credencialesOptional = repositoryCredenciales
                    .findByTercero_IdTercero(tercero.getIdTercero());
            credencialesOptional
                    .ifPresent(credenciales -> terceroDTO.setNombreUsuario(credenciales.getNombreUsuario()));
        }
        return terceroDTO;
    }
}
