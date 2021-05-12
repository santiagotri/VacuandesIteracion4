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
import uniandes.isis2304.vacuandes.negocio.PuntoVacunacion;
import uniandes.isis2304.vacuandes.negocio.Vacuandes;
import uniandes.isis2304.vacuandes.negocio.Vacuna;

public class Req9 {



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
	public void verificarReq9Registrar() {
		escenario1(); 
		vacuandes.agregarOficinaRegional("Region_test", "alemores", 2000, 0, 1);
		OficinaRegionalEPS oficina = vacuandes.darOficinaRegionalEPSPorRegion("Region_test");
		assertEquals(oficina.getCantidad_Vacunas_Actuales(), 0);
		
		vacuandes.registrarLlegadaDeLoteDeVacunasEPS(oficina, 1000, "condicion_test");
		oficina = vacuandes.darOficinaRegionalEPSPorRegion("Region_test");
		assertEquals(oficina.getCantidad_Vacunas_Actuales(), 1000);
		
		vacuandes.agregarPuntoVacunacion("localizacion_test", 5, "infraestructura_de_prueba", 1500, 0, "Hospital", "j.ramirezb", oficina.getId_oficina(), 1);
		PuntoVacunacion punto = vacuandes.darPuntoVacunacionPorLocalizacion("localizacion_test");
		assertEquals(punto.getCantidad_Vacunas_Actuales(), 0);
		
		vacuandes.registrarLlegadaDeLoteDeVacunasAPuntoVacunacion(punto, 1000);
		punto = vacuandes.darPuntoVacunacionPorLocalizacion("localizacion_test");
		assertEquals(punto.getCantidad_Vacunas_Actuales(), 1000);
		
		oficina = vacuandes.darOficinaRegionalEPSPorRegion("Region_test");
		assertEquals(oficina.getCantidad_Vacunas_Actuales(), 0);
		
		List<Vacuna> vacunas = vacuandes.darVacunasPorCondicion("condicion_test");
		assertEquals(vacunas.size(), 1000);
		
		vacuandes.eliminarVacunasPorCondicion("condicion_test");
		vacuandes.eliminarPuntoPorLocalizacion("localizacion_test");
		vacuandes.eliminarOficinaRegionalEPSPorId(oficina.getId_oficina());
		
		vacunas = vacuandes.darVacunasPorCondicion("condicion_test");
		assertEquals(vacunas.size(), 0);
		
		punto = vacuandes.darPuntoVacunacionPorLocalizacion("localizacion_test");
		assertNull(punto);
		
		oficina = vacuandes.darOficinaRegionalEPSPorRegion("Region_test");
		assertNull(oficina);
		
		
		
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
