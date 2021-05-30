package uniandes.isis2304.vacuandes.persistencia;


import java.sql.Timestamp;   
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.vacuandes.negocio.Cita;

public class SQLCita {
	
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
	public SQLCita (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarCita(PersistenceManager pm, Date fecha, long ciudadano, long punto_vacunacion, long vacuna, int hora_cita)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCita() + "(fecha, ciudadano, punto_vacunacion, vacuna, hora_cita) values (?, ?, ?, ?, ?)");
		q.setParameters(new Timestamp(fecha.getTime()), ciudadano, punto_vacunacion, vacuna,hora_cita); 
		return (long) q.executeUnique();	
	}
	
	public long eliminarTodasLasCitas(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCita());
		return (long) q.executeUnique(); 
	}
	
	public long eliminarCitaPorCiudadano(PersistenceManager pm, long ciudadano)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCita() + " WHERE ciudadano = ?");
        q.setParameters(ciudadano);
        return (long) q.executeUnique();
	}
	
	public long eliminarCitaPorPuntoVacunacion(PersistenceManager pm, long punto_Vacunacion)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCita() + " WHERE PUNTO_VACUNACION = ?");
        q.setParameters(punto_Vacunacion);
        return (long) q.executeUnique();
	}
	
	public List<Cita> darListaCitas(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCita());
		q.setResultClass(Cita.class);
		return (List<Cita>) q.execute();
	}

	public int darCantidadCitas(PersistenceManager pm, Date fecha, int hora_cita, long punto_vacunacion) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCita() + " WHERE FECHA = ? AND HORA_CITA = ? AND PUNTO_VACUNACION = ?");
		q.setResultClass(Cita.class);
		q.setParameters(fecha, hora_cita, punto_vacunacion);
		List<Cita> lista = (List<Cita>) q.execute(); 
		return lista.size();
	}
	
	public List<Cita> darCiudadanosPuntoVacunacion(PersistenceManager pm, long punto_vacunacion)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCita() + " WHERE PUNTO_VACUNACION = "+ punto_vacunacion);
		//Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCita() + " WHERE PUNTO_VACUNACION = "+punto_vacunacion + " AND FECHA <= (SELECT TO_CHAR(SYSDATE, 'DD-MON-YYYY') FROM dual)");
		q.setResultClass(Cita.class);
		return (List<Cita>) q.executeList();
	}
	
	public List<Cita> darCiudadanosPuntoVacunacionYFecha(PersistenceManager pm, long punto_vacunacion, String fecha)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCita() + " WHERE punto_vacunacion = ? AND fecha= ?");
		q.setResultClass(Cita.class);
		q.setParameters(punto_vacunacion, fecha);
		return (List<Cita>) q.executeList();
	}

	public List<Cita> darCiudadanosPuntoVacunacionYRangoFechas(PersistenceManager pm, long punto_vacunacion,
			String primera_fecha, String segunda_fecha) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCita() + " WHERE punto_vacunacion = ? AND fecha >= TO_DATE(?, 'dd/mm/yyyy') AND fecha <= TO_DATE(?, 'dd/mm/yyyy')");
		q.setResultClass(Cita.class);
		q.setParameters(punto_vacunacion, primera_fecha, segunda_fecha);
		return (List<Cita>) q.executeList();
	}

	public List<Cita> darCiudadanosPuntoVacunacionYRangoHoras(PersistenceManager pm, long punto_vacunacion,
			int primera_hora, int segunda_hora) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCita() + " WHERE punto_vacunacion = ? AND hora_cita>= ? AND hora_cita<= ?");
		q.setResultClass(Cita.class);
		q.setParameters(punto_vacunacion, primera_hora, segunda_hora);
		return (List<Cita>) q.executeList();
	}

	public List<Object> darPuntosMasEfectivos(PersistenceManager pm) {
		Query q = pm.newQuery(SQL, " select * from (SELECT punto_vacunacion, COUNT(id_cita) citas FROM " + pp.darTablaCita() + " GROUP BY punto_vacunacion ORDER BY citas DESC) WHERE rownum<=20");
		return q.executeList();
	}

	public long eliminarCitaPorPuntoVacunacionDesdeFechaActual(PersistenceManager pm, long punto_vacunacion) {
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCita() + " WHERE PUNTO_VACUNACION = ? AND FECHA >= (SELECT TO_CHAR(SYSDATE, 'DD-MON-YYYY') FROM dual)");
        q.setParameters(punto_vacunacion);
        return (long) q.executeUnique();
	}

	public Cita darCita(PersistenceManager pm, Date fecha, long ciudadano, long punto_vacunacion, int hora_cita) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCita() + " WHERE FECHA = ? AND CIUDADANO = ? AND PUNTO_VACUNACION = ? AND HORA_CITA = ?");
		q.setResultClass(Cita.class);
		q.setParameters(new Timestamp(fecha.getTime()), ciudadano, punto_vacunacion, hora_cita);
		return (Cita) q.executeUnique();
	}

	public List<Cita> darListaCitasQueVanASerEliminadas(PersistenceManager pm, long punto_vacunacion) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCita() + " WHERE PUNTO_VACUNACION = ? AND FECHA >= (SELECT TO_CHAR(SYSDATE, 'DD-MON-YYYY') FROM dual)");
        q.setParameters(punto_vacunacion);
        q.setResultClass(Cita.class);
        return (List<Cita>) q.executeList();
	}

	public List<Cita> darCiudadanosPorFechaYHora(PersistenceManager pm, long punto_vacunacion, Date fecha,
			int hora_cita) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCita() + " WHERE punto_vacunacion = ? AND fecha = ? AND hora_cita = ?");
		q.setResultClass(Cita.class);
		q.setParameters(punto_vacunacion, new Timestamp(fecha.getTime()), hora_cita);
		return (List<Cita>) q.executeList();
	}

	public List<Object> darPuntosMasEfectivosPorFecha(PersistenceManager pm, Date fecha){
		Query q = pm.newQuery(SQL, "select * from (SELECT punto_vacunacion, COUNT(id_cita) citas FROM " + pp.darTablaCita() + " WHERE fecha = ? GROUP BY punto_vacunacion ORDER BY citas DESC) WHERE rownum<=20");
		q.setParameters( new Timestamp(fecha.getTime()));
		return q.executeList();
	}

	public List<Object> darPuntosMasEfectivosPorRangoDeFechas(PersistenceManager pm, Date primera_fecha,
			Date segunda_fecha) {
		Query q = pm.newQuery(SQL, "select * from (SELECT punto_vacunacion, COUNT(id_cita) citas FROM " + pp.darTablaCita() + " WHERE fecha >= ? AND fecha <= ? GROUP BY punto_vacunacion ORDER BY citas DESC) WHERE rownum<=20");
		q.setParameters( new Timestamp(primera_fecha.getTime()), new Timestamp(segunda_fecha.getTime()));
		return q.executeList();
	}

	public List<Object> darPuntosMasEfectivosPorRangoDeHoras(PersistenceManager pm, int primera_hora,
			int segunda_hora) {
		Query q = pm.newQuery(SQL, "select * from (SELECT punto_vacunacion, COUNT(id_cita) citas FROM " + pp.darTablaCita() + " WHERE hora_cita >= ? AND hora_cita <= ? GROUP BY punto_vacunacion ORDER BY citas DESC) WHERE rownum<=20");
		q.setParameters( primera_hora, segunda_hora);
		return q.executeList();
	}

	public List<Object> darPuntosValidos(PersistenceManager pm, String fecha_diez_atras, String fecha)  {
		Query q = pm.newQuery(SQL, "SELECT fecha, hora_cita, punto_vacunacion, count(id_cita) FROM " + pp.darTablaCita() 
		+ " WHERE fecha >= TO_DATE(?, 'dd/mm/yyyy') AND fecha <= TO_DATE(?, 'dd/mm/yyyy') GROUP BY fecha, hora_cita, punto_vacunacion");
		q.setParameters( fecha_diez_atras, fecha);
		return q.executeList();
	}

	public List<Long> getCiudadanosQueSeCruzan(PersistenceManager pm, long idPuntoVacunacion,
			String fechaBusqueda, long horaCita)  {
		Query q = pm.newQuery(SQL, "SELECT ciudadano FROM " + pp.darTablaCita() + " WHERE fecha = TO_DATE(?, 'dd/mm/yyyy') AND hora_cita = ? AND punto_vacunacion = ?");
		q.setParameters( fechaBusqueda, horaCita, idPuntoVacunacion);
		return q.executeList();
	}

	public long elimarCitaPorId(PersistenceManager pm, long cita) {
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCita() + " WHERE id_cita = ?");
        q.setParameters(cita);
        return (long) q.executeUnique();
	}

	public List<Object> consultarFuncionamiento(PersistenceManager pm) {
		Query q = pm.newQuery(SQL, "SELECT p.localizacion, punto_vacunacion, to_char(fecha - 7/24,'IW') semana,count(id_cita) contador"
				+ " FROM cita c inner join punto_vacunacion p on c.punto_vacunacion = p.id_punto_vacunacion" 
				+ " GROUP BY p.localizacion,punto_vacunacion, to_char(fecha - 7/24,'IW')"
				+ " order by semana, contador DESC");
		return q.executeList();
	}

}
