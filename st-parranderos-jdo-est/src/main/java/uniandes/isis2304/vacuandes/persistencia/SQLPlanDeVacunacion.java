package uniandes.isis2304.vacuandes.persistencia;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Visitan;
import uniandes.isis2304.vacuandes.negocio.PlanDeVacunacion;

public class SQLPlanDeVacunacion {
	
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
	public SQLPlanDeVacunacion (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarPlanDeVacunacion(PersistenceManager pm, String nombre, String descripcion, Date fecha_actualizacion)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPlanDeVacunacion() + "(nombre, descripcion, fecha_actualizacion) values (?, ?, ?)");
		q.setParameters(nombre, descripcion, fecha_actualizacion);
		return (long) q.executeUnique(); 
	}
	
	public long eliminarTodosLosPlanesDeVacunacion(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPlanDeVacunacion());
		return (long) q.executeUnique();
	}
	
	public long eliminarPlanDeVacunacionPorId(PersistenceManager pm, long id_plan_de_vacunacion)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPlanDeVacunacion() + " WHERE id_plan_de_vacunacion = ?");
        q.setParameters(id_plan_de_vacunacion);
        return (long) q.executeUnique();
	}
	
	public long eliminarPlanDeVacunacionPorNombre(PersistenceManager pm, String nombre)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPlanDeVacunacion() + " WHERE nombre = ?");
        q.setParameters(nombre);
        return (long) q.executeUnique();
	}
	
	public long eliminarPlanDeVacunacionPorFecha(PersistenceManager pm, Date fecha_de_actualizacion)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPlanDeVacunacion() + " WHERE fecha_de_actualizacion = ?");
        q.setParameters(fecha_de_actualizacion);
        return (long) q.executeUnique();
	}
	
	public List<PlanDeVacunacion> darListPlanDeVacunacion(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT ID_PLAN_DE_VACUNACION, NOMBRE, DESCRIPCION, FECHA_ACTUALIZACION FROM " + pp.darTablaPlanDeVacunacion());
		q.setResultClass(PlanDeVacunacion.class);
		List<PlanDeVacunacion> resp = q.executeList();
		return resp;
	}
}
