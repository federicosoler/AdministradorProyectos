package AdministradorProyectos.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableManager {
	private Connection conexion;

	public TableManager(String dbName) throws SQLException {
		String url = "jdbc:h2:file:E:/UP/Laboratorio I/Eclipse Workspace/AdministradorProyectos/db/" + dbName;
		String user = "sa";
		String password = "";
		conexion = DriverManager.getConnection(url, user, password);
	}

	public void crearTablaEmpleado() throws SQLException {
		String sqlEmpleado = "CREATE TABLE IF NOT EXISTS EMPLEADO " + "(NOMBRE VARCHAR(255) PRIMARY KEY, "
				+ "COSTO_HORA DOUBLE)";
		try (Statement stmt = conexion.createStatement()) {
			stmt.execute(sqlEmpleado);
			System.out.println("Tabla EMPLEADO creada exitosamente.");
		}
	}

	public void crearTablaProyecto() throws SQLException {
		String sqlProyecto = "CREATE TABLE IF NOT EXISTS PROYECTO " + "(NOMBRE VARCHAR(255) PRIMARY KEY, "
				+ "DESCRIPCION VARCHAR(255))";
		try (Statement stmt = conexion.createStatement()) {
			stmt.execute(sqlProyecto);
			System.out.println("Tabla PROYECTO creada exitosamente.");
		}
	}

}
