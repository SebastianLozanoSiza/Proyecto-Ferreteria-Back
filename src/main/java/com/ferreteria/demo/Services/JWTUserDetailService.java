package com.ferreteria.demo.Services;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ferreteria.demo.Repositories.RepositoryCliente;
import com.ferreteria.demo.Repositories.RepositoryCredenciales;
import com.ferreteria.demo.Repositories.RepositoryEmpleado;
import com.ferreteria.demo.Repositories.Entities.Credenciales;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JWTUserDetailService implements UserDetailsService {

    private final RepositoryCredenciales repositoryCredenciales;
    private final RepositoryEmpleado repositoryEmpleado;
    private final RepositoryCliente repositoryCliente;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credenciales credenciales = repositoryCredenciales.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Verificar si el usuario es un cliente
        boolean esCliente = repositoryCliente.findByTercero(credenciales.getTercero()).isPresent();

        // Determinar el rol
        String role = esCliente ? "CLIENTE"
                : repositoryEmpleado.findByTercero(credenciales.getTercero())
                        .map(emp -> emp.getRol().getNombreRol())
                        .orElseThrow(() -> new UsernameNotFoundException("Empleado no encontrado"));

        // Asignar permisos en Spring Security
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        return new User(credenciales.getNombreUsuario(), credenciales.getContrasena(), authorities);
    }
}
