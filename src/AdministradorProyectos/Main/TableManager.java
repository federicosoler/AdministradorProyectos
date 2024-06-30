package AdministradorProyectos.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableManager {
	private Connection conexion;

	public TableManager(String dbName) throws SQLException {
		String url = "jdbc:h2:file:E:/UP/Laboratorio I/Eclipse Workspace/AdministradorProyectos/db/" + dbName;
		conexion = DriverManager.getConnection(url, "sa", "");
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

	public void crearTablaProyectoEmpleado() throws SQLException {
		String sqlProyectoEmpleado = "CREATE TABLE IF NOT EXISTS PROYECTO_EMPLEADO " + "(NOMBRE_PROYECTO VARCHAR(255), "
				+ "NOMBRE_EMPLEADO VARCHAR(255), " + "PRIMARY KEY (NOMBRE_PROYECTO, NOMBRE_EMPLEADO), "
				+ "FOREIGN KEY (NOMBRE_PROYECTO) REFERENCES PROYECTO(NOMBRE), "
				+ "FOREIGN KEY (NOMBRE_EMPLEADO) REFERENCES EMPLEADO(NOMBRE))";
		try (Statement stmt = conexion.createStatement()) {
			stmt.execute(sqlProyectoEmpleado);
			System.out.println("Tabla PROYECTO_EMPLEADO creada exitosamente.");
		}
	}
}
