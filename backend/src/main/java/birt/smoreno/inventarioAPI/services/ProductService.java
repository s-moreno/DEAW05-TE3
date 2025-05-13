package birt.smoreno.inventarioAPI.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import birt.smoreno.inventarioAPI.dto.ProductResponseDTO;
import birt.smoreno.inventarioAPI.entities.ProductEntity;
import birt.smoreno.inventarioAPI.exceptions.CustomException;
import birt.smoreno.inventarioAPI.mappers.ProductMapper;
import birt.smoreno.inventarioAPI.repositories.ProductRepository;

/**
 * {@code ProductService} es una clase de servicio que gestiona la lógica de
 * negocio relacionada con los productos en la aplicación.
 * <p>
 * Esta clase proporciona métodos para realizar operaciones CRUD (Crear, Leer,
 * Actualizar, Eliminar) sobre los productos. Utiliza {@link ProductRepository}
 * para interactuar con la base de datos y realizar las operaciones CRUD sobre
 * la entidad {@link ProductEntity}.
 * </p>
 * <p>
 * Además, {@code ProductService} hace uso de {@link ProductMapper} para
 * convertir entre las entidades {@link ProductEntity} y los DTOs (Data Transfer
 * Objects) utilizados en la API, como {@link ProductResponseDTO}. Esto permite
 * separar la lógica de acceso a datos de la lógica de presentación.
 * </p>
 * <p>
 * En resumen, {@code ProductService} se encarga de la lógica de negocios para
 * las operaciones relacionadas con los productos y sirve como intermediario
 * entre el controlador y el repositorio, asegurando que los datos sean
 * manejados correctamente antes de ser enviados a la base de datos o al
 * cliente.
 * </p>
 */
@Service
public class ProductService {

	// Instanciar el repositorio de productos para ser utilizado en los métodos de
	// la clase
	private final ProductRepository productRepository;
	// Instanciar el mapper de productos para ser utilizado en los métodos de la
	// clase
	private final ProductMapper productMapper;

	/**
	 * Constructor que inyecta el repositorio de productos y el mapper Se podría
	 * usar @Autowired, pero está desaconsejado.
	 *
	 * @param productRepository Repositorio de productos
	 * @param productMapper     Mapper para convertir entre entidades y DTOs
	 */
	public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
	}

	/**
	 * Obtener todos los productos
	 * 
	 * @return Lista de {@link ProductResponseDTO}
	 */
	public List<ProductResponseDTO> getAllProducts() {
		List<ProductEntity> products = productRepository.findAll();
		return products.stream().map(productMapper::toResponseDTO).toList();
	}

	/**
	 * Obtener un producto por su ID
	 * 
	 * @param id ID del producto a buscar
	 * @return {@link ProductResponseDTO} con los datos del producto
	 * @throws CustomException si el producto no se encuentra
	 */
	public ProductResponseDTO getProductById(int id) throws CustomException {
		Optional<ProductEntity> productOptional = productRepository.findById(id);

		if (productOptional.isPresent()) {
			ProductEntity product = productOptional.get();
			return productMapper.toResponseDTO(product);
		} else {
			throw new CustomException("Producto no encontrado", HttpStatus.NOT_FOUND.value());
		}

	}

	/**
	 * Crear un nuevo producto
	 * 
	 * @param product Producto a crear
	 * @return {@link ProductResponseDTO} con los datos del producto creado
	 */
	public ProductResponseDTO createProduct(ProductEntity product) {
		ProductEntity createdProduct = productRepository.save(product);
		return productMapper.toResponseDTO(createdProduct);
	}

	/**
	 * Actualizar un producto existente
	 * 
	 * @param id      ID del producto a actualizar
	 * @param product Producto con los nuevos datos
	 * @return {@link ProductResponseDTO} con los datos del producto actualizado
	 * @throws CustomException si el producto no se encuentra
	 */
	public ProductResponseDTO updateProduct(int id, ProductEntity product) throws CustomException {
		if (productRepository.existsById(id)) {
			product.setId(id);
			ProductEntity updatedProduct = productRepository.save(product);
			return productMapper.toResponseDTO(updatedProduct);
		} else {
			throw new CustomException("Producto no encontrado", HttpStatus.NOT_FOUND.value());
		}
	}

	/**
	 * Eliminar un producto por su ID
	 * 
	 * @param id ID del producto a eliminar
	 * @return {@link ProductResponseDTO} con los datos del producto eliminado
	 * @throws CustomException si el producto no se encuentra
	 */
	public ProductResponseDTO deleteProduct(int id) throws CustomException {
		Optional<ProductEntity> productOptional = productRepository.findById(id);

		if (productOptional.isPresent()) {
			ProductEntity product = productOptional.get();
			productRepository.delete(product);
			return productMapper.toResponseDTO(product);
		} else {
			throw new CustomException("Producto no encontrado", HttpStatus.NOT_FOUND.value());
		}
	}

	/**
	 * Obtener productos con stock bajo
	 * 
	 * @return Lista de {@link ProductResponseDTO} con productos con stock bajo
	 */
	public List<ProductResponseDTO> getProductsUnderStock() {
		return productRepository.findProductUnderStock().stream().map(productMapper::toResponseDTO).toList();
	}

}
