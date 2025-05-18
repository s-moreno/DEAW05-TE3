package birt.smoreno.inventarioAPI.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import birt.smoreno.inventarioAPI.dto.ApiResponseDTO;
import birt.smoreno.inventarioAPI.dto.CategoryRequestDTO;
import birt.smoreno.inventarioAPI.dto.CategoryResponseDTO;
import birt.smoreno.inventarioAPI.entities.CategoryEntity;
import birt.smoreno.inventarioAPI.mappers.CategoryMapper;
import birt.smoreno.inventarioAPI.services.CategoryService;
import birt.smoreno.inventarioAPI.utils.AppConstants;
import jakarta.validation.Valid;

/**
 * {@code CategoryController} es un controlador REST que gestiona las
 * operaciones CRUD para la entidad {@link CategoryEntity}.
 * <p>
 * Proporciona endpoints para crear, leer, actualizar y eliminar categorías.
 * </p>
 * <ul>
 * <li>{@link #getAllCategories()}: Obtiene todas las categorías.</li>
 * <li>{@link #getCategoryById(int)}: Obtiene una categoría por su ID.</li>
 * <li>{@link #createCategory(CategoryRequestDTO)}: Crea una nueva
 * categoría.</li>
 * <li>{@link #updateCategory(int, CategoryRequestDTO)}: Actualiza una categoría
 * existente.</li>
 * <li>{@link #deleteCategory(int)}: Elimina una categoría por su ID.</li>
 * </ul>
 */
@RestController
@CrossOrigin
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryService categoryService;
	private final CategoryMapper categoryMapper;

	public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
		this.categoryService = categoryService;
		this.categoryMapper = categoryMapper;
	}

	// CRUD básico: Create, Read, Update, Delete

	/**
	 * Obtiene todas las categorías.
	 * 
	 * @return ResponseEntity de {@link ApiResponseDTO} con la lista de categorías.
	 */
	@GetMapping
	public ResponseEntity<ApiResponseDTO<List<CategoryResponseDTO>>> getAllCategories() {
		// Obtener todas las categorías
		List<CategoryResponseDTO> categories = categoryService.getAllCategories();
		// Devolver la lista de categorías con el estado 200 (OK)
		return ResponseEntity.ok(new ApiResponseDTO<>(AppConstants.STATUS_SUCCESS, HttpStatus.OK.value(),
				"Categorías obtenidas correctamente", categories));
	}

	/**
	 * Obtiene una categoría por su ID.
	 * 
	 * @param id int ID de la categoría a buscar.
	 * @return ResponseEntity de {@link ApiResponseDTO} con la categoría encontrada.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> getCategoryById(@PathVariable int id) {
		// Obtener la categoría por su ID
		Optional<CategoryResponseDTO> category = categoryService.getCategoryById(id);
		if (category.isPresent()) {
			// Devolver la categoría encontrada con el estado 200 (OK)
			return ResponseEntity.ok(new ApiResponseDTO<>(AppConstants.STATUS_SUCCESS, HttpStatus.OK.value(),
					"Categoría encontrada", category.get()));
		} else {
			// Devolver un estado 404 (NOT FOUND) si no se encuentra la categoría
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDTO<>(AppConstants.STATUS_ERROR,
					HttpStatus.NOT_FOUND.value(), "Categoría no encontrada", null));
		}
	}

	/**
	 * Crea una nueva categoría.
	 * 
	 * @param {@link categoryRequestDTO} DTO con los datos de la categoría a crear.
	 * @return ResponseEntity de {@link ApiResponseDTO} con la categoría creada.
	 */
	@PostMapping
	public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> createCategory(
			@Valid @RequestBody CategoryRequestDTO categoryRequest) {
		// Convertir CategoryRequestDTO a CategoryEntity
		CategoryEntity category = categoryMapper.toEntity(categoryRequest);
		// Crear la categoría
		CategoryResponseDTO createdCategory = categoryService.createCategory(category);
		// Devolver la respuesta con el estado 201 (CREATED)
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(AppConstants.STATUS_SUCCESS,
				HttpStatus.CREATED.value(), "Categoría creada correctamente", createdCategory));
	}

	/**
	 * Actualiza una categoría existente.
	 * 
	 * @param id     int ID de la categoría a actualizar.
	 * @param {@link CategoryRequestDTO} DTO con los datos de la categoría a
	 *               actualizar.
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> updateCategory(@Valid @PathVariable int id,
			@RequestBody CategoryRequestDTO categoryRequest) {
		// Convertir CategoryRequestDTO a CategoryEntity
		CategoryEntity category = categoryMapper.toEntity(categoryRequest);
		// Actualizar la categoría
		Optional<CategoryResponseDTO> updatedCategory = categoryService.updateCategory(id, category);

		if (updatedCategory.isPresent()) {
			// Devolver la categoría actualizada con el estado 200 (OK)
			return ResponseEntity.ok(new ApiResponseDTO<>(AppConstants.STATUS_SUCCESS, HttpStatus.OK.value(),
					"Categoría actualizada correctamente", updatedCategory.get()));
		} else {
			// Devolver un estado 404 (NOT FOUND) si no se encuentra la categoría
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDTO<>(AppConstants.STATUS_ERROR,
					HttpStatus.NOT_FOUND.value(), "Categoría no encontrada", null));
		}
	}

	/**
	 * Elimina una categoría por su ID.
	 * 
	 * @param id int ID de la categoría a eliminar.
	 * @return ResponseEntity de {@link ApiResponseDTO} con la categoría eliminada.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> deleteCategory(@PathVariable int id) {

		try {
			// Eliminar la categoría por su ID
			Optional<CategoryResponseDTO> deletedCategory = categoryService.deleteCategory(id);

			if (deletedCategory.isPresent()) {
				// Devolver la categoría eliminada con el estado 200 (OK)
				return ResponseEntity.ok(new ApiResponseDTO<>(AppConstants.STATUS_SUCCESS, HttpStatus.OK.value(),
						"Categoría eliminada correctamente", deletedCategory.get()));
			} else {
				// Devolver un estado 404 (NOT FOUND) si no se encuentra la categoría
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDTO<>(AppConstants.STATUS_ERROR,
						HttpStatus.NOT_FOUND.value(), "Categoría no encontrada", null));
			}

		} catch (IllegalStateException e) {
			// Manejo del caso: la categoría tiene productos asociados
			return ResponseEntity.status(HttpStatus.CONFLICT).body(
					new ApiResponseDTO<>(AppConstants.STATUS_ERROR, HttpStatus.CONFLICT.value(), e.getMessage(), null));
		}

	}
}
