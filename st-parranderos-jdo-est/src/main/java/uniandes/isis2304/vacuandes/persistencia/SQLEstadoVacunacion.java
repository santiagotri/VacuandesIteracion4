package uniandes.isis2304.vacuandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.vacuandes.negocio.EstadoVacunacion;
import uniandes.isis2304.vacuandes.negocio.OficinaRegionalEPS;

public class SQLEstadoVacunacion {

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
	public SQLEstadoVacunacion (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}


	public List<EstadoVacunacion> darListEstadoVacunacion(PersistenceManager pm) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEstadoVacunacion());
		q.setResultClass(EstadoVacunacion.class);
		List<EstadoVacunacion> resp = q.executeList();
		return resp;
	}
}
