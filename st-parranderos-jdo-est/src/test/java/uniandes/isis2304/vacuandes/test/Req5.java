package uniandes.isis2304.vacuandes.test;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.util.List;

import javax.swing.JOptionPane;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.vacuandes.negocio.Ciudadano;
import uniandes.isis2304.vacuandes.negocio.Vacuandes;

public class Req5 {

	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json"; 

	private Vacuandes vacuandes; 


	private void escenario1()
	{
		vacuandes = new Vacuandes(openConfig (CONFIG_TABLAS_A)); 
	}
	

	@Test
	public void verificarReq5Registrar() {
		escenario1(); 
		long cedula = 222222;
		Long nulo = null;
		vacuandes.agregarCiudadano(cedula, "Ciudadano_test", "No vacunado", "Andina", 1, Long.parseLong("1"), nulo, Long.parseLong("1"));
		
		
		
		Ciudadano nuevo = vacuandes.darCiudadanoPorCedula(cedula);
		assertNotNull(nuevo);
		assertEquals(nuevo.getNombre_Completo(), "Ciudadano_test");
	

		vacuandes.eliminarCiudadanoPorCedula(cedula);
		
		
		Ciudadano rtaNull = vacuandes.darCiudadanoPorCedula(cedula);
		assertNull(rtaNull);
		
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
