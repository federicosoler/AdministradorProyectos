package AdministradorProyectos.Empleado;

import java.sql.*;

public class TableManagerEmpleado {
	public static void crearTabla() {
		String url = "jdbc:h2:file:E:/UP/Laboratorio I/Eclipse Workspace/AdministradorProyectos/db/EMPLEADO";
		String user = "sa";
		String password = "";
		String sqlEmpleado = "CREATE TABLE IF NOT EXISTS EMPLEADO " + "(NOMBRE VARCHAR(255) PRIMARY KEY, "
				+ "COSTO_HORA DOUBLE)";

		try (Connection conexion = DriverManager.getConnection(url, user, password);
				Statement stmt = conexion.createStatement()) {
			stmt.execute(sqlEmpleado);
			System.out.println("Tabla EMPLEADO creada exitosamente.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
