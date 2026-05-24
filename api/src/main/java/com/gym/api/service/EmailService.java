package com.gym.api.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Servicio dedicado al envio de correos electronicos.
 *
 * Aisla la logica de envio de correos del resto del sistema. Los servicios
 * que necesitan enviar correos (como PersonaServiceImpl al registrar a
 * una nueva persona) delegan en esta clase sin preocuparse por los
 * detalles del protocolo SMTP, el formato del correo, ni las plantillas.
 *
 * Usa JavaMailSender de Spring, que esta configurado automaticamente a
 * partir de las propiedades spring.mail.* del archivo application.properties.
 *
 * Los correos se envian como HTML para permitir formato enriquecido
 * (botones, colores, enlaces estilizados). MimeMessage y MimeMessageHelper
 * son las clases estandar de JavaMail para correos con contenido HTML.
 *
 * Las URLs base se obtienen de propiedades configurables, lo cual permite
 * cambiar facilmente entre desarrollo (localhost) y produccion sin tocar
 * el codigo.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String correoRemitente;

    @Value("${app.mail.confirmation-base-url}")
    private String urlBaseConfirmacion;

    /**
     * Envia un correo de bienvenida con el enlace de confirmacion.
     *
     * Si el envio falla, el error se registra en logs pero NO se relanza
     * la excepcion. Esto evita que un fallo temporal del servidor SMTP
     * impida el registro de la persona. El administrador puede consultar
     * los logs y reenviar el correo manualmente si es necesario.
     *
     * @param destinatario Correo de la persona registrada
     * @param nombreCompleto Nombre completo para personalizar el saludo
     * @param token Token UUID para construir el enlace de confirmacion
     */
    public void enviarCorreoBienvenida(String destinatario, String nombreCompleto, String token) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom(correoRemitente);
            helper.setTo(destinatario);
            helper.setSubject("Bienvenido al Gimnasio - Confirma tu cuenta");
            helper.setText(construirContenidoHtml(nombreCompleto, token), true);

            mailSender.send(mensaje);
            log.info("Correo de bienvenida enviado a: {}", destinatario);
        } catch (MessagingException ex) {
            log.error("Error al enviar correo de bienvenida a {}: {}", destinatario, ex.getMessage());
        } catch (Exception ex) {
            log.error("Error inesperado al enviar correo a {}: {}", destinatario, ex.getMessage());
        }
    }

    /**
     * Construye el contenido HTML del correo de bienvenida.
     *
     * El HTML usa estilos inline porque los clientes de correo (Gmail,
     * Outlook, etc.) tienen soporte muy limitado para estilos externos
     * o CSS avanzado. Los estilos inline son la opcion mas compatible.
     */
    private String construirContenidoHtml(String nombreCompleto, String token) {
        String enlaceConfirmacion = urlBaseConfirmacion + "/" + token;

        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                </head>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; margin: 0;">
                    <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 40px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                        <h1 style="color: #1a5490; margin-top: 0; text-align: center;">¡Bienvenido al Gimnasio!</h1>
                        
                        <p style="font-size: 16px; color: #333; line-height: 1.6;">Hola <strong>%s</strong>,</p>
                        
                        <p style="font-size: 16px; color: #333; line-height: 1.6;">
                            Tu cuenta ha sido creada exitosamente en nuestro sistema. 
                            Para activarla y comenzar a disfrutar de todos nuestros servicios, 
                            por favor confirma tu cuenta haciendo clic en el siguiente botón:
                        </p>
                        
                        <div style="text-align: center; margin: 30px 0;">
                            <a href="%s" 
                               style="background-color: #1a5490; color: white; padding: 14px 32px; 
                                      text-decoration: none; border-radius: 5px; font-size: 16px; 
                                      font-weight: bold; display: inline-block;">
                                Confirmar mi cuenta
                            </a>
                        </div>
                        
                        <p style="font-size: 14px; color: #666; line-height: 1.6;">
                            Si el botón no funciona, copia y pega el siguiente enlace en tu navegador:
                        </p>
                        
                        <p style="font-size: 12px; color: #1a5490; word-break: break-all; background-color: #f4f8fb; padding: 10px; border-radius: 4px;">
                            %s
                        </p>
                        
                        <hr style="border: none; border-top: 1px solid #e0e0e0; margin: 30px 0;">
                        
                        <p style="font-size: 12px; color: #999; text-align: center; margin: 0;">
                            Si no te registraste en nuestro gimnasio, por favor ignora este correo.
                        </p>
                    </div>
                </body>
                </html>
                """.formatted(nombreCompleto, enlaceConfirmacion, enlaceConfirmacion);
    }
}
