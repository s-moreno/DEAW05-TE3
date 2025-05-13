package birt.smoreno.inventarioAPI.mappers;

import org.springframework.stereotype.Component;

import birt.smoreno.inventarioAPI.dto.ProductRequestDTO;
import birt.smoreno.inventarioAPI.dto.ProductResponseDTO;
import birt.smoreno.inventarioAPI.entities.CategoryEntity;
import birt.smoreno.inventarioAPI.entities.ProductEntity;
import birt.smoreno.inventarioAPI.repositories.CategoryRepository;

/**
 * {@code ProductMapper} es una clase que se encarga de realizar el mapeo entre
 * las entidades de producto y los DTOs utilizados en la API.
 * <p>
 * Esta clase contiene métodos para convertir entre diferentes representaciones
 * del producto:
 * </p>
 * <ul>
 * <li>{@code ProductRequestDTO} a {@code ProductEntity}, para la creación o
 * actualización de productos.</li>
 * <li>{@code ProductEntity} a {@code ProductResponseDTO}, para devolver una
 * representación del producto en las respuestas.</li>
 * </ul>
 */
@Component
public class ProductMapper {

	// Instanciar el repositorio de categorías para ser utilizado en los métodos de
	// la clase
	private final CategoryRepository categoryRepository;

	// Constructor que inyecta el repositorio de categorías
	// Se podría usar @Autowired, pero está desaconsejado.
	public ProductMapper(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	/**
	 * Convertir RequestDTO a EntitY
	 * 
	 * @param {@link ProductRequestDTO}
	 * @return {@link ProductEntity}
	 */
	public ProductEntity toEntity(ProductRequestDTO productRequestDTO) {

		CategoryEntity category = categoryRepository.findById(productRequestDTO.getId_categoria())
				.orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

		return new ProductEntity(0, // ID se asigna automáticamente
				productRequestDTO.getNombre(), productRequestDTO.getStock_actual(), productRequestDTO.getStock_minimo(),
				null, // createdAt se asigna automáticamente
				null, // updatedAt se asigna automáticamente
				category);
	}

	/**
	 * Convertir Entity a ResponseDTO
	 * 
	 * @param {@link ProductEntity}
	 * @return {@link ProductResponseDTO}
	 */
	public ProductResponseDTO toResponseDTO(ProductEntity productEntity) {
		return new ProductResponseDTO(productEntity.getId(), productEntity.getName(), productEntity.getCurrentStock(),
				productEntity.getMinStock(), productEntity.getCategory().getId());
	}
}
