package com.ferreteria.demo.Services.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ferreteria.demo.Config.CredencialesDTOConverter;
import com.ferreteria.demo.DTO.Usuario.UsuarioDTO;
import com.ferreteria.demo.Repositories.RepositoryCliente;
import com.ferreteria.demo.Repositories.RepositoryCredenciales;
import com.ferreteria.demo.Repositories.RepositoryTercero;
import com.ferreteria.demo.Repositories.Entities.Cliente;
import com.ferreteria.demo.Repositories.Entities.Credenciales;
import com.ferreteria.demo.Repositories.Entities.Tercero;
import com.ferreteria.demo.Services.ServiceCredenciales;

@Service
public class ServiceCredencialesImpl implements ServiceCredenciales {

    @Autowired
    private RepositoryCredenciales repositoryCredenciales;

    @Autowired
    private RepositoryTercero repositoryTercero;

    @Autowired
    private RepositoryCliente repositoryCliente;

    @Autowired
    private CredencialesDTOConverter convert;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    @Override
    public UsuarioDTO save(UsuarioDTO usuarioDTO) {
        if (repositoryCredenciales.findByNombreUsuario(usuarioDTO.getNombreUsuario()).isPresent()) {
            throw new IllegalArgumentException(
                    "El nombre de usuario: " + usuarioDTO.getNombreUsuario() + " ya está en uso");
        }

        if (repositoryTercero.existsByIdentificacion(usuarioDTO.getIdentificacion())) {
            throw new RuntimeException("El usuario con esta identificación ya está registrado.");
        }

        Tercero tercero = new Tercero();
        tercero.setIdentificacion(usuarioDTO.getIdentificacion());
        tercero.setNombre(usuarioDTO.getNombre());
        tercero.setApellidos(usuarioDTO.getApellidos());
        tercero.setCorreo(usuarioDTO.getCorreo());
        tercero.setDireccion(usuarioDTO.getDireccion());
        tercero.setTelefono(usuarioDTO.getTelefono());

        Tercero terceroSaved = repositoryTercero.save(tercero);

        Cliente cliente = new Cliente();
        cliente.setTercero(tercero);
        repositoryCliente.save(cliente);

        Credenciales credenciales = new Credenciales();
        credenciales.setNombreUsuario(usuarioDTO.getNombreUsuario());
        String encryptedPassword = passwordEncoder.encode(usuarioDTO.getContrasena());
        credenciales.setContrasena(encryptedPassword);

        credenciales.setTercero(terceroSaved);

        Credenciales savedNewUsuario = repositoryCredenciales.save(credenciales);
        return convert.convertToDTO(savedNewUsuario);
    }

    @Override
    public Optional<Credenciales> findByNombreUsuario(String nombreUsuario) {
        return repositoryCredenciales.findByNombreUsuario(nombreUsuario);
    }

}
