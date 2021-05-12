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
import uniandes.isis2304.vacuandes.negocio.OficinaRegionalEPS;
import uniandes.isis2304.vacuandes.negocio.PuntoVacunacion;
import uniandes.isis2304.vacuandes.negocio.Vacuandes;
import uniandes.isis2304.vacuandes.negocio.Vacuna;

public class Req10 {



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
	public void verificarReq10Registrar() {
		escenario1();
		long uno  = 1;
		long cedula = 12345678;
		vacuandes.agregarCiudadano(cedula, "ciudadano_test", "Vacunado", "Andina", 1, uno,null, uno);

		Ciudadano ciudadano = vacuandes.darCiudadanoPorCedula(cedula);
		assertNotNull(ciudadano);
		assertNull(ciudadano.getPunto_Vacunacion());

		vacuandes.agregarACiudadanoPuntoDeVacunacion(cedula, uno);

		ciudadano = vacuandes.darCiudadanoPorCedula(cedula);
		assertNotNull(ciudadano);
		assertNotNull(ciudadano.getPunto_Vacunacion());

		long punto = ciudadano.getPunto_Vacunacion();
		assertEquals(uno, punto);

		vacuandes.eliminarCiudadanoPorCedula(cedula);
		ciudadano = vacuandes.darCiudadanoPorCedula(cedula);
		assertNull(ciudadano);

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
