package uniandes.isis2304.vacuandes.test;

import static org.junit.Assert.*;

import java.io.FileReader;

import javax.swing.JOptionPane;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.vacuandes.negocio.Ciudadano;
import uniandes.isis2304.vacuandes.negocio.EstadoVacunacion;
import uniandes.isis2304.vacuandes.negocio.Usuario;
import uniandes.isis2304.vacuandes.negocio.Vacuandes;

public class Req4 {

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
	public void verificarReq3RegistrarUnUsuario() {
		escenario1(); 
		
		//parte 1 verificacion del get del get de un usuario exisitente
		Usuario usuario = vacuandes.darUsuarioPorUsername("j.ramirezb");
		assertNotNull( usuario); 
		assertEquals(usuario.getCiudadano(), 1001168161);
		
		//parte 2 prueba para registar un nuevo usuario, requiere crear un ciudadano
		
		Ciudadano ciudadanoPrueba = vacuandes.agregarCiudadano(1001591721, "Juan Prueba Ramirez", "No vacunado", "Andina", 1, 1, null, null); 
		usuario = vacuandes.agregarUsuarioVacuandes("usuarioPrueba", "contrasenaPrueba", "usuariorandom@hotmail.com", 1, 1001591721);
		assertNotNull(usuario);
		
		//parte 3 verificar get y borrar el usuario creado para prueba
		usuario = vacuandes.darUsuarioPorUsername("usuarioPrueba");
		long eliminado = vacuandes.eliminarUsuarioPorUsername("usuarioPrueba"); 
		long ciudadanoEliminado = vacuandes.eliminarCiudadanoPorCedula(1001591721); 
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
