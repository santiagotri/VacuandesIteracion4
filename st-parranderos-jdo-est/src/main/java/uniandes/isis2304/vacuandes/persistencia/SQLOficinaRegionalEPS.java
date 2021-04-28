package uniandes.isis2304.vacuandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.vacuandes.negocio.OficinaRegionalEPS;
import uniandes.isis2304.vacuandes.negocio.PlanDeVacunacion;
import uniandes.isis2304.vacuandes.negocio.PuntoVacunacion;

public class SQLOficinaRegionalEPS {
	
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
	public SQLOficinaRegionalEPS (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}

	public long agregarOficinaRegional(PersistenceManager pm, String region, String administrador, int cantidad_vacunas_actuales, long plan_de_vacunacion)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOficinaRegionalEPS() + "(region, administrador, cantidad_vacunas_actuales, plan_de_vacunacion) values (?, ?, ?, ?)" ); 
		q.setParameters(region, administrador, cantidad_vacunas_actuales, plan_de_vacunacion); 
		return (long) q.executeUnique();
	}
	
	public long eliminarTodasLasOficinasRegionales(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOficinaRegionalEPS());
		return (long) q.executeUnique();
	}
	
	public long eliminarOficinaPorIdOficina(PersistenceManager pm, long id_oficina)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOficinaRegionalEPS() + " WHERE id_oficina = ?");
        q.setParameters(id_oficina);
        return (long) q.executeUnique();
	}
	
	public long eliminarOficinaPorRegion(PersistenceManager pm, String region)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOficinaRegionalEPS() + " WHERE region = ?");
        q.setParameters(region);
        return (long) q.executeUnique();
	}
	
	public List<OficinaRegionalEPS> darListOficinaRegional(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT ID_OFICINA, REGION, ADMINISTRADOR, CANTIDAD_VACUNAS_ACTUALES, PLAN_DE_VACUNACION FROM " + pp.darTablaOficinaRegionalEPS());
		q.setResultClass(OficinaRegionalEPS.class);
		List<OficinaRegionalEPS> resp = q.executeList();
		return resp;
	}
	
	private OficinaRegionalEPS darOficinaPorId(PersistenceManager pm, long id_oficina) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOficinaRegionalEPS() + " WHERE id_oficina = ?");
		q.setResultClass(OficinaRegionalEPS.class);
		q.setParameters(id_oficina);
        return (OficinaRegionalEPS) q.executeUnique();
	}
	
	public long disminuirVacunasDisponibles(PersistenceManager pm, long id_oficina) {
		Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaOficinaRegionalEPS() + " SET CANTIDAD_VACUNAS_ACTUALES= CANTIDAD_VACUNAS_ACTUALES-1 WHERE id_oficina = ?");
		q.setParameters(id_oficina);
		return (long) q.executeUnique();
	}

	
}
