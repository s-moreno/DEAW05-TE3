package birt.smoreno.inventarioAPI.mappers;

import org.springframework.stereotype.Component;

import birt.smoreno.inventarioAPI.dto.CategoryRequestDTO;
import birt.smoreno.inventarioAPI.dto.CategoryResponseDTO;
import birt.smoreno.inventarioAPI.entities.CategoryEntity;

/**
 * {@code CategoryMapper} es una clase que se encarga de realizar el mapeo entre
 * las entidades de categoría y los DTOs utilizados en la API.
 * <p>
 * Esta clase contiene métodos para convertir entre diferentes representaciones
 * de la categoría:
 * </p>
 * <ul>
 * <li>{@code CategoryRequestDTO} a {@code CategoryEntity}, para la creación o
 * actualización de categorías.</li>
 * <li>{@code CategoryEntity} a {@code CategoryResponseDTO}, para devolver una
 * representación de la categoría en las respuestas.</li>
 * </ul>
 * 
 * @see CategoryRequestDTO
 * @see CategoryResponseDTO
 * @see CategoryEntity
 */
@Component
public class CategoryMapper {

	/**
	 * Convertir RequestDTO a Entity
	 * 
	 * @param {@link CategoryRequestDTO}
	 * @return {@link CategoryEntity}
	 */
	public CategoryEntity toEntity(CategoryRequestDTO categoryRequest) {
		return new CategoryEntity(0, // ID se asigna automáticamente
				categoryRequest.getNombre(), categoryRequest.getDescripcion(), null, // createdAt se asigna
																						// automáticamente
				null, // updatedAt se asigna automáticamente
				null // products se asigna automáticamente
		);
	}

	/**
	 * Convertir Entity a ResponseDTO
	 * 
	 * @param {@link CategoryEntity}
	 * @return {@link CategoryResponseDTO}
	 */
	public CategoryResponseDTO toResponseDTO(CategoryEntity categoryEntity) {
		return new CategoryResponseDTO(categoryEntity.getId(), categoryEntity.getName(),
				categoryEntity.getDescription());
	}
}
