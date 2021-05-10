package uniandes.isis2304.vacuandes.test;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.util.List;

import javax.swing.JOptionPane;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.vacuandes.negocio.Condicion;
import uniandes.isis2304.vacuandes.negocio.OficinaRegionalEPS;
import uniandes.isis2304.vacuandes.negocio.Vacuandes;

public class Req3 {


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
	public void verificarReq3Registrar() {
		escenario1(); 
		
		OficinaRegionalEPS nueva = vacuandes.agregarOficinaRegional("Region_Test", "a.trianaa",1000, 0, 1);
		assertNotNull(nueva);
		
		List<OficinaRegionalEPS> lista = vacuandes.darTodasLasOficinasRegionalEPS();
		for(int i=0;i<lista.size();i++) {
			if(lista.get(i).getRegion().equals("Region_Test")) nueva = lista.get(i);
		}
		System.out.println(nueva.getId_oficina());
		nueva = vacuandes.darOficinaRegionalEPSPorId(nueva.getId_oficina());
		assertNotNull(nueva);
	

		vacuandes.eliminarOficinaRegionalEPSPorId(nueva.getId_oficina());
		
		
		OficinaRegionalEPS rtaNull = vacuandes.darOficinaRegionalEPSPorId(nueva.getId_oficina());
		assertNull(rtaNull);
		
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
