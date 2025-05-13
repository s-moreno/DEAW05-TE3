package birt.smoreno.inventarioAPI.exceptions;

import lombok.Getter;

/**
 * CustomException es una excepción personalizada utilizada para representar errores específicos
 * en la aplicación, permitiendo adjuntar un código de estado HTTP junto con el mensaje de error.
 *
 * <p>Extiende de {@link Exception}, lo que la hace comprobada (checked),
 * y proporciona información adicional para el manejo de errores HTTP en controladores REST.</p>
 *
 * <p>Se utiliza, por ejemplo, para lanzar errores personalizados que luego pueden ser interceptados
 * por un controlador de excepciones global para construir una respuesta consistente.</p>
 */
@Getter
public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final int httpStatusCode;
	
	/**
     * Crea una nueva instancia de {@code CustomException} con el mensaje de error y el código de estado HTTP proporcionados.
     *
     * @param message         el mensaje de error descriptivo.
     * @param httpStatusCode  el código de estado HTTP correspondiente.
     */
    public CustomException(String message, int httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
}
