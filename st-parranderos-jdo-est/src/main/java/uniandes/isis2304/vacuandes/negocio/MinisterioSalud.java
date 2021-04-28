package uniandes.isis2304.vacuandes.negocio;

public class MinisterioSalud implements VOMinisterioSalud{
	private long id_Ministerio;
	private long plan_De_Vacunacion;
	
	public MinisterioSalud() {
		this.id_Ministerio = 0;
		this.plan_De_Vacunacion = 0;
	}
	/**
	 * @param id_Ministerio
	 * @param plan_De_Vacunacion
	 */
	public MinisterioSalud(long id_Ministerio, long plan_De_Vacunacion) {
		this.id_Ministerio = id_Ministerio;
		this.plan_De_Vacunacion = plan_De_Vacunacion;
	}
	/**
	 * @return the id_Ministerio
	 */
	public long getId_Ministerio() {
		return id_Ministerio;
	}
	/**
	 * @return the plan_De_Vacunacion
	 */
	public long getPlan_De_Vacunacion() {
		return plan_De_Vacunacion;
	}
	/**
	 * @param id_Ministerio the id_Ministerio to set
	 */
	public void setId_Ministerio(long id_Ministerio) {
		this.id_Ministerio = id_Ministerio;
	}
	/**
	 * @param plan_De_Vacunacion the plan_De_Vacunacion to set
	 */
	public void setPlan_De_Vacunacion(long plan_De_Vacunacion) {
		this.plan_De_Vacunacion = plan_De_Vacunacion;
	}
	@Override
	public String toString() {
		return "MinisterioSalud [id_Ministerio=" + id_Ministerio + ", plan_De_Vacunacion=" + plan_De_Vacunacion + "]";
	}
	
	
}
