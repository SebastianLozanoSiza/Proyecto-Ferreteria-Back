package com.ferreteria.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ferreteria.demo.Repositories.RepositoryCredenciales;
import com.ferreteria.demo.Repositories.RepositoryEmpleado;
import com.ferreteria.demo.Repositories.Entities.Credenciales;
import com.ferreteria.demo.Repositories.Entities.Empleado;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JWTUserDetailService implements UserDetailsService {

    private final RepositoryCredenciales repositoryCredenciales;
    private final RepositoryEmpleado repositoryEmpleado;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credenciales credenciales = repositoryCredenciales.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Buscar si el usuario es un empleado
        Optional<Empleado> empleadoOpt = repositoryEmpleado.findByTercero(credenciales.getTercero());

        // Determinar el rol
        String role = empleadoOpt.map(emp -> emp.getRol().getNombreRol()).orElse("CLIENTE");

        // Asignar permisos en Spring Security
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        return new User(credenciales.getNombreUsuario(), credenciales.getContrasena(), authorities);
    }
}
