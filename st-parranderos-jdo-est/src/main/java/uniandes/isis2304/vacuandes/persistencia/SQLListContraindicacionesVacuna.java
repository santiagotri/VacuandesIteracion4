package uniandes.isis2304.vacuandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.vacuandes.negocio.ListContraindicacionesVacuna;

public class SQLListContraindicacionesVacuna {
	
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
	public SQLListContraindicacionesVacuna (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}

	public long adicionarContraindicacionesVacuna(PersistenceManager pm, long vacuna, String condicion)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaListCondicionesCiudadano () + "(vacuna, condicion) values (?, ?)");
		q.setParameters(vacuna, condicion);
		return (long) q.executeUnique(); 
	}
	
	public long eliminarTodasLasContraindicacionesVacuna(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaListContraindicacionesVacuna());
		return (long) q.executeUnique();
	}
	
	public long eliminarUnaSolaContraindicacionVacuna(PersistenceManager pm, long vacuna, String condicion)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaListContraindicacionesVacuna() + " WHERE vacuna = ? AND condicion = ?");
        q.setParameters(vacuna, condicion);
        return (long) q.executeUnique();
	}
	
	public long eliminarTodasLasContraindicacionesVacuna(PersistenceManager pm, long vacuna)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaListContraindicacionesVacuna() + " WHERE vacuna = ?");
        q.setParameters(vacuna);
        return (long) q.executeUnique();
	}
	
	public List<ListContraindicacionesVacuna> darListContraindicacionesVacuna(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaListContraindicacionesVacuna());
		q.setResultClass(ListContraindicacionesVacuna.class);
		return (List<ListContraindicacionesVacuna>) q.execute();
	}
}
