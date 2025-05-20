package com.ferreteria.demo.Services;

import com.ferreteria.demo.DTO.Cliente.ListarClienteDTO;
import com.ferreteria.demo.DTO.Usuario.UsuarioDTO;

public interface ServiceCliente {
    
    ListarClienteDTO findAll();

    UsuarioDTO update(Long id, UsuarioDTO usuarioDTO);

    void delete(Long id);
}
