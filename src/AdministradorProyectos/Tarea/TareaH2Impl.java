package AdministradorProyectos.Tarea;

import AdministradorProyectos.Exceptions.DAOException;
import AdministradorProyectos.Main.DBManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TareaH2Impl implements TareaDAO {
    private Connection conexion;

    public TareaH2Impl() throws DAOException {
        this.conexion = DBManager.getConnection();
    }

    @Override
    public void guardarTarea(Tarea tarea) throws DAOException {
        try (PreparedStatement stmt = conexion.prepareStatement(
                "INSERT INTO TAREA (TITULO, DESCRIPCION, HORAS_ESTIMADAS, HORAS_REALES, EMPLEADO_ASIGNADO, ESTADO) VALUES (?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, tarea.getTitulo());
            stmt.setString(2, tarea.getDescripcion());
            stmt.setDouble(3, tarea.getHorasEstimadas());
            stmt.setDouble(4, tarea.getHorasReales());
            if (tarea.getEmpleadoAsignado() != null) {
                stmt.setString(5, tarea.getEmpleadoAsignado());
            } else {
                stmt.setNull(5, Types.VARCHAR);
            }
            if (tarea.getEstado() != null) {
                stmt.setString(6, tarea.getEstado());
            } else {
                stmt.setNull(6, Types.VARCHAR);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al guardar la tarea: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Tarea> obtenerTodasLasTareas() throws DAOException {
        List<Tarea> tareas = new ArrayList<>();
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM TAREA")) {
            while (rs.next()) {
                Tarea tarea = new Tarea(rs.getString("TITULO"), rs.getString("DESCRIPCION"),
                        rs.getDouble("HORAS_ESTIMADAS"), rs.getDouble("HORAS_REALES"), rs.getString("EMPLEADO_ASIGNADO"), rs.getString("ESTADO"));
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
                "UPDATE TAREA SET DESCRIPCION = ?, HORAS_ESTIMADAS = ?, HORAS_REALES = ?, EMPLEADO_ASIGNADO = ?, ESTADO = ? WHERE TITULO = ?")) {
            stmt.setString(1, tarea.getDescripcion());
            stmt.setDouble(2, tarea.getHorasEstimadas());
            stmt.setDouble(3, tarea.getHorasReales());
            if (tarea.getEmpleadoAsignado() != null) {
                stmt.setString(4, tarea.getEmpleadoAsignado());
            } else {
                stmt.setNull(4, Types.VARCHAR);
            }
            stmt.setString(5, tarea.getEstado());
            stmt.setString(6, tarea.getTitulo());
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

    @Override
    public Tarea obtenerTareaPorTitulo(String titulo) throws DAOException {
        Tarea tarea = null;
        try (PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM TAREA WHERE TITULO = ?")) {
            stmt.setString(1, titulo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tarea = new Tarea(rs.getString("TITULO"), rs.getString("DESCRIPCION"),
                            rs.getDouble("HORAS_ESTIMADAS"), rs.getDouble("HORAS_REALES"), rs.getString("EMPLEADO_ASIGNADO"), rs.getString("ESTADO"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener la tarea por título", e);
        }
        return tarea;
    }
}
