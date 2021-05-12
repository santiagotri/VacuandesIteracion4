package uniandes.isis2304.vacuandes.test;

import static org.junit.Assert.*;

import java.io.FileReader;

import javax.swing.JOptionPane;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.vacuandes.negocio.Vacuandes;

public class ReqC2 {

	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json"; 

	private Vacuandes vacuandes; 

	String esperado1 = "265";
	String esperado2 = "149";
	String esperado3 = "195";
	String esperado4 = "243";

	private void escenario1()
	{
		vacuandes = new Vacuandes(openConfig (CONFIG_TABLAS_A)); 
	}


	@Test
	public void verificarReqC2TodosRegistros() {
		escenario1();
		
		
		String rta = vacuandes.mostrar20PuntosMasEfectivosGeneral();
		assertTrue(rta.contains(esperado4));
		assertTrue(rta.contains(esperado1));
		assertTrue(rta.contains(esperado2));
		assertTrue(rta.contains(esperado3));
		
	}
	
	
	@Test
	public void verificarReqC1FechaEspecifica() {
		escenario1();
		
	
		
		String rta = vacuandes.mostrarCiudadanosAtendidosPorUnPuntoDeVacunacionFechaEspecifica(1, "7/7/2021");
		assertTrue(rta.contains(esperado4));
		assertTrue(rta.contains(esperado1));
		assertTrue(rta.contains(esperado2));
		assertTrue(rta.contains(esperado3));
		
	}
	
	@Test
	public void verificarReqC1RangoFechas() {
		escenario1();
		
		
		String rta = vacuandes.mostrarCiudadanosAtendidosPorUnPuntoDeVacunacionRangoFechas(1, "6/7/2021", "8/7/2021");
		assertTrue(rta.contains(esperado4));
		assertTrue(rta.contains(esperado1));
		assertTrue(rta.contains(esperado2));
		assertTrue(rta.contains(esperado3));
		
	}
	
	
	@Test
	public void verificarReqC1RangoHora() {
		escenario1();
		
		String rta = vacuandes.mostrarCiudadanosAtendidosPorUnPuntoDeVacunacionRangoHora(1, 700, 1200);
		assertFalse(rta.contains(esperado4));
		assertTrue(rta.contains(esperado1));
		assertTrue(rta.contains(esperado2));
		assertTrue(rta.contains(esperado3));
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
