package com.ferreteria.demo.Services.Impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferreteria.demo.Config.RolConverterDTO;
import com.ferreteria.demo.DTO.Rol.ListarRolDTO;
import com.ferreteria.demo.DTO.Rol.RolDTO;
import com.ferreteria.demo.Repositories.RepositoryRol;
import com.ferreteria.demo.Repositories.Entities.Rol;
import com.ferreteria.demo.Services.ServiceRol;

@Service
public class ServiceRolImpl implements ServiceRol {

    @Autowired
    private RolConverterDTO convert;

    @Autowired
    private RepositoryRol repositoryRol;

    @Override
    public ListarRolDTO findAll() {
        Iterable<Rol> roIterable = repositoryRol.findAll();
        List<Rol> roles = StreamSupport.stream(roIterable.spliterator(), false).collect(Collectors.toList());
        List<RolDTO> rolDTO = roles.stream().map(convert::convertToListarDTO).collect(Collectors.toList());
        ListarRolDTO listarRolDTO = new ListarRolDTO();
        listarRolDTO.setRoles(rolDTO);
        return listarRolDTO;
    }

}
