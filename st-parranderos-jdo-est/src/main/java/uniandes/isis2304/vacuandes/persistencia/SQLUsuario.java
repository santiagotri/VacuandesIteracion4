package uniandes.isis2304.vacuandes.persistencia;


import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.vacuandes.negocio.Usuario;

public class SQLUsuario {
	
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaVacuandes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaVacuandes pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLUsuario (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarUsuario(PersistenceManager pm, String username, String contrasena, String correo, long plan_de_vacunacion, long ciudadano)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaUsuario() + "(username, contrasena, correo, plan_de_vacunacion, ciudadano) values (?, ?, ?, ?, ?)");
		q.setParameters(username, contrasena, correo, plan_de_vacunacion, ciudadano); 
		return (long) q.executeUnique();	
	}
	
	public long eliminarTodosLosUsuarios(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaUsuario());
		return (long) q.executeUnique(); 
	}
	
	public long eliminarUsuarioPorUsername(PersistenceManager pm, String username)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaUsuario() + " WHERE username = ?");
        q.setParameters(username);
        return (long) q.executeUnique();
	}
	
	public long eliminarUsuarioPorCorreo(PersistenceManager pm, String correo)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaUsuario() + " WHERE correo = ?");
        q.setParameters(correo);
        return (long) q.executeUnique();
	}
	
	public long eliminarUsuarioPorPlanDeVacunacion(PersistenceManager pm, long plan_de_vacunacion)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaUsuario() + " WHERE plan_de_vacunacion = ?");
        q.setParameters(plan_de_vacunacion);
        return (long) q.executeUnique();
	}
	
	public long eliminarUsuarioPorCiudadano(PersistenceManager pm, long ciudadano)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaUsuario() + " WHERE ciudadano = ?");
        q.setParameters(ciudadano);
        return (long) q.executeUnique();
	}
	
	public List<Usuario> darListaUsuarios(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaUsuario());
		q.setResultClass(Usuario.class);
		return (List<Usuario>) q.execute();
	}
	
	public Usuario darUsuario(PersistenceManager pm, String username)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaUsuario() + " WHERE USERNAME = ?");
		q.setResultClass(Usuario.class);
		q.setParameters(username);
		return (Usuario) q.executeUnique();
	}
}
