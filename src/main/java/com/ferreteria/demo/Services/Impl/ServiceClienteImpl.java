package com.ferreteria.demo.Services.Impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferreteria.demo.Config.ClienteConverterDTO;
import com.ferreteria.demo.DTO.Cliente.ClienteDTO;
import com.ferreteria.demo.DTO.Cliente.ListarClienteDTO;
import com.ferreteria.demo.Repositories.RepositoryCliente;
import com.ferreteria.demo.Repositories.Entities.Cliente;
import com.ferreteria.demo.Services.ServiceCliente;

@Service
public class ServiceClienteImpl implements ServiceCliente {

    @Autowired
    private RepositoryCliente repositoryCliente;

    @Autowired
    private ClienteConverterDTO convert;

    @Override
    public ListarClienteDTO findAll() {
        Iterable<Cliente> clienteIterable = repositoryCliente.findAll();
        List<Cliente> clientes = StreamSupport.stream(clienteIterable.spliterator(), false)
                .collect(Collectors.toList());

        List<ClienteDTO> clienteDTO = clientes.stream().map(convert::convertToListarDTO)
                .collect(Collectors.toList());
        ListarClienteDTO listarClienteDTO = new ListarClienteDTO();
        listarClienteDTO.setClientes(clienteDTO);
        return listarClienteDTO;
    }

}
