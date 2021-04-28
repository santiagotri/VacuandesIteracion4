package uniandes.isis2304.vacuandes.negocio;

public interface VOMinisterioSalud {
	/**
	 * @return the idMinisterio
	 */
	public long getId_Ministerio();
	/**
	 * @return the planDeVacunacion
	 */
	public long getPlan_De_Vacunacion();
	
	@Override
	public String toString();
}
