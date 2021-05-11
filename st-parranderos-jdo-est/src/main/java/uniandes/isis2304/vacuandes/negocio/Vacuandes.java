package uniandes.isis2304.vacuandes.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import uniandes.isis2304.vacuandes.persistencia.PersistenciaVacuandes;

public class Vacuandes {
	
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Vacuandes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaVacuandes pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public Vacuandes ()
	{
		pp = PersistenciaVacuandes.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public Vacuandes (JsonObject tableConfig)
	{
		pp = PersistenciaVacuandes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}

	public Usuario verificarInicioDeSesion(String username, String contrasena) {
		//PersistenceManager pm = pmf.getPersistenceManager();
		log.info ("Iniciando sesion para usuario " + username);
		Usuario rta = pp.verificarInicioDeSesion (username, contrasena);
        log.info ("Verificado inicio sesion");
        return rta;
	}
	
	
	
	/* ****************************************************************
	 * 			Métodos para manejar CITA
	 *****************************************************************/
	
	public Cita agregarCita(Date fecha, Long ciudadano, Long punto_vacunacion, int hora_cita) {
		log.info ("Creando una nueva cita");
		//Crea una nueva cita, retorna el id de la primera vacuna disponible, asigna ciudadano, pone vacuna usada en true, disminuye en 1 la cantidad de vacunas del punto vacunacion, disminuye la cantidad de vacunas en oficina regional en 1
		Cita rta = pp.adicionarCita(fecha, ciudadano,punto_vacunacion, hora_cita);
        log.info ("Se creo el cita en el punto: " + punto_vacunacion +" para el ciudadano de cedula: " + ciudadano);
        return rta;
	}
	
	public boolean verificarHoraNoLlena(Date fecha, int hora_cita, Long punto_vacunacion)
	{
		//Traer todas las citas que tengan esa fecha y esa hora, verificar capacidad del punto de vacunacion, contra cuantas citas hay y comparar y si es mayor lanzar un error
		log.info ("Verificando si el horario está disponible para esa cita");
		boolean rta = pp.verificarHorario(fecha,hora_cita ,punto_vacunacion);
        log.info ("Ese horario está disponible para el punto de vacunacion? " + rta);
        return rta; 
	}
	
	public String mostrarCiudadanosAtendidosPorUnPuntoDeVacunacionFechaEspecifica(long punto_vacunacion, String fecha_especifica) {
		log.info ("Buscando los ciudadanos en el punto de vacunacion: " + punto_vacunacion + " en la fecha " + fecha_especifica.toString());
		String rta = pp.darCiudadanosPuntoVacunacionPorFechaEspecifica(punto_vacunacion, fecha_especifica);
        log.info ("Se retornaron todos los ciudadanos encontrados");
        return rta;
	}
	
	public String mostrarCiudadanosAtendidosPorUnPuntoDeVacunacionRangoFechas (long punto_vacunacion, String primera_fecha, String segunda_fecha) {
		log.info ("Buscando los ciudadanos en el punto de vacunacion: " + punto_vacunacion + " en la fechas indicadas ");
		String rta = pp.darCiudadanosPuntoVacunacionPorRangoFechas(punto_vacunacion, primera_fecha, segunda_fecha);
        log.info ("Se retornaron todos los ciudadanos encontrados");
        return rta;
	}
	
	public String mostrarCiudadanosAtendidosPorUnPuntoDeVacunacionRangoHora(long punto_vacunacion, int primera_hora, int segunda_hora) {
		log.info ("Buscando los ciudadanos en el punto de vacunacion: " + punto_vacunacion + " en la horas indicadas ");
		String rta = pp.darCiudadanosPuntoVacunacionPorRangoHoras(punto_vacunacion, primera_hora, segunda_hora);
        log.info ("Se retornaron todos los ciudadanos encontrados");
        return rta;
	}
	
	public String mostrarCiudadanosAtendidosPorUnPuntoDeVacunacion(long punto_vacunacion) {
		log.info ("Buscando los ciudadanos atendidos en el punto de vacunacion: " + punto_vacunacion );
		String rta = pp.darCiudadanosPuntoVacunacion(punto_vacunacion);
        log.info ("Se retornaron todos los ciudadanos atendidos que fueron encontrados");
        return rta;
	}
	
//	public String Mostrar20PuntosDeVacunacionMasEfectivos() {
//		log.info ("Buscando los puntos de vacunacion más efectivos");
//		String rta = pp.darPuntosVacunacionMasEfectivos();
//        log.info ("Se retornaron los 20 puntos");
//        return rta;
//	}
	
	/**
	 * 
	public Cita darCitaPorPuntoDeVacunacionYFecha(Date fecha, long punto_vacunacion) {
		log.info ("Buscando cita en la fecha: " + fecha + " del ciudadano con cedula: " + ciudadano);
		Cita rta = pp.buscarCita(fecha, ciudadano);
        log.info ("Trabajo verificado");
        return rta;
	}
	 */
	
	/* ****************************************************************
	 * 			Métodos para manejar CIUDADANO
	 *****************************************************************/
	
	public Ciudadano agregarCiudadano(long cedula, String nombre_completo, String estado_vacunacion, String region, int desea_ser_vacunado, long plan_de_vacunacion, Long punto_vacunacion, Long oficina_regional_asignada) {
		log.info ("Creando un nuevo ciudadano en VacuAndes de cedula: " + cedula);
		Ciudadano rta = pp.adicionarCiudadano(cedula, nombre_completo,estado_vacunacion, region, desea_ser_vacunado, plan_de_vacunacion, punto_vacunacion, oficina_regional_asignada);
        log.info ("Se creo el ciudadano: " + nombre_completo +" de cedula: " + cedula);
        return rta;
	}
	
	public Ciudadano darCiudadanoPorCedula(long cedula) {
		log.info ("Buscando ciudadano por cedula: " + cedula);
		Ciudadano rta = pp.buscarCiudadano(cedula);
        if(rta!=null) log.info ("ciudadano encontrado");
        return rta;
	}
	
	public long agregarACiudadanoPuntoDeVacunacion(long cedula, long punto_vacunacion) {
		long rta = 0;
		log.info ("Actualizando ciudadano de cedula: " + cedula);
		Ciudadano ciudadano = pp.buscarCiudadano(cedula);
		if(ciudadano!=null)
		{
			rta = pp.actualizarCiudadano(ciudadano.getCedula(), ciudadano.getNombre_Completo(), ciudadano.getEstado_vacunacion(), ciudadano.getRegion(), ciudadano.getDesea_ser_vacunado(), ciudadano.getPlan_De_Vacunacion(), punto_vacunacion, ciudadano.getOficina_Regional_Asignada()); 
		}
		else 
		{
			return 0; 
		}
        log.info ("Trabajo verificado");
        return rta;
	}
	
	public long actualizarEstadoVacunacionCiudadano(long cedula, String estado_vacunacion) {
		long rta = 0;
		log.info ("Actualizando ciudadano de cedula: " + cedula);
		Ciudadano ciudadano = pp.buscarCiudadano(cedula);
		if(ciudadano!=null)
		{
			rta = pp.actualizarCiudadano(ciudadano.getCedula(), ciudadano.getNombre_Completo(), estado_vacunacion, ciudadano.getRegion(), ciudadano.getDesea_ser_vacunado(), ciudadano.getPlan_De_Vacunacion(), ciudadano.getPunto_Vacunacion(), ciudadano.getOficina_Regional_Asignada()); 
		}
		else 
		{
			return 0; 
		}
        log.info ("Trabajo verificado");
        return rta;
	}
	
	public void actualizarOpinionVacunacionCiudadano(long cedula, int desea_ser_vacunado) throws Exception
	{
		log.info ("Actualizando ciudadano de cedula: " + cedula);
		Ciudadano ciudadano = pp.buscarCiudadano(cedula);
		if(ciudadano!=null)
		{
			pp.actualizarCiudadano(ciudadano.getCedula(), ciudadano.getNombre_Completo(), ciudadano.getEstado_vacunacion(), ciudadano.getRegion(), desea_ser_vacunado, ciudadano.getPlan_De_Vacunacion(), ciudadano.getPunto_Vacunacion(), ciudadano.getOficina_Regional_Asignada()); 
		}
		else 
		{
			throw new Exception("El ciudadano no existe"); 
		}
        log.info ("Se actualizó la opinión del ciudadano");
	}
	
	public long eliminarCiudadanoPorCedula(long cedula) {
		log.info ("Eliminando ciudadano en VacuAndes de cedula: " + cedula);
		long rta = pp.eliminarCiudadano(cedula);
        log.info ("Se elimino el ciudadano  de cedula: " + cedula);
        return rta;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar Estado_Vacunacion
	 *****************************************************************/
	public EstadoVacunacion agregarEstadoVacunacion(String estado)
	{
		log.info ("Insertando el estado de vacunacion: " + estado);
		EstadoVacunacion rta = pp.adicionarEstadoVacunacion(estado);
        log.info ("Se agregó correctamente el estado: " + estado);
        return rta; 
	}
	
	public long eliminarEstadoVacunacion(String estado)
	{
		log.info ("Eliminando el estado: " + estado);
		long rta = pp.eliminarEstadoVacunacion(estado);
        log.info ("Se eliminó correctamente el estado: " + estado); 
        return rta; 
	}
	
	public EstadoVacunacion darEstadoVacunacionPorNombre(String estado)
	{
		log.info ("Trayendo el estado de vacunacion: " + estado);
		EstadoVacunacion rta = pp.darEstadoVacunacionPorNombre(estado);
        log.info ("Se trajo correctamente el estado: " + rta.getEstado());
        return rta; 
	}
	
	
	
	/* ****************************************************************
	 * 			Métodos para manejar LIST_CONDICIONES_CIUDADANO
	 *****************************************************************/
	
	
	/* ****************************************************************
	 * 			Métodos para manejar LIST_CONTRAINDICACIONES_CIUDADANO
	 *****************************************************************/
	public void agregarCondicionesCiudadano(long cedula, List<String> condiciones)
	{
		log.info ("Insertando condiciones para el ciudadano de cedula: " + cedula);
		pp.adicionarCondicionesCiudadano(cedula, condiciones);
        log.info ("Se adicionaron las condiciones al ciudadano de cedula: " + cedula);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar MINISTERIO_SALUD
	 *****************************************************************/
	
	
	/* ****************************************************************
	 * 			Métodos para manejar OFICINA_REGIONAL_EPS
	 *****************************************************************/
	
	public OficinaRegionalEPS agregarOficinaRegional(String region, String adminstrador, int cantidad_vacunas_enviables, int cantidad_vacunas_actuales, long plan_de_vacunacion) {
		log.info ("Registrando una nueva oficina regional");
		OficinaRegionalEPS rta = pp.adicionarOficinaRegional(region, adminstrador, cantidad_vacunas_enviables, cantidad_vacunas_actuales, plan_de_vacunacion);
        log.info ("Se creo la oficina en la region: " + region +" con el administrador: " + adminstrador);
        return rta;
	}
	
	public List<OficinaRegionalEPS> darTodasLasOficinasRegionalEPS() {
		log.info ("Buscando oficinas regionales existentes");
		List<OficinaRegionalEPS> rta = pp.darTodasLasOficinasRegionalEPS();
        log.info ("Se han encontrado: " + rta.size() +" oficinas regionales");
        return rta;
	}
	
	public OficinaRegionalEPS darOficinaRegionalEPSPorId(long idOficina) {
		log.info ("Buscando oficina regional con id " +idOficina);
		OficinaRegionalEPS rta = pp.darOficinasRegionalEPSPorId(idOficina);
		if(rta==null) log.info ("No se han encontrado oficinas regionales con id " + idOficina);
		else {log.info ("Se ha encontrado una oficina con id: " + rta.getId_oficina());}
        return rta;
	}
	
	public long registrarLlegadaDeLoteDeVacunasEPS(OficinaRegionalEPS oficina_regional_eps, int cantidad_vacunas, String condiciones_de_preservacion)
	{
		log.info ("Enviando lote de vacunas a oficina regional EPS de id: " + oficina_regional_eps);
		long rta = pp.agregarVacunasEps(oficina_regional_eps, cantidad_vacunas, condiciones_de_preservacion);
		log.info ("Se agregaron " + cantidad_vacunas + " a la eps de id " + oficina_regional_eps.getId_oficina());
		return rta;
	}
	
	public long eliminarOficinaRegionalEPSPorId(long idOficina) {
		log.info ("eliminando una nueva oficina regional con id " +idOficina);
		long rta = pp.eliminarOficinaRegionalPorId(idOficina);
        log.info ("Se elimino la oficina en la region con id " + idOficina);
        return rta;
	}
	
	public long actualizarCantidadVacunasActualesOficinaRegional(long idOficina, int cantidadVacunasAnterior) {
		log.info ("actualizando la oficina regional con id " +idOficina);
		long rta = pp.actualizarCantidadVacunasActualesOficinaRegional(idOficina, cantidadVacunasAnterior);
        log.info ("Se elimino la oficina en la region con id " + idOficina);
        return rta;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar PLAN_DE_VACUNACION
	 *****************************************************************/
	
	
	public List<PlanDeVacunacion> darTodosLosPlanesDeVacunacion() {
		log.info ("Buscando planes de vacunacion existentes");
		List<PlanDeVacunacion> rta = pp.darTodosLosPlanesDeVacunacion();
        log.info ("Se han encontrado: " + rta.size() +" planes de vacunacion");
        return rta;
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar PUNTO_VACUNACION
	 *****************************************************************/
	
	public PuntoVacunacion agregarPuntoVacunacion(String localizacion, int capacidad_de_atencion_simultanea, String infraestructura_para_dosis, int cantidad_vacunas_enviables, int cantidad_vacunas_actuales, String tipo_punto_vacunacion, String administrador, long oficina_regional_eps, int habilitado) {
		log.info ("Creando un nuevo punto de vacunacion");
		PuntoVacunacion rta = pp.adicionarPuntoVacunacion(localizacion, capacidad_de_atencion_simultanea, infraestructura_para_dosis, cantidad_vacunas_enviables,cantidad_vacunas_actuales,tipo_punto_vacunacion,administrador, oficina_regional_eps, habilitado);
        log.info ("Se creo el punto de vacunacion en la localizacion: " + localizacion);
        return rta;
	}

	public List<PuntoVacunacion> darTodosLosPuntosVacunacion() {
		log.info ("Buscando puntos de vacunacion existentes");
		List<PuntoVacunacion> rta = pp.darTodosLosPuntosVacunacion();
        log.info ("Se han encontrado: " + rta.size() +" puntos vacunacion");
        return rta;
	}
	
	public List<PuntoVacunacion> darTodosLosPuntosVacunacionDeLaRegion(String region) {
		log.info ("Buscando puntos de vacunacion con la region "+ region);
		List<PuntoVacunacion> rta = pp.darTodosLosPuntosVacunacionDeLaRegion(region);
        log.info ("Se han encontrado: " + rta.size() +" puntos vacunacion de la region" + region);
        return rta;
	}
	
	public PuntoVacunacion darPuntoVacunacionPorId(long id_punto_vacunacion)
	{
		log.info ("Buscando punto de vacunacion de id"+ id_punto_vacunacion);
		PuntoVacunacion rta = pp.darPuntoVacunacionPorId(id_punto_vacunacion);
        log.info ("Se encontró el punto " + rta +" con el id " + id_punto_vacunacion);
        return rta;
	}
	
	public List<PuntoVacunacion> darTodosLosPuntosVacunacionHabilitados() {
		log.info ("Buscando los puntos de vacunación habilitados");
		List<PuntoVacunacion> rta = pp.darTodosLosPuntosVacunacionHabilitados();
		log.info ("Se han encontrado: " + rta.size() +" puntos vacunacion de que están habilitados");
		return rta;
	}
	
	public List<PuntoVacunacion> darTodosLosPuntosVacunacionDeshabilitados() {
		log.info ("Buscando los puntos de vacunación deshabilitados");
		List<PuntoVacunacion> rta = pp.darTodosLosPuntosVacunacionDeshabilitados();
		log.info ("Se han encontrado: " + rta.size() +" puntos vacunacion de que están deshabilitados");
		return rta;
	}
	
	public List<PuntoVacunacion> darTodosLosPuntosVacunacionDeLaRegionHabilitados(String region) {
		log.info ("Buscando los puntos de vacunación habilitados para la región: " + region);
		List<PuntoVacunacion> rta = pp.darTodosLosPuntosVacunacionDeLaRegionHabilitados(region);
		log.info ("Se han encontrado: " + rta.size() +" puntos vacunacion de la region " + region + " que están habilitados");
		return rta;
	}
	
	public String deshabilitarPuntoVacunacion(long punto_vacunacion, long nuevo_punto)
	{
		log.info ("Deshabilitando el punto de vacunación indicado");
		String rta = pp.deshabilitarPuntoVacunacion(punto_vacunacion, nuevo_punto);
		log.info ("Se ha deshabilitado el punto de vacunación de id: " + punto_vacunacion);
		return rta;
	}
	
	public long rehabilitarPuntoVacunacion(long punto_vacunacion)
	{
		log.info ("Rehabilitando el punto de vacunación indicado (id:" + punto_vacunacion + ")");
		long rta = pp.rehabilitarPuntoVacunacion(punto_vacunacion);
		log.info ("Se ha rehabilitado el punto de vacunación de id: " + punto_vacunacion);
		return rta;
	}
	
	public long registrarLlegadaDeLoteDeVacunasAPuntoVacunacion(PuntoVacunacion punto_vacunacion, int cantidad_vacunas)
	{
		log.info ("Enviando lote de vacunas a punto vacunacion de id: " + punto_vacunacion);
		long rta = pp.agregarVacunasPuntoVacunacion(punto_vacunacion, cantidad_vacunas);
		log.info ("Se agregaron " + cantidad_vacunas + " a la eps de id " + punto_vacunacion);
		return rta;
	}
	
	public String mostrar20PuntosMasEfectivosGeneral() {
		log.info ("Buscando los 20 puntos de vacunación más efectivos");
		String rta = pp.darPuntosMasEfectivos();
        log.info ("Se retornaron los 20 puntos de vacunación más efectivos");
        return rta;
	}
	
	public String mostrar20PuntosMasEfectivosEnFechaEspecifica(Date fecha) {
		log.info ("Buscando los 20 puntos de vacunación más efectivos en la fecha: " + fecha);
		String rta = pp.darPuntosMasEfectivosPorFecha(fecha);
        log.info ("Se retornaron los 20 puntos de vacunación más efectivos");
        return rta;
	}
	
	public String mostrar20PuntosMasEfectivosEnRangoDeFechas(Date primera_fecha, Date segunda_fecha) {
		log.info ("Buscando los 20 puntos de vacunación más efectivos entre las fechas: " + primera_fecha + "  -  " + segunda_fecha);
		String rta = pp.darPuntosMasEfectivosPorRangoDeFechas(primera_fecha,segunda_fecha);
        log.info ("Se retornaron los 20 puntos de vacunación más efectivos");
        return rta;
	}
	
	public String mostrar20PuntosMasEfectivosEnRangoDeHoras(int primera_hora, int segunda_hora) {
		log.info ("Buscando los 20 puntos de vacunación más efectivos entre las horas: " + primera_hora + "  -  " + segunda_hora);
		String rta = pp.darPuntosMasEfectivosPorRangoDeHoras(primera_hora,segunda_hora);
        log.info ("Se retornaron los 20 puntos de vacunación más efectivos");
        return rta;
	}
	
	public String analizarOperacionDeVacuandesDiaEspecificoSobrecupo(String tipo_punto, String dia)
	{
		log.info ("Buscando para ese día las horas de mayor afluencia");
		String rta = pp.analizarOperacionesEnDiaEspecificoSobrecupo(tipo_punto, dia);
		log.info ("Se retornaron las horas de mayor afluencia");
		return rta;
	}
	
	public String analizarOperacionDeVacuandesDiaEspecificoFaltaDeCupo(String tipo_punto, String dia)
	{
		log.info ("Buscando para ese día las horas de menor afluencia");
		String rta = pp.analizarOperacionesEnDiaEspecificoFaltaDeCupo(tipo_punto, dia);
		log.info ("Se retornaron las horas de menor afluencia");
		return rta;
	}
	
	public String analizarOperacionDeVacuandesEnRangoHorasSobreCupo(String tipo_punto, int primera_hora, int segunda_hora)
	{
		log.info ("Buscando para ese día las horas de mayor afluencia en el rango de horas" + primera_hora + " - " + segunda_hora);
		String rta = pp.analizarOperacionesEnRangoDeHorasSobrecupo(tipo_punto, primera_hora,segunda_hora);
		log.info ("Se retornaron las horas de mayor afluencia");
		return rta;
	}
	
	public String analizarOperacionDeVacuandesEnRangoHorasFaltaDeCupo(String tipo_punto, int primera_hora, int segunda_hora)
	{
		log.info ("Buscando para ese día las horas de mayor afluencia en el rango de horas" + primera_hora + " - " + segunda_hora);
		String rta = pp.analizarOperacionesEnRangoDeHorasFaltaDeCupo(tipo_punto, primera_hora,segunda_hora);
		log.info ("Se retornaron las horas de menor afluencia");
		return rta;
	}
	
	public String analizarOperacionDeVacuandesEnRangoDeFechasSobrecupo(String tipo_punto, String primera_fecha, String segunda_fecha)
	{
		log.info ("Buscando para ese día las horas de mayor afluencia en el rango de fechas" + primera_fecha + " - " + segunda_fecha);
		String rta = pp.analizarOperacionesEnRangoDeFechasSobreCupo(tipo_punto, primera_fecha,segunda_fecha);
		log.info ("Se retornaron las horas de mayor afluencia");
		return rta;
	}
	
	public String analizarOperacionDeVacuandesEnRangoDeFechasFaltaDeCupo(String tipo_punto, String primera_fecha, String segunda_fecha)
	{
		log.info ("Buscando para ese día las horas de menor afluencia en el rango de fechas" + primera_fecha + " - " + segunda_fecha);
		String rta = pp.analizarOperacionesEnRangoDeFechasFaltaDeCupo(tipo_punto, primera_fecha,segunda_fecha);
		log.info ("Se retornaron las horas de menor afluencia");
		return rta;
	}
	
	public PuntoVacunacion darPuntoVacunacionPorLocalizacion(String localizacion) {
		log.info ("Buscando punto de vacunacion de localizacion "+ localizacion);
		PuntoVacunacion rta = pp.darPuntoVacunacionPorLocalizacion(localizacion);
        log.info ("Se encontró el punto " + rta);
        return rta;
	}
	
	public long eliminarPuntoPorLocalizacion(String localizacion) {
		log.info ("Eliminando punto de vacunacion de localizacion "+ localizacion);
		long rta = pp.eliminarPuntoPorLocalizacion(localizacion);
        log.info ("Se eliminó el punto en la localización: " + localizacion);
        return rta;
	}
	/* ****************************************************************
	 * 			Métodos para manejar TRABAJADOR
	 *****************************************************************/
	
	public Trabajador darTrabajadorPorCedula(long cedula) {
		log.info ("Buscando trabjador de cedula: " + cedula);
		Trabajador rta = pp.buscarTrabajadorPorCedula(cedula);
        log.info ("Trabajo verificado");
        return rta;
	}
	
	public Trabajador agregarTrabajador(long cedula, String trabajo, int administrador_vacuandes, long punto_vacunacion) {
		log.info ("Creando un nuevo trabajador");
		Trabajador rta = pp.adicionarTrabajador(cedula, trabajo, administrador_vacuandes, punto_vacunacion);
        log.info ("Se creo el trabajador con cedula: " + cedula);
        return rta;
	}
	
	public long eliminarTrabajador(long cedula)
	{
		log.info ("Eliminando trabajador");
		long rta = pp.eliminarTrabajador(cedula);
        log.info ("Se eliminó el trabajador con cedula: " + cedula);
        return rta;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar USUARIO
	 *****************************************************************/
	
	public Usuario agregarUsuarioVacuandes(String username, String contrasena, String correo, long plan_de_vacunacion, long ciudadano) {
		log.info ("Creando un nuevo usuario en VacuAndes");
		Usuario rta = pp.adicionarUsuario(username, contrasena,correo, plan_de_vacunacion, ciudadano);
        log.info ("Se creo el usuario: " + username +" de cedula: " + ciudadano);
        return rta;
	}
	
	public Usuario darUsuarioPorUsername(String username) {
		//PersistenceManager pm = pmf.getPersistenceManager();
		log.info ("buscando usuario por username: " + username);
		Usuario rta = pp.darUsuarioPorUsername(username);
        if(rta!=null)log.info ("Usuario encontrado");
        else {log.info ("Usuario NO encontrado");}
        return rta;
	}
	
	public long eliminarUsuarioPorUsername(String username)
	{
		log.info ("Eliminando el usuario: " + username);
		long rta = pp.eliminarUsuarioPorUsername(username);
        log.info ("Se elimino el usuario: " + username);
        return rta;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar VACUNA
	 *****************************************************************/
	
	public List<Vacuna> darVacunasPorCondicion(String condicion) {
		log.info ("buscando vacunas por condicion " +condicion);
		List<Vacuna> rta = pp.darVacunasPorCondicion(condicion);
        if(rta!=null)log.info ("vacunas encotradas " + rta.size());
        else {log.info("vacunas NO encontradas");}
        return rta;
	}
	
	public long eliminarVacunasPorCondicion(String condicion) {
		log.info ("eliminando vacunas por condicion " +condicion);
		long rta = pp.eliminarVacunasPorCondicion(condicion);
        log.info ("vacunas eliminadas");
        return rta;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar CONDICION
	 *****************************************************************/
	
	public Condicion agregarCondicion(String condiciones, int etapa) {
		log.info ("Creando una nueva condicion");
		Condicion rta = pp.adicionarCondicion(condiciones, etapa);
        log.info ("Se creo la condicion: " + condiciones +" de etapa numero:" + etapa);
        return rta;
	}
	
	public Condicion getCondicion(String condiciones)
	{
		log.info ("Buscando una condicion");
		Condicion rta = pp.getCondicionPorCondiciones(condiciones);
        if(rta != null)log.info ("Se encontró la condición: " + condiciones);
        else {log.error("No se ha encontrado la condicion " + condiciones);}
        return rta;
	}
	
	public long updateCondicion(String condiciones, int etapa)
	{
		log.info ("Actualizando una condicion");
		long rta = pp.updateCondicionPorCondiciones(condiciones, etapa);
        log.info ("Se actualizo la condicion" + condiciones + " a etapa " + etapa);
        return rta;
	}
	
	public Condicion registrarCondicionesDePriorizacion(String condiciones, int etapa)
	{
		Condicion rta = getCondicion(condiciones);
		if(rta != null)
		{
			updateCondicion(condiciones, etapa); 
		}
		else
		{
			rta = agregarCondicion(condiciones, etapa); 
		}
		
		return rta;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar Consultas
	 *****************************************************************/
	public String encontrarCiudadanosEnContacto(String fecha_diez_atras, String fecha)
	{
		log.info ("Buscando ciudadanos que se hayan cruzado en el rango de fechas: " + fecha_diez_atras + " hasta la fecha " + fecha);
		String rta = pp.encontrarCiudadanosEnContacto(fecha_diez_atras, fecha);
		log.info ("Se retornaron los ciudadanos que estuvieron en contacto en las mismas horas, fechas y puntos");
		return rta;
	}
	
	public String analizarLasCohorteDeCiudadanos(ArrayList<String> condiciones, ArrayList<Long> puntos_vacunacion, Integer cantVacunasAplicadas)
	{
		log.info ("Buscando ciudadanos con cohortes flexibles: ");
		String rta = pp.analizarCohorteFlexibleCompleto(condiciones, puntos_vacunacion, cantVacunasAplicadas);
		log.info ("Se retornaron los ciudadanos que cumplen ");
		return rta;
	}

	/* ****************************************************************
	 * 			Métodos para manejar ESTADO_VACUNACION
	 *****************************************************************/
	
	public List<EstadoVacunacion> darTodosLosEstadosVacunacion() {
		log.info ("Buscando estados de vacunacion existentes");
		List<EstadoVacunacion> rta = pp.darTodosLosEstadosVacunacion();
        log.info ("Se han encontrado: " + rta.size() +" estado(s) de vacunacion");
        return rta;
	}

	

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public void limpiarVacuandes()
	{
        log.info ("Limpiando la BD de Parranderos");
        pp.limpiarParranderos();	
        log.info ("Limpiando la BD de Parranderos: Listo!");
	}

	
}
