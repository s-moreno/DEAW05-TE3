package birt.smoreno.inventarioAPI.dto;

/**
 * {@code ApiResponseDTO} es una clase que representa la estructura de respuesta general para la API
 * <p>
 * Esta clase contiene cuatro propiedades principales: {@code status}, {@code code}, {@code message} y {@code data}.
 * El campo {@code data} es genérico y puede contener cualquier tipo de dato que represente el resultado de la operación.
 * Usualmente, se usa para devolver un objeto o una lista de objetos, como productos o categorías.
 * </p>
 * 
 * @param <T> Tipo de dato que se incluye en el campo {@code data}. Este tipo puede ser cualquier entidad o colección
 *            de entidades, como por ejemplo un producto o una lista de productos, dependiendo del contexto de la respuesta.
 */
public record ApiResponseDTO<T> (
    String status,
    int code,
    String message,
    T data
){}

