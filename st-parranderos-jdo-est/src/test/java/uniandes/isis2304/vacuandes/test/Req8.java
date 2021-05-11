package uniandes.isis2304.vacuandes.test;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.util.List;

import javax.swing.JOptionPane;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.vacuandes.negocio.Ciudadano;

import uniandes.isis2304.vacuandes.negocio.Vacuna;
import uniandes.isis2304.vacuandes.negocio.OficinaRegionalEPS;
import uniandes.isis2304.vacuandes.negocio.Vacuandes;

public class Req8 {


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
	public void verificarReq8Registrar() {
		escenario1(); 
		int cantidadVacunasAProbar = 100;
		OficinaRegionalEPS oficina = vacuandes.darOficinaRegionalEPSPorId(1);
		vacuandes.registrarLlegadaDeLoteDeVacunasEPS(oficina, cantidadVacunasAProbar, "condicion_prueba");
		
		OficinaRegionalEPS oficinaNueva = vacuandes.darOficinaRegionalEPSPorId(1);
		assertEquals(oficinaNueva.getCantidad_Vacunas_Actuales(), oficina.getCantidad_Vacunas_Actuales()+cantidadVacunasAProbar);
		
		List<Vacuna> vacunasCreadas = vacuandes.darVacunasPorCondicion("condicion_prueba");
		assertEquals(vacunasCreadas.size(), cantidadVacunasAProbar);
		
		vacuandes.eliminarVacunasPorCondicion("condicion_prueba");
		vacunasCreadas = vacuandes.darVacunasPorCondicion("condicion_prueba");
		assertEquals(vacunasCreadas.size(), 0);
		
		vacuandes.actualizarCantidadVacunasActualesOficinaRegional(Long.parseLong("1"), cantidadVacunasAProbar);
		oficinaNueva = vacuandes.darOficinaRegionalEPSPorId(1);
		assertEquals(oficinaNueva.getCantidad_Vacunas_Actuales(), oficina.getCantidad_Vacunas_Actuales());
		
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
