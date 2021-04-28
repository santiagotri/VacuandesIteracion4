package uniandes.isis2304.vacuandes.negocio;

public class Usuario implements VOUsuario {

	private String username;
	private String contrasena;
	private String correo;
	private long plan_de_vacunacion;
	private long ciudadano;

	public Usuario() {
		this.username = "";
		this.contrasena = "";
		this.correo = "";
		this.plan_de_vacunacion = 0;
		this.ciudadano = 0;
	}
	
	/**
	 * @param username
	 * @param contrasena
	 * @param correo
	 * @param plan_de_vacunacion
	 * @param ciudadano
	 */
	public Usuario(String username, String contrasena, String correo, long plan_De_Vacunacion, long ciudadano) {
		this.username = username;
		this.contrasena = contrasena;
		this.correo = correo;
		this.plan_de_vacunacion = plan_De_Vacunacion;
		this.ciudadano = ciudadano;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the contrasena
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @return the plan_de_vacunacion
	 */
	public long getPlan_de_vacunacion() {
		return plan_de_vacunacion;
	}

	/**
	 * @return the ciudadano
	 */
	public long getCiudadano() {
		return ciudadano;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param contrasena the contrasena to set
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * @param plan_de_vacunacion the plan_de_vacunacion to set
	 */
	public void setPlan_de_vacunacion(long plan_de_vacunacion) {
		this.plan_de_vacunacion = plan_de_vacunacion;
	}

	/**
	 * @param ciudadano the ciudadano to set
	 */
	public void setCiudadano(long ciudadano) {
		this.ciudadano = ciudadano;
	}

	@Override
	public String toString() {
		return "Usuario [username=" + username + ", contrasena=" + contrasena + ", correo=" + correo
				+ ", plan_de_vacunacion=" + plan_de_vacunacion + ", ciudadano=" + ciudadano + "]";
	}

}
