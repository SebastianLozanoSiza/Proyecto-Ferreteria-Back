package com.ferreteria.demo.Services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ferreteria.demo.Config.CredencialesDTOConverter;
import com.ferreteria.demo.Config.TerceroConverterDTO;
import com.ferreteria.demo.DTO.Tercero.ListarTerceroDTO;
import com.ferreteria.demo.DTO.Tercero.TerceroDTO;
import com.ferreteria.demo.DTO.Usuario.UsuarioDTO;
import com.ferreteria.demo.Repositories.RepositoryCredenciales;
import com.ferreteria.demo.Repositories.RepositoryTercero;
import com.ferreteria.demo.Repositories.Entities.Credenciales;
import com.ferreteria.demo.Repositories.Entities.Tercero;
import com.ferreteria.demo.Services.ServiceTercero;

@Service
public class ServiceTerceroImpl implements ServiceTercero {

    @Autowired
    private RepositoryTercero repositoryTercero;

    @Autowired
    private RepositoryCredenciales repositoryCredenciales;

    @Autowired
    private CredencialesDTOConverter credencialesDTOConverter;

    @Autowired
    private TerceroConverterDTO convert;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ListarTerceroDTO findByNombreUsuario(String nombreUsuario) {
        Optional<Credenciales> credencialesOpt = repositoryCredenciales.findByNombreUsuario(nombreUsuario);

        ListarTerceroDTO listarTerceroDTO = new ListarTerceroDTO();

        if (credencialesOpt.isPresent()) {
            Credenciales credenciales = credencialesOpt.get();
            Tercero tercero = credenciales.getTercero();
            if (tercero != null) {
                TerceroDTO terceroDTO = convert.convertToListarDTO(tercero);
                listarTerceroDTO.setTerceros(List.of(terceroDTO));
                return listarTerceroDTO;
            }
        }
        listarTerceroDTO.setTerceros(List.of());
        return listarTerceroDTO;
    }

    @Override
    public UsuarioDTO update(Long id, UsuarioDTO usuarioDTO) {
        Optional<Tercero> terceroOptional = repositoryTercero.findById(id);
        if (terceroOptional.isPresent()) {
            Tercero terceroCurrent = terceroOptional.get();

            if (!terceroCurrent.getIdentificacion().equals(usuarioDTO.getIdentificacion()) &&
                    repositoryTercero.existsByIdentificacion(usuarioDTO.getIdentificacion())) {
                throw new IllegalArgumentException(
                        "La identificación: " + usuarioDTO.getIdentificacion() + " ya está registrada.");
            }

            if (!terceroCurrent.getCorreo().equals(usuarioDTO.getCorreo()) &&
                    repositoryTercero.existsByCorreo(usuarioDTO.getCorreo())) {
                throw new IllegalArgumentException("El correo: " + usuarioDTO.getCorreo() + " ya está en uso");
            }

            if (!terceroCurrent.getTelefono().equals(usuarioDTO.getTelefono()) &&
                    repositoryTercero.existsByTelefono(usuarioDTO.getTelefono())) {
                throw new IllegalArgumentException("El teléfono: " + usuarioDTO.getTelefono() + " ya está en uso");
            }

            terceroCurrent.setIdentificacion(usuarioDTO.getIdentificacion());
            terceroCurrent.setNombre(usuarioDTO.getNombre());
            terceroCurrent.setApellidos(usuarioDTO.getApellidos());
            terceroCurrent.setCorreo(usuarioDTO.getCorreo());
            terceroCurrent.setDireccion(usuarioDTO.getDireccion());
            terceroCurrent.setTelefono(usuarioDTO.getTelefono());
            repositoryTercero.save(terceroCurrent);

            Credenciales credenciales = repositoryCredenciales.findByTercero_IdTercero(terceroCurrent.getIdTercero())
                    .orElseThrow(() -> new IllegalArgumentException("No se encontraron credenciales para el tercero."));

            if (!credenciales.getNombreUsuario().equals(usuarioDTO.getNombreUsuario()) &&
                    repositoryCredenciales.findByNombreUsuario(usuarioDTO.getNombreUsuario()).isPresent()) {
                throw new IllegalArgumentException(
                        "El nombre de usuario: " + usuarioDTO.getNombreUsuario() + " ya está en uso");
            }

            credenciales.setNombreUsuario(usuarioDTO.getNombreUsuario());
            if (usuarioDTO.getContrasena() != null && !usuarioDTO.getContrasena().isBlank()) {
                String encryptedPassword = passwordEncoder.encode(usuarioDTO.getContrasena());
                credenciales.setContrasena(encryptedPassword);
            }

            repositoryCredenciales.save(credenciales);

            return credencialesDTOConverter.convertToDTO(credenciales);
        }
        throw new IllegalArgumentException("El cliente con ID " + id + " no existe.");
    }

    @Override
    public Optional<Credenciales> findCredencialesByTerceroId(Long idTercero) {
        return repositoryCredenciales.findByTercero_IdTercero(idTercero);
    }
}
