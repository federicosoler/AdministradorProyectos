package AdministradorProyectos.Empleado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableManagerEmpleado {
	public static void crearTabla() {
		String url = "jdbc:h2:~/test";
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
