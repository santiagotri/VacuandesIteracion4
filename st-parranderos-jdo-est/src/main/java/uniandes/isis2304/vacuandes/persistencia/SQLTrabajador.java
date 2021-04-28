package uniandes.isis2304.vacuandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.vacuandes.negocio.Trabajador;

public class SQLTrabajador {
	
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
	public SQLTrabajador (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarTrabajador(PersistenceManager pm, long cedula, String trabajo, int administrador_vacuandes, long punto_vacunacion)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaTrabajador() + "(cedula, trabajo, administrador_vacuandes, punto_vacunacion) values (?, ?, ?, ?)");
		q.setParameters(cedula, trabajo, administrador_vacuandes, punto_vacunacion);
		return (long) q.executeUnique(); 
	}
	
	public long eliminarTodosLosTrabajadores(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaTrabajador());
		return (long) q.executeUnique();
	}
	
	public long eliminarTrabajadorPorCedula(PersistenceManager pm, long cedula)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaTrabajador() + " WHERE cedula = ? ");
        q.setParameters(cedula);
        return (long) q.executeUnique();
	}
	
	public long eliminarTrabajadorPorTrabajo(PersistenceManager pm, String trabajo)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaTrabajador() + " WHERE trabajo = ? ");
        q.setParameters(trabajo);
        return (long) q.executeUnique();
	}
	
	public List<Trabajador> darListaTrabajadores(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaTrabajador());
		q.setResultClass(Trabajador.class);
		return (List<Trabajador>) q.execute();
	}
	
	public Trabajador darTrabajador(PersistenceManager pm, long cedula)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaTrabajador() + " WHERE cedula = ?");
		q.setResultClass(Trabajador.class);
		q.setParameters(cedula);
		return (Trabajador) q.executeUnique();
	}
}
