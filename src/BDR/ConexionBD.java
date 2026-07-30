package BDR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {


	private final String USER = "root";
	private final String PWD = "";
	private final String URL = "jdbc:mysql://localhost:3307/tienda";


	public Connection abrirConexion() {

		Connection con = null;

		try {
			con = DriverManager.getConnection(URL, USER, PWD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	/*
	public void cerrarConexion(Connection con) {
		
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	 */
	
}
