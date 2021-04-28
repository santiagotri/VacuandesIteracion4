package uniandes.isis2304.vacuandes.negocio;

import java.util.Date;

/**
 * Intrefaz para los metodos de Plan de vacunación
 * @author j.ramirezb
 *
 */
public interface VOPlanDeVacunacion {

	public long getId_plan_de_vacunacion();
	
	public String getNombre(); 
	
	public String getDescripcion(); 
	
	public Date getFecha_Actualizacion(); 
	
	@Override
	public String toString(); 
	
}
