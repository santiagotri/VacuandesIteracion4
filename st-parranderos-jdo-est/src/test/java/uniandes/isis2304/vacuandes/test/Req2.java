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
	public void verificarReq2RegistrarEstado() {
		escenario1(); 
		
		//parte 1 probar que el estado guardado es correcto
		EstadoVacunacion estado = vacuandes.darTodosLosEstadosVacunacion().get(2);
		assertEquals( estado.getEstado(), "Vacunado"); 
		
		//parte 2 prueba para registar un nuevo estado
		estado = vacuandes.agregarEstadoVacunacion("No se va a vacunar");
		assertNotNull(estado);
		
		//parte 3 verificar get y borrar el estado creado para prueba
		estado = vacuandes.darEstadoVacunacionPorNombre("No se va a vacunar");
		assertEquals(estado.getEstado(), "No se va a vacunar"); 
		
		long verEliminacion = vacuandes.eliminarEstadoVacunacion("No se va a vacunar");
		assertEquals(verEliminacion, 1);
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
