package birt.smoreno.inventarioAPI.dto;

/**
 * {@code ProductResponseDTO} es una clase que representa la estructura de respuesta para un producto
 * <p>
 * Esta clase contiene cinco propiedades principales: {@code id}, {@code nombre}, {@code stock_actual},
 * {@code stock_minimo} y {@code id_categoria}. Estas propiedades representan los atributos de un producto
 * en el sistema.
 * </p>
 *
 * @param id           Identificador único del producto.
 * @param nombre       Nombre del producto.
 * @param stock_actual Cantidad actual de unidades disponibles del producto.
 * @param stock_minimo Cantidad mínima de unidades que deben estar disponibles del producto.
 * @param id_categoria Identificador de la categoría a la que pertenece el producto.
 */
public record ProductResponseDTO(
        int id,
        String nombre,
        int stock_actual,
        int stock_minimo,
        int id_categoria) {
}
