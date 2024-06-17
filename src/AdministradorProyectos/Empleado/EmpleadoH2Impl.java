package AdministradorProyectos.Empleado;

import AdministradorProyectos.Exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoH2Impl implements EmpleadoDAO {
	private Connection conexion;

	public EmpleadoH2Impl() throws DAOException {
        try {
            String url = "jdbc:h2:file:E:/UP/Laboratorio I/Eclipse Workspace/AdministradorProyectos/db/EMPLEADO";
            conexion = DriverManager.getConnection(url, "sa", "");
            TableManagerEmpleado.crearTabla();
        } catch (SQLException e) {
        	throw new DAOException("Error al conectar con la base de datos", e);
        }
    }

	@Override
	public void guardarEmpleado(Empleado empleado) throws DAOException {
		String sql = "INSERT INTO EMPLEADO (NOMBRE, COSTO_HORA) VALUES (?, ?)";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setString(1, empleado.getNombre());
			stmt.setDouble(2, empleado.getCostoHora());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error al guardar empleado", e);
		}
	}

	@Override
	public Empleado obtenerEmpleadoPorNombre(String nombre) throws DAOException {
		Empleado empleado = null;
		String sql = "SELECT * FROM EMPLEADO WHERE NOMBRE = ?";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setString(1, nombre);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				empleado = new Empleado(rs.getString("NOMBRE"), rs.getDouble("COSTO_HORA"));
			}
		} catch (SQLException e) {
			throw new DAOException("Error al obtener empleado por nombre", e);
		}
		return empleado;
	}

	@Override
	public List<Empleado> obtenerTodosLosEmpleados() throws DAOException {
		List<Empleado> empleados = new ArrayList<>();
		String sql = "SELECT * FROM EMPLEADO";
		try (Statement stmt = conexion.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Empleado empleado = new Empleado(rs.getString("NOMBRE"), rs.getDouble("COSTO_HORA"));
				empleados.add(empleado);
			}
		} catch (SQLException e) {
			throw new DAOException("Error al obtener empleados", e);
		}
		return empleados;
	}

	@Override
	public void actualizarEmpleado(Empleado empleado) throws DAOException {
		String sql = "UPDATE EMPLEADO SET COSTO_HORA = ? WHERE NOMBRE = ?";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setDouble(1, empleado.getCostoHora());
			stmt.setString(2, empleado.getNombre());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error al actualizar empleado", e);
		}
	}

	@Override
	public void eliminarEmpleado(String nombre) throws DAOException {
		String sql = "DELETE FROM EMPLEADO WHERE NOMBRE = ?";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setString(1, nombre);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error al eliminar empleado", e);
		}
	}
}
