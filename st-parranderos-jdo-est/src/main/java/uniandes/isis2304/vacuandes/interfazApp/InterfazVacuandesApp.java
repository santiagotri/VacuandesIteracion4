/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.vacuandes.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.vacuandes.negocio.PlanDeVacunacion;
import uniandes.isis2304.vacuandes.negocio.PuntoVacunacion;
import uniandes.isis2304.vacuandes.negocio.Trabajador;
import uniandes.isis2304.vacuandes.negocio.Cita;
import uniandes.isis2304.vacuandes.negocio.Ciudadano;
import uniandes.isis2304.vacuandes.negocio.EstadoVacunacion;
import uniandes.isis2304.vacuandes.negocio.OficinaRegionalEPS;
import uniandes.isis2304.vacuandes.negocio.Usuario;
import uniandes.isis2304.vacuandes.negocio.Vacuandes;

/**
 * Clase principal de la interfaz
 * @author Germán Bravo
 */
@SuppressWarnings("serial")

public class InterfazVacuandesApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazVacuandesApp.class.getName());

	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 

	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 

	private static final String ADMINISTRADOR_PLAN_DE_VACUNACION = "Administrador plan de vacunacion";
	private static final String ADMINISTRADOR_PUNTO_VACUNACION = "Administrador punto vacunacion";
	private static final String ADMINISTRADOR_OFICINA_PUNTO_REGIONAL_EPS = "Administrador oficina punto regional eps";
	private static final String OPERADOR_PUNTO_VACUNACION = "Operador punto vacunacion";
	private static final String TALENTO_HUMANO_PUNTO_VACUNACION = "Talento humano punto vacunacion";


	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
	 */
	private JsonObject tableConfig;

	/**
	 * Asociación a la clase principal del negocio.
	 */
	private Vacuandes vacuandes;

	private InterfazDate interfazDate;
	private InterfazLogin interfazLogin;
	private InterfazCarga interfazCarga;
	private InterfazCargandoRequerimiento interfazCargandoRequerimiento;
	private Usuario usuarioActual;
	private Trabajador trabajadorActual;
	

	private String [] opciones1 = {
			"Adulto mayor (+80)", 
			"THS servicio social obligatorio", 
			"THS contacto directo", 
			"THS servicios generales", 
			"Tecnico y epidemiologo", 
			"Adulto mayor (60-79)", 
			"THS establecimiento carcelario", 
			"THS tradicional", 
			"THS estudiante", 
			"Condicion vulnerable", 
			"Agente educativo", 
			"Funcionario publico", 
			"THS funerarias", 
			"Privado de libertad", 
			"Bombero o socorrista", 
			"Habitante de calle", 
			"Trabajador aereo", 
			"Adulto (50-59)", 
	"Adulto (16-49)"};

	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
	/**
	 * Objeto JSON con la configuración de interfaz de la app.
	 */
	private JsonObject guiConfig;

	/**
	 * Panel de despliegue de interacción para los requerimientos
	 */
	private PanelDatos panelDatos;

	/**
	 * Menú de la aplicación
	 */
	private JMenuBar menuBar;
	
	

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Construye la ventana principal de la aplicación. <br>
	 * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
	 */
	public InterfazVacuandesApp( )
	{
		// Carga la configuración de la interfaz desde un archivo JSON
		guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);

		// Configura la apariencia del frame que contiene la interfaz gráfica
		configurarFrame ( );
		if (guiConfig != null) 	   
		{
			crearMenu( guiConfig.getAsJsonArray("menuBar") );
		}

		tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
		vacuandes = new Vacuandes (tableConfig);

		String path = guiConfig.get("bannerPath").getAsString();
		panelDatos = new PanelDatos ( );

		setLayout (new BorderLayout());
		add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
		add( panelDatos, BorderLayout.CENTER );
		moveToCenter(this);
		//interfazCarga = new InterfazCarga(); 
		interfazCargandoRequerimiento = new InterfazCargandoRequerimiento();
		
		
	}
	
	public static void moveToCenter(Window window) {
	      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	      window.setLocation(
	              (int) (screenSize.getWidth() / 2.0 - window.getWidth() / 2.0),
	              (int) (screenSize.getHeight() / 2.0 - window.getHeight() / 2.0));
	  }

	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
	/**
	 * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
	 * @param tipo - El tipo de configuración deseada
	 * @param archConfig - Archivo Json que contiene la configuración
	 * @return Un objeto JSON con la configuración del tipo especificado
	 * 			NULL si hay un error en el archivo.
	 */
	private JsonObject openConfig (String tipo, String archConfig)
	{
		JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
			//			// e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}

	/**
	 * Método para configurar el frame principal de la aplicación
	 */
	private void configurarFrame(  )
	{
		int alto = 0;
		int ancho = 0;
		String titulo = "";	

		if ( guiConfig == null )
		{
			log.info ( "Se aplica configuración por defecto" );			
			titulo = "Vacuandes APP Default";
			alto = 300;
			ancho = 500;
		}
		else
		{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
			titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
		}

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLocation (50,50);
		setResizable( true );
		setBackground( Color.WHITE );

		setTitle( titulo );
		setSize ( ancho, alto);        
	}

	/**
	 * Método para crear el menú de la aplicación con base em el objeto JSON leído
	 * Genera una barra de menú y los menús con sus respectivas opciones
	 * @param jsonMenu - Arreglo Json con los menùs deseados
	 */
	private void crearMenu(  JsonArray jsonMenu )
	{    	
		// Creación de la barra de menús
		menuBar = new JMenuBar();       
		for (JsonElement men : jsonMenu)
		{
			// Creación de cada uno de los menús
			JsonObject jom = men.getAsJsonObject(); 

			String menuTitle = jom.get("menuTitle").getAsString();        	
			JsonArray opciones = jom.getAsJsonArray("options");

			JMenu menu = new JMenu( menuTitle);

			for (JsonElement op : opciones)
			{       	
				// Creación de cada una de las opciones del menú
				JsonObject jo = op.getAsJsonObject(); 
				String lb =   jo.get("label").getAsString();
				String event = jo.get("event").getAsString();

				JMenuItem mItem = new JMenuItem( lb );
				mItem.addActionListener( this );
				mItem.setActionCommand(event);

				menu.add(mItem);
			}       
			menuBar.add( menu );
		}        
		setJMenuBar ( menuBar );	
	}


	/* ****************************************************************
	 * 			CRUD de TipoBebida
	 *****************************************************************/
	//RF1
	public void registrarCondicionesDePriorizacion() {
		if (trabajadorActual!=null) {
			if(trabajadorActual.getTrabajo().equals(ADMINISTRADOR_PLAN_DE_VACUNACION)) VerificadoRegistrarCondicionesDePriorizacion();
			else {JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);}
		}else {
			JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
		}

	}
	private void VerificadoRegistrarCondicionesDePriorizacion() {
		try {
			JComboBox optionList1 = new JComboBox(opciones1);
			optionList1.setSelectedIndex(0);
			JOptionPane.showMessageDialog(this, "Seleccione la condicion que desea priorizar", "Seleccione condicion", JOptionPane.QUESTION_MESSAGE);
			JOptionPane.showMessageDialog(this, optionList1, "Seleccione condicion", JOptionPane.QUESTION_MESSAGE);

			String [] opciones2 = {"1","2","3","4","5"};
			JComboBox optionList2 = new JComboBox(opciones2);
			optionList2.setSelectedIndex(0);
			JOptionPane.showMessageDialog(this, "Seleccione la etapa de priorizacion", "Seleccione condicion", JOptionPane.QUESTION_MESSAGE);
			JOptionPane.showMessageDialog(this, optionList2, "Seleccione etapa", JOptionPane.QUESTION_MESSAGE);

			vacuandes.registrarCondicionesDePriorizacion(optionList1.getSelectedItem().toString(), Integer.parseInt(optionList2.getSelectedItem().toString()));
			//System.out.println(optionList1.getSelectedItem().toString() + Integer.parseInt(optionList2.getSelectedItem().toString()));

			String resultado = "Condicion escogida : " + optionList1.getSelectedItem();
			resultado += "\nEtapa asignada: Etapa " + optionList2.getSelectedItem();
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		}
		catch(Exception e) {

			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);

		}
	}

	//RF2
	public void registrarSecuenciaDeEstadosValidos() {
		if (trabajadorActual!=null) {
			if(trabajadorActual.getTrabajo().equals(ADMINISTRADOR_PLAN_DE_VACUNACION)) VerificadoRegistrarSecuenciaDeEstadosValidos();
			else {JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);}
		}else {
			JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void VerificadoRegistrarSecuenciaDeEstadosValidos() {
		try {
			JOptionPane.showMessageDialog(this, "Ingrese los estados validos dentro del proceso de vacunacion de una persona", "Ingrese estados", JOptionPane.QUESTION_MESSAGE);

			ArrayList<String> estados = new ArrayList<String>();
			boolean termino = false;
			while(!termino) {
				String nombreTipo = JOptionPane.showInputDialog (this, "Nombre del estado a añadir", "Adicionar estado", JOptionPane.QUESTION_MESSAGE);
				if(nombreTipo.isBlank()) {
					JOptionPane.showMessageDialog(this, "El estado no puede estar vacío", "Estado vacío", JOptionPane.ERROR_MESSAGE);
				}else {
					estados.add(nombreTipo);
				}
				interfazCargandoRequerimiento.mostrar();
				int rta = JOptionPane.showConfirmDialog(this, "¿Desea añadir otro estado?", "", JOptionPane.YES_NO_OPTION);
				if (rta==1) termino=true;
			}

			if(estados.isEmpty()) {
				String resultado = "No se han añadido estados para el proceso de vacunacion de un ciudadano";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else {
				interfazCargandoRequerimiento.traerAlfrente();
				for (String act : estados) {
					vacuandes.agregarEstadoVacunacion(act);
				}
				


				String resultado = "Secuencia de estados validos añadidos : " + estados.toString();
				resultado += "\nEn total se han añadido " + estados.size() + " estados para el proceso de vacunacion";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
				interfazCargandoRequerimiento.ocultar();

			}
		}

		catch(Exception e) {

			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);

		}

	}

	//RF3
	public void registrarOficinaDeEPSRegional() {
		if (trabajadorActual!=null) {
			if(trabajadorActual.getTrabajo().equals(ADMINISTRADOR_PLAN_DE_VACUNACION)) VerificadoRegistrarOficinaDeEPSRegional();
			else {JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);}
		}else {
			JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void VerificadoRegistrarOficinaDeEPSRegional() {
		try {
			JOptionPane.showMessageDialog(this, "Ingrese los datos de la oficina regional EPS que desea registrar", "Ingrese datos oficina", JOptionPane.QUESTION_MESSAGE);
			String region = JOptionPane.showInputDialog (this, "Region de la oficina", "Adicionar oficina", JOptionPane.QUESTION_MESSAGE);
			String usernameAdministrador = JOptionPane.showInputDialog (this, "Username del administrador de la oficina", "Adicionar oficina", JOptionPane.QUESTION_MESSAGE);
			String cantidadVacunasEnviables = mostrarMensajeIntroducirTexto("Cantidad vacunas enviables", "Ingrese la cantidad de vacunas que puede tener esta oficina regional a la vez");
			Long plan_de_vacunacion = escogerPlanDeVacunacion();
			Usuario usuarioAdministrador = vacuandes.darUsuarioPorUsername(usernameAdministrador);
			Trabajador trabajadorAdministrador = null;
			if(usuarioAdministrador!=null) trabajadorAdministrador = vacuandes.darTrabajadorPorCedula(usuarioAdministrador.getCiudadano());
			String resultado = "";

			if(usuarioAdministrador==null) {
				JOptionPane.showMessageDialog(this, "No existe el usuario " + usernameAdministrador, "Usuario inexistente", JOptionPane.ERROR_MESSAGE);
				resultado = "-- El usuario ingresado como adminstrador no existe --";
				resultado += "\n Operación terminada";
			}else if(trabajadorAdministrador==null){
				JOptionPane.showMessageDialog(this, "No existe el trabajador con usuario " + usernameAdministrador, "Usuario no trabajador", JOptionPane.ERROR_MESSAGE);
				resultado = "-- El usuario ingresado no es un trabajador --";
				resultado += "\n Operación terminada";
			}else if(!trabajadorAdministrador.getTrabajo().equals(ADMINISTRADOR_OFICINA_PUNTO_REGIONAL_EPS)) {
				JOptionPane.showMessageDialog(this, "El usuario " + usernameAdministrador + " trabaja como " + trabajadorAdministrador.getTrabajo()+ ". Es necesario que sea un " + ADMINISTRADOR_OFICINA_PUNTO_REGIONAL_EPS, "Trabajador no indicado", JOptionPane.ERROR_MESSAGE);
				resultado = "-- El trabajador no tenia el cargo indicado para ser administrador de la oficina --";
				resultado += "\n Operación terminada";
			}else {
				OficinaRegionalEPS nueva = vacuandes.agregarOficinaRegional(region, usernameAdministrador,Integer.parseInt(cantidadVacunasEnviables), 0, plan_de_vacunacion);
				resultado = "-- Se ha añadido una oficina regional --";
				resultado += "\n - Region: " + nueva.getRegion();
				resultado += "\n - Administrador: " + nueva.getAdministrador();
				resultado += "\n - Id plan de vacunacion: " + nueva.getPlan_De_Vacunacion();
				resultado += "\n Operación terminada";
			}

			panelDatos.actualizarInterfaz(resultado);
		}
		catch(Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	//RF4
	public void registrarUsuarioDeVacuandes() {
		if (trabajadorActual!=null) {
			if(trabajadorActual.getTrabajo().equals(ADMINISTRADOR_PLAN_DE_VACUNACION)) VerificadoRegistrarUsuarioDeVacuandes();
			else {JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);}
		}else {
			JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void VerificadoRegistrarUsuarioDeVacuandes() {
		try {
			JOptionPane.showMessageDialog(this, "Ingrese los datos del usuario a registrar", "Ingrese datos usuario", JOptionPane.QUESTION_MESSAGE);
			String username = JOptionPane.showInputDialog (this, "Ingresa username", "Adicionar usuario", JOptionPane.QUESTION_MESSAGE);
			String usernameConfirmacion = JOptionPane.showInputDialog (this, "Ingresa confirmacion username", "Adicionar usuario", JOptionPane.QUESTION_MESSAGE);
			String contrasena = JOptionPane.showInputDialog (this, "Ingresa contraseña para tu usuario", "Adicionar usuario", JOptionPane.QUESTION_MESSAGE);
			String contrasenaConfirmacion = JOptionPane.showInputDialog (this, "Ingresa confirmacion contraseña", "Adicionar usuario", JOptionPane.QUESTION_MESSAGE);
			if (!username.equals(usernameConfirmacion) || !contrasena.equals(contrasenaConfirmacion)) {
				if (!username.equals(usernameConfirmacion))JOptionPane.showMessageDialog(this, "Se ha n ingresado usuarios diferentes, operacion cancelada", "Error de digitacion", JOptionPane.ERROR_MESSAGE);
				if (!contrasena.equals(contrasenaConfirmacion))JOptionPane.showMessageDialog(this, "Se ha n ingresado contraseñas diferentes, operacion cancelada", "Error de digitacion", JOptionPane.ERROR_MESSAGE);
				String resultado = "Ha existido un error de digacion";
				resultado += "\n Operacion cancelada";
				panelDatos.actualizarInterfaz(resultado);
			}else {
				String correo = JOptionPane.showInputDialog (this, "Correo del usuario a añadir", "Adicionar usuario", JOptionPane.QUESTION_MESSAGE);
				long plan_de_vacunacion = escogerPlanDeVacunacion();
				Long cedula = Long.parseLong( JOptionPane.showInputDialog (this, "Cedula del ciudadano", "Adicionar usuario", JOptionPane.QUESTION_MESSAGE));
				Usuario nuevo = vacuandes.agregarUsuarioVacuandes(username, contrasena, correo, plan_de_vacunacion, cedula);

				String resultado = "-- Se ha añadido un usuario a vacuandes --";
				resultado += "\n - Username: " + nuevo.getUsername();
				resultado += "\n - Contrasena: *********";
				resultado += "\n - Correo: " + nuevo.getCorreo();
				resultado += "\n - Cedula ciudadano: " + nuevo.getCiudadano();
				resultado += "\n - Id Plan de vacunacion: " + nuevo.getPlan_de_vacunacion();
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	//RF5
	public void registrarCiudadanosColombianos() {

		if (trabajadorActual!=null) {
			if(trabajadorActual.getTrabajo().equals(ADMINISTRADOR_PLAN_DE_VACUNACION)) VerificadoRegistrarCiudadanosColombianos();
			else {JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);}
		}else {
			JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void VerificadoRegistrarCiudadanosColombianos() {
		try {
			JOptionPane.showMessageDialog(this, "Ingrese los datos del ciudadano a registrar", "Ingrese datos ciudadano", JOptionPane.QUESTION_MESSAGE);
			String nombre = JOptionPane.showInputDialog (this, "Ingresa nombre completo", "Adicionar ciudadano", JOptionPane.QUESTION_MESSAGE);
			Long cedula = Long.parseLong( JOptionPane.showInputDialog (this, "Ingresa cedula del ciudadano", "Adicionar ciudadano", JOptionPane.QUESTION_MESSAGE));
			String estado_vacunacion = escogerEstadoVacunacionConNull();
			String region = escogerRegion();
			int desea_ser_vacunado = JOptionPane.showConfirmDialog(this, "¿Desea ser vacunado?", "", JOptionPane.YES_NO_OPTION);
			if(desea_ser_vacunado==1) desea_ser_vacunado = 0;
			else if(desea_ser_vacunado==0) desea_ser_vacunado = 1;
			long plan_de_vacunacion = escogerPlanDeVacunacion();
			long oficina_regional_asignada = asignarPorRegionOficinaRegionalEPS(region);

			ArrayList<String> condicionesAAñadir = new ArrayList<String>();
			boolean termino = false;
			while(!termino) {
				JComboBox optionList1 = new JComboBox(opciones1);
				JOptionPane.showMessageDialog(this, optionList1, "Seleccione condicion", JOptionPane.QUESTION_MESSAGE);
				condicionesAAñadir.add(opciones1[optionList1.getSelectedIndex()]);
				int rta = JOptionPane.showConfirmDialog(this, "¿Desea añadir otra condicion?", "", JOptionPane.YES_NO_OPTION);
				if (rta==1) termino=true;
			}



			Ciudadano nuevo = vacuandes.agregarCiudadano(cedula, nombre, estado_vacunacion, region, desea_ser_vacunado, plan_de_vacunacion,null, oficina_regional_asignada);
			if (nuevo!=null) vacuandes.agregarCondicionesCiudadano(nuevo.getCedula(), condicionesAAñadir);

			String resultado = "Se ha añadido un ciudadano a vacuandes: ";
			resultado += "\n - Nombre: " + nuevo.getNombre_Completo();
			resultado += "\n - Cedula: " + nuevo.getCedula();
			resultado += "\n - estado vacunacion: " + nuevo.getEstado_vacunacion();
			resultado += "\n - region: " + nuevo.getRegion();
			resultado += "\n - Desea ser vacunado (1 si, 0 no): " + nuevo.getDesea_ser_vacunado();
			resultado += "\n - Id plan de vacunacion: "+nuevo.getPlan_De_Vacunacion();
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);

		}
		catch(Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	//RF6
	public void registarPuntoDeVacunacion() {
		if (trabajadorActual!=null) {
			if(trabajadorActual.getTrabajo().equals(ADMINISTRADOR_OFICINA_PUNTO_REGIONAL_EPS)) VerificadoRegistarPuntoDeVacunacion();
			else {JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);}
		}else {
			JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void VerificadoRegistarPuntoDeVacunacion () {

		try {
			JOptionPane.showMessageDialog(this, "Ingrese los datos del punto vacunacion a registrar", "Ingrese datos punto vacunacion", JOptionPane.QUESTION_MESSAGE);
			String localizacion = JOptionPane.showInputDialog (this, "Ingresa localizacion", "Adicionar punto vacunacion", JOptionPane.QUESTION_MESSAGE);
			int capacidad_atencion_simultanea =Integer.parseInt(JOptionPane.showInputDialog (this, "Ingresar la capacidad de atencion simultanea (numero)", "Adicionar punto vacunacion", JOptionPane.QUESTION_MESSAGE));
			int cantidad_vacunas_enviables = Integer.parseInt(JOptionPane.showInputDialog (this, "Ingresar la cantidad de vacunas enviables", "Adicionar punto vacunacion", JOptionPane.QUESTION_MESSAGE));
			String infraestructura_para_dosis = JOptionPane.showInputDialog (this, "Ingresar una descripcion de la infraestructura que tiene para almacenar las dosis", "Adicionar punto vacunacion", JOptionPane.QUESTION_MESSAGE);

			String [] opciones1 = {
					"Hospital", 
					"Clinica", 
					"Centro de salud", 
			"Otro"};
			JComboBox optionList1 = new JComboBox(opciones1);
			optionList1.setSelectedIndex(0);
			JOptionPane.showMessageDialog(this, "Seleccione el tipo del punto de vacunacion", "Seleccione tipo punto", JOptionPane.QUESTION_MESSAGE);
			JOptionPane.showMessageDialog(this, optionList1, "Seleccione tipo punto", JOptionPane.QUESTION_MESSAGE);

			int habilitado = JOptionPane.showConfirmDialog(this, "¿Desea iniciar este punto de vacunacion como habilitado?", "Estado del punto", JOptionPane.YES_NO_OPTION);
			if (habilitado==1) habilitado =0;
			else if (habilitado==0) habilitado =1;

			String tipo_punto_vacunacion = opciones1[optionList1.getSelectedIndex()];
			String usernameAdministrador = JOptionPane.showInputDialog (this, "Ingresa username administrador", "Adicionar punto vacunacion", JOptionPane.QUESTION_MESSAGE);
			String region = escogerRegion();
			long oficina_regional_eps = asignarPorRegionOficinaRegionalEPS(region);

			Usuario usuarioAdministrador = vacuandes.darUsuarioPorUsername(usernameAdministrador);
			Trabajador trabajadorAdministrador = null;
			if(usuarioAdministrador!=null) trabajadorAdministrador = vacuandes.darTrabajadorPorCedula(usuarioAdministrador.getCiudadano());
			String resultado = "";

			if(usuarioAdministrador==null) {
				JOptionPane.showMessageDialog(this, "No existe el usuario " + usernameAdministrador, "Usuario inexistente", JOptionPane.ERROR_MESSAGE);
				resultado = "-- El usuario ingresado como adminstrador no existe --";
				resultado += "\n Operación terminada";
			}else if(trabajadorAdministrador==null){
				JOptionPane.showMessageDialog(this, "No existe el trabajador con usuario " + usernameAdministrador, "Usuario no trabajador", JOptionPane.ERROR_MESSAGE);
				resultado = "-- El usuario ingresado no es un trabajador --";
				resultado += "\n Operación terminada";
			}else if(!trabajadorAdministrador.getTrabajo().equals(ADMINISTRADOR_PUNTO_VACUNACION)) {
				JOptionPane.showMessageDialog(this, "El usuario " + usernameAdministrador + " trabaja como " + trabajadorAdministrador.getTrabajo()+ ". Es necesario que sea un " + ADMINISTRADOR_PUNTO_VACUNACION, "Trabajador no indicado", JOptionPane.ERROR_MESSAGE);
				resultado = "-- El trabajador no tenia el cargo indicado para ser administrador de la oficina --";
				resultado += "\n Operación terminada";
			}else {
				PuntoVacunacion nuevo = vacuandes.agregarPuntoVacunacion(localizacion, capacidad_atencion_simultanea, infraestructura_para_dosis, cantidad_vacunas_enviables, 0, tipo_punto_vacunacion, usernameAdministrador, oficina_regional_eps, habilitado);

				resultado = "-- Se ha añadido un punto de vacunacion-- ";
				resultado += "\n - Tipo punto vacunacion: " + nuevo.getTipo_Punto_Vacunacion();
				resultado += "\n - Localizacion: " + nuevo.getLocalizacion();
				resultado += "\n - capacidad de atencion simultanea: " + nuevo.getCapacidad_de_Atencion_Simultanea();
				resultado += "\n - cantidad vacunas enviables: " + nuevo.getCantidad_Vacunas_Enviables();
				resultado += "\n - infraestructura para dosis: " + nuevo.getInfraestructura_Para_Dosis();
				resultado += "\n - username administrador: "+nuevo.getAdministrador();
				resultado += "\n - habilitado (1=si, 0=no): "+nuevo.getHabilitado();
				resultado += "\n Operación terminada";
			}

			panelDatos.actualizarInterfaz(resultado);

		}
		catch(Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	//RF8
	public void registrarLlegadaLoteVacunasEPSRegional() {
		//No especifica quienes lo pueden hacer o quienes no
		VerificadoRegistrarLlegadaLoteVacunasEPSRegional();
	}
	private void VerificadoRegistrarLlegadaLoteVacunasEPSRegional() {
		try {
			long idOficina = escogerOficinaRegionalEPS();
			OficinaRegionalEPS oficinaAct = vacuandes.darOficinaRegionalEPSPorId(idOficina);
			String cantidadVacunas = mostrarMensajeIntroducirTexto("Cantidad de vacunas que llegaran", "Introduzca el numero de la cantidad de vacunas que llegaran (Ocuppación actual "+oficinaAct.getCantidad_Vacunas_Actuales() + "/" + oficinaAct.getCantidad_Vacunas_Enviables() +")");
			interfazCargandoRequerimiento.mostrar();
			String condiciones_de_preservacion = mostrarMensajeIntroducirTexto("Condiciones preservación", "Ingrese las condiciones de preservación de TODAS las vacunas que llegaran");
			interfazCargandoRequerimiento.traerAlfrente();
			 
			long rta = vacuandes.registrarLlegadaDeLoteDeVacunasEPS(oficinaAct, Integer.parseInt(cantidadVacunas), condiciones_de_preservacion);
			String resultado ="";
			if(idOficina != rta) {
				resultado += "-- Ha surgido un error con la cantidad de vacunas que se han intentado ingresar -- ";
				resultado += "\nOperacion cancelada ";
			}else {
				oficinaAct = vacuandes.darOficinaRegionalEPSPorId(idOficina);
				resultado += "-- Se han registrado correctamente la llegada del lote de vacunas -- ";
				resultado += "\n - Region: " + oficinaAct.getRegion();
				resultado += "\n - id de la Oficina: " + oficinaAct.getId_oficina();
				resultado += "\n - Condiciones de preservación de las vacunas: " + condiciones_de_preservacion;
				resultado += "\n - Cantidad vacunas nuevas: " + cantidadVacunas;
				resultado += "\n - Cantidad vacunas actuales en la oficina: "+ oficinaAct.getCantidad_Vacunas_Actuales();
				resultado += "\n Operación terminada";

			}
			panelDatos.actualizarInterfaz(resultado);
			interfazCargandoRequerimiento.ocultar();
			
		}

		catch(Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
			interfazCargandoRequerimiento.ocultar();
		}
	}

	//RF9
	public void registrarLlegadaLoteVacunasPuntoVacunacion() {
		if(verificarPermisos(ADMINISTRADOR_PUNTO_VACUNACION)) VerificadoRegistrarLlegadaLoteVacunasPuntoVacunacion();
	}
	private void VerificadoRegistrarLlegadaLoteVacunasPuntoVacunacion() {
		
		try {
			long idOficina = escogerOficinaRegionalEPS();
			OficinaRegionalEPS oficinaAct = vacuandes.darOficinaRegionalEPSPorId(idOficina);
			long idPuntoVacunacion = escogerPuntoVacunacionPorRegion(oficinaAct.getRegion());
			PuntoVacunacion puntoVacunacion = vacuandes.darPuntoVacunacionPorId(idPuntoVacunacion);
			String cantidadVacunas = mostrarMensajeIntroducirTexto("Cantidad de vacunas que llegaran", "Introduzca el numero de la cantidad de vacunas que llegaran (Ocupación punto vacunacion "+puntoVacunacion.getCantidad_Vacunas_Actuales() + "/" + puntoVacunacion.getCantidad_Vacunas_Enviables() +", Vacunas disponibles en of. regional "+oficinaAct.getCantidad_Vacunas_Actuales() +")");
			long rta = vacuandes.registrarLlegadaDeLoteDeVacunasAPuntoVacunacion(puntoVacunacion, Integer.parseInt(cantidadVacunas));
		
			puntoVacunacion = vacuandes.darPuntoVacunacionPorId(idPuntoVacunacion);
			String resultado = "-- Se han registrado correctamente la llegada del lote de vacunas -- ";
			resultado += "\n - Region: " + oficinaAct.getRegion();
			resultado += "\n - id de la Oficina: " + oficinaAct.getId_oficina();
			resultado += "\n - Localizacion punto vacunacion: " + puntoVacunacion.getLocalizacion();
			resultado += "\n - id del punto vacunacion: " + puntoVacunacion.getId_Punto_Vacunacion();
			resultado += "\n - Cantidad vacunas nuevas: " + cantidadVacunas;
			resultado += "\n - Cantidad vacunas actuales en la oficina: " + puntoVacunacion.getCantidad_Vacunas_Actuales();
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		}

		catch(Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	

	//RF 12
	public void registrarAvanceEnVacunacionDePersona() {
		if (trabajadorActual!=null) {
			if(trabajadorActual.getTrabajo().equals(OPERADOR_PUNTO_VACUNACION)) VerificadoRegistrarAvanceEnVacunacionDePersona();
			else {JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);}
		}else {
			JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void VerificadoRegistrarAvanceEnVacunacionDePersona() {
		try {
			long cedula = Long.parseLong(JOptionPane.showInputDialog (this, "Ingresa la cedula del ciudadano", "Registrar avance", JOptionPane.QUESTION_MESSAGE));
			Ciudadano act = vacuandes.darCiudadanoPorCedula(cedula);

			if (act == null) {
				JOptionPane.showMessageDialog(this, "No existe un ciudadano con la cedula " +cedula, "Ciudadano no existe", JOptionPane.ERROR_MESSAGE);
				String resultado = "Se ha introducido una cedula inexistente en la BD";
				resultado += "\n Operación terminada";
			}else if(act.getPunto_Vacunacion()==null) {
				JOptionPane.showMessageDialog(this, "El ciudadano " +act.getNombre_Completo() + " aun no tiene un punto de vacunacion asignado, por lo que no puede actualizarse su estado de vacunacion", "Ciudadano sin punto vacunacion", JOptionPane.ERROR_MESSAGE);
				String resultado = "El ciudadano no tiene aun un punto de vacunacion asignado";
				resultado += "\n Operación terminada";
			}
			else {
				String estadoAnterior = null;
				if(act.getEstado_vacunacion()==null) {
					JOptionPane.showMessageDialog(this, "No se ha decidido anteriormente el estado de vacunacion de este ciudadano, ingrese el nuevo estado de vacunacion", "Nuevo estado vacunacion", JOptionPane.INFORMATION_MESSAGE);

				}else {
					estadoAnterior = act.getEstado_vacunacion();
					JOptionPane.showMessageDialog(this, "Actualmente el ciudadano se encuentra en estado " + act.getEstado_vacunacion() +". Escoja el nuevo estado del paciente.", "Actualizar estado vacunacion", JOptionPane.INFORMATION_MESSAGE);
				}
				String estado_vacunacion = escogerEstadoVacunacion();
				vacuandes.actualizarEstadoVacunacionCiudadano(act.getCedula(),estado_vacunacion);

				String resultado = "-- Se ha registrado el prograso en el avance de la vacunacion -- ";
				resultado += "\n - Nombre completo: " + act.getNombre_Completo();
				resultado += "\n - Cedula: " + act.getCedula();
				if(estadoAnterior !=null) resultado += "\n - Estado anterior: " + estadoAnterior;
				resultado += "\n - Nuevo estado: " + estado_vacunacion;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void cambiarOpinionVacunacionCiudadano() {
		if (trabajadorActual!=null) {
			if(trabajadorActual.getTrabajo().equals(OPERADOR_PUNTO_VACUNACION)) VerificadoCambiarOpinionVacunacionCiudadano();
			else {JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);}
		}else {
			JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void VerificadoCambiarOpinionVacunacionCiudadano() {
		try {
			long cedula = Long.parseLong(JOptionPane.showInputDialog (this, "Ingresa la cedula del ciudadano", "Registrar avance", JOptionPane.QUESTION_MESSAGE));
			Ciudadano act = vacuandes.darCiudadanoPorCedula(cedula);

			if (act == null) {
				JOptionPane.showMessageDialog(this, "No existe un ciudadano con la cedula " +cedula, "Ciudadano no existe", JOptionPane.ERROR_MESSAGE);
				String resultado = "Se ha introducido una cedula inexistente en la BD";
				resultado += "\n Operación terminada";
			}
			else {
				int estadoAnterior = -1;
				if(act.getDesea_ser_vacunado()!=1 && act.getDesea_ser_vacunado()!=0) {//OR
					JOptionPane.showMessageDialog(this, "No se ha decidido anteriormente el estado de vacunacion de este ciudadano, ingrese el nuevo estado de vacunacion", "Nuevo estado vacunacion", JOptionPane.INFORMATION_MESSAGE);

				}else {
					estadoAnterior = act.getDesea_ser_vacunado();
					if(estadoAnterior==1) JOptionPane.showMessageDialog(this, "Actualmente el ciudadano SI quiere ser vacunado. Escoja el nuevo estado del paciente.", "Actualizar estado vacunacion", JOptionPane.INFORMATION_MESSAGE);
					else if(estadoAnterior==0) JOptionPane.showMessageDialog(this, "Actualmente el ciudadano NO quiere ser vacunado. Escoja el nuevo estado del paciente.", "Actualizar estado vacunacion", JOptionPane.INFORMATION_MESSAGE);
					else {
						JOptionPane.showMessageDialog(this, "error desconocido intentelo más tarde", "error", JOptionPane.ERROR_MESSAGE);
					}
				}
				int rta = JOptionPane.showConfirmDialog(this, "¿Desea ser vacunado?", "", JOptionPane.YES_NO_OPTION);
				if (rta==1) rta =0;
				else if (rta==0) rta =1;

				vacuandes.actualizarOpinionVacunacionCiudadano(cedula, rta);

				String resultado = "-- Se ha actualizado la opinion de vacunacion del ciudadano -- ";
				resultado += "\n - Nombre completo: " + act.getNombre_Completo();
				resultado += "\n - Cedula: " + act.getCedula();
				if(estadoAnterior !=-1) resultado += "\n - Estado anterior (1=si, 0=no): " + estadoAnterior;
				resultado += "\n - Nuevo estado (1=si, 0=no):: " + rta;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	//RF13,14,15 PENDIENTE
	public void registrarCambioEstadoPuntoVacunacion() {
		if(verificarPermisos(ADMINISTRADOR_OFICINA_PUNTO_REGIONAL_EPS)) VerificadoRegistrarCambioEstadoPuntoVacunacion();
	}
	private void VerificadoRegistrarCambioEstadoPuntoVacunacion() {
		int opcion = mostrarMensajeBooleano("Escoger Accion", "¿Desea deshabilitar un punto que se encuentra habilitado? (Si deshabilitar, no habilitar un punto)");
		String resultado ="";
		if(opcion ==1) {
			mostrarMensajeInformativo("Escoger punto a deshabilitar", "Escoja primero el punto que se desea deshabilitar");
			long idPuntoVacunacionADeshabilitar = escogerPuntoVacunacion();
			PuntoVacunacion puntoVacunacionADeshabilitar = vacuandes.darPuntoVacunacionPorId(idPuntoVacunacionADeshabilitar);
			OficinaRegionalEPS oficinaPunto = vacuandes.darOficinaRegionalEPSPorId(puntoVacunacionADeshabilitar.getOficina_regional_eps());
			mostrarMensajeInformativo("Escoger punto reemplazo", "Escoger punto al que se trasladaran los ciudadanos asignados a ese punto");
			
			long idPuntoVacunacionNuevo = escogerPuntoVacunacionPorRegion(oficinaPunto.getRegion());
			if(idPuntoVacunacionADeshabilitar==idPuntoVacunacionNuevo) {
				mostrarMensajeError("Error mismo punto", "Ha escogido el mismo punto de reemplazo");
				resultado+= "-- Se ha escogido el mismo punto de vacunacion como reemplazo --";
				resultado+= "\n Operacion cancelada";
			}else {
				PuntoVacunacion puntoReemplazo = vacuandes.darPuntoVacunacionPorId(idPuntoVacunacionNuevo);
				vacuandes.deshabilitarPuntoVacunacion(idPuntoVacunacionADeshabilitar, idPuntoVacunacionNuevo);
				resultado+= "-- Se ha dehabilitado correctamente el punto --";
				resultado+= "\n Localizacion punto deshabilitado: " +puntoVacunacionADeshabilitar.getLocalizacion() ;
				resultado+= "\n id punto deshabilitado: " +puntoVacunacionADeshabilitar.getId_Punto_Vacunacion() ;
				resultado+= "\n Localizacion punto de reemplazo: " +puntoReemplazo.getLocalizacion() ;
				resultado+= "\n id punto de reemplazo: " +puntoReemplazo.getId_Punto_Vacunacion() ;
				resultado += "\n Operación terminada";
			}
			
		}
		else if(opcion==0) {
			mostrarMensajeInformativo("Escoger punto a habilitar", "Escoja el punto que desea habilitar");
			long idPuntoRehabilitar = escogerPuntoVacunacionDeshabilitado();
			PuntoVacunacion habilitado = vacuandes.darPuntoVacunacionPorId(idPuntoRehabilitar);
			long rta =  vacuandes.rehabilitarPuntoVacunacion(idPuntoRehabilitar);
			if(rta!=idPuntoRehabilitar) {
				resultado+= "-- Ha surgido un error --";
				resultado+= "\n (id recibido " +rta + ")" ;
				resultado += "\n Operación cancelada";
			}else {
				resultado+= "-- Se ha habilitado correctamente el punto --";
				resultado+= "\n Localizacion punto habilitado: " +habilitado.getLocalizacion() ;
				resultado+= "\n id punto habilitado: " +habilitado.getId_Punto_Vacunacion() ;
				resultado += "\n Operación terminada";
			}
		}
		panelDatos.actualizarInterfaz(resultado);
	}

	//RF7
	public void asignarTalentoHumanoAUnPuntoDeVacunacion() {
		if (trabajadorActual!=null) {
			if(trabajadorActual.getTrabajo().equals(ADMINISTRADOR_OFICINA_PUNTO_REGIONAL_EPS)) VerificadoAsignarTalentoHumanoAUnPuntoDeVacunacion();
			else {JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);}
		}else {
			JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void VerificadoAsignarTalentoHumanoAUnPuntoDeVacunacion() {
		JOptionPane.showMessageDialog(this, "Ingrese los datos del talento humano a registrar", "Ingrese datos punto vacunacion", JOptionPane.QUESTION_MESSAGE);
		try {
			long cedula = Long.parseLong(JOptionPane.showInputDialog (this, "Ingresa la cedula del ciudadano al que se desea inscribir como trabajador del punto de vacunacion", "Registrar TH punto vacunacion", JOptionPane.QUESTION_MESSAGE));
			Ciudadano act = vacuandes.darCiudadanoPorCedula(cedula);

			if (act == null) {
				JOptionPane.showMessageDialog(this, "No existe un ciudadano con la cedula " +cedula, "Ciudadano no existe", JOptionPane.ERROR_MESSAGE);
				String resultado = "Se ha introducido una cedula inexistente en la BD";
				resultado += "\n Operación terminada";
			}
			else {
				long punto_vacunacion = escogerPuntoVacunacion();
				Trabajador agregado = vacuandes.agregarTrabajador(cedula, TALENTO_HUMANO_PUNTO_VACUNACION, 0, punto_vacunacion);

				String resultado = "-- Se ha registrado un nuevo trabajador en el punto de vacunacion-- ";
				resultado += "\n - Nombre completo: " + act.getNombre_Completo();
				resultado += "\n - Cedula: " + act.getCedula();
				resultado += "\n - Id punto vacunacion agregado: " + punto_vacunacion;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	//RF10
	public void asignarCiudadanoAPuntoDeVacunacion() {
		if (trabajadorActual!=null) {
			if(trabajadorActual.getTrabajo().equals(ADMINISTRADOR_OFICINA_PUNTO_REGIONAL_EPS)) VerificadoAsignarCiudadanoAPuntoDeVacunacion() ;
			else {JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);}
		}else {
			JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void VerificadoAsignarCiudadanoAPuntoDeVacunacion() {
		try {
			long cedula = Long.parseLong(JOptionPane.showInputDialog (this, "Ingresa la cedula del ciudadano", "Asignar ciudadano punto vacunacion", JOptionPane.QUESTION_MESSAGE));
			Ciudadano act = vacuandes.darCiudadanoPorCedula(cedula);

			if (act == null) {
				JOptionPane.showMessageDialog(this, "No existe un ciudadano con la cedula " +cedula, "Ciudadano no existe", JOptionPane.ERROR_MESSAGE);
				String resultado = "Se ha introducido una cedula inexistente en la BD";
				resultado += "\n Operación terminada";
			}
			else {
				Long IdpuntoVacunacionAnterior = null;
				PuntoVacunacion puntoVacunacionAnterior =null;
				if(act.getPunto_Vacunacion()==null) {
					JOptionPane.showMessageDialog(this, "No se ha decidido anteriormente el punto de vacunacion de este ciudadano, ingrese el punto de vacunacion deñ vacunacion", "Nuevo punto vacunacion", JOptionPane.INFORMATION_MESSAGE);
				}else {
					IdpuntoVacunacionAnterior = act.getPunto_Vacunacion();
					puntoVacunacionAnterior = vacuandes.darPuntoVacunacionPorId(IdpuntoVacunacionAnterior);
					JOptionPane.showMessageDialog(this, "Actualmente el ciudadano se encuentra en el punto de vacunacion localizado en " + puntoVacunacionAnterior.getLocalizacion() +". Escoja el nuevo punto de vacunacion", "Nuevo punto vacunacion", JOptionPane.INFORMATION_MESSAGE);
				}
				long punto_vacunacion = escogerPuntoVacunacionPorRegion(act.getRegion());

				long id = vacuandes.agregarACiudadanoPuntoDeVacunacion(cedula, punto_vacunacion);

				String resultado = "-- Se ha asignado el ciudadano al punto de vacunacion -- ";
				resultado += "\n - Nombre completo: " + act.getNombre_Completo();
				resultado += "\n - Cedula: " + act.getCedula();
				if(puntoVacunacionAnterior !=null) resultado += "\n - Id punto anterior: " + IdpuntoVacunacionAnterior;
				resultado += "\n - Id nuevo punto vacunacion: " + punto_vacunacion;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);

			}
		}
		catch(Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	//RF11
	public void asignarDeVacunacionACiudadano() {
		if (trabajadorActual!=null) {
			if(trabajadorActual.getTrabajo().equals(ADMINISTRADOR_PUNTO_VACUNACION)) VerificadoAsignarCitaDeVacunacionACiudadano();
			else {JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);}
		}else {
			JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void VerificadoAsignarCitaDeVacunacionACiudadano() {
		interfazDate = new InterfazDate(this);

	}

	public void Verificar_disponibilidad() {
		try {

			Date dateIngresada = interfazDate.getDate();
			Date dateActual = new Date();
			int horaIngresada = interfazDate.getHora();
			Long cedulaCiudadano = interfazDate.getCedula();

			Ciudadano ciudadano = vacuandes.darCiudadanoPorCedula(cedulaCiudadano);

			if(ciudadano == null) {
				JOptionPane.showMessageDialog(this, "El ciudadano con la cedula " + cedulaCiudadano + " no existe", "Error en cedula", JOptionPane.ERROR_MESSAGE);
			} else if ( ciudadano.getPunto_Vacunacion() == null){
				JOptionPane.showMessageDialog(this, "El ciudadano no tiene un punto de vacunacion aun, por lo que no se le pueden agendar citas", "Error en ciudadano", JOptionPane.ERROR_MESSAGE);
			} 
			else if(dateIngresada.before(dateActual)) {
				JOptionPane.showMessageDialog(this, "No puede escoger un dia igual o menor a la fecha actual", "Error en fecha", JOptionPane.ERROR_MESSAGE);
			}else if (dateIngresada.getDay()==0 || dateIngresada.getDay()==6){
				JOptionPane.showMessageDialog(this, "Los puntos de vacunacion solamente asignan citas de lunes a viernes", "Error en fecha", JOptionPane.ERROR_MESSAGE);
			}else if(vacuandes.darPuntoVacunacionPorId(ciudadano.getPunto_Vacunacion()).getCantidad_Vacunas_Actuales()<=0) {
				JOptionPane.showMessageDialog(this, "El punto de vacunacion asignado para el ciudadano no tiene vacunas disponibles actualmente, por ello no puede asignar citas", "Error en fecha", JOptionPane.ERROR_MESSAGE);
			}
			else if(vacuandes.verificarHoraNoLlena(dateIngresada, horaIngresada, ciudadano.getPunto_Vacunacion())){
				JOptionPane.showMessageDialog(this, "El punto de vacunacion asignado para el ciudadano no tiene disponibilidad para la fecha y hora indicadas", "Error en fecha", JOptionPane.ERROR_MESSAGE);
			}else {
				interfazDate.close();
				Cita citaCreada = vacuandes.agregarCita(dateIngresada, cedulaCiudadano, ciudadano.getPunto_Vacunacion(), horaIngresada);
				String resultado ="";
				if(citaCreada == null) {
					resultado = "No hay disponibilidad para esa fecha y hora en el punto de vacunacion";
					resultado += "\n Operación cancelada";
				}else {
					resultado = "-- Se ha asignado la cita correctamente -- ";
					resultado += "\n - Cedula: " + citaCreada.getCiudadano();
					resultado += "\n - Hora: " + citaCreada.getHora_cita();
					Date fechaCita = citaCreada.getFecha();
					SimpleDateFormat formatoFecha = new SimpleDateFormat( "dd/MM/yyyy");
					resultado += "\n - Fecha: " + formatoFecha.format(fechaCita);
					resultado += "\n - id_cita: " + citaCreada.getId_cita();
					resultado += "\n Operación terminada";
				}
				
				panelDatos.actualizarInterfaz(resultado);
			}

		}
		catch(Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	//RFC1
	public void mostrarCiudadanosAtendidosPorUnPuntoDeVacunacion() {
		if (trabajadorActual!=null) {
			if(trabajadorActual.getAdministrador_Vacuandes()==1) VerificadoMostrarCiudadanosAtendidosPorUnPuntoDeVacunacion();
			else {JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);}
		}else {
			JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
		}

	}
	private void VerificadoMostrarCiudadanosAtendidosPorUnPuntoDeVacunacion() {
		try {
			String region = escogerRegion();
			Long idPuntoVacunacion =escogerPuntoVacunacionPorRegion(region);
			String rta ="Error desconocido";
			int tipo_busqueda = escogerTipoDeBusquedaFechaRangoFechasUHoras();
			if(tipo_busqueda==0) {
				String fechaEspecifica = mostrarMensajeIntroducirTexto("Fecha", "Introduzca la fecha especifica de la busqueda en el formato dd/mm/yyyy");
				rta = vacuandes.mostrarCiudadanosAtendidosPorUnPuntoDeVacunacionFechaEspecifica (idPuntoVacunacion, fechaEspecifica);
			}else if (tipo_busqueda==1) {
				String fechaInicial = mostrarMensajeIntroducirTexto("Fecha inicial", "Introduzca la fecha incial de la busqueda en el formato dd/mm/yyyy");
				String fechaFinal = mostrarMensajeIntroducirTexto("Fecha final", "Introduzca la fecha final de la busqueda en el formato dd/mm/yyyy");
				rta = vacuandes.mostrarCiudadanosAtendidosPorUnPuntoDeVacunacionRangoFechas (idPuntoVacunacion, fechaInicial, fechaFinal);

			}else if (tipo_busqueda==2) {
				Integer[] rangoHoras = escogerRangoDeHoras();
				rta = vacuandes.mostrarCiudadanosAtendidosPorUnPuntoDeVacunacionRangoHora (idPuntoVacunacion, rangoHoras[0], rangoHoras[1]);
			}else if (tipo_busqueda==3) {
				rta = vacuandes.mostrarCiudadanosAtendidosPorUnPuntoDeVacunacion(idPuntoVacunacion);
			}
			if(rta==null) rta = "No se han encontrado resultados para la busqueda realizada en la region " + region + ", con el punto de vacunacioncon id "+ idPuntoVacunacion;
			else if(rta.equals("")) rta = "No se han encontrado resultados para la busqueda realizada en la region " + region + ", con el punto de vacunacioncon id "+ idPuntoVacunacion;
			else {
				rta = "-- Resultados busqueda --\n \n" + rta + "\nOperacion terminada.";
			}
			panelDatos.actualizarInterfaz(rta);
		}
		catch(Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	//RFC2 PENDIENTE
	public void mostrar20PuntosDeVacunacionMasEfectivos() {
		if (trabajadorActual!=null) {
			if(trabajadorActual.getAdministrador_Vacuandes()==1) VerificadoMostrar20PuntosDeVacunacionMasEfectivos();
			else {JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);}
		}else {
			JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void VerificadoMostrar20PuntosDeVacunacionMasEfectivos() {
				try {
					String rta =null;
					int tipo_busqueda = escogerTipoDeBusquedaFechaRangoFechasUHoras();
					if(tipo_busqueda==0) {
						String fechaEspecifica = escogerFechaEspecifica();
						//rta = vacuandes.Mostrar20PuntosDeVacunacionMasEfectivosFechaEspecifica (fechaEspecifica);
					}else if (tipo_busqueda==1) {
						String[] rangoFechas = escogerRangoDeFechas();
						//rta = vacuandes.Mostrar20PuntosDeVacunacionMasEfectivosRangoFechas (rangoFechas[0], rangoFechas[1]);
		
					}else if (tipo_busqueda==2) {
						Integer[] rangoHoras = escogerRangoDeHoras();
						//rta = vacuandes.Mostrar20PuntosDeVacunacionMasEfectivosRangoHoras (rangoHoras[0], rangoHoras[1]);
					}else if (tipo_busqueda==3) {
						//rta = vacuandes.Mostrar20PuntosDeVacunacionMasEfectivos();
					}
					if(rta==null) rta = "No se han encontrado resultados para la busqueda realizada (error00)";
					else if(rta.equals(""))rta = "No se han encontrado resultados para la busqueda realizada (error01)";
					else {
						rta = "-- Resultados busqueda --\n \n" + rta + "\nOperacion terminada.";
					}
					panelDatos.actualizarInterfaz(rta);
				}catch(Exception e) {
					e.printStackTrace();
					String resultado = generarMensajeError(e);
					panelDatos.actualizarInterfaz(resultado);
				}

	}
	
	//RFC3 PENDIENTE
	public void mostrarIndiceDeVacunacionParaGrupoPoblacional() {
		if(verificarPermisos(ADMINISTRADOR_PLAN_DE_VACUNACION+ADMINISTRADOR_OFICINA_PUNTO_REGIONAL_EPS))VerificadoMostrarIndiceDeVacunacionParaGrupoPoblacional();
	}
	private void VerificadoMostrarIndiceDeVacunacionParaGrupoPoblacional() {
				try {
					String region = escogerRegion();
					//String rta = vacuandes.mostrarIndiceDeVacunacionParaGrupoPoblacional(region);
					panelDatos.actualizarInterfaz(rta);
				} catch (Exception e) {
					e.printStackTrace();
					String resultado = generarMensajeError(e);
					panelDatos.actualizarInterfaz(resultado);
				}

	}


	//RFC4
	public void mostrarPuntosVacunacionConDisponibilidadDosis() {
		if(verificarPermisos(ADMINISTRADOR_OFICINA_PUNTO_REGIONAL_EPS+ADMINISTRADOR_PLAN_DE_VACUNACION+ADMINISTRADOR_PUNTO_VACUNACION));
	}
	private void VerificadoMostrarPuntosVacunacionConDisponibilidadDosis() {
		String rta  ="";
		if(rta.equals("")) rta = "No existen puntos de vacunacion que tengan dosis disponibles";
		 List<PuntoVacunacion> puntos = vacuandes.darTodosLosPuntosVacunacion();
		 for (int i = 0 ; i<puntos.size(); i++) {
			PuntoVacunacion puntoVacunacion = puntos.get(i);
			rta += "\nPunto de vacunacion "+ (i+1);
			rta +="\n - Region: " + vacuandes.darOficinaRegionalEPSPorId(puntoVacunacion.getOficina_regional_eps()).getRegion();
			rta +="\n - Localizacion: " + puntoVacunacion.getLocalizacion();
			rta +="\n - Administador: " + puntoVacunacion.getAdministrador();
			rta +="\n - Dosis disponibles: " + puntoVacunacion.getCantidad_Vacunas_Actuales();
			rta +="\n - Habilitado(1=si,0=no): " + puntoVacunacion.getHabilitado();
			rta +="\n " ;
		}
		if(rta.equals(""))rta = "No se han encontrado resultados para la busqueda realizada (error01)";
		else {
			rta = "-- Resultados busqueda --\n \n" + rta + "\nOperacion terminada.";
		}
	}


	//RFC5 PENDIENTE
	public void mostrarProcesoVacunacionCiudadano() {

	}
	public void VerificadoMostrarProcesoVacunacionCiudadano() {

	}

	//RFC7 PEDIENTE
	

	public void analizarOperacionVacuandes() {

	}
	private void VerificadoAnalizarOperacionVacuandes() {

	}

	//RFC8 PENDIENDE
	public void analizarCohortesCiudadanosDadosCriteriosFlexibles() {

	}
	private void VerificadoAnalizarCohortesCiudadanosDadosCriteriosFlexibles() {

	}

	//RFC9 PENDIENTE
	public void encontrarCiudadanosEnContacto() {

	}
	private void VerificadoEncontrarCiudadanosEnContacto() {

	}



	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogVacuandes ()
	{
		mostrarArchivo ("vacuandes.log");
	}

	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}

	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogVacuandes ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("vacuandes.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de vacuandes ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/Iteracion1_D-04_atrianaa_jramirezb.pdf");
	}

	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual Vacuandes.pdf");
	}

	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD Vacuandes.pdf");
	}

	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("doc/archivos_SQL/Creacion_tablas_iteracion2.sql");
	}



	/**
	 * Muestra la información acerca del desarrollo de esta apicación
	 */
	public void acercaDe ()
	{
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: Vacuandes Uniandes - Iteracion 2\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Santiago Triana 201923265\n";
		resultado += " * @author Juan Sebastian Ramirez 201923800\n";
		resultado += " * Abril de 2021\n";
		resultado += " * \n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
	}



	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/


	/**
	 * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
	 * @param e - La excepción recibida
	 * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos para la creacion de dialogos genericos
	 *****************************************************************/

	private Long escogerPlanDeVacunacion() {
		List <PlanDeVacunacion> planes = vacuandes.darTodosLosPlanesDeVacunacion();
		String [] nombresDePlanes = new String [planes.size()];
		for (int i = 0; i<planes.size(); i++) {
			nombresDePlanes[i] = planes.get(i).getNombre();
		}
		JComboBox optionList1 = new JComboBox(nombresDePlanes);
		optionList1.setSelectedIndex(0);
		JOptionPane.showMessageDialog(this, "Seleccione el plan de vacunacion asociado", "Seleccione plan de vacunacion", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(this, optionList1, "Seleccione plan", JOptionPane.QUESTION_MESSAGE);

		return planes.get(optionList1.getSelectedIndex()).getId_plan_de_vacunacion();
	}

	private Long escogerPlanDeVacunacionConNull() {
		List <PlanDeVacunacion> puntos = vacuandes.darTodosLosPlanesDeVacunacion();
		String [] nombresDePuntos = new String [puntos.size()+1];
		nombresDePuntos[0] = "NO ESCOGER AUN";
		for (int i = 0; i<puntos.size(); i++) {
			nombresDePuntos[i+1] = puntos.get(i).getNombre();
		}
		JComboBox optionList1 = new JComboBox(nombresDePuntos);
		optionList1.setSelectedIndex(0);
		JOptionPane.showMessageDialog(this, "Seleccione el plan de vacunacion asociado", "Seleccione plan de vacunacion", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(this, optionList1, "Seleccione plan", JOptionPane.QUESTION_MESSAGE);

		if(optionList1.getSelectedIndex()==0) return null;
		return puntos.get(optionList1.getSelectedIndex()-1).getId_plan_de_vacunacion();
	}

	private Long escogerPuntoVacunacionPorRegion(String region) {
		List <PuntoVacunacion> planes = vacuandes.darTodosLosPuntosVacunacionDeLaRegionHabilitados(region);
		String [] nombresDePlanes = new String [planes.size()];
		for (int i = 0; i<planes.size(); i++) {
			nombresDePlanes[i] = planes.get(i).getLocalizacion();
		}
		JComboBox optionList1 = new JComboBox(nombresDePlanes);
		optionList1.setSelectedIndex(0);
		JOptionPane.showMessageDialog(this, "Seleccione el punto de vacunacion asociado (mostrando solamente puntos de vacunacion en la region "+ region + ")", "Seleccione punto vacunacion", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(this, optionList1, "Seleccione punto", JOptionPane.QUESTION_MESSAGE);

		return planes.get(optionList1.getSelectedIndex()).getId_Punto_Vacunacion();
	}

	private Long escogerPuntoVacunacion() {
		List <PuntoVacunacion> planes = vacuandes.darTodosLosPuntosVacunacionHabilitados();
		String [] nombresDePlanes = new String [planes.size()];
		for (int i = 0; i<planes.size(); i++) {
			nombresDePlanes[i] = planes.get(i).getLocalizacion();
		}
		JComboBox optionList1 = new JComboBox(nombresDePlanes);
		optionList1.setSelectedIndex(0);
		JOptionPane.showMessageDialog(this, "Seleccione el punto de vacunacion asociado", "Seleccione punto vacunacion", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(this, optionList1, "Seleccione punto", JOptionPane.QUESTION_MESSAGE);

		return planes.get(optionList1.getSelectedIndex()).getId_Punto_Vacunacion();
	}
	
	private Long escogerPuntoVacunacionDeshabilitado() {
		List <PuntoVacunacion> planes = vacuandes.darTodosLosPuntosVacunacionDeshabilitados();
		String [] nombresDePlanes = new String [planes.size()];
		for (int i = 0; i<planes.size(); i++) {
			nombresDePlanes[i] = planes.get(i).getLocalizacion();
		}
		JComboBox optionList1 = new JComboBox(nombresDePlanes);
		optionList1.setSelectedIndex(0);
		JOptionPane.showMessageDialog(this, "Seleccione el punto de vacunacion asociado", "Seleccione punto vacunacion", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(this, optionList1, "Seleccione punto", JOptionPane.QUESTION_MESSAGE);

		return planes.get(optionList1.getSelectedIndex()).getId_Punto_Vacunacion();
	}

	private Long escogerPuntoVacunacionConNull() {
		List <PuntoVacunacion> puntos = vacuandes.darTodosLosPuntosVacunacionHabilitados();
		String [] nombresDePuntos = new String [puntos.size()+1];
		nombresDePuntos[0] = "NO ESCOGER AUN";
		for (int i = 0; i<puntos.size(); i++) {
			nombresDePuntos[i+1] = puntos.get(i).getLocalizacion();
		}
		JComboBox optionList1 = new JComboBox(nombresDePuntos);
		optionList1.setSelectedIndex(0);
		JOptionPane.showMessageDialog(this, "Seleccione el punto de vacunacion asociado", "Seleccione punto vacunacion", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(this, optionList1, "Seleccione punto", JOptionPane.QUESTION_MESSAGE);
		if(optionList1.getSelectedIndex()==0) return null;
		return puntos.get(optionList1.getSelectedIndex()-1).getId_Punto_Vacunacion();
	}

	private Long escogerPuntoVacunacionPorRegionConDeshabilitados(String region) {
		List <PuntoVacunacion> planes = vacuandes.darTodosLosPuntosVacunacionDeLaRegion(region);
		String [] nombresDePlanes = new String [planes.size()];
		for (int i = 0; i<planes.size(); i++) {
			nombresDePlanes[i] = planes.get(i).getLocalizacion();
		}
		JComboBox optionList1 = new JComboBox(nombresDePlanes);
		optionList1.setSelectedIndex(0);
		JOptionPane.showMessageDialog(this, "Seleccione el punto de vacunacion asociado (mostrando solamente puntos de vacunacion en la region "+ region + ")", "Seleccione punto vacunacion", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(this, optionList1, "Seleccione punto", JOptionPane.QUESTION_MESSAGE);

		return planes.get(optionList1.getSelectedIndex()).getId_Punto_Vacunacion();
	}

	private Long escogerPuntoVacunacionConDeshabilitados() {
		List <PuntoVacunacion> planes = vacuandes.darTodosLosPuntosVacunacion();
		String [] nombresDePlanes = new String [planes.size()];
		for (int i = 0; i<planes.size(); i++) {
			nombresDePlanes[i] = planes.get(i).getLocalizacion();
		}
		JComboBox optionList1 = new JComboBox(nombresDePlanes);
		optionList1.setSelectedIndex(0);
		JOptionPane.showMessageDialog(this, "Seleccione el punto de vacunacion asociado", "Seleccione punto vacunacion", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(this, optionList1, "Seleccione punto", JOptionPane.QUESTION_MESSAGE);

		return planes.get(optionList1.getSelectedIndex()).getId_Punto_Vacunacion();
	}

	private Long escogerPuntoVacunacionConNullConDeshabilitados() {
		List <PuntoVacunacion> puntos = vacuandes.darTodosLosPuntosVacunacion();
		String [] nombresDePuntos = new String [puntos.size()+1];
		nombresDePuntos[0] = "NO ESCOGER AUN";
		for (int i = 0; i<puntos.size(); i++) {
			nombresDePuntos[i+1] = puntos.get(i).getLocalizacion();
		}
		JComboBox optionList1 = new JComboBox(nombresDePuntos);
		optionList1.setSelectedIndex(0);
		JOptionPane.showMessageDialog(this, "Seleccione el punto de vacunacion asociado", "Seleccione punto vacunacion", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(this, optionList1, "Seleccione punto", JOptionPane.QUESTION_MESSAGE);
		if(optionList1.getSelectedIndex()==0) return null;
		return puntos.get(optionList1.getSelectedIndex()-1).getId_Punto_Vacunacion();
	}

	private Long escogerOficinaRegionalEPS() {
		List <OficinaRegionalEPS> planes = vacuandes.darTodasLasOficinasRegionalEPS();
		String [] nombresDePlanes = new String [planes.size()];
		for (int i = 0; i<planes.size(); i++) {
			nombresDePlanes[i] = planes.get(i).getRegion();
		}
		JComboBox optionList1 = new JComboBox(nombresDePlanes);
		optionList1.setSelectedIndex(0);
		JOptionPane.showMessageDialog(this, "Seleccione la oficina regional", "Seleccione oficina", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(this, optionList1, "Seleccione oficina", JOptionPane.QUESTION_MESSAGE);

		return planes.get(optionList1.getSelectedIndex()).getId_oficina();
	}

	private Long escogerOficinaRegionalEPSConNull() {
		List <OficinaRegionalEPS> puntos = vacuandes.darTodasLasOficinasRegionalEPS();
		String [] nombresDePuntos = new String [puntos.size()+1];
		nombresDePuntos[0] = "NO ESCOGER AUN";
		for (int i = 0; i<puntos.size(); i++) {
			nombresDePuntos[i+1] = puntos.get(i).getRegion();
		}
		JComboBox optionList1 = new JComboBox(nombresDePuntos);
		optionList1.setSelectedIndex(0);
		JOptionPane.showMessageDialog(this, "Seleccione la oficina regional asociada", "Seleccione oficina", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(this, optionList1, "Seleccione etapa", JOptionPane.QUESTION_MESSAGE);
		if(optionList1.getSelectedIndex()==0) return null;
		return puntos.get(optionList1.getSelectedIndex()-1).getId_oficina();
	}

	private Long asignarPorRegionOficinaRegionalEPS(String region) {
		List <OficinaRegionalEPS> planes = vacuandes.darTodasLasOficinasRegionalEPS();
		for (int i = 0; i<planes.size(); i++) {
			if(region.equals(planes.get(i).getRegion())) return planes.get(i).getId_oficina();
		}
		return null;
	}

	private String escogerRegion() {
		List <OficinaRegionalEPS> planes = vacuandes.darTodasLasOficinasRegionalEPS();
		String [] nombresDePlanes = new String [planes.size()];
		for (int i = 0; i<planes.size(); i++) {
			nombresDePlanes[i] = planes.get(i).getRegion();
		}
		JComboBox optionList1 = new JComboBox(nombresDePlanes);
		optionList1.setSelectedIndex(0);
		JOptionPane.showMessageDialog(this, "Seleccione la region", "Seleccione region", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(this, optionList1, "Seleccione region", JOptionPane.QUESTION_MESSAGE);

		return planes.get(optionList1.getSelectedIndex()).getRegion();
	}

	private String escogerEstadoVacunacion() {
		List <EstadoVacunacion> planes = vacuandes.darTodosLosEstadosVacunacion();
		String [] nombresDePlanes = new String [planes.size()];
		for (int i = 0; i<planes.size(); i++) {
			nombresDePlanes[i] = planes.get(i).getEstado();
		}
		JComboBox optionList1 = new JComboBox(nombresDePlanes);
		optionList1.setSelectedIndex(0);
		JOptionPane.showMessageDialog(this, "Seleccione el estado de vacunacion", "Seleccione estado", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(this, optionList1, "Seleccione etapa", JOptionPane.QUESTION_MESSAGE);

		return planes.get(optionList1.getSelectedIndex()).getEstado();
	}

	private String escogerEstadoVacunacionConNull() {
		List <EstadoVacunacion> puntos = vacuandes.darTodosLosEstadosVacunacion();
		String [] nombresDePuntos = new String [puntos.size()+1];
		nombresDePuntos[0] = "NO ESCOGER AUN";
		for (int i = 0; i<puntos.size(); i++) {
			nombresDePuntos[i+1] = puntos.get(i).getEstado();
		}
		JComboBox optionList1 = new JComboBox(nombresDePuntos);
		optionList1.setSelectedIndex(0);
		JOptionPane.showMessageDialog(this, "Seleccione el estado de vacunacion", "Seleccione estado", JOptionPane.QUESTION_MESSAGE);
		JOptionPane.showMessageDialog(this, optionList1, "Seleccione etapa", JOptionPane.QUESTION_MESSAGE);
		if(optionList1.getSelectedIndex()==0) return null;
		return puntos.get(optionList1.getSelectedIndex()-1).getEstado();
	}

	private int escogerTipoDeBusquedaFechaRangoFechasUHoras() {
		Object[] options = {"Fecha especifica",
				"Rango de fechas",
				"Rango de horas",
		"Todos los registros"};
		int n = JOptionPane.showOptionDialog(null,//parent container of JOptionPane
				"Escoja el tipo de busqueda que desea realizar",
				"Tipo de busqueda",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,//do not use a custom Icon
				options,//the titles of buttons
				options[3]);
		return n;
	}

	private String escogerFechaEspecifica(){
		boolean centinela =true;
		String fechaEspecifica ="";
		while (centinela) {
			fechaEspecifica = mostrarMensajeIntroducirTexto("Fecha", "Introduzca la fecha especifica de la busqueda en el formato dd/mm/yyyy");
			String [] informacionFecha = fechaEspecifica.split("/");
			if(informacionFecha.length>2) centinela = false;
			else {
				mostrarMensajeError("Error en formato fecha", "Ha existido un error en la entrada de la fecha por favor revise la entrada e intentelo de nuevo");
			}
		}
		return fechaEspecifica;
	}

	private String[] escogerRangoDeFechas() {
		String[] rta = new String[2];
		boolean centinela =true;
		while (centinela) {
			rta[1] = mostrarMensajeIntroducirTexto("Fecha", "Introduzca la fecha inicial de la busqueda en el formato dd/mm/yyyy");
			String [] informacionFecha = rta[1].split("/");
			if(informacionFecha.length>2) centinela = false;
			else {
				mostrarMensajeError("Error en formato fecha", "Ha existido un error en la entrada de la fecha por favor revise la entrada e intentelo de nuevo");
			}
		}
		centinela = true;
		while (centinela) {
			rta[2] = mostrarMensajeIntroducirTexto("Fecha", "Introduzca la fecha final de la busqueda en el formato dd/mm/yyyy");
			String [] informacionFecha = rta[2].split("/");
			if(informacionFecha.length>2) centinela = false;
			else {
				mostrarMensajeError("Error en formato fecha", "Ha existido un error en la entrada de la fecha por favor revise la entrada e intentelo de nuevo");
			}
		}
		return rta;
	}

	private Integer[] escogerRangoDeHoras() {
		try {
			Integer[] rta = new Integer [2];
			String [] horas = {
					"7:00", //1 
					"7:30", //2
					"8:00", //3
					"8:30", //4
					"9:00", //5
					"9:30", //6
					"10:00", //7
					"10:30", //8
					"11:00", //9
					"11:30",//10
					"12:00", //11
					"12:30", //12
					"13:00", //13
					"13:30", //14
					"14:00", //15
					"14:30", //16
					"15:00", //17
					"15:30",//18
					"16:00", //19
					"16:30", //20
			"17:00"};//21

			JComboBox optionList1 = new JComboBox(horas);
			optionList1.setSelectedIndex(0);

			JOptionPane.showMessageDialog(this, "Seleccione la hora mas temprana", "Seleccione rango horas", JOptionPane.QUESTION_MESSAGE);
			JOptionPane.showMessageDialog(this, optionList1, "Seleccione hora", JOptionPane.QUESTION_MESSAGE);
			rta [0] = transformarHoraAMilitar(horas[optionList1.getSelectedIndex()]);

			JOptionPane.showMessageDialog(this, "Seleccione la hora mas tarde", "Seleccione rango horas", JOptionPane.QUESTION_MESSAGE);
			JOptionPane.showMessageDialog(this, optionList1, "Seleccione hora", JOptionPane.QUESTION_MESSAGE);
			rta [1] = transformarHoraAMilitar(horas[optionList1.getSelectedIndex()]);

			return rta;
		}catch (Exception e) {
			panelDatos.actualizarInterfaz("Ha existido un error con el formato de la hora ingresasda \n Operacion terminada");
			return null;
		}

	}

	private int transformarHoraAMilitar(String hora) {
		String[] numeros = hora.split(":");
		return Integer.parseInt(numeros[0]+numeros[1]);
	}


	@SuppressWarnings("deprecation")
	private Date convertirFechaDeStringADate(String fechaIngresada){

		try {
			String[] fechaIngresadaSplit = fechaIngresada.split("/");
			Date fecha = new Date();
			fecha.setDate(Integer.parseInt(fechaIngresadaSplit[0]));
			fecha.setMonth(Integer.parseInt(fechaIngresadaSplit[1])-1);
			fecha.setYear(Integer.parseInt(fechaIngresadaSplit[2])-1900);
			fecha.setSeconds(0);
			fecha.setMinutes(0);
			fecha.setHours(0);
			return fecha;
		}catch (Exception e) {
			panelDatos.actualizarInterfaz("Ha existino un error con el formato de la fecha ingresasda \n Operacion terminada");
			return null;
		}
	}

	private void mostrarMensajeInformativo(String titulo, String contenido) {
		JOptionPane.showMessageDialog(this, contenido, titulo, JOptionPane.INFORMATION_MESSAGE);
	}

	private void mostrarMensajeError(String titulo, String contenido) {
		JOptionPane.showMessageDialog(this, contenido, titulo, JOptionPane.ERROR_MESSAGE);
	}

	private String mostrarMensajeIntroducirTexto (String titulo, String contenido){
		String rta = JOptionPane.showInputDialog (this, contenido, titulo, JOptionPane.QUESTION_MESSAGE);
		return rta;
	}

	private int mostrarMensajeBooleano (String titulo, String contenido){
		int rta = JOptionPane.showConfirmDialog(this, contenido, titulo, JOptionPane.YES_NO_OPTION);
		if (rta==1) rta =0;
		else if (rta==0) rta =1;
		return rta;
	}



	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
	/**
	 * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
	 * Invoca al método correspondiente según el evento recibido
	 * @param pEvento - El evento del usuario
	 */
	@Override
	public void actionPerformed(ActionEvent pEvento)
	{

		String evento = pEvento.getActionCommand( );
		//System.out.println(evento);

		try 
		{
			Method req = InterfazVacuandesApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}


	/* ****************************************************************
	 * 			Métodos login
	 *****************************************************************/

	public boolean verificarPermisos(String cargo) {
		if (trabajadorActual!=null) {
			if(cargo.contains(trabajadorActual.getTrabajo())) return true;
			else {JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);}
		}else {
			JOptionPane.showMessageDialog(this, "No tiene permiso para ejecutar esta accion", "Permisos insuficientes", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	public void iniciarSesion() {
		interfazCargandoRequerimiento.mostrar();
		interfazLogin = new InterfazLogin(this);
	}

	public void cerrarSesion() {

		if (usuarioActual==null) {
			JOptionPane.showMessageDialog(this, "No hay una sesion para cerrar", "Error cierre de sesion", JOptionPane.ERROR_MESSAGE);
		}else {
			JOptionPane.showMessageDialog (this, "Cierre de sesion exitoso", "Se ha cerrado la sesion del usuario "+ usuarioActual.getUsername() +" exitosamente.", JOptionPane.INFORMATION_MESSAGE);
		}
		usuarioActual =null;
		panelDatos.setUsuario(null);
		trabajadorActual = null;
	}

	public void infoUsuarioActual() {
		if(usuarioActual==null) {
			JOptionPane.showMessageDialog (this,"Actualmente no esta loggeado como ningun usuario", "Informacion sesion actual", JOptionPane.ERROR_MESSAGE);

		}else {
			String rta = "Actualmente esta loggeado como "+ usuarioActual.getUsername();
			if(trabajadorActual!=null) rta += " (" + trabajadorActual.getTrabajo() + ")";
			JOptionPane.showMessageDialog (this,rta, "Informacion sesion actual", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public void Enviar() {
		interfazLogin.close();
		interfazCargandoRequerimiento.traerAlfrente();
		String usernameUsuarioActual = interfazLogin.darUsuarioIngresado();
		String contrasenaUsuarioActual = interfazLogin.darContrasenaIngresada();
		Usuario rta = vacuandes.verificarInicioDeSesion(usernameUsuarioActual, contrasenaUsuarioActual);
		if(rta==null) {
			JOptionPane.showMessageDialog (this,"El usuario " + usernameUsuarioActual + " no existe.", "Usuario inexistente", JOptionPane.ERROR_MESSAGE);
			trabajadorActual = null;
			panelDatos.setUsuario(null);
		}
		else if(rta.getContrasena().equals(contrasenaUsuarioActual)) {
			usuarioActual = rta;
			trabajadorActual = vacuandes.darTrabajadorPorCedula(usuarioActual.getCiudadano());
			panelDatos.setUsuario(rta.getUsername());
			infoUsuarioActual();
		}else {
			usuarioActual = null;
			trabajadorActual = null;
			panelDatos.setUsuario(null);
			JOptionPane.showMessageDialog (this,"Contraseña incorrecta", "Contraseña incorrecta", JOptionPane.ERROR_MESSAGE);


		}
		interfazCargandoRequerimiento.ocultar();
	}



	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
	/**
	 * Este método ejecuta la aplicación, creando una nueva interfaz
	 * @param args Arreglo de argumentos que se recibe por línea de comandos
	 */
	public static void main( String[] args )
	{
		try
		{

			// Unifica la interfaz para Mac y para Windows.
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
			InterfazVacuandesApp interfaz = new InterfazVacuandesApp( );
			interfaz.setVisible( true );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}
}
