package uniandes.isis2304.vacuandes.negocio;

public class Condicion implements VOCondicion{
	
	private String condiciones;
	private int etapa;
	
	public Condicion() {
		this.condiciones = "";
		this.etapa = 0;
	}
	/**
	 * @param condiciones
	 * @param etapa
	 */
	public Condicion(String condiciones, int etapa) {
		this.condiciones = condiciones;
		this.etapa = etapa;
	}
	/**
	 * @return the condiciones
	 */
	public String getCondiciones() {
		return condiciones;
	}
	/**
	 * @param condiciones the condiciones to set
	 */
	public void setCondiciones(String condiciones) {
		this.condiciones = condiciones;
	}
	/**
	 * @return the etapa
	 */
	public int getEtapa() {
		return etapa;
	}
	/**
	 * @param etapa the etapa to set
	 */
	public void setEtapa(int etapa) {
		this.etapa = etapa;
	}
	@Override
	public String toString() {
		return "Condicion [condiciones=" + condiciones + ", etapa=" + etapa + "]";
	}
	
	

}
