package uniandes.isis2304.vacuandes.negocio;

public class PuntoVacunacion implements VOPuntoVacunacion{

	private long id_Punto_Vacunacion;
	private String localizacion;
	private int capacidad_de_Atencion_Simultanea;
	private String infraestructura_Para_Dosis;
	private int cantidad_Vacunas_Enviables;
	private int cantidad_Vacunas_Actuales;
	private String tipo_Punto_Vacunacion;
	private String administrador;
	private long oficina_regional_eps;
	
	
	public PuntoVacunacion() {
		
		this.id_Punto_Vacunacion = 0;
		this.localizacion = "";
		this.capacidad_de_Atencion_Simultanea = 0;
		this.infraestructura_Para_Dosis = "";
		this.cantidad_Vacunas_Enviables = 0;
		this.cantidad_Vacunas_Actuales = 0;
		this.tipo_Punto_Vacunacion = "";
		this.administrador = "";
		this.oficina_regional_eps = 0;
	}


	/**
	 * @param id_Punto_Vacunacion
	 * @param localizacion
	 * @param capacidad_de_Atencion_Simultanea
	 * @param infraestructura_Para_Dosis
	 * @param cantidad_Vacunas_Enviables
	 * @param cantidad_Vacunas_Actuales
	 * @param tipo_Punto_Vacunacion
	 * @param administrador
	 * @param oficina_regional_eps
	 */
	public PuntoVacunacion(long id_Punto_Vacunacion, String localizacion, int capacidad_de_Atencion_Simultanea,
			String infraestructura_Para_Dosis, int cantidad_Vacunas_Enviables, int cantidad_Vacunas_Actuales,
			String tipo_Punto_Vacunacion, String administrador, long oficina_regional_eps) {
		this.id_Punto_Vacunacion = id_Punto_Vacunacion;
		this.localizacion = localizacion;
		this.capacidad_de_Atencion_Simultanea = capacidad_de_Atencion_Simultanea;
		this.infraestructura_Para_Dosis = infraestructura_Para_Dosis;
		this.cantidad_Vacunas_Enviables = cantidad_Vacunas_Enviables;
		this.cantidad_Vacunas_Actuales = cantidad_Vacunas_Actuales;
		this.tipo_Punto_Vacunacion = tipo_Punto_Vacunacion;
		this.administrador = administrador;
		this.oficina_regional_eps = oficina_regional_eps;
	}


	/**
	 * @return the id_Punto_Vacunacion
	 */
	public long getId_Punto_Vacunacion() {
		return id_Punto_Vacunacion;
	}


	/**
	 * @return the localizacion
	 */
	public String getLocalizacion() {
		return localizacion;
	}


	/**
	 * @return the capacidad_de_Atencion_Simultanea
	 */
	public int getCapacidad_de_Atencion_Simultanea() {
		return capacidad_de_Atencion_Simultanea;
	}


	/**
	 * @return the infraestructura_Para_Dosis
	 */
	public String getInfraestructura_Para_Dosis() {
		return infraestructura_Para_Dosis;
	}


	/**
	 * @return the cantidad_Vacunas_Enviables
	 */
	public int getCantidad_Vacunas_Enviables() {
		return cantidad_Vacunas_Enviables;
	}


	/**
	 * @return the cantidad_Vacunas_Actuales
	 */
	public int getCantidad_Vacunas_Actuales() {
		return cantidad_Vacunas_Actuales;
	}


	/**
	 * @return the tipo_Punto_Vacunacion
	 */
	public String getTipo_Punto_Vacunacion() {
		return tipo_Punto_Vacunacion;
	}


	/**
	 * @return the administrador
	 */
	public String getAdministrador() {
		return administrador;
	}


	/**
	 * @return the oficina_regional_eps
	 */
	public long getOficina_regional_eps() {
		return oficina_regional_eps;
	}


	/**
	 * @param id_Punto_Vacunacion the id_Punto_Vacunacion to set
	 */
	public void setId_Punto_Vacunacion(long id_Punto_Vacunacion) {
		this.id_Punto_Vacunacion = id_Punto_Vacunacion;
	}


	/**
	 * @param localizacion the localizacion to set
	 */
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}


	/**
	 * @param capacidad_de_Atencion_Simultanea the capacidad_de_Atencion_Simultanea to set
	 */
	public void setCapacidad_de_Atencion_Simultanea(int capacidad_de_Atencion_Simultanea) {
		this.capacidad_de_Atencion_Simultanea = capacidad_de_Atencion_Simultanea;
	}


	/**
	 * @param infraestructura_Para_Dosis the infraestructura_Para_Dosis to set
	 */
	public void setInfraestructura_Para_Dosis(String infraestructura_Para_Dosis) {
		this.infraestructura_Para_Dosis = infraestructura_Para_Dosis;
	}


	/**
	 * @param cantidad_Vacunas_Enviables the cantidad_Vacunas_Enviables to set
	 */
	public void setCantidad_Vacunas_Enviables(int cantidad_Vacunas_Enviables) {
		this.cantidad_Vacunas_Enviables = cantidad_Vacunas_Enviables;
	}


	/**
	 * @param cantidad_Vacunas_Actuales the cantidad_Vacunas_Actuales to set
	 */
	public void setCantidad_Vacunas_Actuales(int cantidad_Vacunas_Actuales) {
		this.cantidad_Vacunas_Actuales = cantidad_Vacunas_Actuales;
	}


	/**
	 * @param tipo_Punto_Vacunacion the tipo_Punto_Vacunacion to set
	 */
	public void setTipo_Punto_Vacunacion(String tipo_Punto_Vacunacion) {
		this.tipo_Punto_Vacunacion = tipo_Punto_Vacunacion;
	}


	/**
	 * @param administrador the administrador to set
	 */
	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}


	/**
	 * @param oficina_regional_eps the oficina_regional_eps to set
	 */
	public void setOficina_regional_eps(long oficina_regional_eps) {
		this.oficina_regional_eps = oficina_regional_eps;
	}


	@Override
	public String toString() {
		return "PuntoVacunacion [id_Punto_Vacunacion=" + id_Punto_Vacunacion + ", localizacion=" + localizacion
				+ ", capacidad_de_Atencion_Simultanea=" + capacidad_de_Atencion_Simultanea
				+ ", infraestructura_Para_Dosis=" + infraestructura_Para_Dosis + ", cantidad_Vacunas_Enviables="
				+ cantidad_Vacunas_Enviables + ", cantidad_Vacunas_Actuales=" + cantidad_Vacunas_Actuales
				+ ", tipo_Punto_Vacunacion=" + tipo_Punto_Vacunacion + ", administrador=" + administrador
				+ ", oficina_regional_eps=" + oficina_regional_eps + "]";
	}
	
	
	
}
