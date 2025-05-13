package com.ferreteria.demo.Services.Impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferreteria.demo.Config.FerreteriaConverterDTO;
import com.ferreteria.demo.DTO.Ferreteria.FerreteriaDTO;
import com.ferreteria.demo.DTO.Ferreteria.ListarFerreteriaDTO;
import com.ferreteria.demo.Repositories.RepositoryFerreteria;
import com.ferreteria.demo.Repositories.Entities.Ferreteria;
import com.ferreteria.demo.Services.ServiceFerreteria;

@Service
public class ServiceFerreteriaImpl implements ServiceFerreteria {

    @Autowired
    private RepositoryFerreteria repositoryFerreteria;

    @Autowired
    private FerreteriaConverterDTO convert;

    @Override
    public ListarFerreteriaDTO findAll() {
        Iterable<Ferreteria> ferreteriaIterable = repositoryFerreteria.findAll();
        List<Ferreteria> ferreterias = StreamSupport.stream(ferreteriaIterable.spliterator(), false)
                .collect(Collectors.toList());

        List<FerreteriaDTO> ferreteriaDTOs = ferreterias.stream().map(convert::convertToListarDTO)
                .collect(Collectors.toList());
        ListarFerreteriaDTO listarFerreteriaDTO = new ListarFerreteriaDTO();
        listarFerreteriaDTO.setFerreteria(ferreteriaDTOs);
        return listarFerreteriaDTO;
    }

}
