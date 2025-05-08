package com.ferreteria.demo.Services;

import com.ferreteria.demo.DTO.Empleado.ConvertirClienteAEmpleadoDTO;
import com.ferreteria.demo.DTO.Empleado.CrearEmpleadoDTO;
import com.ferreteria.demo.DTO.Empleado.ListarEmpleadoDTO;

public interface ServiceEmpleado {
    
    ListarEmpleadoDTO findAll();

    CrearEmpleadoDTO save(CrearEmpleadoDTO crearEmpleadoDTO);

    CrearEmpleadoDTO update(Long id, CrearEmpleadoDTO crearEmpleadoDTO);

    void delete(Long id);

    ConvertirClienteAEmpleadoDTO convertir(ConvertirClienteAEmpleadoDTO convertirClienteAEmpleadoDTO);
}
