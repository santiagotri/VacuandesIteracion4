package uniandes.isis2304.vacuandes.negocio;

import java.util.Date;

public class Cita implements VOCita {
	
	private long id_cita;
	private Date fecha;
	private long ciudadano;
	private long punto_Vacunacion;
	private long vacuna;
	private int hora_cita;

	public Cita() {
		this.id_cita = 0;
		this.fecha = new Date(0);
		this.ciudadano = 0;
		this.punto_Vacunacion = 0;
		this.vacuna = 0;
		this.hora_cita = 0; 
	}
	
	/**
	 * @param idCita
	 * @param fecha
	 * @param ciudadano
	 * @param punto_Vacunacion
	 * @param vacuna
	 */
	public Cita(long id_cita, Date fecha, long ciudadano, long punto_Vacunacion, long vacuna, int hora_cita) {
		this.id_cita = id_cita;
		this.fecha = fecha;
		this.ciudadano = ciudadano;
		this.punto_Vacunacion = punto_Vacunacion;
		this.vacuna = vacuna;
		this.hora_cita = hora_cita; 
	}

	/**
	 * @return the id_cita
	 */
	public long getId_cita() {
		return id_cita;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @return the ciudadano
	 */
	public long getCiudadano() {
		return ciudadano;
	}

	/**
	 * @return the punto_Vacunacion
	 */
	public long getPunto_Vacunacion() {
		return punto_Vacunacion;
	}

	/**
	 * @return the vacuna
	 */
	public long getVacuna() {
		return vacuna;
	}

	/**
	 * @return the hora_cita
	 */
	public int getHora_cita() {
		return hora_cita;
	}

	/**
	 * @param id_cita the id_cita to set
	 */
	public void setId_cita(long id_cita) {
		this.id_cita = id_cita;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @param ciudadano the ciudadano to set
	 */
	public void setCiudadano(long ciudadano) {
		this.ciudadano = ciudadano;
	}

	/**
	 * @param punto_Vacunacion the punto_Vacunacion to set
	 */
	public void setPunto_Vacunacion(long punto_Vacunacion) {
		this.punto_Vacunacion = punto_Vacunacion;
	}

	/**
	 * @param vacuna the vacuna to set
	 */
	public void setVacuna(long vacuna) {
		this.vacuna = vacuna;
	}

	/**
	 * @param hora_cita the hora_cita to set
	 */
	public void setHora_cita(int hora_cita) {
		this.hora_cita = hora_cita;
	}

	@Override
	public String toString() {
		return "Cita [id_cita=" + id_cita + ", fecha=" + fecha + ", ciudadano=" + ciudadano + ", punto_Vacunacion="
				+ punto_Vacunacion + ", vacuna=" + vacuna + ", hora_cita=" + hora_cita + "]";
	}
	
	
}
