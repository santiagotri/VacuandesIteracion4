package uniandes.isis2304.vacuandes.negocio;

public interface VOOficinaRegionalEPS {

	/**
	 * @return the id_oficina
	 */
	public long getId_oficina();
	/**
	 * @return the region
	 */
	public String getRegion() ;
	/**
	 * @return the username
	 */
	public String getAdministrador() ;
	/**
	 * @return the cantidad_Vacunas_Actuales
	 */
	public int getCantidad_Vacunas_Actuales();
	/**
	 * @return the plan_De_Vacunacion
	 */
	public long getPlan_De_Vacunacion();
}
