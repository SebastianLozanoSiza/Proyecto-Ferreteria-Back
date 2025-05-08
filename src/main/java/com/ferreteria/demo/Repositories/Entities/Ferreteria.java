package com.ferreteria.demo.Repositories.Entities;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ferreterias")
public class Ferreteria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFerreteria;

    @NotBlank(message = "El NIT no puede estar vacío")
    @Column(nullable = false, unique = true)
    private String nit;

    @NotBlank(message = "La razón social no puede estar vacía")
    @Column(nullable = false)
    private String razonSocial;

    @NotBlank(message = "El representante no puede estar vacío")
    @Column(nullable = false)
    private String representante;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;
}

