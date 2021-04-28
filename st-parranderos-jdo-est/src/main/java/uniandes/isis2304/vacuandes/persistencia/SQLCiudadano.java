package uniandes.isis2304.vacuandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.vacuandes.negocio.Ciudadano;
import uniandes.isis2304.vacuandes.negocio.Trabajador;

public class SQLCiudadano {
	
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
	public SQLCiudadano (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}

	public long adicionarCiudadano(PersistenceManager pm, long cedula, String nombre_completo, String estado_vacunacion, String region, int desea_ser_vacunado, long plan_de_vacunacion, Long punto_vacunacion, Long oficina_regional_asignada)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCiudadano() + "(cedula, nombre_completo,estado_vacunacion,region,desea_ser_vacunado, plan_de_vacunacion, punto_vacunacion, oficina_regional_asignada) values (?, ? ,? ,? ,? ,? ,? ,?)");
		q.setParameters(cedula, nombre_completo, estado_vacunacion, region, desea_ser_vacunado, plan_de_vacunacion, punto_vacunacion, oficina_regional_asignada);
		return (long) q.executeUnique(); 
	}
	
	public long eliminarTodosLosCiudadanos(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCiudadano());
		return (long) q.executeUnique();	
	}
	
	public long eliminarCiudadanoPorCedula(PersistenceManager pm, long cedula)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCiudadano() + " WHERE cedula = ?");
        q.setParameters(cedula);
        return (long) q.executeUnique();
	}
	
	public List<Ciudadano> darListCiudadanos(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCiudadano());
		q.setResultClass(Ciudadano.class);
		return (List<Ciudadano>) q.execute();
	}
	
	public Ciudadano darCiudadanoPorCedula(PersistenceManager pm, long cedula)
	{
		Query q = pm.newQuery(SQL, "SELECT CEDULA, NOMBRE_COMPLETO, ESTADO_VACUNACION, REGION, DESEA_SER_VACUNADO, PLAN_DE_VACUNACION, PUNTO_VACUNACION, OFICINA_REGIONAL_ASIGNADA FROM " + pp.darTablaCiudadano() + " WHERE cedula = ?");
		q.setResultClass(Ciudadano.class);
		q.setParameters(cedula);
		return (Ciudadano) q.executeUnique();
	}
	
	public long actualizarCiudadanoPorCedula(PersistenceManager pm, long cedula, String nombre_completo, String estado_vacunacion, String region, int desea_ser_vacunado, long plan_de_vacunacion, Long punto_vacunacion, Long oficina_regional_asignada) {
		Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaCiudadano() + " SET cedula= ? , nombre_completo= ?, estado_vacunacion= ?, region= ?, desea_ser_vacunado= ?, plan_de_vacunacion= ?, punto_vacunacion= ?, oficina_regional_asignada= ? WHERE cedula = ?");
		q.setParameters(cedula, nombre_completo, estado_vacunacion, region, desea_ser_vacunado, plan_de_vacunacion, punto_vacunacion, oficina_regional_asignada, cedula);
		return (long) q.executeUnique();
	}
	
	public List<Ciudadano> darCiudadanosPuntoVacunacion(PersistenceManager pm, long punto_vacunacion)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCiudadano() + " WHERE punto_vacunacion = ?");
		q.setResultClass(Ciudadano.class);
		q.setParameters(punto_vacunacion);
		return (List<Ciudadano>) q.executeUnique();
	}
	
}
