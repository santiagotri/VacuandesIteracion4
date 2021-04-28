package uniandes.isis2304.vacuandes.negocio;

public class EstadoVacunacion implements VOEstadoVacunacion{
	
	private String estado;

	
	/**
	 * @param estado
	 */
	public EstadoVacunacion(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "EstadoVacunacion [estado=" + estado + "]";
	}
	
	
	

}
