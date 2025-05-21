package com.ferreteria.demo.Services;

import java.util.Optional;

import com.ferreteria.demo.DTO.Tercero.ListarTerceroDTO;
import com.ferreteria.demo.DTO.Usuario.UsuarioDTO;
import com.ferreteria.demo.Repositories.Entities.Credenciales;

public interface ServiceTercero {

    ListarTerceroDTO findByNombreUsuario(String nombreUsuario);

    Optional<Credenciales> findCredencialesByTerceroId(Long idTercero);

    UsuarioDTO update(Long id, UsuarioDTO usuarioDTO);
}
