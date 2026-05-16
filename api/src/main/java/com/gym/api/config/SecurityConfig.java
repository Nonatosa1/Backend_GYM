package com.gym.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuracion basica de seguridad criptografica.
 *
 * Por ahora esta clase declara un unico bean: el PasswordEncoder usado para
 * hashear contraseñas antes de almacenarlas en la base de datos. Cuando en
 * el futuro integremos Spring Security completo para autenticacion con JWT,
 * esta clase crecera con configuraciones de filtros de seguridad, reglas de
 * autorizacion y configuracion de tokens. Por ahora se mantiene minima.
 *
 * BCryptPasswordEncoder es la implementacion estandar del algoritmo BCrypt
 * para el hashing de contraseñas. Tiene tres caracteristicas importantes:
 *
 *   Es lento por diseño, lo cual hace inviables los ataques de fuerza bruta.
 *   Una operacion de hash toma decimas de segundo, lo cual es imperceptible
 *   para el usuario pero hace que probar millones de contraseñas sea
 *   computacionalmente prohibitivo para un atacante.
 *
 *   Aplica salt automaticamente. Cada hash incluye un valor aleatorio unico,
 *   lo cual significa que dos personas con la misma contraseña tendran
 *   hashes completamente distintos en la base de datos. Esto neutraliza
 *   los ataques basados en tablas precomputadas de hashes comunes.
 *
 *   Tiene un factor de costo configurable. El valor por defecto es 10, que
 *   significa que internamente el algoritmo se aplica 2^10 = 1024 veces.
 *   A medida que las computadoras se vuelven mas rapidas, este factor puede
 *   incrementarse para mantener la dificultad del ataque.
 *
 * La verificacion de una contraseña se hace con el metodo matches del
 * PasswordEncoder, que compara una contraseña en texto plano contra un hash
 * almacenado. Nunca se descifra el hash; en su lugar se calcula el hash de
 * la contraseña entrante con el mismo salt y se comparan los resultados.
 */
@Configuration
public class SecurityConfig {

    /**
     * Bean del PasswordEncoder disponible para inyectarse en cualquier
     * servicio que necesite hashear o verificar contraseñas. Como es un
     * bean singleton, todos los servicios comparten la misma instancia,
     * lo cual es completamente seguro porque el BCryptPasswordEncoder es
     * inmutable y seguro para uso concurrente.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
