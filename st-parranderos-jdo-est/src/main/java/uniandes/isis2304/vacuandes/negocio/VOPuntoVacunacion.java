package uniandes.isis2304.vacuandes.negocio;

public interface VOPuntoVacunacion {
	
	/**
	 * @return the idPuntoVacunacion
	 */
	public long getId_Punto_Vacunacion();
	/**
	 * @return the localizacion
	 */
	public String getLocalizacion();
	/**
	 * @return the capacidadAtencionSimultanea
	 */
	public int getCapacidad_de_Atencion_Simultanea();
	
	/**
	 * @return the infraestructuraParaDosis
	 */
	public String getInfraestructura_Para_Dosis() ;
	/**
	 * @return the cantidadVacunasEnviables
	 */
	public int getCantidad_Vacunas_Enviables() ;
	
	/**
	 * @return the cantidadVacunasActuales
	 */
	public int getCantidad_Vacunas_Actuales() ;
	/**
	 * @return the tipoPuntoVacunacion
	 */
	public String getTipo_Punto_Vacunacion();
	/**
	 * @return the administrador
	 */
	public String getAdministrador();
	
	/**
	 * @return the oficina_regional_eps
	 */
	public long getOficina_regional_eps();
	
	@Override
	public String toString();
}
