package birt.smoreno.inventarioAPI.controllers;

import java.util.List;

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
import birt.smoreno.inventarioAPI.dto.ProductRequestDTO;
import birt.smoreno.inventarioAPI.dto.ProductResponseDTO;
import birt.smoreno.inventarioAPI.entities.ProductEntity;
import birt.smoreno.inventarioAPI.exceptions.CustomException;
import birt.smoreno.inventarioAPI.mappers.ProductMapper;
import birt.smoreno.inventarioAPI.services.ProductService;
import birt.smoreno.inventarioAPI.utils.AppConstants;
import jakarta.validation.Valid;

/**
 * {@code ProductController} es un controlador REST que gestiona las operaciones
 * CRUD para la entidad {@link ProductEntity}.
 * <p>
 * Proporciona endpoints para crear, leer, actualizar, eliminar productos y
 * obtener todos los productos que están por debajo de su mínimo.
 * </p>
 * <ul>
 * <li>{@link #getAllProducts()}: Obtiene todos los productos.</li>
 * <li>{@link #getProductById(Long)}: Obtiene un producto por su ID.</li>
 * <li>{@link #createProduct(ProductRequestDTO)}: Crea un nuevo producto.</li>
 * <li>{@link #updateProduct(Long, ProductRequestDTO)}: Actualiza un producto
 * existente.</li>
 * <li>{@link #deleteProduct(Long)}: Elimina un producto por su ID.</li>
 * <li>{@link #getProductsUnderStock()}: Obtiene los productos cuyo stock está
 * por debajo del mínimo.</li>
 * </ul>
 */
@RestController
@CrossOrigin
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;
	private final ProductMapper productMapper;

	public ProductController(ProductService productService, ProductMapper productMapper) {
		this.productService = productService;
		this.productMapper = productMapper;
	}

	// CRUD básico: Create, Read, Update, Delete

	/**
	 * Obtiene todos los productos.
	 * 
	 * @return ResponseEntity de {@link ApiResponseDTO} con la lista de productos.
	 */
	@GetMapping
	public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getAllProducts() {
		List<ProductResponseDTO> products = productService.getAllProducts();
		return ResponseEntity.ok(new ApiResponseDTO<>(AppConstants.STATUS_SUCCESS, HttpStatus.OK.value(),
				"Productos obtenidos correctamente", products));
	}

	/**
	 * Obtiene un producto por su ID.
	 * 
	 * @param id int ID del producto a buscar.
	 * @return ResponseEntity de {@link ApiResponseDTO} con el producto encontrado.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> getProductById(@PathVariable int id) {
		try {
			ProductResponseDTO product = productService.getProductById(id);
			return ResponseEntity.ok(new ApiResponseDTO<>(AppConstants.STATUS_SUCCESS, HttpStatus.OK.value(),
					"Producto encontrado", product));
		} catch (CustomException e) {
			return ResponseEntity.status(e.getHttpStatusCode())
					.body(new ApiResponseDTO<>(AppConstants.STATUS_ERROR, e.getHttpStatusCode(), e.getMessage(), null));
		}
	}

	/**
	 * Crea un nuevo producto.
	 * 
	 * @param {@link ProductRequestDTO} objeto que contiene los datos del producto a
	 *               crear.
	 * @return ResponseEntity de {@link ApiResponseDTO} con el producto creado.
	 */
	@PostMapping
	public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> createProduct(
			@Valid @RequestBody ProductRequestDTO productDTO) {

		// Convertir ProductRequestDTO a ProductEntity
		ProductEntity product = productMapper.toEntity(productDTO);
		// Crear el producto
		ProductResponseDTO createdProduct = productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(AppConstants.STATUS_SUCCESS,
				HttpStatus.CREATED.value(), "Producto creado correctamente", createdProduct));
	}

	/**
	 * Actualiza un producto existente.
	 * 
	 * @param id     int ID del producto a actualizar.
	 * @param {@link ProductRequestDTO} objeto que contiene los datos del producto a
	 *               actualizar.
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> updateProduct(@PathVariable int id,
			@Valid @RequestBody ProductRequestDTO productDTO) {
		try {
			ProductEntity product = productMapper.toEntity(productDTO);
			ProductResponseDTO updatedProduct = productService.updateProduct(id, product);
			return ResponseEntity.ok(new ApiResponseDTO<>(AppConstants.STATUS_SUCCESS, HttpStatus.OK.value(),
					"Producto actualizado correctamente", updatedProduct));
		} catch (CustomException e) {
			return ResponseEntity.status(e.getHttpStatusCode())
					.body(new ApiResponseDTO<>(AppConstants.STATUS_ERROR, e.getHttpStatusCode(), e.getMessage(), null));
		}
	}

	/**
	 * Elimina un producto por su ID.
	 * 
	 * @param id int ID del producto a eliminar.
	 * @return ResponseEntity de {@link ApiResponseDTO} con el producto eliminado.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> deleteProduct(@PathVariable int id) {
		try {
			ProductResponseDTO deletedProduct = productService.deleteProduct(id);
			return ResponseEntity.ok(new ApiResponseDTO<>(AppConstants.STATUS_SUCCESS, HttpStatus.OK.value(),
					"Producto eliminado correctamente", deletedProduct));
		} catch (CustomException e) {
			return ResponseEntity.status(e.getHttpStatusCode())
					.body(new ApiResponseDTO<>(AppConstants.STATUS_ERROR, e.getHttpStatusCode(), e.getMessage(), null));
		}
	}

	/**
	 * Obtiene los productos cuyo stock está por debajo del mínimo.
	 * 
	 * @return ResponseEntity de {@link ApiResponseDTO} con la lista de productos
	 *         que están por debajo del stock mínimo.
	 */
	@GetMapping("/min")
	public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getProductsUnderStock() {
		List<ProductResponseDTO> productsUnderStock = productService.getProductsUnderStock();
		return ResponseEntity.ok(new ApiResponseDTO<>(AppConstants.STATUS_SUCCESS, HttpStatus.OK.value(),
				"Productos que están por debajo de su stock mínimo establecido", productsUnderStock));
	}
}
