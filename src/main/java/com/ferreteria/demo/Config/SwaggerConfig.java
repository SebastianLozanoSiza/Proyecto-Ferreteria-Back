package com.ferreteria.demo.Config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                                .components(new Components().addSecuritySchemes("Bearer Authentication",
                                                createAPIKeyScheme()))
                                .info(new Info().title("Ferreteria")
                                                .description("API REST creada para el sector ferretero")
                                                .contact(new Contact()
                                                                .name("Juan Sebastian Lozano Siza")
                                                                .email("jsebastianlozano1207@outlook.com")
                                                                .url("https://github.com/SebastianLozanoSiza"))
                                                .version("1.0"));
        }

        @Bean
        public GroupedOpenApi productosApi() {
                return GroupedOpenApi.builder()
                                .group("Productos")
                                .pathsToMatch("/productos/**")
                                .build();
        }

        @Bean
        public GroupedOpenApi clientesApi() {
                return GroupedOpenApi.builder()
                                .group("Clientes")
                                .pathsToMatch("/clientes/**")
                                .build();
        }

        @Bean
        public GroupedOpenApi empleadosApi() {
                return GroupedOpenApi.builder()
                                .group("Empleados")
                                .pathsToMatch("/empleados/**")
                                .build();
        }

        @Bean
        public GroupedOpenApi ferreteriasApi() {
                return GroupedOpenApi.builder()
                                .group("Ferreterias")
                                .pathsToMatch("/ferreterias/**")
                                .build();
        }
        @Bean
        public GroupedOpenApi TercerosApi() {
                return GroupedOpenApi.builder()
                                .group("Terceros")
                                .pathsToMatch("/terceros/**")
                                .build();
        }

        @Bean
        public GroupedOpenApi rolesApi() {
                return GroupedOpenApi.builder()
                                .group("Roles")
                                .pathsToMatch("/roles/**")
                                .build();
        }

        @Bean
        public GroupedOpenApi departamentosApi() {
                return GroupedOpenApi.builder()
                                .group("Departamentos")
                                .pathsToMatch("/departamentos/**")
                                .build();
        }
        @Bean
        public GroupedOpenApi permisosApi() {
                return GroupedOpenApi.builder()
                                .group("Permisos")
                                .pathsToMatch("/permisos/**")
                                .build();
        }

        @Bean
        public GroupedOpenApi loginApi() {
                return GroupedOpenApi.builder()
                                .group("Login")
                                .pathsToMatch("/login/**")
                                .build();
        }
        @Bean
        public GroupedOpenApi nuevoUsuarioApi() {
                return GroupedOpenApi.builder()
                                .group("Nuevo Usuario")
                                .pathsToMatch("/registrarNuevo/**")
                                .build();
        }

        @Bean
        public GroupedOpenApi ferreteriaApi() {
                return GroupedOpenApi.builder()
                                .group("Ferreteria API")
                                .pathsToMatch("/**")
                                .build();
        }


        private SecurityScheme createAPIKeyScheme() {
                return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer");
        }
}
