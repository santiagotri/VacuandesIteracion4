package uniandes.isis2304.vacuandes.test;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.vacuandes.negocio.Vacuandes;

public class ReqC8 {
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json"; 

	private Vacuandes vacuandes; 

	String nombre1 = "JUAN MARTIN BALOSTRO";
	String cedula1 = "1001169115";

	private void escenario1()
	{
		vacuandes = new Vacuandes(openConfig (CONFIG_TABLAS_A)); 
	}


	@Test
	public void verificarReqC9busq1() {
		escenario1();
		ArrayList<Long> puntos = new ArrayList<Long>();

		ArrayList<String> condiciones = new ArrayList<String>();
		
		long uno = 1;
		puntos.add(uno);
		condiciones.add("Adulto (50-59)");
		
				
		String rta = vacuandes.analizarLasCohorteDeCiudadanos(condiciones, puntos, null);
		

		assertTrue(rta.contains(nombre1));
		assertTrue(rta.contains(cedula1));
		
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
