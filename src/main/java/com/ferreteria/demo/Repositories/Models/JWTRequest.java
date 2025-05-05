package com.ferreteria.demo.Repositories.Models;

import lombok.Data;

@Data
public class JWTRequest {
    
    private String username;
    private String password;
}
