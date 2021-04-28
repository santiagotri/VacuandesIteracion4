package uniandes.isis2304.vacuandes.negocio;

public class Vacuna implements VOVacuna {

	private long id_Vacuna;
	private String condicion_Preservacion;
	private long punto_Vacunacion;
	private long plan_De_Vacunacion;
	private long oficina_Regional;
	private int utilizada;
	
	/**
	 * @param id_Vacuna
	 * @param condicion_Preservacion
	 * @param punto_Vacunacion
	 * @param plan_De_Vacunacion
	 * @param oficina_Regional
	 * @param utilizada
	 */
	
	public Vacuna() {
		this.id_Vacuna = 0;
		this.condicion_Preservacion = "";
		this.punto_Vacunacion = 0;
		this.plan_De_Vacunacion = 0;
		this.oficina_Regional = 0;
		this.utilizada = 0;
	}
	
	public Vacuna(long id_Vacuna, String condicion_Preservacion, long punto_Vacunacion, long plan_De_Vacunacion,
			long oficina_Regional, int utilizada) {
		this.id_Vacuna = id_Vacuna;
		this.condicion_Preservacion = condicion_Preservacion;
		this.punto_Vacunacion = punto_Vacunacion;
		this.plan_De_Vacunacion = plan_De_Vacunacion;
		this.oficina_Regional = oficina_Regional;
		this.utilizada = utilizada;
	}
	/**
	 * @return the id_Vacuna
	 */
	public long getId_Vacuna() {
		return id_Vacuna;
	}
	/**
	 * @return the condicion_Preservacion
	 */
	public String getCondicion_Preservacion() {
		return condicion_Preservacion;
	}
	/**
	 * @return the punto_Vacunacion
	 */
	public long getPunto_Vacunacion() {
		return punto_Vacunacion;
	}
	/**
	 * @return the plan_De_Vacunacion
	 */
	public long getPlan_De_Vacunacion() {
		return plan_De_Vacunacion;
	}
	/**
	 * @return the oficina_Regional
	 */
	public long getOficina_Regional() {
		return oficina_Regional;
	}
	
	public int getUtilizada() {
		return utilizada;
	}

	/**
	 * @param id_Vacuna the id_Vacuna to set
	 */
	public void setId_Vacuna(long id_Vacuna) {
		this.id_Vacuna = id_Vacuna;
	}
	/**
	 * @param condicion_Preservacion the condicion_Preservacion to set
	 */
	public void setCondicion_Preservacion(String condicion_Preservacion) {
		this.condicion_Preservacion = condicion_Preservacion;
	}
	/**
	 * @param punto_Vacunacion the punto_Vacunacion to set
	 */
	public void setPunto_Vacunacion(long punto_Vacunacion) {
		this.punto_Vacunacion = punto_Vacunacion;
	}
	/**
	 * @param plan_De_Vacunacion the plan_De_Vacunacion to set
	 */
	public void setPlan_De_Vacunacion(long plan_De_Vacunacion) {
		this.plan_De_Vacunacion = plan_De_Vacunacion;
	}
	/**
	 * @param oficina_Regional the oficina_Regional to set
	 */
	public void setOficina_Regional(long oficina_Regional) {
		this.oficina_Regional = oficina_Regional;
	}
	/**
	 * @param utilizada the utilizada to set
	 */
	public void setUtilizada(int utilizada) {
		this.utilizada = utilizada;
	}

	@Override
	public String toString() {
		return "Vacuna [id_Vacuna=" + id_Vacuna + ", condicion_Preservacion=" + condicion_Preservacion
				+ ", punto_Vacunacion=" + punto_Vacunacion + ", plan_De_Vacunacion=" + plan_De_Vacunacion
				+ ", oficina_Regional=" + oficina_Regional + ", utilizada=" + utilizada + "]";
	}
	
	
	
}
