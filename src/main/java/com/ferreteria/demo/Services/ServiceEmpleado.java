package com.ferreteria.demo.Services;

import com.ferreteria.demo.DTO.Empleado.ConvertirClienteAEmpleadoDTO;
import com.ferreteria.demo.DTO.Empleado.CrearEmpleadoDTO;
import com.ferreteria.demo.DTO.Empleado.EditarEmpleadoDTO;
import com.ferreteria.demo.DTO.Empleado.ListarEmpleadoDTO;

public interface ServiceEmpleado {
    
    ListarEmpleadoDTO findAll(String identificacion, String nombre, String correo, String rol, String ferreteria);

    CrearEmpleadoDTO save(CrearEmpleadoDTO crearEmpleadoDTO);

    EditarEmpleadoDTO update(Long id, EditarEmpleadoDTO editarEmpleadoDTO);

    void delete(Long id);

    ConvertirClienteAEmpleadoDTO convertir(ConvertirClienteAEmpleadoDTO convertirClienteAEmpleadoDTO);
}
