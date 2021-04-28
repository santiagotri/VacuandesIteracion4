package uniandes.isis2304.vacuandes.negocio;

public interface VOUsuario {
	/**
	 * @return the username
	 */
	public String getUsername();
	/**
	 * @return the contrasena
	 */
	public String getContrasena() ;
	/**
	 * @return the correo
	 */
	public String getCorreo() ;
	/**
	 * @return the plan_de_vacunacion
	 */
	public long getPlan_de_vacunacion();
	/**
	 * @return the ciudadano
	 */
	public long getCiudadano();
	
	@Override
	public String toString();
}
