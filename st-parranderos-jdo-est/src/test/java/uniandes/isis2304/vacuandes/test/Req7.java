package uniandes.isis2304.vacuandes.test;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.util.List;

import javax.swing.JOptionPane;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.vacuandes.negocio.OficinaRegionalEPS;
import uniandes.isis2304.vacuandes.negocio.Trabajador;
import uniandes.isis2304.vacuandes.negocio.Vacuandes;
import uniandes.isis2304.vacuandes.negocio.Vacuna;

public class Req7 {

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
	public void verificarReq7Registrar() {
		escenario1();
		long cedula = 12345678;
		long uno = 1;
		vacuandes.agregarCiudadano(cedula, "ciudadano_test","Vacunado", "Andina", 1, uno,uno,uno);
		vacuandes.agregarTrabajador(cedula, "Talento humano punto vacunacion", 0, uno);
		Trabajador trabajador = vacuandes.darTrabajadorPorCedula(cedula);
		assertNotNull(trabajador);
		assertEquals(cedula, trabajador.getCedula());
		
		long rta = vacuandes.eliminarTrabajador(cedula);
		trabajador = vacuandes.darTrabajadorPorCedula(cedula);
		assertNull(trabajador);
		assertEquals(uno, rta);
		
		vacuandes.eliminarCiudadanoPorCedula(cedula);
		assertNull(vacuandes.darCiudadanoPorCedula(cedula));
		
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
