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
        clientes.setLeer(roles.contains("Admin"));
        clientes.setCrear(roles.contains("Admin"));
        clientes.setActualizar(roles.contains("Admin") || roles.contains("CLIENTE"));
        clientes.setEliminar(roles.contains("Admin"));
        if (clientes.isLeer() || clientes.isCrear() || clientes.isActualizar() || clientes.isEliminar()) {
            modulos.add(clientes);
        }

        // EMPLEADOS
        ModuloPermisoDTO empleados = new ModuloPermisoDTO();
        empleados.setNombreModulo("Empleados");
        empleados.setLeer(roles.contains("Admin"));
        empleados.setCrear(roles.contains("Admin"));
        empleados.setActualizar(roles.contains("Admin"));
        empleados.setEliminar(roles.contains("Admin"));
        if (empleados.isLeer() || empleados.isCrear() || empleados.isActualizar() || empleados.isEliminar()) {
            modulos.add(empleados);
        }

        // FERRETERIAS
        ModuloPermisoDTO ferreterias = new ModuloPermisoDTO();
        ferreterias.setNombreModulo("Ferreterias");
        ferreterias.setLeer(roles.contains("Admin") || roles.contains("Supervisor"));
        ferreterias.setCrear(roles.contains("Supervisor") || roles.contains("Admin"));
        ferreterias.setActualizar(roles.contains("Supervisor") || roles.contains("Admin"));
        ferreterias.setEliminar(roles.contains("Supervisor") || roles.contains("Admin"));
        if (ferreterias.isLeer() || ferreterias.isCrear() || ferreterias.isActualizar() || ferreterias.isEliminar()) {
            modulos.add(ferreterias);
        }

        // DEPARTAMENTOS
        ModuloPermisoDTO departamentos = new ModuloPermisoDTO();
        departamentos.setNombreModulo("Departamentos");
        departamentos.setLeer(roles.contains("Admin"));
        departamentos.setCrear(roles.contains("Admin"));
        departamentos.setActualizar(roles.contains("Admin"));
        departamentos.setEliminar(roles.contains("Admin"));
        if (departamentos.isLeer() || departamentos.isCrear() || departamentos.isActualizar()
                || departamentos.isEliminar()) {
            modulos.add(departamentos);
        }

        // PRODUCTOS
        ModuloPermisoDTO productos = new ModuloPermisoDTO();
        productos.setNombreModulo("Productos");
        productos.setLeer(roles.contains("Admin") || roles.contains("Supervisor") || roles.contains("CLIENTE"));
        productos.setCrear(roles.contains("Admin") || roles.contains("Supervisor"));
        productos.setActualizar(roles.contains("Admin") || roles.contains("Supervisor"));
        productos.setEliminar(roles.contains("Admin") || roles.contains("Supervisor"));
        if (productos.isLeer() || productos.isCrear() || productos.isActualizar() || productos.isEliminar()) {
            modulos.add(productos);
        }

        // TERCEROS
        ModuloPermisoDTO terceros = new ModuloPermisoDTO();
        terceros.setNombreModulo("Terceros");
        terceros.setLeer(roles.contains("Admin") || roles.contains("Supervisor") || roles.contains("CLIENTE"));
        terceros.setCrear(roles.contains("Admin") || roles.contains("Supervisor") || roles.contains("CLIENTE"));
        terceros.setActualizar(roles.contains("Admin") || roles.contains("Supervisor") || roles.contains("CLIENTE"));
        terceros.setEliminar(roles.contains("Admin") || roles.contains("Supervisor") || roles.contains("CLIENTE"));
        if (terceros.isLeer() || terceros.isCrear() || terceros.isActualizar() || terceros.isEliminar()) {
            modulos.add(terceros);
        }

        // ROLES
        ModuloPermisoDTO rolesModulo = new ModuloPermisoDTO();
        rolesModulo.setNombreModulo("Roles");
        rolesModulo.setLeer(roles.contains("Admin"));
        rolesModulo.setCrear(roles.contains("Admin"));
        rolesModulo.setActualizar(roles.contains("Admin"));
        rolesModulo.setEliminar(roles.contains("Admin"));
        if (rolesModulo.isLeer() || rolesModulo.isCrear() || rolesModulo.isActualizar() || rolesModulo.isEliminar()) {
            modulos.add(rolesModulo);
        }

        respuesta.setRespuesta(new RespuestaDTO(false, "200", "Permisos listados correctamente"));
        respuesta.setModulos(modulos);
        return respuesta;
    }
}
