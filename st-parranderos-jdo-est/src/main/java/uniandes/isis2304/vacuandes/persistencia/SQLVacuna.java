package uniandes.isis2304.vacuandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.vacuandes.negocio.Vacuna;

public class SQLVacuna {
	
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
	public SQLVacuna (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarVacunaOficinaRegional(PersistenceManager pm, String condicion_preservacion, long plan_de_vacunacion,  Long oficina_regional)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVacuna() + "(condicion_preservacion, plan_de_vacunacion, oficina_regional, utilizada) values (?, ?, ?, ?)");
		q.setParameters(condicion_preservacion, plan_de_vacunacion,oficina_regional, 0); 
		return (long) q.executeUnique();	
	}
	
	public long adicionarVacuna(PersistenceManager pm, String condicion_preservacion, Long punto_vacunacion, Long plan_de_vacunacion, Long oficina_regional, boolean esta_utilizada)
	{
		int utilizada; 
		if(esta_utilizada) {utilizada=1;}else {utilizada= 0;}
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVacuna() + "(condicion_preservacion, punto_vacunacion, plan_de_vacunacion, oficina_regional, utilizada) values (?, ?, ?, ?, ?)");
		q.setParameters(condicion_preservacion, punto_vacunacion, plan_de_vacunacion, utilizada); 
		return (long) q.executeUnique();	
	}
	
	public long eliminarTodasLasVacunas(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVacuna());
		return (long) q.executeUnique(); 
	}
	
	public long eliminarVacunaPorId(PersistenceManager pm, long id_vacuna)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVacuna() + " WHERE id_vacuna = ?");
        q.setParameters(id_vacuna);
        return (long) q.executeUnique();
	}
	
	public Vacuna darVacunaPorId(PersistenceManager pm, long id_vacuna)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVacuna() + " WHERE id_vacuna = ?");
		q.setResultClass(Vacuna.class);
		q.setParameters(id_vacuna);
        return (Vacuna) q.executeUnique();
	}
	
	public List<Vacuna> darListaVacunas(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVacuna());
		q.setResultClass(Vacuna.class);
		return (List<Vacuna>) q.execute();
	}
	
	public List<Vacuna> darVacunasDisponiblesPorPuntoDeVacunacion(PersistenceManager pm, long punto_vacunacion)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVacuna() + " WHERE punto_vacunacion = "+punto_vacunacion+" AND utilizada = 0");
		q.setResultClass(Vacuna.class);
		
		return (List<Vacuna>) q.execute();
	}
	
	public long actualizarEstadoAUsada(PersistenceManager pm, long id_vacuna)
	{
		Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaVacuna() + " SET utilizada = 1 WHERE id_vacuna = ?");
		q.setParameters(id_vacuna);
		return (long) q.executeUnique();
	}
	
	public Vacuna darPrimeraVacunaPorPuntoDeVacunacion(PersistenceManager pm, long punto_vacunacion)
	{
		List<Vacuna> vacunas = darVacunasDisponiblesPorPuntoDeVacunacion(pm, punto_vacunacion);
		Vacuna vacuna = null;
		if(!vacunas.isEmpty()) {
			vacuna = vacunas.get(0);
		}
		actualizarEstadoAUsada(pm, vacuna.getId_Vacuna());
		return vacuna;
	}

	public long eliminarVacunasPorCondicion(PersistenceManager pm, String condicion) {
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVacuna() + " WHERE CONDICION_PRESERVACION = ?");
        q.setParameters(condicion);
        return (long) q.executeUnique();
	}

	public List<Vacuna> darVacunasPorCondicion(PersistenceManager pm, String condicion) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVacuna() + " WHERE CONDICION_PRESERVACION = ?");
		
		q.setResultClass(Vacuna.class);
		q.setParameters(condicion);
		return (List<Vacuna>) q.executeList();
	}
}
