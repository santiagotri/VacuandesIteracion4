package uniandes.isis2304.vacuandes.negocio;

public class ListCondicionesCiudadano implements VOListCondicionesCiudadano {
	public long ciudadano;
	public String condicion;
	public ListCondicionesCiudadano() {
		this.ciudadano = 0;
		this.condicion = "";
	}
	/**
	 * @param ciudadano
	 * @param condicion
	 */
	public ListCondicionesCiudadano(long ciudadano, String condicion) {
		this.ciudadano = ciudadano;
		this.condicion = condicion;
	}
	/**
	 * @return the ciudadano
	 */
	public long getCiudadano() {
		return ciudadano;
	}
	/**
	 * @return the condicion
	 */
	public String getCondicion() {
		return condicion;
	}
	/**
	 * @param ciudadano the ciudadano to set
	 */
	public void setCiudadano(long ciudadano) {
		this.ciudadano = ciudadano;
	}
	/**
	 * @param condicion the condicion to set
	 */
	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
	@Override
	public String toString() {
		return "ListCondicionesCiudadano [ciudadano=" + ciudadano + ", condicion=" + condicion + "]";
	}
	
	
}
