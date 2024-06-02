package AdministradorProyectos.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoH2Impl implements EmpleadoDAO {
	private Connection conexion;

	public EmpleadoH2Impl() {
		try {
			this.conexion = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void guardarEmpleado(Empleado empleado) {
		String sql = "INSERT INTO EMPLEADO (NOMBRE, COSTO_HORA) VALUES (?, ?)";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setString(1, empleado.getNombre());
			stmt.setDouble(2, empleado.getCostoHora());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Empleado obtenerEmpleadoPorNombre(String nombre) {
		Empleado empleado = null;
		String sql = "SELECT * FROM EMPLEADO WHERE NOMBRE = ?";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setString(1, nombre);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				empleado = new Empleado(rs.getString("NOMBRE"), rs.getDouble("COSTO_HORA"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empleado;
	}

	@Override
	public List<Empleado> obtenerTodosLosEmpleados() {
		List<Empleado> empleados = new ArrayList<>();
		String sql = "SELECT * FROM EMPLEADO";
		try (Statement stmt = conexion.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Empleado empleado = new Empleado(rs.getString("NOMBRE"), rs.getDouble("COSTO_HORA"));
				empleados.add(empleado);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empleados;
	}

	@Override
	public void actualizarEmpleado(Empleado empleado) {
		String sql = "UPDATE EMPLEADO SET COSTO_HORA = ? WHERE NOMBRE = ?";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setDouble(1, empleado.getCostoHora());
			stmt.setString(2, empleado.getNombre());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminarEmpleado(String nombre) {
		String sql = "DELETE FROM EMPLEADO WHERE NOMBRE = ?";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setString(1, nombre);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
