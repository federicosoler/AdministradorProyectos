package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Exceptions.DAOException;
import AdministradorProyectos.Main.TableManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProyectoH2Impl implements ProyectoDAO {
	private Connection conexion;
	private String url = "jdbc:h2:file:E:/UP/Laboratorio I/Eclipse Workspace/AdministradorProyectos/db/PROYECTO";
	private String user = "sa";
	private String password = "";

	public ProyectoH2Impl() throws DAOException {
		try {
			TableManager tableManager = new TableManager("PROYECTO");
			tableManager.crearTablaProyecto();
			tableManager.crearTablaProyectoEmpleado();
			conexion = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new DAOException("Error al conectar con la base de datos", e);
		}
	}

	@Override
	public void guardarProyecto(Proyecto proyecto) throws DAOException {
		String sql = "INSERT INTO PROYECTO (NOMBRE, DESCRIPCION) VALUES (?, ?)";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setString(1, proyecto.getNombre());
			stmt.setString(2, proyecto.getDescripcion());
			stmt.executeUpdate();

			// Guardar empleados asignados
			for (String empleado : proyecto.getEmpleadosAsignados()) {
				asignarEmpleadoAProyecto(proyecto.getNombre(), empleado);
			}
		} catch (SQLException e) {
			throw new DAOException("Error al guardar proyecto", e);
		}
	}

	@Override
	public void actualizarProyecto(Proyecto proyecto) throws DAOException {
		String sql = "UPDATE PROYECTO SET DESCRIPCION = ? WHERE NOMBRE = ?";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setString(1, proyecto.getDescripcion());
			stmt.setString(2, proyecto.getNombre());
			stmt.executeUpdate();

			// Actualizar empleados asignados
			eliminarAsignacionesDeProyecto(proyecto.getNombre());
			for (String empleado : proyecto.getEmpleadosAsignados()) {
				asignarEmpleadoAProyecto(proyecto.getNombre(), empleado);
			}
		} catch (SQLException e) {
			throw new DAOException("Error al actualizar proyecto", e);
		}
	}

	@Override
	public void eliminarProyecto(String nombre) throws DAOException {
		String sql = "DELETE FROM PROYECTO WHERE NOMBRE = ?";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setString(1, nombre);
			stmt.executeUpdate();

			// Eliminar asignaciones de empleados
			eliminarAsignacionesDeProyecto(nombre);
		} catch (SQLException e) {
			throw new DAOException("Error al eliminar proyecto", e);
		}
	}

	@Override
	public List<Proyecto> obtenerTodosLosProyectos() throws DAOException {
		List<Proyecto> proyectos = new ArrayList<>();
		String sql = "SELECT * FROM PROYECTO";
		try (PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				String nombre = rs.getString("NOMBRE");
				String descripcion = rs.getString("DESCRIPCION");
				Proyecto proyecto = new Proyecto(nombre, descripcion);
				proyecto.setEmpleadosAsignados(obtenerEmpleadosDeProyecto(nombre));
				proyectos.add(proyecto);
			}
		} catch (SQLException e) {
			throw new DAOException("Error al obtener proyectos", e);
		}
		return proyectos;
	}

	@Override
	public void asignarEmpleadoAProyecto(String nombreProyecto, String nombreEmpleado) throws DAOException {
		String sql = "INSERT INTO PROYECTO_EMPLEADO (NOMBRE_PROYECTO, NOMBRE_EMPLEADO) VALUES (?, ?)";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setString(1, nombreProyecto);
			stmt.setString(2, nombreEmpleado);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error al asignar empleado al proyecto", e);
		}
	}

	private List<String> obtenerEmpleadosDeProyecto(String nombreProyecto) throws DAOException {
		List<String> empleados = new ArrayList<>();
		String sql = "SELECT NOMBRE_EMPLEADO FROM PROYECTO_EMPLEADO WHERE NOMBRE_PROYECTO = ?";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setString(1, nombreProyecto);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				empleados.add(rs.getString("NOMBRE_EMPLEADO"));
			}
		} catch (SQLException e) {
			throw new DAOException("Error al obtener empleados del proyecto", e);
		}
		return empleados;
	}

	private void eliminarAsignacionesDeProyecto(String nombreProyecto) throws DAOException {
		String sql = "DELETE FROM PROYECTO_EMPLEADO WHERE NOMBRE_PROYECTO = ?";
		try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
			stmt.setString(1, nombreProyecto);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error al eliminar asignaciones del proyecto", e);
		}
	}
}
