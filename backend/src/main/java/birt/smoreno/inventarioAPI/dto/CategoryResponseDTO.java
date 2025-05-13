package birt.smoreno.inventarioAPI.dto;

/**
 * {@code CategoryResponseDTO} es una clase que representa la estructura de respuesta para las categorías en la API.
 * <p>
 * Esta clase contiene tres propiedades: {@code id}, {@code nombre} y {@code descripcion}.
 * </p>
 *
 * @param id          Identificador único de la categoría.
 * @param nombre      Nombre de la categoría.
 * @param descripcion Descripción de la categoría.
 */
public record CategoryResponseDTO(
        int id,
        String nombre,
        String descripcion
){}
