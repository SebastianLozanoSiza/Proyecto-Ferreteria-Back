package com.ferreteria.demo.Config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ferreteria.demo.DTO.Empleado.ConvertirClienteAEmpleadoDTO;
import com.ferreteria.demo.DTO.Empleado.CrearEmpleadoDTO;
import com.ferreteria.demo.DTO.Empleado.EmpleadoDTO;
import com.ferreteria.demo.Repositories.RepositoryCredenciales;
import com.ferreteria.demo.Repositories.Entities.Empleado;

@Component
public class EmpleadoDTOConverter {

    private final ModelMapper dbm;

    @Autowired
    private RepositoryCredenciales repositoryCredenciales;

    public EmpleadoDTOConverter(ModelMapper modelMapper) {
        this.dbm = modelMapper;
    }

    public CrearEmpleadoDTO convertToDTO(Empleado empleado) {
        CrearEmpleadoDTO crearEmpleadoDTO = dbm.map(empleado, CrearEmpleadoDTO.class);
        if (empleado.getTercero() != null) {
            crearEmpleadoDTO.setIdentificacion(empleado.getTercero().getIdentificacion());
            crearEmpleadoDTO.setNombre(empleado.getTercero().getNombre());
            crearEmpleadoDTO.setApellidos(empleado.getTercero().getApellidos());
            crearEmpleadoDTO.setCorreo(empleado.getTercero().getCorreo());
            crearEmpleadoDTO.setDireccion(empleado.getTercero().getDireccion());
            crearEmpleadoDTO.setTelefono(empleado.getTercero().getTelefono());
        }
        if (empleado.getRol() != null) {
            crearEmpleadoDTO.setIdRol(empleado.getRol().getIdRol());
        }
        if (empleado.getFerreteria() != null) {
            crearEmpleadoDTO.setIdFerreteria(empleado.getFerreteria().getIdFerreteria());
        }
        return crearEmpleadoDTO;
    }

    public Empleado convertToEntity(CrearEmpleadoDTO crearEmpleadoDTO) {
        Empleado empleado = dbm.map(crearEmpleadoDTO, Empleado.class);
        return empleado;
    }

    public EmpleadoDTO convertToListarDTO(Empleado empleado) {
        EmpleadoDTO empleadoDTO = dbm.map(empleado, EmpleadoDTO.class);
        empleadoDTO.setIdEmpleado(empleado.getId());
        if (empleado.getTercero() != null) {
            empleadoDTO.setIdentificacion(empleado.getTercero().getIdentificacion());
            empleadoDTO.setNombre(empleado.getTercero().getNombre());
            empleadoDTO.setApellidos(empleado.getTercero().getApellidos());
            empleadoDTO.setCorreo(empleado.getTercero().getCorreo());
            empleadoDTO.setDireccion(empleado.getTercero().getDireccion());
            empleadoDTO.setTelefono(empleado.getTercero().getTelefono());
            repositoryCredenciales.findByTercero_IdTercero(empleado.getTercero().getIdTercero())
                    .ifPresent(credenciales -> empleadoDTO.setNombreUsuario(credenciales.getNombreUsuario()));
        }
        if (empleado.getRol() != null) {
            empleadoDTO.setIdRol(empleado.getRol().getIdRol());
            empleadoDTO.setNombreRol(empleado.getRol().getNombreRol());
        }
        if (empleado.getFerreteria() != null) {
            empleadoDTO.setIdFerreteria(empleado.getFerreteria().getIdFerreteria());
            empleadoDTO.setRazonSocial(empleado.getFerreteria().getRazonSocial());
        }
        return empleadoDTO;
    }

    public ConvertirClienteAEmpleadoDTO convertToConvertirDTO(Empleado empleado) {
        ConvertirClienteAEmpleadoDTO convertirClienteAEmpleadoDTO = dbm.map(empleado,
                ConvertirClienteAEmpleadoDTO.class);
        if (empleado.getTercero() != null) {
            convertirClienteAEmpleadoDTO.setIdTercero(empleado.getTercero().getIdTercero());
        }
        if (empleado.getRol() != null) {
            convertirClienteAEmpleadoDTO.setIdRol(empleado.getRol().getIdRol());
        }
        if (empleado.getFerreteria() != null) {
            convertirClienteAEmpleadoDTO.setIdFerreteria(empleado.getFerreteria().getIdFerreteria());
        }
        return convertirClienteAEmpleadoDTO;
    }
}
