package BDR;

import BDR.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

	private ConexionBD conexionBD;

	public PersonaDAO(ConexionBD conexionBD) {
		this.conexionBD = conexionBD;
	}

	public boolean darDeAltaPersona(PersonaEntity persona) {
		// Omite 'id' en el INSERT porque MySQL lo genera automáticamente (AUTO_INCREMENT)
		String sql = "INSERT INTO persona (nombre, apellidos, edad) VALUES (?, ?, ?)";

		// El try-with-resources abre la conexión y el statement,
		// y se asegura de CERRARLOS automáticamente al salir del bloque
		try {

			Connection con = conexionBD.abrirConexion();
		    PreparedStatement stmt = con.prepareStatement(sql);

			// Asignamos los valores a los signos de interrogación (?) de la consulta SQL
			stmt.setString(1, persona.getNombre());
			stmt.setString(2, persona.getApellidos());
			stmt.setInt(3, persona.getEdad());

			// executeUpdate() ejecuta el INSERT y devuelve el número de filas afectadas
			int filasAfectadas = stmt.executeUpdate();

			return filasAfectadas > 0; // Devuelve true si guardó al menos 1 fila

		} catch (SQLException e) {
			System.err.println("Error al insertar la persona en la BD: " + e.getMessage());
			return false;
		}
	}

	// 2. ELIMINAR POR ID
	public boolean eliminarPersona(int id) {
		String sql = "DELETE FROM persona WHERE id = ?";
		try {
			Connection con = conexionBD.abrirConexion();
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setInt(1, id);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error en DAO al eliminar: " + e.getMessage());
			return false;
		}
	}


	// 3. CONSULTAR POR ID
	public PersonaEntity consultarPersonaPorId(int id) {
		String sql = "SELECT id, nombre, apellidos, edad FROM persona WHERE id = ?";
		try {
			Connection con = conexionBD.abrirConexion();
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new PersonaEntity(

						rs.getString("nombre"),
						rs.getString("apellidos"),
						rs.getInt("edad"),
						rs.getInt("id")
				);
			}
		} catch (SQLException e) {
			System.err.println("Error en DAO al consultar: " + e.getMessage());
		}
		return null;
	}


	// 4. LISTADO DE TODAS LAS PERSONAS
	public ArrayList<PersonaEntity> listarPersonas() {
		ArrayList<PersonaEntity> personas = new ArrayList<>();
		String sql = "SELECT id, nombre, apellidos, edad FROM persona";

		//modificar esto

		try {

			Connection con = conexionBD.abrirConexion();
		     PreparedStatement stmt = con.prepareStatement(sql);
		     ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				personas.add(new PersonaEntity(

						rs.getString("nombre"),
						rs.getString("apellidos"),
						rs.getInt("edad"),
						rs.getInt("id")
				));
			}
		} catch (SQLException e) {
			System.err.println("Error en DAO al listar: " + e.getMessage());
		}
		return personas;
	}


	// 5. MODIFICAR PERSONA
	public boolean modificarPersona(PersonaEntity persona) {
		String sql = "UPDATE persona SET nombre = ?, apellidos = ?, edad = ? WHERE id = ?";
		try {
			Connection con = conexionBD.abrirConexion();
			PreparedStatement stmt = con.prepareStatement(sql);


			stmt.setString(1, persona.getNombre());
			stmt.setString(2, persona.getApellidos());
			stmt.setInt(3, persona.getEdad());
			stmt.setInt(4, persona.getId());

			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error en DAO al modificar: " + e.getMessage());
			return false;
		}
	}

}
