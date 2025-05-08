package com.ferreteria.demo.Repositories.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "barrios")
public class Barrio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBarrio;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_municipio", nullable = false)
    private Municipio municipio;

}
