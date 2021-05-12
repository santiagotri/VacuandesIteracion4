package uniandes.isis2304.vacuandes.test;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.vacuandes.negocio.Cita;
import uniandes.isis2304.vacuandes.negocio.Ciudadano;
import uniandes.isis2304.vacuandes.negocio.Vacuandes;

public class Req11 {

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
	public void verificarReq11Asignar() {
		escenario1();
		long uno  = 1;
		long cedula = 12345678;
		vacuandes.agregarCiudadano(cedula, "ciudadano_test", "Vacunado", "Andina", 1, uno,uno, uno);

		Ciudadano ciudadano = vacuandes.darCiudadanoPorCedula(cedula);
		assertNotNull(ciudadano);
		
		Date fechaprueba = new Date();

		fechaprueba.setTime(0);
		fechaprueba.setYear(121);
		fechaprueba.setMonth(10);
		fechaprueba.setDate(11);
		
		vacuandes.agregarCita(fechaprueba,cedula,uno,1200);
		List<Cita> citas = vacuandes.darCitaPorFechaYHora(fechaprueba, 1200, uno);
		Cita buscada = null;
		for(int i = 0; i<citas.size(); i++) {
			if(citas.get(i).getCiudadano()==cedula) buscada = citas.get(i);
		}
		assertNotNull(buscada);
		
		vacuandes.eliminarCitaPorId(buscada.getId_cita());

		
		citas = vacuandes.darCitaPorFechaYHora(fechaprueba, 1200, uno);
		buscada = null;
		for(int i = 0; i<citas.size(); i++) {
			if(citas.get(i).getCiudadano()==cedula) buscada = citas.get(i);
		}
		assertNull(buscada);

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
