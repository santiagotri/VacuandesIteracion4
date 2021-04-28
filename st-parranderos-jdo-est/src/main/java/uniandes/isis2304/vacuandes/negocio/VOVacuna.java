package uniandes.isis2304.vacuandes.negocio;

public interface VOVacuna {
	/**
	 * @return the idVacuna
	 */
	public long getId_Vacuna() ;
	/**
	 * @return the condicionPreservacion
	 */
	public String getCondicion_Preservacion() ;
	/**
	 * @return the puntoVacunacion
	 */
	public long getPunto_Vacunacion();
	/**
	 * @return the planDeVacunacion
	 */
	public long getPlan_De_Vacunacion();
	/**
	 * @return the oficinaRegional
	 */
	public long getOficina_Regional() ;
	/**
	 * @return the utilizada
	 */
	public int getUtilizada() ;
	
	@Override
	public String toString();
}
