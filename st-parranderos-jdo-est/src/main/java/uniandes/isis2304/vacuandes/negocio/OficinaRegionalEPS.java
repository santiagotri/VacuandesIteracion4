package uniandes.isis2304.vacuandes.negocio;

public class OficinaRegionalEPS implements VOOficinaRegionalEPS{
	
	private long id_oficina;
	private String region;
	private String administrador;
	private int cantidad_Vacunas_Actuales;
	private long plan_De_Vacunacion;
	public OficinaRegionalEPS() {
		this.id_oficina = 0;
		this.region = "";
		this.administrador =  "";
		this.cantidad_Vacunas_Actuales = 0;
		this.plan_De_Vacunacion = 0;
	}
	/**
	 * @param id_oficina
	 * @param region
	 * @param administrador
	 * @param cantidad_Vacunas_Actuales
	 * @param plan_De_Vacunacion
	 */
	public OficinaRegionalEPS(long id_oficina, String region, String administrador, int cantidad_Vacunas_Actuales,
			long plan_De_Vacunacion) {
		this.id_oficina = id_oficina;
		this.region = region;
		this.administrador = administrador;
		this.cantidad_Vacunas_Actuales = cantidad_Vacunas_Actuales;
		this.plan_De_Vacunacion = plan_De_Vacunacion;
	}
	/**
	 * @return the id_oficina
	 */
	public long getId_oficina() {
		return id_oficina;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @return the administrador
	 */
	public String getAdministrador() {
		return administrador;
	}
	/**
	 * @return the cantidad_Vacunas_Actuales
	 */
	public int getCantidad_Vacunas_Actuales() {
		return cantidad_Vacunas_Actuales;
	}
	/**
	 * @return the plan_De_Vacunacion
	 */
	public long getPlan_De_Vacunacion() {
		return plan_De_Vacunacion;
	}
	/**
	 * @param id_oficina the id_oficina to set
	 */
	public void setId_oficina(long id_oficina) {
		this.id_oficina = id_oficina;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	/**
	 * @param administrador the administrador to set
	 */
	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}
	/**
	 * @param cantidad_Vacunas_Actuales the cantidad_Vacunas_Actuales to set
	 */
	public void setCantidad_Vacunas_Actuales(int cantidad_Vacunas_Actuales) {
		this.cantidad_Vacunas_Actuales = cantidad_Vacunas_Actuales;
	}
	/**
	 * @param plan_De_Vacunacion the plan_De_Vacunacion to set
	 */
	public void setPlan_De_Vacunacion(long plan_De_Vacunacion) {
		this.plan_De_Vacunacion = plan_De_Vacunacion;
	}
	@Override
	public String toString() {
		return "OficinaRegionalEPS [id_oficina=" + id_oficina + ", region=" + region + ", administrador=" + administrador
				+ ", cantidad_Vacunas_Actuales=" + cantidad_Vacunas_Actuales + ", plan_De_Vacunacion="
				+ plan_De_Vacunacion + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
