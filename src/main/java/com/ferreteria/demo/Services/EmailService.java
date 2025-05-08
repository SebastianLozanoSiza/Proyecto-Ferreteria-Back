package com.ferreteria.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCredenciales(String destinatario, String usuario, String contraseña) {
        String asunto = "Credenciales de acceso al sistema";
        String cuerpo = String.format(
            "Hola,\n\nTu usuario ha sido creado en el sistema.\n\nUsuario: %s\nContraseña: %s\n\nPuedes cambiar la contraseña una vez inicies sesión.\n\nSaludos.",
            usuario, contraseña
        );

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario); // ← ESTE VIENE DEL DTO
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        mensaje.setFrom("xgoguetaxd@gmail.com");


        mailSender.send(mensaje);
    }
}
