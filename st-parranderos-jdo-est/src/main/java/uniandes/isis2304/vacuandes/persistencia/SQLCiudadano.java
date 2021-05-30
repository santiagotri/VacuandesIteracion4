package uniandes.isis2304.vacuandes.persistencia;

import java.util.ArrayList;
import java.util.Date;
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
		return (List<Ciudadano>) q.execute();
	}

	public long cambiarPuntosVacunacionCiudadanos(PersistenceManager pm, long punto_vacunacion, long nuevo_punto) {
		Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaCiudadano() + " SET punto_vacunacion= ? WHERE punto_vacunacion = ?");
		q.setParameters(nuevo_punto , punto_vacunacion);
		return (long) q.executeUnique();
	}

	public List<Ciudadano> darCohorteFlexible(PersistenceManager pm, String stringCondiciones,
			String stringPuntosVacunacion, String stringCantVacunasAplicadas) {
		
		if(stringCantVacunasAplicadas.equals("")) {
			
			Query q = pm.newQuery(SQL,
					" SELECT cedula, nombre_completo, estado_vacunacion, region, desea_ser_vacunado, plan_de_vacunacion, punto_vacunacion, oficina_regional_asignada "
					+ " FROM CIUDADANO tabla_ciudadano INNER JOIN "
					+ " (SELECT ciudadano FROM LIST_CONDICIONES_CIUDADANO "
					+ stringCondiciones
					+ ") tabla_condiciones ON tabla_ciudadano.cedula = tabla_condiciones.ciudadano "
					+ stringPuntosVacunacion );
				q.setResultClass(Ciudadano.class);
				return (List<Ciudadano>) q.execute();
		}else {
		Query q = pm.newQuery(SQL, "Select cedula, nombre_completo, estado_vacunacion, region, desea_ser_vacunado, plan_de_vacunacion, punto_vacunacion, oficina_regional_asignada FROM (" 
			+ " SELECT cedula, nombre_completo, estado_vacunacion, region, desea_ser_vacunado, plan_de_vacunacion, punto_vacunacion, oficina_regional_asignada "
			+ " FROM CIUDADANO tabla_ciudadano INNER JOIN "
			+ " (SELECT ciudadano FROM LIST_CONDICIONES_CIUDADANO "
			+ stringCondiciones
			+ ") tabla_condiciones ON tabla_ciudadano.cedula = tabla_condiciones.ciudadano "
			+ stringPuntosVacunacion 
			+ " ) tabla_ciudadanos INNER JOIN ( "
			+ " SELECT CIUDADANO, COUNT(id_cita) contador "	
			+ " FROM cita "
			+ " WHERE FECHA <= (SELECT TO_CHAR(SYSDATE, 'DD-MON-YYYY') FROM dual) "
			+ " GROUP BY ciudadano) TABLA_CITAS "
			+ " ON tabla_citas.ciudadano = tabla_ciudadanos.cedula " 
			+ stringCantVacunasAplicadas);
		q.setResultClass(Ciudadano.class);
		return (List<Ciudadano>) q.execute();
		}
	}

	public List<Ciudadano> darCiudadanosVacunadosAdminPlan(PersistenceManager pm, String primera_fecha, String segunda_fecha, String agrupar,
			String ordenar) {

			Query q = pm.newQuery(SQL,
					" SELECT cd.cedula, cd.nombre_completo, cd.estado_vacunacion, cd.region, cd.desea_ser_vacunado, cd.plan_de_vacunacion, cd.punto_vacunacion, cd.oficina_regional_asignada"
					+ " FROM ciudadano cd inner join cita cta on cd.cedula = cta.ciudadano "
					+ " INNER JOIN list_condiciones_ciudadano cond on cd.cedula = cond.ciudadano "
					+ " WHERE fecha BETWEEN TO_DATE('"+ primera_fecha +"', 'dd/mm/yyyy') and TO_DATE('"+ segunda_fecha + "', 'dd/mm/yyyy') and estado_vacunacion = 'Vacunado' " + agrupar 
					+ " ORDER BY " + ordenar);
				q.setResultClass(Ciudadano.class);
				return (List<Ciudadano>) q.execute();
	}

	public List<Ciudadano> darCiudadanosVacunadosAdminEPS(PersistenceManager pm, String primera_fecha,
			String segunda_fecha, String agrupar, String ordenar, long eps) {

		Query q = pm.newQuery(SQL,
				" SELECT cd.cedula, cd.nombre_completo, cd.estado_vacunacion, cd.region, cd.desea_ser_vacunado, cd.plan_de_vacunacion, cd.punto_vacunacion, cd.oficina_regional_asignada"
				+ " FROM ciudadano cd inner join cita cta on cd.cedula = cta.ciudadano "
				+ " INNER JOIN list_condiciones_ciudadano cond on cd.cedula = cond.ciudadano "
				+ " WHERE fecha BETWEEN TO_DATE('"+ primera_fecha +"', 'dd/mm/yyyy') and TO_DATE('"+ segunda_fecha +"', 'dd/mm/yyyy') and estado_vacunacion = 'Vacunado' and oficina_regional_asignada = " + eps + " " + agrupar 
				+ " ORDER BY " + ordenar);
			q.setResultClass(Ciudadano.class);
			return (List<Ciudadano>) q.execute();
}

	public List<Ciudadano> darCiudadanosNoVacunadosAdminPlan(PersistenceManager pm, String primera_fecha,
			String segunda_fecha, String agrupar, String ordenar){

		Query q = pm.newQuery(SQL,
				" SELECT cd.cedula, cd.nombre_completo, cd.estado_vacunacion, cd.region, cd.desea_ser_vacunado, cd.plan_de_vacunacion, cd.punto_vacunacion, cd.oficina_regional_asignada"
				+ " FROM ciudadano cd inner join cita cta on cd.cedula = cta.ciudadano "
				+ " INNER JOIN list_condiciones_ciudadano cond on cd.cedula = cond.ciudadano "
				+ " WHERE fecha BETWEEN TO_DATE('"+ primera_fecha +"', 'dd/mm/yyyy') and TO_DATE('"+ segunda_fecha +"', 'dd/mm/yyyy') and estado_vacunacion = 'No vacunado' " + agrupar 
				+ " ORDER BY " + ordenar);
			q.setResultClass(Ciudadano.class);
			return (List<Ciudadano>) q.execute();
}

	public List<Ciudadano> darCiudadanosNoVacunadosAdminEPS(PersistenceManager pm, String primera_fecha,
			String segunda_fecha, String agrupar, String ordenar, long eps) {

		Query q = pm.newQuery(SQL,
				" SELECT cd.cedula, cd.nombre_completo, cd.estado_vacunacion, cd.region, cd.desea_ser_vacunado, cd.plan_de_vacunacion, cd.punto_vacunacion, cd.oficina_regional_asignada"
				+ " FROM ciudadano cd inner join cita cta on cd.cedula = cta.ciudadano "
				+ " INNER JOIN list_condiciones_ciudadano cond on cd.cedula = cond.ciudadano "
				+ " WHERE fecha BETWEEN TO_DATE('"+ primera_fecha +"', 'dd/mm/yyyy') and TO_DATE('"+ segunda_fecha +"', 'dd/mm/yyyy') and estado_vacunacion = 'No vacunado' and oficina_regional_asignada = " + eps + " " + agrupar 
				+ " ORDER BY " + ordenar);
			q.setResultClass(Ciudadano.class);
			return (List<Ciudadano>) q.execute();
}

	@SuppressWarnings("deprecation")
	public List<Object> consultarLideresEPS(PersistenceManager pm, String fecha, int diasAtras) {
		Date fechaEnDate = pp.convertirFechaDeStringADate(fecha);
		
		Date fechaInicial = new Date(fechaEnDate.getTime()-diasAtras*(1000 * 60 * 60 * 24));
		System.out.println("fecha inicial convertida "+ pp.convertirDateAformatoString(fechaInicial));
		System.out.println("fecha final string "+ fecha);
		Query q = pm.newQuery(SQL,
				" SELECT c.fecha, p.oficina_regional_eps, COUNT(id_cita)"
				+ " from cita c INNER JOIN punto_vacunacion p on c.punto_vacunacion = p.id_punto_vacunacion "
				+ " where fecha between TO_DATE('"+ pp.convertirDateAformatoString(fechaInicial)+ "', 'dd/mm/yyyy') and TO_DATE('"+ fecha + "', 'dd/mm/yyyy') "
				+ " GROUP BY c.fecha, p.oficina_regional_eps"
				+ " ORDER BY p.oficina_regional_eps");
		return q.executeList();
		
	}
}
