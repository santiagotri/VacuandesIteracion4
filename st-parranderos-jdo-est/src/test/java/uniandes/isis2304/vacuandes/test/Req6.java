package uniandes.isis2304.vacuandes.test;

import static org.junit.Assert.*;

import java.io.FileReader;

import javax.swing.JOptionPane;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.vacuandes.negocio.Ciudadano;
import uniandes.isis2304.vacuandes.negocio.PuntoVacunacion;
import uniandes.isis2304.vacuandes.negocio.Usuario;
import uniandes.isis2304.vacuandes.negocio.Vacuandes;

public class Req6 {

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
	public void verificarReq6RegistrarUnPuntoVacunacion() {
		escenario1(); 
		
		//parte 1 verificacion del get del get de un punto existente
		PuntoVacunacion punto = vacuandes.darPuntoVacunacionPorId(1);
		assertNotNull( punto); 
		
		//parte 2 prueba para registar un nuevo puntoVacunacion
		
		punto = vacuandes.agregarPuntoVacunacion("La clinica de pruebas", 100, "Neveras de pruebas", 10, 0, "Otro", "j.ramirezb", 1, 1);
		assertNotNull(punto);
		
		//parte 3 verificar get y borrar el punto creado para prueba
		vacuandes.darPuntoVacunacionPorLocalizacion("La clinica de pruebas");
		long eliminado = vacuandes.eliminarPuntoPorLocalizacion("La clinica de pruebas"); 
		assertEquals(eliminado, 1); 
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
