package uniandes.isis2304.vacuandes.test;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.vacuandes.negocio.Vacuandes;

public class ReqC9 {

	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json"; 

	private Vacuandes vacuandes; 

	String esperado1 = "1001168162";
	String esperado2 = "1001168437";
	String esperado3 = "1001168501";
	String esperado4 = "1001168160";
	
	
	String esperado5 = "1001169036";
	String esperado6 = "1001169115";

	private void escenario1()
	{
		vacuandes = new Vacuandes(openConfig (CONFIG_TABLAS_A)); 
	}


	@Test
	public void verificarReqC9() {
		escenario1();
		
		String fechaInicial = "1/7/2021";
		String fechaFinal = "11/7/2021";
		String rta = vacuandes.encontrarCiudadanosEnContacto(fechaInicial, fechaFinal);
		assertTrue(rta.contains(esperado4));
		assertTrue(rta.contains(esperado1));
		assertTrue(rta.contains(esperado2));
		assertTrue(rta.contains(esperado3));
		assertTrue(rta.contains(esperado5));
		assertTrue(rta.contains(esperado6));
		
	}


	//No es posible que hayan errores en la seleccion de la condicion ni de la etapa porque es un menu desplegable

	private JsonObject openConfig (String archConfig)
	{
		JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
		} 
		catch (Exception e)
		{
			e.printStackTrace ();
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ", "TipoBebidaTest", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}

}
