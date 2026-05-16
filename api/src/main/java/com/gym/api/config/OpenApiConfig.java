package com.gym.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuracion personalizada de SpringDoc OpenAPI (Swagger).
 *
 * Esta clase reemplaza los valores genericos que SpringDoc usa por defecto
 * con metainformacion descriptiva del proyecto. El resultado se ve reflejado
 * inmediatamente en la cabecera de Swagger UI cuando se visita la ruta
 * configurada en application.properties (por defecto /swagger-ui.html).
 *
 * Si en el futuro necesitas anyadir esquemas de seguridad (por ejemplo JWT
 * cuando integremos Spring Security), tags globales, o documentacion de
 * servidores adicionales (staging, produccion), este es el lugar donde
 * deben centralizarse esos cambios.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Construye el objeto OpenAPI que SpringDoc va a usar para componer
     * la documentacion visual en Swagger UI.
     *
     * La anotacion @Bean indica a Spring que el resultado de este metodo
     * debe registrarse en su contenedor de inyeccion de dependencias.
     * SpringDoc lo detecta automaticamente y reemplaza con el la
     * configuracion por defecto.
     */
    @Bean
    public OpenAPI gymApiOpenAPI() {
        return new OpenAPI()
                .info(buildApiInfo())
                .servers(buildServersList());
    }

    /**
     * Construye el bloque "info" de la especificacion OpenAPI.
     *
     * Esta informacion aparece en la cabecera de Swagger UI e incluye
     * el titulo de la API, su version, una descripcion general, los
     * datos de contacto del equipo y la licencia bajo la cual se
     * distribuye el proyecto.
     */
    private Info buildApiInfo() {
        return new Info()
                .title("Gym API")
                .version("1.0.0")
                .description("""
                        API REST para la gestion integral de un gimnasio.
                        
                        Esta API permite administrar usuarios, membresias, clases,
                        inscripciones, pagos, inventario, espacios fisicos, accesos
                        por areas, agendas y mas. Esta diseyada para ser consumida
                        por aplicaciones cliente como apps moviles o paneles web.
                        """)
                .contact(buildContact())
                .license(buildLicense());
    }

    /**
     * Define los datos de contacto del equipo responsable del proyecto.
     * Aparecen en Swagger UI como enlaces clicables, facilitando que
     * quien consume la API pueda reportar errores o pedir aclaraciones.
     */
    private Contact buildContact() {
        return new Contact()
                .name("Equipo de Desarrollo Gym API")
                .email("equipo@gym.com")
                .url("https://gym.com");
    }

    /**
     * Define la licencia bajo la cual se distribuye el proyecto.
     * Si el proyecto es privado o interno, este campo puede ajustarse
     * o eliminarse, pero conviene declararlo explicitamente para evitar
     * ambiguedad legal.
     */
    private License buildLicense() {
        return new License()
                .name("Uso Privado")
                .url("https://gym.com/licencia");
    }

    /**
     * Lista los servidores donde la API esta disponible.
     *
     * Swagger UI muestra un selector con estos servidores y permite
     * cambiar entre ellos al ejecutar peticiones de prueba. Por ahora
     * declaramos solamente el servidor de desarrollo local. Conforme
     * el proyecto crezca y se despliegue en otros entornos, se pueden
     * anyadir aqui las URLs de staging y produccion.
     */
    private List<Server> buildServersList() {
        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Servidor local de desarrollo");

        return List.of(localServer);
    }
}
