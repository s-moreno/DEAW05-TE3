package birt.smoreno.inventarioAPI.utils;

/**
 * {@code AppConstants} es una clase utilitaria que centraliza todas las
 * constantes estáticas utilizadas a lo largo de la aplicación.
 * <p>
 * Su propósito es mejorar la mantenibilidad del código y evitar la repetición
 * de literales, especialmente en aspectos como respuestas estándar de API,
 * mensajes comunes, rutas, y otros valores fijos.
 * </p>
 * <p>
 * Esta clase no debe ser instanciada, ya que su única función es almacenar
 * constantes.
 * </p>
 */
public class AppConstants {
	/** Representa el estado de éxito (success) en una respuesta API. */
	public static final String STATUS_SUCCESS = "success";
	/** Representa el estado de error en una respuesta API. */
	public static final String STATUS_ERROR = "error";

	/**
	 * Constructor privado para evitar la instanciación de la clase
	 * {@code AppConstants}.
	 * <p>
	 * Lanzar una excepción en el constructor es una técnica común para reforzar la
	 * intención de que esta clase no debe ser utilizada para crear objetos.
	 * </p>
	 *
	 * @throws UnsupportedOperationException siempre que se intente instanciar esta
	 *                                       clase.
	 */
	private AppConstants() {
		// Previene la creación de instancias de esta clase
		throw new UnsupportedOperationException("No se puede instanciar la clase AppConstants.");
	}
}
