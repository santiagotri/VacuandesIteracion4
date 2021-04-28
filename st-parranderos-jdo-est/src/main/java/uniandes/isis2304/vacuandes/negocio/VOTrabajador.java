package uniandes.isis2304.vacuandes.negocio;

public interface VOTrabajador {
	/**
	 * @return the cedula
	 */
	public long getCedula();


	/**
	 * @return the trabajo
	 */
	public String getTrabajo() ;


	/**
	 * @return the administrador_Vacuandes
	 */
	public int getAdministrador_Vacuandes();


	/**
	 * @return the punto_vacunacion
	 */
	public Long getPunto_vacunacion() ;
	
	@Override
	public String toString();
	
}
