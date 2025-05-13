package birt.smoreno.inventarioAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import birt.smoreno.inventarioAPI.entities.CategoryEntity;

/**
 * {@code CategoryRepository} es una interfaz que extiende {@link JpaRepository}
 * para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la
 * entidad {@link CategoryEntity}.
 * <p>
 * Al extender {@code JpaRepository}, {@code CategoryRepository} hereda los
 * métodos básicos para manipular los datos de las categorías en la base de
 * datos, como {@code save}, {@code findById}, {@code deleteById}, y otros.
 * </p>
 * <p>
 * Este repositorio permite acceder, modificar y eliminar registros relacionados
 * con categorías dentro de la base de datos, y puede ser utilizado para
 * implementar la lógica de negocio relacionada con las categorías de productos
 * u otros elementos en la aplicación.
 * </p>
 * 
 * @see {@link JpaRepository}
 */
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}
