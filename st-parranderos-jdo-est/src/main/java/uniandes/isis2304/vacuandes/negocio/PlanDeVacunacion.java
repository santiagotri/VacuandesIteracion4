package uniandes.isis2304.vacuandes.negocio;

import java.util.Date;

public class PlanDeVacunacion implements VOPlanDeVacunacion {

	private long id_plan_de_vacunacion; 
	private String nombre; 
	private String descripcion; 
	private Date fecha_Actualizacion;
	
	public PlanDeVacunacion() {
		this.id_plan_de_vacunacion = 0;
		this.nombre = "";
		this.descripcion = "";
		this.fecha_Actualizacion = new Date(0); 
	}
	
	public PlanDeVacunacion(long id_plan_de_vacunacion, String nombre, String descripcion, Date fecha_Actualizacion)
	{
		this.id_plan_de_vacunacion = id_plan_de_vacunacion;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fecha_Actualizacion = fecha_Actualizacion; 
	}
	
	
	/**
	 * @return the id_plan_de_vacunacion
	 */
	public long getId_plan_de_vacunacion() {
		return id_plan_de_vacunacion;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @return the fecha_Actualizacion
	 */
	public Date getFecha_Actualizacion() {
		return fecha_Actualizacion;
	}

	/**
	 * @param id_plan_de_vacunacion the id_plan_de_vacunacion to set
	 */
	public void setId_plan_de_vacunacion(long id_plan_de_vacunacion) {
		this.id_plan_de_vacunacion = id_plan_de_vacunacion;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @param fecha_Actualizacion the fecha_Actualizacion to set
	 */
	public void setFecha_Actualizacion(Date fecha_Actualizacion) {
		this.fecha_Actualizacion = fecha_Actualizacion;
	}

	@Override
	public String toString() {
		return "PlanDeVacunacion [id_plan_de_vacunacion=" + id_plan_de_vacunacion + ", nombre=" + nombre + ", descripcion="
				+ descripcion + ", fecha_Actualizacion=" + fecha_Actualizacion + "]";
	}
	
}
