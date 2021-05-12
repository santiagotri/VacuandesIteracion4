package uniandes.isis2304.vacuandes.test;

import static org.junit.Assert.*;

import java.io.FileReader;

import javax.swing.JOptionPane;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.vacuandes.negocio.Vacuandes;

public class ReqC7 {

	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json"; 

	private Vacuandes vacuandes; 

	String esperado1 = "Calle 12#57-34";
	String esperado2 = "Diagonal 93 # 65-100";
	String esperado3 = "Carrera 43 # 50-2";
	String esperado4 = "Calle 66 # 52-29";
	
	String esperado5 = "Carrera 87 # 35-85";
	String esperado6 = "1001169115";

	private void escenario1()
	{
		vacuandes = new Vacuandes(openConfig (CONFIG_TABLAS_A)); 
	}


	@Test
	public void verificarReqC7RangoHorasSobrecupo() {
		escenario1();
		
		String rta = vacuandes.analizarOperacionDeVacuandesEnRangoHorasSobreCupo("Hospital", 700, 1200);
		assertTrue(rta.contains(esperado1));
		assertTrue(rta.contains(esperado2));
		
	}
	
	@Test
	public void verificarReqC7RangoFechasSobrecupo() {
		escenario1();
		
		String rta = vacuandes.analizarOperacionDeVacuandesEnRangoDeFechasSobrecupo("Hospital", "6/6/2021", "7/7/2021");
		assertTrue(rta.contains(esperado1));
		
	}
	
	@Test
	public void verificarReqC7FechaEspecificaSobrecupo() {
		escenario1();
		
		String rta = vacuandes.analizarOperacionDeVacuandesDiaEspecificoSobrecupo("Hospital", "25/6/2021");
		assertTrue(rta.contains(esperado1));
		
	}
	
	@Test
	public void verificarReqC7RangoHorasFaltaCupo() {
		escenario1();
		
		String rta = vacuandes.analizarOperacionDeVacuandesEnRangoHorasFaltaDeCupo("Hospital", 700, 1200);
		assertTrue(rta.contains(esperado3));
		assertTrue(rta.contains(esperado4));
		
	}
	
	@Test
	public void verificarReqC7RangoFechasFaltaCupo() {
		escenario1();
		
		String rta = vacuandes.analizarOperacionDeVacuandesEnRangoDeFechasFaltaDeCupo("Hospital", "6/6/2021", "7/7/2021");
		assertTrue(rta.contains(esperado4));
		assertTrue(rta.contains(esperado5));
		
	}
	
	@Test
	public void verificarReqC7FechaEspecificaFaltacupo() {
		escenario1();
		
		String rta = vacuandes.analizarOperacionDeVacuandesDiaEspecificoFaltaDeCupo("Hospital", "28/6/2021");
		assertTrue(rta.contains(esperado4));
		
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
