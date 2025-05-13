package birt.smoreno.inventarioAPI.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * {@code CategoryRequestDTO} es una clase que representa la estructura de datos para la solicitud de creación o actualización de una categoría.
 * <p>
 * Esta clase contiene dos propiedades principales: {@code nombre} y {@code descripcion}, que representan los atributos básicos de una categoría.
 * </p>
 *
 * @param nombre      Nombre de la categoría.
 * @param descripcion Descripción de la categoría.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryRequestDTO {
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 255, message = "El nombre no puede superar los 255 caracteres")
        String nombre;
        @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
        String descripcion;
}
