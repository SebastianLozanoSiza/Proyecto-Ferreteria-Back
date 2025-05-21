package com.ferreteria.demo.Services.Impl;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ferreteria.demo.Config.CredencialesDTOConverter;
import com.ferreteria.demo.Config.EmpleadoDTOConverter;
import com.ferreteria.demo.DTO.Empleado.ConvertirClienteAEmpleadoDTO;
import com.ferreteria.demo.DTO.Empleado.CrearEmpleadoDTO;
import com.ferreteria.demo.DTO.Empleado.EditarEmpleadoDTO;
import com.ferreteria.demo.DTO.Empleado.EmpleadoDTO;
import com.ferreteria.demo.DTO.Empleado.ListarEmpleadoDTO;
import com.ferreteria.demo.Repositories.RepositoryCredenciales;
import com.ferreteria.demo.Repositories.RepositoryEmpleado;
import com.ferreteria.demo.Repositories.RepositoryFerreteria;
import com.ferreteria.demo.Repositories.RepositoryRol;
import com.ferreteria.demo.Repositories.RepositoryTercero;
import com.ferreteria.demo.Repositories.Entities.Credenciales;
import com.ferreteria.demo.Repositories.Entities.Empleado;
import com.ferreteria.demo.Repositories.Entities.Ferreteria;
import com.ferreteria.demo.Repositories.Entities.Rol;
import com.ferreteria.demo.Repositories.Entities.Tercero;
import com.ferreteria.demo.Services.EmailService;
import com.ferreteria.demo.Services.ServiceEmpleado;

@Service
public class ServiceEmpleadoImpl implements ServiceEmpleado {

    @Autowired
    private EmailService emailService;

    @Autowired
    private RepositoryEmpleado repositoryEmpleado;

    @Autowired
    private RepositoryTercero repositoryTercero;

    @Autowired
    private RepositoryCredenciales repositoryCredenciales;

    @Autowired
    private RepositoryRol repositoryRol;

    @Autowired
    private RepositoryFerreteria repositoryFerreteria;

    @Autowired
    private EmpleadoDTOConverter convert;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CredencialesDTOConverter credencialesDTOConverter;

    @Override
    public ListarEmpleadoDTO findAll() {
        Iterable<Empleado> empleadoIterable = repositoryEmpleado.findAll();
        List<Empleado> empleados = StreamSupport.stream(empleadoIterable.spliterator(), false)
                .collect(Collectors.toList());

        List<EmpleadoDTO> empleadoDTOs = empleados.stream().map(convert::convertToListarDTO)
                .collect(Collectors.toList());
        ListarEmpleadoDTO listarEmpleadoDTO = new ListarEmpleadoDTO();
        listarEmpleadoDTO.setEmpleados(empleadoDTOs);
        return listarEmpleadoDTO;

    }

    @Override
    public CrearEmpleadoDTO save(CrearEmpleadoDTO crearEmpleadoDTO) {

        if (repositoryTercero.existsByIdentificacion(crearEmpleadoDTO.getIdentificacion())) {
            throw new RuntimeException("El usuario con esta identificación ya está registrado.");
        }

        Tercero tercero = new Tercero();
        tercero.setIdentificacion(crearEmpleadoDTO.getIdentificacion());
        tercero.setNombre(crearEmpleadoDTO.getNombre());
        tercero.setApellidos(crearEmpleadoDTO.getApellidos());
        tercero.setCorreo(crearEmpleadoDTO.getCorreo());
        tercero.setDireccion(crearEmpleadoDTO.getDireccion());
        tercero.setTelefono(crearEmpleadoDTO.getTelefono());
        Tercero terceroSaved = repositoryTercero.save(tercero);

        // 2. Generar nombre de usuario y contraseña aleatorios
        String nombreUsuario = generarNombreUsuario(terceroSaved);
        while (repositoryCredenciales.findByNombreUsuario(nombreUsuario).isPresent()) {
            nombreUsuario = generarNombreUsuario(terceroSaved);
        }
        String contrasenaGenerada = generarContrasenaAleatoria();

        // 3. Guardar credenciales
        Credenciales credenciales = new Credenciales();
        credenciales.setNombreUsuario(nombreUsuario);
        credenciales.setContrasena(passwordEncoder.encode(contrasenaGenerada));
        credenciales.setTercero(terceroSaved);
        repositoryCredenciales.save(credenciales);

        // 4. Crear empleado
        Rol rol = repositoryRol.findById(crearEmpleadoDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        Ferreteria ferreteria = repositoryFerreteria.findById(crearEmpleadoDTO.getIdFerreteria())
                .orElseThrow(() -> new RuntimeException("Ferretería no encontrada"));

        Empleado empleado = new Empleado();
        empleado.setTercero(terceroSaved);
        empleado.setRol(rol);
        empleado.setFerreteria(ferreteria);
        Empleado empleadoSaved = repositoryEmpleado.save(empleado);

        // 5. Enviar correo
        try {
            String asunto = "Bienvenido a Ferretería";
            String cuerpo = "Hola " + terceroSaved.getNombre() + ",\n\n"
                    + "Tu cuenta ha sido creada.\n\n"
                    + "Usuario: " + nombreUsuario + "\n"
                    + "Contraseña: " + contrasenaGenerada + "\n\n"
                    + "Por favor inicia sesión y cambia tu contraseña cuanto antes.\n\n"
                    + "Saludos,\nEquipo de Ferretería";
            emailService.enviarCredenciales(terceroSaved.getCorreo(), asunto, cuerpo);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }

        // 6. Convertir a DTO y retornar
        return convert.convertToDTO(empleadoSaved);
    }

    private String generarNombreUsuario(Tercero tercero) {
        String base = tercero.getNombre().toLowerCase().replaceAll("\\s+", "") + new Random().nextInt(1000);
        return base;
    }

    private String generarContrasenaAleatoria() {
        int length = 10;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        StringBuilder password = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    @Override
    public EditarEmpleadoDTO update(Long id, EditarEmpleadoDTO editarEmpleadoDTO) {
        Optional<Empleado> empleadoCuurentOptional = repositoryEmpleado.findById(id);
        if (empleadoCuurentOptional.isPresent()) {
            Empleado empleadoCurrent = empleadoCuurentOptional.get();
            Tercero tercero = empleadoCurrent.getTercero();

            if (!tercero.getIdentificacion().equals(editarEmpleadoDTO.getIdentificacion()) &&
                    repositoryTercero.existsByIdentificacion(editarEmpleadoDTO.getIdentificacion())) {
                throw new IllegalArgumentException(
                        "La identificación: " + editarEmpleadoDTO.getIdentificacion() + " ya está registrada.");
            }

            if (!tercero.getCorreo().equals(editarEmpleadoDTO.getCorreo()) &&
                    repositoryTercero.existsByCorreo(editarEmpleadoDTO.getCorreo())) {
                throw new IllegalArgumentException("El correo: " + editarEmpleadoDTO.getCorreo() + " ya está en uso");
            }

            if (!tercero.getTelefono().equals(editarEmpleadoDTO.getTelefono()) &&
                    repositoryTercero.existsByTelefono(editarEmpleadoDTO.getTelefono())) {
                throw new IllegalArgumentException(
                        "El teléfono: " + editarEmpleadoDTO.getTelefono() + " ya está en uso");
            }

            tercero.setIdentificacion(editarEmpleadoDTO.getIdentificacion());
            tercero.setNombre(editarEmpleadoDTO.getNombre());
            tercero.setApellidos(editarEmpleadoDTO.getApellidos());
            tercero.setCorreo(editarEmpleadoDTO.getCorreo());
            tercero.setDireccion(editarEmpleadoDTO.getDireccion());
            tercero.setTelefono(editarEmpleadoDTO.getTelefono());
            repositoryTercero.save(tercero);

            Credenciales credenciales = repositoryCredenciales.findByTercero_IdTercero(tercero.getIdTercero())
                    .orElseThrow(() -> new IllegalArgumentException("No se encontraron credenciales para el cliente."));

            if (!credenciales.getNombreUsuario().equals(editarEmpleadoDTO.getApellidos()) &&
                    repositoryCredenciales.findByNombreUsuario(editarEmpleadoDTO.getNombreUsuario()).isPresent()) {
                throw new IllegalArgumentException(
                        "El nombre de usuario: " + editarEmpleadoDTO.getNombreUsuario() + " ya está en uso");
            }

            credenciales.setNombreUsuario(editarEmpleadoDTO.getNombreUsuario());

            if (editarEmpleadoDTO.getContrasena() != null && !editarEmpleadoDTO.getContrasena().isBlank()) {
                String encryptedPassword = passwordEncoder.encode(editarEmpleadoDTO.getContrasena());
                credenciales.setContrasena(encryptedPassword);
            }

            repositoryCredenciales.save(credenciales);

            return credencialesDTOConverter.convertToDTOEditar(credenciales);
        }
        throw new IllegalArgumentException("El empleado con ID " + id + " no existe.");
    }

    @Override
    public void delete(Long id) {
        Optional<Empleado> empleadoOptional = repositoryEmpleado.findById(id);
        if (empleadoOptional.isEmpty()) {
            throw new IllegalArgumentException("El empleado con ID " + id + " no existe.");
        }

        Empleado empleado = empleadoOptional.get();
        Tercero tercero = empleado.getTercero();
        repositoryCredenciales.findByTercero_IdTercero(tercero.getIdTercero())
                .ifPresent(repositoryCredenciales::delete);

        repositoryEmpleado.delete(empleado);
        repositoryTercero.delete(tercero);
    }

    @Override
    public ConvertirClienteAEmpleadoDTO convertir(ConvertirClienteAEmpleadoDTO convertirClienteAEmpleadoDTO) {
        // Verificar si el cliente (Tercero) existe
        Tercero tercero = repositoryTercero.findById(convertirClienteAEmpleadoDTO.getIdTercero())
                .orElseThrow(() -> new RuntimeException("Cliente (Tercero) no encontrado"));

        // Verificar si ya es un empleado
        if (repositoryEmpleado.findByTercero(tercero).isPresent()) {
            throw new RuntimeException("El cliente ya está registrado como empleado.");
        }

        // Verificar si el rol existe
        Rol rol = repositoryRol.findById(convertirClienteAEmpleadoDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Verificar si la ferretería existe
        Ferreteria ferreteria = repositoryFerreteria.findById(convertirClienteAEmpleadoDTO.getIdFerreteria())
                .orElseThrow(() -> new RuntimeException("Ferretería no encontrada"));

        // Crear el empleado
        Empleado empleado = new Empleado();
        empleado.setTercero(tercero);
        empleado.setRol(rol);
        empleado.setFerreteria(ferreteria);
        Empleado empleadoSaved = repositoryEmpleado.save(empleado);

        // Convertir a DTO y retornar
        return convert.convertToConvertirDTO(empleadoSaved);
    }

}
