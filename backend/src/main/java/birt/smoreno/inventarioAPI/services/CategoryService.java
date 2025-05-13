package birt.smoreno.inventarioAPI.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import birt.smoreno.inventarioAPI.dto.CategoryResponseDTO;
import birt.smoreno.inventarioAPI.entities.CategoryEntity;
import birt.smoreno.inventarioAPI.mappers.CategoryMapper;
import birt.smoreno.inventarioAPI.repositories.CategoryRepository;

/**
 * {@code CategoryService} es una clase de servicio que gestiona la lógica de
 * negocio relacionada con las categorías en la aplicación.
 * <p>
 * Esta clase proporciona métodos para realizar operaciones CRUD (Crear, Leer,
 * Actualizar, Eliminar) sobre las categorías. Utiliza
 * {@link CategoryRepository} para interactuar con la base de datos y realizar
 * las operaciones CRUD sobre la entidad {@link CategoryEntity}.
 * </p>
 * <p>
 * Además, {@code CategoryService} hace uso de {@link CategoryMapper} para
 * convertir entre las entidades {@link CategoryEntity} y los DTOs (Data
 * Transfer Objects) utilizados en la API {@link CategoryResponseDTO}. Esto
 * permite separar la lógica de acceso a datos de la lógica de presentación.
 * </p>
 * <p>
 * En resumen, {@code CategoryService} se encarga de la lógica de negocios para
 * las operaciones relacionadas con las categorías y sirve como intermediario
 * entre el controlador y el repositorio, asegurando que los datos sean
 * manejados correctamente antes de ser enviados a la base de datos o al
 * cliente.
 * </p>
 * 
 */
@Service
public class CategoryService {

	// Instanciar el repositorio de categorías para ser utilizado en los métodos de la clase
	private final CategoryRepository categoryRepository;
	// Instanciar el mapper para convertir entre entidades y DTOs
	private final CategoryMapper categoryMapper;

	/**
	 * Constructor que inyecta el repositorio de categorías y el mapper Se podría
	 * usar @Autowired, pero está desaconsejado.
	 * 
	 * @param categoryRepository Repositorio de categorías
	 * 
	 * @param categoryMapper      Mapper para convertir entre entidades y DTOs
	 */
	public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
	}

	/**
	 * Obtener todas las categorías
	 * 
	 * @return Lista de {@link CategoryResponseDTO} con todas las categorías
	 */
	public List<CategoryResponseDTO> getAllCategories() {
		List<CategoryEntity> categories = categoryRepository.findAll();
		return categories.stream().map(categoryMapper::toResponseDTO) // Convertir cada CategoryEntity a
																		// CategoryResponseDTO
				.toList();
	}

	/**
	 * Obtener una categoría por su ID
	 * 
	 * @param id ID de la categoría a buscar
	 * 
	 * @return {@link Optional<CategoryResponseDTO>} con la categoría encontrada o
	 *         vacío si no se encuentra
	 */
	public Optional<CategoryResponseDTO> getCategoryById(int id) {
		return categoryRepository.findById(id).map(categoryMapper::toResponseDTO);
	}

	/**
	 * Crear una nueva categoría
	 * 
	 * @param category Categoría a crear
	 * 
	 * @return {@link CategoryResponseDTO} con la categoría creada
	 */
	public CategoryResponseDTO createCategory(CategoryEntity category) {
		CategoryEntity createdCategory = categoryRepository.save(category);
		return categoryMapper.toResponseDTO(createdCategory);
	}

	/**
	 * Actualizar una categoría existente
	 * 
	 * @param id       ID de la categoría a actualizar
	 * @param category Nueva categoría con los datos actualizados
	 * 
	 * @return {@link Optional<CategoryResponseDTO>} con la categoría actualizada o
	 *         vacío si no se encuentra
	 */
	public Optional<CategoryResponseDTO> updateCategory(int id, CategoryEntity category) {
		if (categoryRepository.existsById(id)) {
			category.setId(id);
			CategoryEntity updatedCategory = categoryRepository.save(category);
			return Optional.of(categoryMapper.toResponseDTO(updatedCategory));
		}
		return Optional.empty();
	}

	/**
	 * Eliminar una categoría por su ID
	 * 
	 * @param id ID de la categoría a eliminar
	 * 
	 * @return {@link Optional<CategoryResponseDTO>} con la categoría eliminada o
	 *         vacío si no se encuentra
	 */
	public Optional<CategoryResponseDTO> deleteCategory(int id) {
		Optional<CategoryEntity> deletedCategory = categoryRepository.findById(id);
		if (deletedCategory.isEmpty()) {
			return Optional.empty();
		}
		categoryRepository.deleteById(id);
		return Optional.of(categoryMapper.toResponseDTO(deletedCategory.get()));
	}
}
