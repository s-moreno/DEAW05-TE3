package birt.smoreno.inventarioAPI.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import birt.smoreno.inventarioAPI.entities.ProductEntity;

/**
 * {@code ProductRepository} es una interfaz que extiende {@link JpaRepository}
 * para realizar operaciones CRUD en la entidad {@link ProductEntity}.
 * <p>
 * Esta interfaz proporciona métodos para acceder, manipular y consultar los
 * datos de los productos almacenados en la base de datos. Al extender
 * {@code JpaRepository}, hereda operaciones comunes como {@code save},
 * {@code findById}, {@code deleteById}, entre otras.
 * </p>
 * <p>
 * Además, incluye una consulta personalizada usando JPQL (Java Persistence
 * Query Language) para obtener productos cuyo stock actual es inferior al stock
 * mínimo, lo cual puede ser útil para mantener un control sobre los productos
 * con bajo inventario.
 * </p>
 * * @see {@link JpaRepository}
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

	/**
	 * Consulta personalizada para obtener productos cuyo stock actual es inferior
	 * al stock mínimo establecido.
	 * <p>
	 * Esta consulta utiliza JPQL (Java Persistence Query Language) para seleccionar
	 * todos los productos de la entidad {@link ProductEntity} donde el stock actual
	 * es menor que el stock mínimo.
	 * </p>
	 * 
	 * <p>
	 * La misma consulta en SQL nativo sería: <code>
	 * &#064;Query(value = "SELECT * FROM products WHERE stock_actual < stock_minimo", nativeQuery = true);
	 * </code>
	 * </p>
	 * 
	 * @return Lista de {@link ProductEntity} con stock bajo.
	 */
	@Query(value = "SELECT p FROM ProductEntity p WHERE p.currentStock < p.minStock")
	List<ProductEntity> findProductUnderStock();
}
