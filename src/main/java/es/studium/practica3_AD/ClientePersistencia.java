package es.studium.practica3_AD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientePersistencia {

	// usuario de la BD
	private final static String user = "root";
	// contraseña del usuario de la BD
	private final static String password = "Studium2022;";
	// conector para comunicarse con la BD
	private final static String driver = "com.mysql.cj.jdbc.Driver";
	// url para localizar la BD
	private final static String url = "jdbc:mysql://localhost/hotel";

	// método para conectarse a la BD que devuelve un objeto de Connection
	public static Connection conexion() {
		try {
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexión con la BD
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException ce) {
			System.out.println("Driver erróneo " + ce.getMessage());
		} catch (SQLException e) {
			System.out.println("Error de SQL " + e.getMessage());
		}
		return null;
	}

	/* Devuelve el id del nuevo cliente */
	// método para dar de alta al cliente a la BD
	public static int createCliente(String nombre, String apellidos, String email, String dni, String clave)
			throws SQLException {
		// la sentencia SQL que utiliza variables 'bind' a las que podemos asignar valores más adelante
		String sql = "INSERT INTO Cliente (nombre, apellidos, email, dni, clave) VALUES (?, ?, ?, ?, ?)";
		// variable para el id (primary key)
		int id = -1;
		// try-with-resources para que se cierren los recursos automáticamente al final del bloque try
		// abrir la conexión a la BD
		try (Connection conn = conexion();
				// crear un objeto de PreparedStatement
				// al método prepareStatement le pasamos como parámetro la sentencia
				PreparedStatement ps = conn.prepareStatement(sql)) {
			// establecer los valores a cada de los campos
			ps.setString(1, nombre);
			ps.setString(2, apellidos);
			ps.setString(3, email);
			ps.setString(4, dni);
			ps.setString(5, clave);
			// realizar las actualizaciones
			int res = ps.executeUpdate();
			// borrar los parámetros
			ps.clearParameters();
			// si el método executeUpdate nos devuelve 0, indica que ninguna fila ha sido afectada
			if (res == 0) {
				throw new SQLException("Ha ocurrido un error en el alta del cliente.");
			} else {
				// crear una sentencia para consultar el id
				String sql2 = "SELECT id FROM Cliente";
				// crear un objeto de PreparedStatement y otro de ResultSet y realizamos la consulta a través del método executeQuery()
				try (PreparedStatement ps2 = conn.prepareStatement(sql2); 
						ResultSet rs2 = ps2.executeQuery()) {
					// mientras el resultado no está vacío
					while (rs2.next()) {
						id = rs2.getInt("id"); // obtener el id del cliente
					}
					System.out.println("Cliente creado.");
				}
			}
		} catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
		}
		return id;
	}

	/* Devuelve el valor de la columna "campo" del cliente identificado por "idCliente" */
	// método para consultar un campo pasado como parámetro del cliente identificado por su id
	public static String readCliente(int idCliente, String campo) throws SQLException {
		// la sentencia SQL para hacer una consulta
		String sql = "SELECT * FROM Cliente WHERE id = ?";
		// declarar una variable para guardar el dato que se pide
		String query = "";
		// try-with-resources para que se cierren los recursos automáticamente al final del bloque try
		// abrir la conexión a la BD, creamos un objeto de PreparedStatement
		try (Connection conn = conexion();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			// asignar el id pasado como parámetro a la sentencia
			ps.setInt(1, idCliente);
			// guardar los resultados de la consulta en la variable 'resultado'
			ResultSet resultado = ps.executeQuery();
			// borrar los parámetros
			ps.clearParameters();
			// comprobar que hay datos en el resultado
			if (resultado.next()) {
				// obtener el resultado de la columna especificada
				query = resultado.getString(campo);
			} else {
				System.out.println("El cliente no existe.");
			}
			// cerrar ResultSet
			resultado.close();
		} catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
		}
		return query;
	}

	/* Actualiza el valor de la columna "campo" del cliente identificado por "idCliente". Devuelve true si se ha logrado actualizar. */
	// método para modificar los datos del cliente
	public static boolean updateCliente(int idCliente, String campo, String nuevoValor) throws SQLException {
		// la sentencia SQL para hacer una modificación
		String sql = "UPDATE Cliente SET " + campo + " = ? WHERE id = ?";
		boolean actualizado = false;
		// abrir la conexión a la BD, crear un objeto de PreparedStatement
		try (Connection conn = conexion();
			PreparedStatement ps = conn.prepareStatement(sql)) {
			// asignar los valores a los parámetros de la sentencia
			ps.setString(1, nuevoValor);
			ps.setInt(2, idCliente);
			// realizar la actualización
			int res = ps.executeUpdate();
			// borrar los parámetros
			ps.clearParameters();
			// si el método executeUpdate nos devuelve 0, indica que ninguna fila ha sido afectada
			if (res == 0) {
				throw new SQLException("Ha ocurrido un error en la modificación del cliente.");
			} else {
				actualizado = true;
				System.out.println("Cliente modificado.");
			}
		} catch (SQLException sqle) { 
			System.out.println("Error de SQL: " + sqle.getMessage()); 
		}
		return actualizado;
	}

	/* Elimina el cliente identificado por "idCliente". Devuelve true si se ha logrado eliminar. */
	// método para dar de baja al cliente
	public static boolean deleteCliente(int idCliente) throws SQLException {
		// la sentencia SQL para eliminar un registro
		String sql = "DELETE FROM Cliente WHERE id = ?";
		boolean eliminado = false;
		// abrir la conexión a la BD, crear un objeto de PreparedStatement
		try (Connection conn = conexion();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			// asignar el valor al parámetro de la sentencia
			ps.setInt(1, idCliente);
			// realizar la actualización
			int res = ps.executeUpdate();
			// borrar los parámetros
			ps.clearParameters();
			// si el método executeUpdate nos devuelve 0, indica que ninguna fila ha sido afectada
			if (res == 0) {
				throw new SQLException("Ha ocurrido un error en la baja del cliente.");
			} else {
				eliminado = true;
				System.out.println("Cliente eliminado.");
			}
		} catch (SQLException sqle) { 
			System.out.println("Error de SQL: " + sqle.getMessage()); 
		}
		return eliminado;
	}

}
