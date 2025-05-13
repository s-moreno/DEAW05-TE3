package birt.smoreno.inventarioAPI.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import birt.smoreno.inventarioAPI.dto.ApiResponseDTO;
import birt.smoreno.inventarioAPI.utils.AppConstants;

/**
 * {@code ValidationHandler} se encarga de interceptar y gestionar las
 * excepciones de validación generadas por anotaciones como {@code @Valid} en
 * los controladores.
 * <p>
 * Extiende {@link ResponseEntityExceptionHandler} para sobrescribir el
 * comportamiento por defecto del manejo de excepciones de Spring MVC.
 * </p>
 *
 * <p>
 * Cuando se produce una excepción de tipo
 * {@link MethodArgumentNotValidException}, construye una respuesta
 * estandarizada con los errores de validación y la devuelve al cliente en
 * formato JSON usando {@link ApiResponseDTO}.
 * </p>
 *
 * @see ResponseEntityExceptionHandler
 * @see ApiResponseDTO
 */
@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

	/**
	 * Maneja las excepciones lanzadas cuando un argumento anotado con
	 * {@code @Valid} no cumple con las restricciones de validación definidas.
	 *
	 * @param exception excepción lanzada por errores de validación.
	 * @param headers   cabeceras HTTP asociadas a la respuesta.
	 * @param status    estado HTTP correspondiente.
	 * @param request   información adicional sobre la solicitud web.
	 * @return {@link ResponseEntity} con un cuerpo estandarizado que contiene los
	 *         errores de validación.
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException exception,
			@NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {

		Map<String, String> errors = new HashMap<>();

		exception.getBindingResult().getFieldErrors().forEach(error -> {
			String fieldName = error.getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		ApiResponseDTO<Object> apiResponse = new ApiResponseDTO<>(AppConstants.STATUS_ERROR,
				HttpStatus.BAD_REQUEST.value(), "Error de validación", errors);

		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
}
