package uniandes.isis2304.vacuandes.negocio;

public class Trabajador implements VOTrabajador{
	
	private long cedula;
	private String trabajo;
	private int administrador_Vacuandes;
	private Long punto_vacunacion;
	
	
	public Trabajador() {
		this.cedula = 0;
		this.trabajo = "";
		this.administrador_Vacuandes = 0;
		this.punto_vacunacion = Long.parseLong("0");
	}


	/**
	 * @param cedula
	 * @param trabajo
	 * @param administrador_Vacuandes
	 * @param punto_vacunacion
	 */
	public Trabajador(long cedula, String trabajo, int administrador_Vacuandes, Long punto_vacunacion) {
		super();
		this.cedula = cedula;
		this.trabajo = trabajo;
		this.administrador_Vacuandes = administrador_Vacuandes;
		this.punto_vacunacion = punto_vacunacion;
	}


	/**
	 * @return the cedula
	 */
	public long getCedula() {
		return cedula;
	}


	/**
	 * @return the trabajo
	 */
	public String getTrabajo() {
		return trabajo;
	}


	/**
	 * @return the administrador_Vacuandes
	 */
	public int getAdministrador_Vacuandes() {
		return administrador_Vacuandes;
	}


	/**
	 * @return the punto_vacunacion
	 */
	public Long getPunto_vacunacion() {
		return punto_vacunacion;
	}


	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(long cedula) {
		this.cedula = cedula;
	}


	/**
	 * @param trabajo the trabajo to set
	 */
	public void setTrabajo(String trabajo) {
		this.trabajo = trabajo;
	}


	/**
	 * @param administrador_Vacuandes the administrador_Vacuandes to set
	 */
	public void setAdministrador_Vacuandes(int administrador_Vacuandes) {
		this.administrador_Vacuandes = administrador_Vacuandes;
	}


	/**
	 * @param punto_vacunacion the punto_vacunacion to set
	 */
	public void setPunto_vacunacion(Long punto_vacunacion) {
		this.punto_vacunacion = punto_vacunacion;
	}


	@Override
	public String toString() {
		return "Trabajador [cedula=" + cedula + ", trabajo=" + trabajo + ", administrador_Vacuandes="
				+ administrador_Vacuandes + ", punto_vacunacion=" + punto_vacunacion + "]";
	}
	
	
	
	
}
