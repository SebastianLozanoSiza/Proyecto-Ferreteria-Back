package com.ferreteria.demo.DTO.Ferreteria;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FerreteriaDTO {
    
    private Long idFerreteria;
    private String nit;
    private String razonSocial;
    private String representante;
    private LocalDateTime fechaRegistro;
}
