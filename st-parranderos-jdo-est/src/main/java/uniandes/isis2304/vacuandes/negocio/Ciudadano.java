package uniandes.isis2304.vacuandes.negocio;

public class Ciudadano implements VOCiudadano{
	
	private long cedula; 
	private String nombre_Completo;
	private String estado_vacunacion;
	private String region; 
	private int desea_ser_vacunado; 
	private long plan_De_Vacunacion;
	private Long punto_Vacunacion;
	private Long oficina_Regional_Asignada;
	
	public Ciudadano() {
		this.cedula = 0;
		this.nombre_Completo = "";
		this.estado_vacunacion = "";
		this.region = ""; 
		this.desea_ser_vacunado = 0; 
		this.plan_De_Vacunacion = 0;
		this.punto_Vacunacion = Long.parseLong("0");
		this.oficina_Regional_Asignada = Long.parseLong("0");
	}
	
	
	/**
	 * @param cedula
	 * @param nombre_Completo
	 * @param desea_Ser_Vacunado
	 * @param plan_De_Vacunacion
	 * @param punto_Vacunacion
	 * @param oficina_Regional_Asignada
	 */
	public Ciudadano(long cedula, String nombre_Completo, String estado_vacunacion, String region, int desea_ser_vacunado, long plan_De_Vacunacion,
			Long punto_Vacunacion, Long oficina_Regional_Asignada) {
		this.cedula = cedula;
		this.nombre_Completo = nombre_Completo;
		this.estado_vacunacion = estado_vacunacion;
		this.region = region; 
		this.desea_ser_vacunado = desea_ser_vacunado; 
		this.plan_De_Vacunacion = plan_De_Vacunacion;
		this.punto_Vacunacion = punto_Vacunacion;
		this.oficina_Regional_Asignada = oficina_Regional_Asignada;
	}
	/**
	 * @return the cedula
	 */
	public long getCedula() {
		return cedula;
	}
	/**
	 * @return the nombre_Completo
	 */
	public String getNombre_Completo() {
		return nombre_Completo;
	}
	
	public String getEstado_vacunacion() {
		return estado_vacunacion;
	}


	public String getRegion() {
		return region;
	}


	public int getDesea_ser_vacunado() {
		return desea_ser_vacunado;
	}

	/**
	 * @return the plan_De_Vacunacion
	 */
	public long getPlan_De_Vacunacion() {
		return plan_De_Vacunacion;
	}
	/**
	 * @return the punto_Vacunacion
	 */
	public Long getPunto_Vacunacion() {
		return punto_Vacunacion;
	}
	/**
	 * @return the oficina_Regional_Asignada
	 */
	public Long getOficina_Regional_Asignada() {
		return oficina_Regional_Asignada;
	}
	
	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(long cedula) {
		this.cedula = cedula;
	}
	/**
	 * @param nombre_Completo the nombre_Completo to set
	 */
	public void setNombre_Completo(String nombre_Completo) {
		this.nombre_Completo = nombre_Completo;
	}

	public void setEstado_vacunacion(String estado_vacunacion) {
		this.estado_vacunacion = estado_vacunacion;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public void setDesea_ser_vacunado(int desea_ser_vacunado) {
		this.desea_ser_vacunado = desea_ser_vacunado;
	}
	/**
	 * @param plan_De_Vacunacion the plan_De_Vacunacion to set
	 */
	public void setPlan_De_Vacunacion(long plan_De_Vacunacion) {
		this.plan_De_Vacunacion = plan_De_Vacunacion;
	}
	/**
	 * @param punto_Vacunacion the punto_Vacunacion to set
	 */
	public void setPunto_Vacunacion(Long punto_Vacunacion) {
		this.punto_Vacunacion = punto_Vacunacion;
	}
	/**
	 * @param oficina_Regional_Asignada the oficina_Regional_Asignada to set
	 */
	public void setOficina_Regional_Asignada(Long oficina_Regional_Asignada) {
		this.oficina_Regional_Asignada = oficina_Regional_Asignada;
	}


	@Override
	public String toString() {
		return "Ciudadano [cedula=" + cedula + ", nombre_Completo=" + nombre_Completo + ", estado_vacunacion="
				+ estado_vacunacion + ", region=" + region + ", desea_ser_vacunado=" + desea_ser_vacunado
				+ ", plan_De_Vacunacion=" + plan_De_Vacunacion + ", punto_Vacunacion=" + punto_Vacunacion
				+ ", oficina_Regional_Asignada=" + oficina_Regional_Asignada + "]";
	}
	
	
}
