package uniandes.isis2304.vacuandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.vacuandes.negocio.MinisterioSalud;

public class SQLMinisterioSalud {

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
	public SQLMinisterioSalud (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	public long agregarMinisterioDeSalud(PersistenceManager pm, long plan_de_vacunacion)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaMinisterioSalud() + "(plan_de_vacunacion) values (?)");
		q.setParameters(plan_de_vacunacion); 
		return (long) q.executeUnique();
	}
	
	public long eliminarTodosLosMinisteriosDeSalud(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaMinisterioSalud());
        return (long) q.executeUnique();
	}
	
	public long eliminarMinisterioPorPlanDeVacunacion(PersistenceManager pm,  long plan_de_vacunacion)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaMinisterioSalud() + " WHERE plan_de_vacunacion = ?");
		q.setParameters(plan_de_vacunacion); 
        return (long) q.executeUnique();
	}
	
	public List<MinisterioSalud> darListMinisterioDeSalud(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaMinisterioSalud());
		q.setResultClass(MinisterioSalud.class);
		return (List<MinisterioSalud>) q.execute();
	}
}
