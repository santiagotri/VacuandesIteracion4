package uniandes.isis2304.vacuandes.persistencia;

import java.sql.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.vacuandes.negocio.PlanDeVacunacion;
import uniandes.isis2304.vacuandes.negocio.PuntoVacunacion;

public class SQLPuntoVacunacion {
	
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaVacuandes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaVacuandes pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLPuntoVacunacion (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarPuntoVacunacion(PersistenceManager pm, String localizacion, int capacidad_de_atencion_simultanea, String infraestructura_para_dosis, int cantidad_de_vacunas_enviables, int cantidad_de_vacunas_actuales, String tipo_punto_vacunacion, String administrador, long oficina_regional_eps)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPuntoVacunacion() + "(localizacion, capacidad_de_atencion_simultanea, infraestructura_para_dosis, cantidad_vacunas_enviables, cantidad_vacunas_actuales, tipo_punto_vacunacion, administrador, oficina_regional_eps) values (?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(localizacion, capacidad_de_atencion_simultanea, infraestructura_para_dosis, cantidad_de_vacunas_enviables, cantidad_de_vacunas_actuales, tipo_punto_vacunacion, administrador, oficina_regional_eps);
		return (long) q.executeUnique(); 
	}

	public long eliminarTodosLosPuntosVacunacion(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPuntoVacunacion());
		return (long) q.executeUnique();
	}
	
	public long eliminarPuntoVacunacionPorId(PersistenceManager pm, long id_punto_vacunacion)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPuntoVacunacion() + " WHERE id_punto_vacunacion = ?");
        q.setParameters(id_punto_vacunacion);
        return (long) q.executeUnique();
	}
	
	public long eliminarPuntoVacunacionPorLocalizacion(PersistenceManager pm, long localizacion)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPuntoVacunacion() + " WHERE localizacion = ?");
        q.setParameters(localizacion);
        return (long) q.executeUnique();
	}
	
	public List<PuntoVacunacion> darListPuntoVacunacion(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT id_Punto_Vacunacion,LOCALIZACION,CAPACIDAD_DE_ATENCION_SIMULTANEA,INFRAESTRUCTURA_PARA_DOSIS,CANTIDAD_VACUNAS_ENVIABLES,CANTIDAD_VACUNAS_ACTUALES,TIPO_PUNTO_VACUNACION,ADMINISTRADOR,OFICINA_REGIONAL_EPS FROM " + pp.darTablaPuntoVacunacion());
		q.setResultClass(PuntoVacunacion.class);
		List<PuntoVacunacion> resp = q.executeList();
		return resp;
	}

	public List<PuntoVacunacion> darListPuntoVacunacionDeLaRegion(PersistenceManager pm, String region) {
		Query q = pm.newQuery(SQL, "\n"
				+ "SELECT ID_PUNTO_VACUNACION, LOCALIZACION, CAPACIDAD_DE_ATENCION_SIMULTANEA, \n"
				+ "INFRAESTRUCTURA_PARA_DOSIS, CANTIDAD_VACUNAS_ENVIABLES, punto.CANTIDAD_VACUNAS_ACTUALES, punto.TIPO_PUNTO_VACUNACION, punto.ADMINISTRADOR, OFICINA_REGIONAL_EPS \n"
				+ "FROM PUNTO_VACUNACION punto INNER JOIN OFICINA_REGIONAL_EPS oficina ON punto.oficina_regional_eps = oficina.id_oficina \n"
				+ "WHERE oficina.region = ?");
		q.setParameters(region);
		q.setResultClass(PuntoVacunacion.class);
		List<PuntoVacunacion> resp = q.executeList();
		return resp;
	}

	public PuntoVacunacion darPuntoPorId(PersistenceManager pm, long id_punto_vacunacion) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPuntoVacunacion() + " WHERE id_punto_vacunacion = ?");
		q.setResultClass(PuntoVacunacion.class);
		q.setParameters(id_punto_vacunacion); 
		return (PuntoVacunacion) q.executeUnique();
	}

	public long disminuirVacunasDisponibles(PersistenceManager pm, long id_punto_vacunacion) {
		Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaPuntoVacunacion() + " SET CANTIDAD_VACUNAS_ACTUALES= CANTIDAD_VACUNAS_ACTUALES-1 WHERE id_punto_vacunacion = ?");
		q.setParameters(id_punto_vacunacion);
		return (long) q.executeUnique();
	}
	
}
