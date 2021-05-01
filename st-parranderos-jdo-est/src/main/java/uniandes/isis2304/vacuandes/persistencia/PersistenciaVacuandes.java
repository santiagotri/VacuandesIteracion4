
package uniandes.isis2304.vacuandes.persistencia;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.negocio.Bebedor;
import uniandes.isis2304.vacuandes.negocio.Cita;
import uniandes.isis2304.vacuandes.negocio.Ciudadano;
import uniandes.isis2304.vacuandes.negocio.Condicion;
import uniandes.isis2304.vacuandes.negocio.EstadoVacunacion;
import uniandes.isis2304.vacuandes.negocio.ListCondicionesCiudadano;
import uniandes.isis2304.vacuandes.negocio.OficinaRegionalEPS;
import uniandes.isis2304.vacuandes.negocio.PlanDeVacunacion;
import uniandes.isis2304.vacuandes.negocio.PuntoVacunacion;
import uniandes.isis2304.vacuandes.negocio.Trabajador;
import uniandes.isis2304.vacuandes.negocio.Usuario;
import uniandes.isis2304.vacuandes.negocio.Vacuna;


public class PersistenciaVacuandes {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaVacuandes.class.getName());

	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaVacuandes instance;

	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;

	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, Cita, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;

	
	/**
	 * Atributo para el acceso a la tabla Cita de la base de datos
	 */
	private SQLCita sqlCita;

	/**
	 * Atributo para el acceso a la tabla Ciudadano de la base de datos
	 */
	private SQLCiudadano sqlCiudadano;
	
	/**
	 * Atributo para el acceso a la tabla Ciudadano de la base de datos
	 */
	private SQLCondicion sqlCondicion;	
	
	/**
	 * Atributo para el acceso a la tabla Ciudadano de la base de datos
	 */
	private SQLEstadoVacunacion sqlEstadoVacunacion;
	
	/**
	 * Atributo para el acceso a la tabla ListCondicionesCiudadano de la base de datos
	 */
	private SQLListCondicionesCiudadano sqlListCondicionesCiudadano;


	/**
	 * Atributo para el acceso a la tabla ListContraindicacionesVacuna de la base de datos
	 */
	private SQLListContraindicacionesVacuna sqlListContraindicacionesVacuna;

	/**
	 * Atributo para el acceso a la tabla MinisterioSalud de la base de datos
	 */
	private SQLMinisterioSalud sqlMinisterioSalud;

	/**
	 * Atributo para el acceso a la tabla OficinaRegionalEPS de la base de datos
	 */
	private SQLOficinaRegionalEPS sqlOficinaRegionalEPS;

	/**
	 * Atributo para el acceso a la tabla PlanDeVacunacion de la base de datos
	 */
	private SQLPlanDeVacunacion sqlPlanDeVacunacion;

	/**
	 * Atributo para el acceso a la tabla PuntoVacunacion de la base de datos
	 */
	private SQLPuntoVacunacion sqlPuntoVacunacion;

	/**
	 * Atributo para el acceso a la tabla Trabajador de la base de datos
	 */
	private SQLTrabajador sqlTrabajador;

	/**
	 * Atributo para el acceso a la tabla Usuario de la base de datos
	 */
	private SQLUsuario sqlUsuario;

	/**
	 * Atributo para el acceso a la tabla Vacuna de la base de datos
	 */
	private SQLVacuna sqlVacuna;

	private PersistenciaVacuandes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Parranderos");		
		crearClasesSQL ();

		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("CITA");
		tablas.add ("CIUDADANO");
		tablas.add ("CONDICION");
		tablas.add ("LIST_CONDICIONES_CIUDADANO");
		tablas.add ("LIST_CONTRAINDICACIONES_VACUNA");
		tablas.add ("MINISTERIO_DE_SALUD");
		tablas.add ("OFICINA_REGIONAL_EPS");
		tablas.add ("PLAN_DE_VACUNACION");
		tablas.add ("PUNTO_VACUNACION");
		tablas.add ("TRABAJADOR");
		tablas.add ("USUARIO");
		tablas.add ("VACUNA");
	}
	
	
	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaVacuandes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaVacuandes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaVacuandes ();
		}
		return instance;
	}
	
	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaVacuandes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaVacuandes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		
		sqlCita = new SQLCita(this);
		sqlCiudadano = new SQLCiudadano(this);
		sqlCondicion = new SQLCondicion(this);
		sqlEstadoVacunacion = new SQLEstadoVacunacion(this);
		sqlListCondicionesCiudadano = new SQLListCondicionesCiudadano(this);
		sqlListContraindicacionesVacuna = new SQLListContraindicacionesVacuna(this);
		sqlMinisterioSalud = new SQLMinisterioSalud(this);
		sqlOficinaRegionalEPS = new SQLOficinaRegionalEPS(this);
		sqlPlanDeVacunacion = new SQLPlanDeVacunacion(this);
		sqlPuntoVacunacion = new SQLPuntoVacunacion(this);
		sqlTrabajador = new SQLTrabajador(this);
		sqlUsuario = new SQLUsuario(this);
		sqlVacuna = new SQLVacuna(this);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Cita de vacuandes
	 */
	public String darTablaCita ()
	{
		return tablas.get (0);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Ciudadano de vacuandes
	 */
	public String darTablaCiudadano ()
	{
		return tablas.get (1);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Condicion de vacuandes
	 */
	public String darTablaCondicion()
	{
		return tablas.get (2);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Condicion de vacuandes
	 */
	public String darTablaEstadoVacunacion()
	{
		return tablas.get (3);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de ListCondicionesCiudadano de vacuandes
	 */
	public String darTablaListCondicionesCiudadano ()
	{
		return tablas.get (4);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de ListContraindicacionesVacuna de vacuandes
	 */
	public String darTablaListContraindicacionesVacuna ()
	{
		return tablas.get (5);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de MinisterioSalud de vacuandes
	 */
	public String darTablaMinisterioSalud ()
	{
		return tablas.get (6);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de OficinaRegionalEPS de vacuandes
	 */
	public String darTablaOficinaRegionalEPS ()
	{
		return tablas.get (7);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de PlanDeVacunacion de vacuandes
	 */
	public String darTablaPlanDeVacunacion ()
	{
		return tablas.get (8);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de PuntoVacunacion de vacuandes
	 */
	public String darTablaPuntoVacunacion ()
	{
		return tablas.get (9);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Trabajador de vacuandes
	 */
	public String darTablaTrabajador ()
	{
		return tablas.get (10);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Usuario de vacuandes
	 */
	public String darTablaUsuario ()
	{
		return tablas.get (11);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Vacuna de vacuandes
	 */
	public String darTablaVacuna ()
	{
		return tablas.get (12);
	}
	
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}
	
	
	
	public ListCondicionesCiudadano adicionarCondicionCiudadano( String condicion, long cedula) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();          
            long tuplasInsertadas = sqlListCondicionesCiudadano.adicionarCondicionesCiudadano(pm, condicion, cedula);
            tx.commit();
            
            log.trace ("Inserción condicionCiudadano: " + cedula + ": " + tuplasInsertadas + " tuplas insertadas");
            return new ListCondicionesCiudadano(cedula, condicion);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
        
	}
	
	public Usuario darUsuarioPorUsername(String username)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando usuario \"" + username + "\"");
            tx.begin();
            Usuario usuario = sqlUsuario.darUsuario(pm, username);
            tx.commit();
            log.info ("Usuario buscado \"" + usuario.getUsername() + "\" ");
            
            return usuario;
        	
        	
        	
//        	tx.begin();
//        	long rta = sqlMinisterioSalud.agregarMinisterioDeSalud(pm, 1);
//        	System.out.println(rta);
//        	tx.commit();
//        	return null;
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/* ****************************************************************
	 * 			Métodos para manejar inicio sesion
	 *****************************************************************/
	

	public Usuario verificarInicioDeSesion(String username, String contrasena)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando usuario \"" + username + "\" con contrasena \""+ contrasena+ "\"");
            tx.begin();
            Usuario usuario = sqlUsuario.darUsuario(pm, username);
            tx.commit();
            log.info ("Usuario buscado \"" + usuario.getUsername() + "\" con contrasena \""+ usuario.getContrasena()+ "\"");
            
            return usuario;
        	
        	
        	
//        	tx.begin();
//        	long rta = sqlMinisterioSalud.agregarMinisterioDeSalud(pm, 1);
//        	System.out.println(rta);
//        	tx.commit();
//        	return null;
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/* ****************************************************************
	 * 			Métodos para manejar trabajo
	 *****************************************************************/

	public Trabajador buscarTrabajadorPorCedula(long cedula) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando el trabajo de la cedula " + cedula);
            tx.begin();
            Trabajador trabajador = sqlTrabajador.darTrabajador(pm, cedula);
            tx.commit();
            log.info ("Trabajador buscado por cedula " + cedula);
            
            return trabajador;
        	
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public Condicion adicionarCondicion(String condiciones, int etapa) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Agregando condicion" + condiciones);
            tx.begin();
            long tuplaInsertada = sqlCondicion.adicionarCondicion(pm, condiciones, etapa);
            tx.commit();
            log.info ("Inserción de la condicion: " + condiciones + ": " + tuplaInsertada + " tuplas insertadas");
            
            return new Condicion(condiciones, etapa);
        	
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	throw e;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public Condicion getCondicionPorCondiciones(String condiciones) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando la condicion llamada " + condiciones);
            tx.begin();
            Condicion condicion = sqlCondicion.darCondicionPorCondiciones(pm, condiciones);
            tx.commit();
            log.info ("Se encontró la condicion " + condiciones);
            
            return condicion;
        	
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public long updateCondicionPorCondiciones(String condiciones, int etapa) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Actualizando la condicion llamada: " + condiciones);
            tx.begin();
            long condicion = sqlCondicion.actualizarCondicionPorCondiciones(pm, condiciones, etapa);
            tx.commit();
            log.info ("Se actualizo la condicion " + condiciones + " a etapa " + etapa);
            
            return condicion;
        	
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	throw e;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public OficinaRegionalEPS adicionarOficinaRegional(String region, String administrador, int cantidad_vacunas_enviables, 
		int cantidad_vacunas_actuales, long plan_de_vacunacion) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Agregando oficina regional en la region " + region);
            tx.begin();
            long tuplaInsertada = sqlOficinaRegionalEPS.agregarOficinaRegional(pm, region, administrador, cantidad_vacunas_enviables, cantidad_vacunas_actuales, plan_de_vacunacion);
            tx.commit();
            log.info ("Inserción de la oficina en la region: " + region + ": " + tuplaInsertada + " tuplas insertadas");
            
            return new OficinaRegionalEPS(tuplaInsertada,region,administrador, cantidad_vacunas_enviables, cantidad_vacunas_actuales, plan_de_vacunacion);
        	
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	throw e;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public Usuario adicionarUsuario(String username, String contrasena, String correo, long plan_de_vacunacion,
			long ciudadano) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Agregando un nuevo usuario llamado: " + username);
            tx.begin();
            long tuplaInsertada = sqlUsuario.adicionarUsuario(pm, username, contrasena, correo, plan_de_vacunacion, ciudadano);
            tx.commit();
            log.info ("Inserción del usuario: " + username + ": " + tuplaInsertada + " tuplas insertadas");
            
            return new Usuario(username, contrasena, correo, plan_de_vacunacion, ciudadano);
        	
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	throw e;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public Ciudadano adicionarCiudadano(long cedula, String nombre_completo, String estado_vacunacion, String region,
			int desea_ser_vacunado, long plan_de_vacunacion, Long punto_vacunacion, Long oficina_regional_asignada) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Agregando un nuevo ciudadano llamado: " + nombre_completo);
            tx.begin();
            long tuplaInsertada = sqlCiudadano.adicionarCiudadano(pm, cedula, nombre_completo, estado_vacunacion, region, desea_ser_vacunado, plan_de_vacunacion, punto_vacunacion, oficina_regional_asignada);
            tx.commit();
            log.info ("Inserción del ciudadano de cedula: " + cedula + ": " + tuplaInsertada + " tuplas insertadas");
            
            return new Ciudadano(cedula, nombre_completo, estado_vacunacion, region, desea_ser_vacunado, plan_de_vacunacion, punto_vacunacion, oficina_regional_asignada);
        	
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	throw e;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public PuntoVacunacion adicionarPuntoVacunacion(String localizacion, int capacidad_de_atencion_simultanea,
			String infraestructura_para_dosis, int cantidad_vacunas_enviables,
			int cantidad_vacunas_actuales, String tipo_punto_vacunacion, String administrador, long oficina_regional_eps, int habilitado) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Agregando un nuevo punto de vacuancion en la localizacion: " + localizacion);
            tx.begin();
            long tuplaInsertada = sqlPuntoVacunacion.adicionarPuntoVacunacion(pm, localizacion, capacidad_de_atencion_simultanea, infraestructura_para_dosis, cantidad_vacunas_enviables, cantidad_vacunas_actuales, tipo_punto_vacunacion, administrador, oficina_regional_eps, habilitado);
            tx.commit();
            log.info ("Inserción del punto de vacunacion en: " + localizacion + ": " + tuplaInsertada + " tuplas insertadas");
            
            return new PuntoVacunacion(tuplaInsertada, localizacion, capacidad_de_atencion_simultanea, infraestructura_para_dosis, cantidad_vacunas_enviables, cantidad_vacunas_actuales, tipo_punto_vacunacion, administrador, oficina_regional_eps, habilitado);
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	throw e;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public Ciudadano buscarCiudadano(long cedula) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando el ciudadano de la cedula " + cedula);
            tx.begin();
            Ciudadano ciudadano = sqlCiudadano.darCiudadanoPorCedula(pm, cedula);
            tx.commit();
            log.info ("Trabajador buscado por cedula " + cedula);
            
            return ciudadano;
        	
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public long actualizarCiudadano(long cedula, String nombre_Completo, String estado_vacunacion,
			String region, int desea_ser_vacunado, long plan_De_Vacunacion, long punto_vacunacion,
			long oficina_Regional_Asignada){
	PersistenceManager pm = pmf.getPersistenceManager();
    Transaction tx=pm.currentTransaction();
    try
    {
    	log.info ("Actualizando el ciudadano de cedula: " + cedula + " con el punto de vacunación: " + punto_vacunacion);
        tx.begin();
        long condicion = sqlCiudadano.actualizarCiudadanoPorCedula(pm, cedula, nombre_Completo, estado_vacunacion, region, desea_ser_vacunado, plan_De_Vacunacion, punto_vacunacion, oficina_Regional_Asignada);
        tx.commit();
        log.info ("Se actualizo el ciudadano de cedula: " + cedula + " a punto vacunacion: " + punto_vacunacion);
        
        return condicion;
    	
    }
    catch (Exception e)
    {
    	// e.printStackTrace();
    	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
    	throw e;
    }
    finally
    {
        if (tx.isActive())
        {
            tx.rollback();
        }
        pm.close();
    }
}


	public Cita adicionarCita(Date fecha, long ciudadano, long punto_vacunacion, int hora_cita) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	Vacuna vacuna = sqlVacuna.darPrimeraVacunaPorPuntoDeVacunacion(pm, punto_vacunacion); 
        	log.info ("Agregando una nueva cita en el punto de vacunación: " + punto_vacunacion);
            tx.begin();
            long tuplaInsertada = sqlCita.adicionarCita(pm, fecha, ciudadano, punto_vacunacion, vacuna.getId_Vacuna(), hora_cita);
            PuntoVacunacion punto = sqlPuntoVacunacion.darPuntoPorId(pm, punto_vacunacion); 
            sqlPuntoVacunacion.disminuirVacunasDisponibles(pm, punto_vacunacion);
            //sqlOficinaRegionalEPS.disminuirVacunasDisponibles(pm, punto.getOficina_regional_eps());
            tx.commit();
            log.info ("Inserción de la cita en el punto: " + punto_vacunacion + ": " + tuplaInsertada + " tuplas insertadas");
            
            return new Cita(fecha, ciudadano, punto_vacunacion, vacuna.getId_Vacuna(), hora_cita);
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	throw e;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	
	
	public List<PlanDeVacunacion> darTodosLosPlanesDeVacunacion() {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando planesDeVacunacion en BD");
            tx.begin();
            List<PlanDeVacunacion> lista = sqlPlanDeVacunacion.darListPlanDeVacunacion(pm);
            tx.commit();
            
            return lista;
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public List<PuntoVacunacion> darTodosLosPuntosVacunacion() {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando PuntosVacunacion en BD");
            tx.begin();
            List<PuntoVacunacion> lista = sqlPuntoVacunacion.darListPuntoVacunacion(pm);
            tx.commit();
            
            return lista;
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public List<OficinaRegionalEPS> darTodasLasOficinasRegionalEPS() {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando todas las OficinaRegionalEPS en BD");
            tx.begin();
            List<OficinaRegionalEPS> lista = sqlOficinaRegionalEPS.darListOficinaRegional(pm);
            tx.commit();
            
            return lista;
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public List<EstadoVacunacion> darTodosLosEstadosVacunacion() {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando todas las condiciones en BD");
            tx.begin();
            List<EstadoVacunacion> lista = sqlEstadoVacunacion.darListEstadoVacunacion(pm);
            tx.commit();
            
            return lista;
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public List<PuntoVacunacion> darTodosLosPuntosVacunacionDeLaRegion(String region) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando PuntosVacunacion de la region " +  region +" en BD");
            tx.begin();
            List<PuntoVacunacion> lista = sqlPuntoVacunacion.darListPuntoVacunacionDeLaRegion(pm, region);
            tx.commit();
            
            return lista;
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public PuntoVacunacion darPuntoVacunacionPorId(long id_punto_vacunacion) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando el punto de vacunacion de id " + id_punto_vacunacion);
            tx.begin();
            PuntoVacunacion punto_vacunacion = sqlPuntoVacunacion.darPuntoPorId(pm, id_punto_vacunacion);
            tx.commit();
            log.info ("Se encontro el punto de vacunacion por el id" + id_punto_vacunacion);
            
            return punto_vacunacion;
        	
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public Trabajador adicionarTrabajador(long cedula, String trabajo, int administrador_vacuandes,
			long punto_vacunacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Agregando un nuevo trabajador con cedula: " + cedula);
            tx.begin();
            long tuplaInsertada = sqlTrabajador.adicionarTrabajador(pm, cedula, trabajo, administrador_vacuandes, punto_vacunacion);
            tx.commit();
            log.info ("Inserción del trabajador de cedula: " + cedula + ": " + tuplaInsertada + " tuplas insertadas");
            
            return new Trabajador(cedula, trabajo, administrador_vacuandes, punto_vacunacion);
        	
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	throw e;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public String darCiudadanosPuntoVacunacionPorFechaEspecifica(long punto_vacunacion, Date fecha_especifica) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	String rta = ""; 
        	log.info ("Buscando ciudadanos " +  punto_vacunacion );
            tx.begin();
            List<Cita> lista = sqlCita.darCiudadanosPuntoVacunacionYFecha(pm, punto_vacunacion, fecha_especifica);
            for(int i =0; i < lista.size(); i++)
            {
            	Cita act = lista.get(i);
            	Ciudadano ciudadano = sqlCiudadano.darCiudadanoPorCedula(pm, act.getCiudadano());
            	rta += "\n-" + ciudadano.toString(); 
            }
            tx.commit();
            
            return rta;
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public boolean verificarHorario(Date fecha, int hora_cita, long punto_vacunacion) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Verificando horario para la cita en el punto " + punto_vacunacion);
            tx.begin();
            boolean cantidadDisponible = true; 
            int cantidadCitasEnFechaYHora = sqlCita.darCantidadCitas(pm, fecha, hora_cita, punto_vacunacion);
            int cantidadCitasAlMismoTiempo = sqlPuntoVacunacion.darPuntoPorId(pm, punto_vacunacion).getCapacidad_de_Atencion_Simultanea();
            if(cantidadCitasEnFechaYHora+1 > cantidadCitasAlMismoTiempo)
            {
            	cantidadDisponible =false; 
            }
            List<Vacuna> vacunas = sqlVacuna.darVacunasDisponiblesPorPuntoDeVacunacion(pm, punto_vacunacion);
            int cantidadVacunasDisponibles = vacunas.size();
            boolean hayVacunas = (cantidadVacunasDisponibles>0)?true: false; 
            boolean rta = cantidadDisponible && hayVacunas; 
            
            tx.commit();
            log.info ("El punto de vacunacion tiene disponible horario y vacunas? " + rta);
            return rta;
        	
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return false;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public void adicionarCondicionesCiudadano(long cedula, List<String> condiciones) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            log.trace ("Insertando las condiciones a ciudadano");
            long tuplasInsertadas = 0; 
            for (int i = 0; i < condiciones.size(); i++) 
            {
            String condicion = condiciones.get(i); 
            tuplasInsertadas += sqlListCondicionesCiudadano.adicionarCondicionesCiudadano(pm, condicion, cedula);	
			}
            tx.commit();
            log.trace ("Inserción condicionCiudadano: " + cedula + ": " + tuplasInsertadas + " tuplas insertadas");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
        
	}


	public String darCiudadanosPuntoVacunacionPorRangoFechas(long punto_vacunacion, Date primera_fecha,
			Date segunda_fecha) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	String rta = ""; 
        	log.info ("Buscando ciudadanos en el rango de fechas en el punto: " +  punto_vacunacion );
            tx.begin();
            List<Cita> lista = sqlCita.darCiudadanosPuntoVacunacionYRangoFechas(pm, punto_vacunacion, primera_fecha, segunda_fecha);
            for(int i =0; i < lista.size(); i++)
            {
            	Cita act = lista.get(i);
            	Ciudadano ciudadano = sqlCiudadano.darCiudadanoPorCedula(pm, act.getCiudadano());
            	rta += "\n-" + ciudadano.toString(); 
            }
            tx.commit();
            
            return rta;
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public void limpiarParranderos ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			Query BORRAR = pm.newQuery(SQL, "drop table CIUDADANO CASCADE CONSTRAINTS;"
					+ "drop table USUARIO CASCADE CONSTRAINTS;"
					+ "drop table PLAN_DE_VACUNACION CASCADE CONSTRAINTS;"
					+ "drop table MINISTERIO_DE_SALUD CASCADE CONSTRAINTS;"
					+ "drop table PUNTO_VACUNACION CASCADE CONSTRAINTS;"
					+ "drop table CITA CASCADE CONSTRAINTS;"
					+ "drop table TRABAJADOR CASCADE CONSTRAINTS;"
					+ "drop table VACUNA CASCADE CONSTRAINTS;"
					+ "drop table OFICINA_REGIONAL_EPS CASCADE CONSTRAINTS;"
					+ "drop table LIST_CONDICIONES_CIUDADANO CASCADE CONSTRAINTS;"
					+ "drop table LIST_CONTRAINDICACIONES_VACUNA CASCADE CONSTRAINTS;"
					+ "drop table CONDICION CASCADE CONSTRAINTS;"
					+ "drop table TIPO_PUNTO_VACUNACION CASCADE CONSTRAINTS;"
					+ "drop table ESTADO_VACUNACION CASCADE CONSTRAINTS;"
					+ "drop table TRABAJO CASCADE CONSTRAINTS;"
					+ "drop table PRIORIZACION CASCADE CONSTRAINTS;");
			tx.commit ();
			log.info ("Borrada la base de datos");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}


	public String darCiudadanosPuntoVacunacionPorRangoHoras(long punto_vacunacion, int primera_hora, int segunda_hora) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	String rta = ""; 
        	log.info ("Buscando ciudadanos en el rango de horas en el punto: " +  punto_vacunacion );
            tx.begin();
            List<Cita> lista = sqlCita.darCiudadanosPuntoVacunacionYRangoHoras(pm, punto_vacunacion, primera_hora, segunda_hora);
            for(int i =0; i < lista.size(); i++)
            {
            	Cita act = lista.get(i);
            	Ciudadano ciudadano = sqlCiudadano.darCiudadanoPorCedula(pm, act.getCiudadano());
            	rta += "\n-" + ciudadano.toString(); 
            }
            tx.commit();
            
            return rta;
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	public String darCiudadanosPuntoVacunacion(long punto_vacunacion) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	String rta = ""; 
        	log.info ("Buscando ciudadanos en el punto: " +  punto_vacunacion );
            tx.begin();
            List<Long> lista = sqlCita.darCiudadanosPuntoVacunacion(pm, punto_vacunacion);
            for(int i =0; i < lista.size(); i++)
            {
            	Ciudadano ciudadanoAct = sqlCiudadano.darCiudadanoPorCedula(pm, lista.get(i)); 
            	rta = rta + ciudadanoAct.toString() + "\n"; 
            }
            tx.commit();
            
            return rta;
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	
	public String darPuntosMasEfectivos()
	{
		String rta = ""; 
		List<Object []> respuesta = new LinkedList <Object []> ();
		List<Object> tuplas = sqlCita.darPuntosMasEfectivos(pmf.getPersistenceManager());
        for ( Object tupla : tuplas)
        {
			Object [] datos = (Object []) tupla;
			long idPuntoVacunacion = ((BigDecimal) datos [0]).longValue ();
			int cantidad = (int) datos [1];
			rta += "\n-" + "Punto Vacunacion: " + idPuntoVacunacion + " Cantidad atendidos: " + cantidad; 
        }

		return rta;
	}
	
	//Dar todos los puntos de vacunación habilitados
	public List<PuntoVacunacion> darTodosLosPuntosVacunacionHabilitados()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando todos los puntos de vacunación");
            tx.begin();
            List<PuntoVacunacion> lista = sqlPuntoVacunacion.darListPuntoVacunacionHabilitados(pm);
            tx.commit();
            
            return lista;
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	public List<PuntoVacunacion> darTodosLosPuntosVacunacionDeshabilitados() {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando todos los puntos de vacunación");
            tx.begin();
            List<PuntoVacunacion> lista = sqlPuntoVacunacion.darListPuntoVacunacionDeshabilitados(pm);
            tx.commit();
            
            return lista;
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	
	//Dar todos los puntos de vacunación habilitados para una región especifica
	public List<PuntoVacunacion> darTodosLosPuntosVacunacionDeLaRegionHabilitados(String region) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando los puntos de vacunación que estén habilitados en la región " +  region +" en BD");
            tx.begin();
            List<PuntoVacunacion> lista = sqlPuntoVacunacion.darListPuntoVacunacionHabilitadosPorRegion(pm, region);
            tx.commit();
            
            return lista;
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	//Metodo que sirve para deshabilitar un punto de vacunación, lo que hace es: Elimina las citas que estén en ese punto de vacunación después de la fecha actual, cambia todos los ciudadanos a otro punto de vacunación que entra por parámetro
	// -1 si no se logró hacer, el id del punto si si sirvió
	public long deshabilitarPuntoVacunacion(long punto_vacunacion, long nuevo_punto)
	{
		long rta = punto_vacunacion; 
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            log.trace ("Deshabilitando el punto de vacunación de id: " + punto_vacunacion);
            long tuplasInsertadas = 0; 
            
            //Cambiamos a false el booleano de habilitado 
            tuplasInsertadas += sqlPuntoVacunacion.actualizarEstado(pm, punto_vacunacion, 0);	
            
            //Todos los ciudadanos se les cambia el punto de vacunación por el nuevo asignado
            tuplasInsertadas += sqlCiudadano.cambiarPuntosVacunacionCiudadanos(pm, punto_vacunacion, nuevo_punto);
            
            //Todas las citas que estaban con ese punto en una fecha mayor o igual a la actual se eliminan
            tuplasInsertadas += sqlCita.eliminarCitaPorPuntoVacunacionDesdeFechaActual(pm, punto_vacunacion);
            
            tx.commit();
            log.trace ("Se desahabilitó el punto de id: " + punto_vacunacion + "Se actualizaron: " + tuplasInsertadas + " tuplas cambiadas");
        }
        catch (Exception e)
        {	
        	rta = -1; 
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
        return rta; 
        
	}


	public long rehabilitarPuntoVacunacion(long punto_vacunacion) {
		long rta = punto_vacunacion; 
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            log.trace ("Rehabilitando el punto de vacunación de id: " + punto_vacunacion);
            long tuplasInsertadas = 0; 
            
            //Cambiamos a false el booleano de habilitado 
            tuplasInsertadas += sqlPuntoVacunacion.actualizarEstado(pm, punto_vacunacion, 1);	
            
            tx.commit();
            log.trace ("Se rehabilitó el punto de id: " + punto_vacunacion + "Se actualizaron: " + tuplasInsertadas + " tuplas cambiadas");
        }
        catch (Exception e)
        {	
        	rta = -1; 
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
        return rta; 
        
	}

	//Agrega las vacunas a la eps regional solamente, las vacunas solo se crean en la tabla vacunas cuando se agregan al punto de vacunación
	public long agregarVacunasEps(OficinaRegionalEPS oficinaRegionalEPS, int cantidad_vacunas, String condiciones_de_preservacion) {
		long rta = oficinaRegionalEPS.getId_oficina(); 
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            log.trace ("Agregando lote de vacunas a la eps regional de id: " + oficinaRegionalEPS.getId_oficina());
            long tuplasInsertadas = 0; 
            
            //Verificamos que no exceda la capacidad de la oficina
            if((oficinaRegionalEPS.getCantidad_Vacunas_Actuales()+ cantidad_vacunas)> oficinaRegionalEPS.getCantidad_Vacunas_Enviables())
            {
            	rta = -1; 
            	log.trace ("La eps regional de id: " + oficinaRegionalEPS.getId_oficina() + " no tiene suficiente capacidad para almacenar las vacunas");
            }
            else
            {
            	//Se necesita la información de las vacunas que van a llegar
            	tuplasInsertadas+= sqlOficinaRegionalEPS.agregarVacunasAOficina(pm, oficinaRegionalEPS.getId_oficina(), cantidad_vacunas); 
            	for (int i = 0; i < cantidad_vacunas; i++) 
            	{
					tuplasInsertadas += sqlVacuna.adicionarVacunaOficinaRegional(pm, condiciones_de_preservacion, oficinaRegionalEPS.getPlan_De_Vacunacion(), oficinaRegionalEPS.getId_oficina());
				}
            }
            
            tx.commit();
            log.trace ("Se agregaron las vacunas a la eps de id: " + oficinaRegionalEPS.getId_oficina() + "Se actualizaron: " + tuplasInsertadas + " tuplas cambiadas");
        }
        catch (Exception e)
        {	
        	rta = -1; 
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
        return rta; 
        
	}

	//Agrega las vacunas al punto de vacunación y luego las crea en la tabla de vacunas
	public long agregarVacunasPuntoVacunacion(PuntoVacunacion punto_vacunacion, int cantidad_vacunas) {
		long rta = punto_vacunacion.getId_Punto_Vacunacion(); 
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            log.trace ("Agregando lote de vacunas a punto de vacunacion de id: " + punto_vacunacion);
            long tuplasInsertadas = 0; 
            
            //Verificamos que no exceda las vacunas enviables y también que si se envían, la capacidad sea suficiente para guardar las actuales y las que llegarían
            if((punto_vacunacion.getCantidad_Vacunas_Actuales() + cantidad_vacunas) > punto_vacunacion.getCantidad_Vacunas_Enviables())
            {
            	rta = -1; 
            	log.trace ("El punto de vacunación de id: " + punto_vacunacion + " no tiene suficiente capacidad para almacenar las vacunas");
            }
            else
            {
            	tuplasInsertadas += sqlPuntoVacunacion.adicionarVacunasAPunto(pm, punto_vacunacion.getId_Punto_Vacunacion(), cantidad_vacunas); 
            	tuplasInsertadas += sqlOficinaRegionalEPS.disminuirVacunasDisponiblesConValor(pm, punto_vacunacion.getOficina_regional_eps(), cantidad_vacunas);
            }
            tx.commit();
            log.trace ("Se agregaron las vacunas al punto de id: " + punto_vacunacion.getId_Punto_Vacunacion() + "Se actualizaron: " + tuplasInsertadas + " tuplas cambiadas");
        }
        catch (Exception e)
        {	
        	rta = -1; 
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
        return rta; 
        
	}


	public OficinaRegionalEPS darOficinasRegionalEPSPorId(long idOficina) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando oficina regional con id " +idOficina);
            tx.begin();
            OficinaRegionalEPS oficina = sqlOficinaRegionalEPS.darOficinaPorId(pm, idOficina);
            tx.commit();
            
            return oficina;
        	
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}



	
	/**
	public Cita buscarCita(Date fecha, long ciudadano) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
        	log.info ("Buscando la cita del ciudadano con cedula: " + ciudadano);
            tx.begin();
            Cita cita = sqlCita.darCitaPorCiudadanoYFecha(pm, fecha, ciudadano);
            tx.commit();
            log.info ("Encontrada la cita del ciudadano de cedula: " + ciudadano);
            
            return cita;
        	
        }
        catch (Exception e)
        {
        	// e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
 */

}
