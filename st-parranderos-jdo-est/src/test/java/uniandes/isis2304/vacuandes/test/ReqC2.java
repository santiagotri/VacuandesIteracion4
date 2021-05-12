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
	
	
	String esperado5 = "1";
	String esperado6 = "266";
	String esperado7 = "247";
	String esperado8 = "161";
	
	String esperado9 = "109";
	
	String esperado10 = "31";
	String esperado11 = "188";

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
	public void verificarReqC2FechaEspecifica() {
		escenario1();
		
		
		String fechaEspecifica = "7/7/2021";
		
		String rta ="";
		try {
			rta = vacuandes.mostrar20PuntosMasEfectivosEnFechaEspecifica(new SimpleDateFormat("dd/MM/yyyy").parse(fechaEspecifica));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(rta);
		assertTrue(rta.contains(esperado5));
		assertTrue(rta.contains(esperado6));
		assertTrue(rta.contains(esperado7));
		assertTrue(rta.contains(esperado8));
		
	}
	
	@Test
	public void verificarReqC2RangoFechas() {
		escenario1();
		
		
		Date fechaInicial = new Date();
		fechaInicial.setTime(0);
		fechaInicial.setYear(121);
		fechaInicial.setMonth(5);
		fechaInicial.setDate(7);
		
		Date fechaFinal = new Date();
		fechaFinal.setTime(0);
		fechaFinal.setYear(121);
		fechaFinal.setMonth(7);
		fechaFinal.setDate(7);
		
		String rta = vacuandes.mostrar20PuntosMasEfectivosEnRangoDeFechas(fechaInicial, fechaFinal);
		assertTrue(rta.contains(esperado5));
		assertTrue(rta.contains(esperado4));
		assertTrue(rta.contains(esperado7));
		assertTrue(rta.contains(esperado9));
		
		
	}
	
	
	@Test
	public void verificarReqC2RangoHora() {
		escenario1();
		
		String rta = vacuandes.mostrar20PuntosMasEfectivosEnRangoDeHoras(700, 1200);
		assertTrue(rta.contains(esperado4));
		assertTrue(rta.contains(esperado5));
		assertTrue(rta.contains(esperado10));
		assertTrue(rta.contains(esperado11));
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
