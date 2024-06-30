package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Exceptions.DAOException;
import AdministradorProyectos.Main.TableManager;

import java.sql.*;
import java.util.*;

public class ProyectoH2Impl implements ProyectoDAO {
	private Connection conexion;

	public ProyectoH2Impl() throws DAOException {
		try {
			TableManager tableManager = new TableManager("PROYECTO");
			tableManager.crearTablaProyecto();
			String url = "jdbc:h2:file:E:/UP/Laboratorio I/Eclipse Workspace/AdministradorProyectos/db/PROYECTO";
			conexion = DriverManager.getConnection(url, "sa", "");
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
				proyectos.add(proyecto);
			}
		} catch (SQLException e) {
			throw new DAOException("Error al obtener proyectos", e);
		}
		return proyectos;
	}
}
