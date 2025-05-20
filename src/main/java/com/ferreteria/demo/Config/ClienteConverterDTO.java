package com.ferreteria.demo.Config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.ferreteria.demo.DTO.Cliente.ClienteDTO;
import com.ferreteria.demo.Repositories.Entities.Cliente;

@Component
public class ClienteConverterDTO {

    private final ModelMapper dbm;

    public ClienteConverterDTO(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.dbm = modelMapper;
    }

    public ClienteDTO convertToListarDTO(Cliente cliente){
        ClienteDTO clienteDTO = dbm.map(cliente, ClienteDTO.class);
        if (cliente.getTercero() != null) {
            clienteDTO.setIdTercero(cliente.getTercero().getIdTercero());
            clienteDTO.setIdentificacion(cliente.getTercero().getIdentificacion());
            clienteDTO.setNombre(cliente.getTercero().getNombre());
            clienteDTO.setApellidos(cliente.getTercero().getApellidos());
            clienteDTO.setCorreo(cliente.getTercero().getCorreo());
            clienteDTO.setDireccion(cliente.getTercero().getDireccion());
            clienteDTO.setTelefono(cliente.getTercero().getTelefono());
        }
        return clienteDTO;
    }
}
