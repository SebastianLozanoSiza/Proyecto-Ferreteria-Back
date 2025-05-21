package com.ferreteria.demo.DTO.Modulo;

import lombok.Data;

@Data
public class ModuloPermisoDTO {
    private boolean crear;
    private boolean actualizar;
    private boolean leer;
    private boolean eliminar;
    private String nombreModulo;
}
