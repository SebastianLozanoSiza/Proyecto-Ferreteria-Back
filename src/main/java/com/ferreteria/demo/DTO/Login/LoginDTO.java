package com.ferreteria.demo.DTO.Login;

import com.ferreteria.demo.DTO.RespuestaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {

    private RespuestaDTO respuesta;
    private String token;
}
