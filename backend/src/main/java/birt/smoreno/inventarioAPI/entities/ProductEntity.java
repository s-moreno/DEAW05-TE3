package birt.smoreno.inventarioAPI.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code ProductEntity} es una clase que representa la entidad de producto en
 * la base de datos.
 * <p>
 * Esta clase contiene cinco propiedades principales: {@code id},
 * {@code nombre}, {@code stock_actual}, {@code stock_minimo} y
 * {@code id_categoria}. Estas propiedades representan los atributos de un
 * producto en el sistema.
 * </p>
 */
@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

	@Id
	@Column(name = "id_producto")
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private int id;

	@Column(name = "nombre", nullable = false)
	private String name;

	@Column(name = "stock_actual", nullable = false)
	private int currentStock;

	@Column(name = "stock_minimo", nullable = false)
	private int minStock;

	@Column(name = "created_at", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	// Relaciones. Muchos productos pertenecen a una categoria
	@ManyToOne
	@JoinColumn(name = "id_categoria", nullable = false)
	private CategoryEntity category;
}
