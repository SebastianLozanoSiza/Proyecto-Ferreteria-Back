package com.ferreteria.demo.Services;

import com.ferreteria.demo.DTO.Ferreteria.CrearFerreteriaDTO;
import com.ferreteria.demo.DTO.Ferreteria.ListarFerreteriaDTO;

public interface ServiceFerreteria {

    ListarFerreteriaDTO findAll(String nit, String razonSocial, String representante);

    CrearFerreteriaDTO save(CrearFerreteriaDTO crearFerreteriaDTO);

    CrearFerreteriaDTO update(Long id, CrearFerreteriaDTO crearFerreteriaDTO);

    void delete(Long id);
}
