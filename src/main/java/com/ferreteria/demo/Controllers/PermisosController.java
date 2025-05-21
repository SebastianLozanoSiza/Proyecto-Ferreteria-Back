package com.ferreteria.demo.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.demo.DTO.RespuestaDTO;
import com.ferreteria.demo.DTO.Modulo.ModuloPermisoDTO;
import com.ferreteria.demo.DTO.Modulo.RespuestaPermisosDTO;

@RestController
public class PermisosController {

    @GetMapping("/permisos")
    public RespuestaPermisosDTO getPermisos(Authentication authentication) {
        RespuestaPermisosDTO respuesta = new RespuestaPermisosDTO();
        List<ModuloPermisoDTO> modulos = new ArrayList<>();

        if (authentication == null) {
            respuesta.setRespuesta(new RespuestaDTO(true, "401", "No autenticado. Debe enviar un token válido."));
            respuesta.setModulos(List.of());
            return respuesta;
        }

        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // CLIENTES
        ModuloPermisoDTO clientes = new ModuloPermisoDTO();
        clientes.setNombreModulo("Clientes");
        clientes.setLeer(roles.contains("Superadmin") || roles.contains("Admin"));
        clientes.setCrear(roles.contains("Superadmin") || roles.contains("Admin"));
        clientes.setActualizar(roles.contains("Superadmin") || roles.contains("Admin") || roles.contains("CLIENTE"));
        clientes.setEliminar(roles.contains("Superadmin") || roles.contains("Admin"));
        if (clientes.isLeer() || clientes.isCrear() || clientes.isActualizar() || clientes.isEliminar()) {
            modulos.add(clientes);
        }

        // EMPLEADOS
        ModuloPermisoDTO empleados = new ModuloPermisoDTO();
        empleados.setNombreModulo("Empleados");
        empleados.setLeer(roles.contains("Superadmin") || roles.contains("Admin"));
        empleados.setCrear(roles.contains("Superadmin") || roles.contains("Admin"));
        empleados.setActualizar(roles.contains("Superadmin") || roles.contains("Admin"));
        empleados.setEliminar(roles.contains("Superadmin"));
        if (empleados.isLeer() || empleados.isCrear() || empleados.isActualizar() || empleados.isEliminar()) {
            modulos.add(empleados);
        }

        // FERRETERIAS
        ModuloPermisoDTO ferreterias = new ModuloPermisoDTO();
        ferreterias.setNombreModulo("Ferreterias");
        ferreterias.setLeer(roles.contains("Superadmin") || roles.contains("Admin") || roles.contains("CLIENTE"));
        ferreterias.setCrear(roles.contains("Superadmin") || roles.contains("Admin"));
        ferreterias.setActualizar(roles.contains("Superadmin") || roles.contains("Admin"));
        ferreterias.setEliminar(roles.contains("Superadmin") || roles.contains("Admin"));
        if (ferreterias.isLeer() || ferreterias.isCrear() || ferreterias.isActualizar() || ferreterias.isEliminar()) {
            modulos.add(ferreterias);
        }

        // DEPARTAMENTOS
        ModuloPermisoDTO departamentos = new ModuloPermisoDTO();
        departamentos.setNombreModulo("Departamentos");
        departamentos.setLeer(roles.contains("Superadmin") || roles.contains("Admin"));
        departamentos.setCrear(roles.contains("Superadmin"));
        departamentos.setActualizar(roles.contains("Superadmin"));
        departamentos.setEliminar(roles.contains("Superadmin"));
        if (departamentos.isLeer() || departamentos.isCrear() || departamentos.isActualizar()
                || departamentos.isEliminar()) {
            modulos.add(departamentos);
        }

        // PRODUCTOS
        ModuloPermisoDTO productos = new ModuloPermisoDTO();
        productos.setNombreModulo("Productos");
        productos.setLeer(roles.contains("Superadmin") || roles.contains("CLIENTE"));
        productos.setCrear(roles.contains("Superadmin"));
        productos.setActualizar(roles.contains("Superadmin"));
        productos.setEliminar(roles.contains("Superadmin"));
        if (productos.isLeer() || productos.isCrear() || productos.isActualizar() || productos.isEliminar()) {
            modulos.add(productos);
        }

        respuesta.setRespuesta(new RespuestaDTO(false, "200", "Permisos listados correctamente"));
        respuesta.setModulos(modulos);
        return respuesta;
    }
}
