package com.ferreteria.demo.Services;

import java.util.Optional;

import com.ferreteria.demo.DTO.Usuario.UsuarioDTO;
import com.ferreteria.demo.Repositories.Entities.Credenciales;

public interface ServiceCredenciales {
    
    UsuarioDTO save(UsuarioDTO usuarioDTO);

    Optional<Credenciales> findByNombreUsuario(String nombreUsuario);
}
