package AdministradorProyectos.Tarea;

import AdministradorProyectos.Exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TareaH2Impl implements TareaDAO {
	private Connection conexion;

	public TareaH2Impl() throws DAOException {
		try {
			String url = "jdbc:h2:file:E:/UP/Laboratorio I/Eclipse Workspace/AdministradorProyectos/db/TareaDB";
			conexion = DriverManager.getConnection(url, "sa", "");
			crearTabla();
		} catch (SQLException e) {
			throw new DAOException("Error al conectar con la base de datos", e);
		}
	}

	private void crearTabla() throws DAOException {
		try (Statement stmt = conexion.createStatement()) {
			String sqlTarea = "CREATE TABLE IF NOT EXISTS TAREA (TITULO VARCHAR(255) PRIMARY KEY, DESCRIPCION VARCHAR(255), ESTIMACION_HORAS INT, HORAS_REALES INT, EMPLEADO_ASIGNADO VARCHAR(255))";
			stmt.execute(sqlTarea);
			System.out.println("Tabla TAREA creada exitosamente.");
		} catch (SQLException e) {
			throw new DAOException("Error al crear la tabla TAREA", e);
		}
	}

	@Override
	public void guardarTarea(Tarea tarea) throws DAOException {
		try (PreparedStatement stmt = conexion.prepareStatement(
				"INSERT INTO TAREA (TITULO, DESCRIPCION, ESTIMACION_HORAS, HORAS_REALES, EMPLEADO_ASIGNADO) VALUES (?, ?, ?, ?, ?)")) {
			stmt.setString(1, tarea.getTitulo());
			stmt.setString(2, tarea.getDescripcion());
			stmt.setInt(3, tarea.getEstimacionHoras());
			stmt.setInt(4, tarea.getHorasReales());
			stmt.setString(5, tarea.getEmpleadoAsignado());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error al guardar la tarea", e);
		}
	}

	@Override
	public List<Tarea> obtenerTodasLasTareas() throws DAOException {
		List<Tarea> tareas = new ArrayList<>();
		try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM TAREA")) {
			while (rs.next()) {
				Tarea tarea = new Tarea(rs.getString("TITULO"), rs.getString("DESCRIPCION"),
						rs.getInt("ESTIMACION_HORAS"), rs.getInt("HORAS_REALES"), rs.getString("EMPLEADO_ASIGNADO"));
				tareas.add(tarea);
			}
		} catch (SQLException e) {
			throw new DAOException("Error al obtener las tareas", e);
		}
		return tareas;
	}

	@Override
	public void actualizarTarea(Tarea tarea) throws DAOException {
		try (PreparedStatement stmt = conexion.prepareStatement(
				"UPDATE TAREA SET DESCRIPCION = ?, ESTIMACION_HORAS = ?, HORAS_REALES = ?, EMPLEADO_ASIGNADO = ? WHERE TITULO = ?")) {
			stmt.setString(1, tarea.getDescripcion());
			stmt.setInt(2, tarea.getEstimacionHoras());
			stmt.setInt(3, tarea.getHorasReales());
			stmt.setString(4, tarea.getEmpleadoAsignado());
			stmt.setString(5, tarea.getTitulo());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error al actualizar la tarea", e);
		}
	}

	@Override
	public void eliminarTarea(String titulo) throws DAOException {
		try (PreparedStatement stmt = conexion.prepareStatement("DELETE FROM TAREA WHERE TITULO = ?")) {
			stmt.setString(1, titulo);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error al eliminar la tarea", e);
		}
	}
}
