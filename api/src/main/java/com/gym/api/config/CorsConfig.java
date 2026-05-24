package com.gym.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuracion global de CORS (Cross-Origin Resource Sharing) para la API.
 *
 * Permite que aplicaciones frontend ejecutandose en otros origenes (otro
 * dominio o puerto) puedan consumir esta API. Sin esta configuracion, los
 * navegadores bloquean las peticiones por la politica de "Same-Origin"
 * implementada por seguridad.
 *
 * El metodo addCorsMappings se sobreescribe de WebMvcConfigurer para
 * registrar las reglas de CORS que Spring aplica automaticamente a todas
 * las peticiones entrantes.
 *
 * Configuracion actual:
 *   - Aplica a todas las rutas que comienzan con /api
 *   - Permite peticiones desde localhost en puertos comunes de desarrollo
 *     de frameworks frontend (Vite usa 5173/5174, Create React App usa 3000,
 *     Next.js usa 3000, Angular usa 4200)
 *   - Permite todos los metodos HTTP estandar
 *   - Permite todos los headers en la peticion
 *   - Permite el envio de credenciales (cookies, tokens de autorizacion)
 *   - Cachea la respuesta del preflight por una hora
 *
 * Para produccion, "allowedOrigins" debe restringirse al dominio real de
 * la aplicacion frontend en lugar de localhost. Por ejemplo:
 *   .allowedOrigins("https://miapp.com", "https://www.miapp.com")
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://localhost:4200",
                        "http://localhost:5173",
                        "http://localhost:5174",
                        "http://localhost:8080"
                )
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
