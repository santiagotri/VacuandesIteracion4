package uniandes.isis2304.vacuandes.test;

import static org.junit.Assert.*;

import java.io.FileReader;

import javax.swing.JOptionPane;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.vacuandes.negocio.EstadoVacunacion;
import uniandes.isis2304.vacuandes.negocio.Vacuandes;

public class Req2 {

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
	public void verificarReq1RegistrarEstado() {
		escenario1(); 
		
		//parte 1
		EstadoVacunacion estado = vacuandes.darTodosLosEstadosVacunacion().get(0);
		assertEquals( estado.getEtapa(),1); 
		
		//parte 2
		cond = vacuandes.registrarCondicionesDePriorizacion("Adulto mayor (+80)", 3);
		assertNotNull(cond);
		
		//parte 3
		cond = vacuandes.getCondicion("Adulto mayor (+80)");
		assertEquals(cond.getEtapa(), 3); 

		vacuandes.registrarCondicionesDePriorizacion("Adulto mayor (+80)", 1);
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
