package com.ferreteria.demo.Config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.ferreteria.demo.DTO.Usuario.UsuarioDTO;
import com.ferreteria.demo.Repositories.Entities.Credenciales;

@Component
public class CredencialesDTOConverter {

    private final ModelMapper dbm;

    public CredencialesDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.dbm = modelMapper;
    }

    public UsuarioDTO convertToDTO(Credenciales credenciales) {
        UsuarioDTO usuarioDTO = dbm.map(credenciales, UsuarioDTO.class);
        if (credenciales.getTercero() !=null) {
            usuarioDTO.setIdentificacion(credenciales.getTercero().getIdentificacion());
            usuarioDTO.setNombre(credenciales.getTercero().getNombre());
            usuarioDTO.setApellidos(credenciales.getTercero().getApellidos());
            usuarioDTO.setCorreo(credenciales.getTercero().getCorreo());
            usuarioDTO.setDireccion(credenciales.getTercero().getDireccion());
            usuarioDTO.setTelefono(credenciales.getTercero().getTelefono());
        }
        return usuarioDTO;
    }

    public Credenciales convertToEntity(UsuarioDTO usuarioDTO) {
        Credenciales credenciales = dbm.map(usuarioDTO, Credenciales.class);
        return credenciales;
    }
}
