package uniandes.isis2304.vacuandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.vacuandes.negocio.ListCondicionesCiudadano;

public class SQLListCondicionesCiudadano {

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
	public SQLListCondicionesCiudadano (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarCondicionesCiudadano (PersistenceManager pm, String condicion, long cedula) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaListCondicionesCiudadano () + "(condicion, ciudadano) values (?, ?)");
        q.setParameters(condicion, cedula);
        return (long) q.executeUnique();
	}

	/**
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarTodasLasCondicionesCiudadano (PersistenceManager pm) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaListCondicionesCiudadano ());
        return (long) q.executeUnique();
	}

	/**
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCondicionesCiudadano(PersistenceManager pm, long cedula, String condicion) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaListCondicionesCiudadano () + " WHERE ciudadano = ? AND condicion = ?");
        q.setParameters(cedula, condicion);
        return (long) q.executeUnique();
	}

	/**
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCondicionesCiudadanoPorCiudadano (PersistenceManager pm, long cedula) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaListCondicionesCiudadano () + " WHERE ciudadano = ?");
        q.setParameters(cedula);
        return (long) q.executeUnique();
	}

	/**
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos VISITAN
	 */
	public List<ListCondicionesCiudadano> darListCondicionesCiudadano (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaListCondicionesCiudadano ());
		q.setResultClass(ListCondicionesCiudadano.class);
		return (List<ListCondicionesCiudadano>) q.execute();
	}
		 	


}
