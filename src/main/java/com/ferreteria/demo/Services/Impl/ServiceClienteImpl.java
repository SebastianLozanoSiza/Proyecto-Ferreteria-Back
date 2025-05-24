package com.ferreteria.demo.Services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ferreteria.demo.Config.ClienteConverterDTO;
import com.ferreteria.demo.Config.CredencialesDTOConverter;
import com.ferreteria.demo.DTO.Cliente.ClienteDTO;
import com.ferreteria.demo.DTO.Cliente.ListarClienteDTO;
import com.ferreteria.demo.DTO.Usuario.UsuarioDTO;
import com.ferreteria.demo.Repositories.RepositoryCliente;
import com.ferreteria.demo.Repositories.RepositoryCredenciales;
import com.ferreteria.demo.Repositories.RepositoryTercero;
import com.ferreteria.demo.Repositories.Entities.Cliente;
import com.ferreteria.demo.Repositories.Entities.Credenciales;
import com.ferreteria.demo.Repositories.Entities.Tercero;
import com.ferreteria.demo.Services.ServiceCliente;

@Service
public class ServiceClienteImpl implements ServiceCliente {

    @Autowired
    private RepositoryCliente repositoryCliente;

    @Autowired
    private RepositoryTercero repositoryTercero;

    @Autowired
    private RepositoryCredenciales repositoryCredenciales;

    @Autowired
    private ClienteConverterDTO convert;

    @Autowired
    private CredencialesDTOConverter credencialesDTOConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ListarClienteDTO findAll(String identificacion, String nombre, String correo) {
        Iterable<Cliente> clienteIterable = repositoryCliente.findAll();
        List<Cliente> clientes = StreamSupport.stream(clienteIterable.spliterator(), false)
                .collect(Collectors.toList());

        if (identificacion != null && !identificacion.isEmpty()) {
            clientes = clientes.stream()
                    .filter(cli -> cli.getTercero().getIdentificacion()
                            .contains(identificacion))
                    .collect(Collectors.toList());
        }

        if (nombre != null && !nombre.isEmpty()) {
            clientes = clientes.stream()
                    .filter(cli -> cli.getTercero().getNombre().toLowerCase()
                            .contains(nombre.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (correo != null && !correo.isEmpty()) {
            clientes = clientes.stream()
                    .filter(cli -> cli.getTercero().getCorreo().toLowerCase()
                            .contains(correo.toLowerCase()))
                    .collect(Collectors.toList());
        }

        List<ClienteDTO> clienteDTO = clientes.stream().map(convert::convertToListarDTO)
                .collect(Collectors.toList());
        ListarClienteDTO listarClienteDTO = new ListarClienteDTO();
        listarClienteDTO.setClientes(clienteDTO);
        return listarClienteDTO;
    }

    @Override
    public ListarClienteDTO findByNombreUsuario(String nombreUsuario) {
        Optional<Credenciales> credencialesOpt = repositoryCredenciales.findByNombreUsuario(nombreUsuario);

        ListarClienteDTO listarClienteDTO = new ListarClienteDTO();

        if (credencialesOpt.isPresent()) {
            Credenciales credenciales = credencialesOpt.get();
            Tercero tercero = credenciales.getTercero();
            if (tercero != null) {
                Optional<Cliente> clienteOpt = repositoryCliente.findByTercero(tercero);
                if (clienteOpt.isPresent()) {
                    ClienteDTO clienteDTO = convert.convertToListarDTO(clienteOpt.get());
                    listarClienteDTO.setClientes(List.of(clienteDTO));
                    return listarClienteDTO;
                }
            }
        }
        listarClienteDTO.setClientes(List.of());
        return listarClienteDTO;
    }

    @Override
    public UsuarioDTO update(Long id, UsuarioDTO usuarioDTO) {
        Optional<Cliente> clienteCurrentOptional = repositoryCliente.findById(id);
        if (clienteCurrentOptional.isPresent()) {
            Cliente clienteCurrent = clienteCurrentOptional.get();
            Tercero tercero = clienteCurrent.getTercero();

            if (!tercero.getIdentificacion().equals(usuarioDTO.getIdentificacion()) &&
                    repositoryTercero.existsByIdentificacion(usuarioDTO.getIdentificacion())) {
                throw new IllegalArgumentException(
                        "La identificación: " + usuarioDTO.getIdentificacion() + " ya está registrada.");
            }

            if (!tercero.getCorreo().equals(usuarioDTO.getCorreo()) &&
                    repositoryTercero.existsByCorreo(usuarioDTO.getCorreo())) {
                throw new IllegalArgumentException("El correo: " + usuarioDTO.getCorreo() + " ya está en uso");
            }

            if (!tercero.getTelefono().equals(usuarioDTO.getTelefono()) &&
                    repositoryTercero.existsByTelefono(usuarioDTO.getTelefono())) {
                throw new IllegalArgumentException("El teléfono: " + usuarioDTO.getTelefono() + " ya está en uso");
            }

            tercero.setIdentificacion(usuarioDTO.getIdentificacion());
            tercero.setNombre(usuarioDTO.getNombre());
            tercero.setApellidos(usuarioDTO.getApellidos());
            tercero.setCorreo(usuarioDTO.getCorreo());
            tercero.setDireccion(usuarioDTO.getDireccion());
            tercero.setTelefono(usuarioDTO.getTelefono());
            repositoryTercero.save(tercero);

            Credenciales credenciales = repositoryCredenciales.findByTercero_IdTercero(tercero.getIdTercero())
                    .orElseThrow(() -> new IllegalArgumentException("No se encontraron credenciales para el cliente."));

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
    public void delete(Long id) {
        Optional<Cliente> clienteOptional = repositoryCliente.findById(id);
        if (clienteOptional.isEmpty()) {
            throw new IllegalArgumentException("El cliente con ID " + id + " no existe.");
        }

        Cliente cliente = clienteOptional.get();
        Tercero tercero = cliente.getTercero();
        repositoryCredenciales.findByTercero_IdTercero(tercero.getIdTercero())
                .ifPresent(repositoryCredenciales::delete);

        repositoryCliente.delete(cliente);
        repositoryTercero.delete(tercero);
    }

}
