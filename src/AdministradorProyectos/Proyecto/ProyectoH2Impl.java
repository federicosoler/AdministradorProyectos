package AdministradorProyectos.Proyecto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.HistorialTarea.HistorialTarea;

public class ProyectoH2Impl implements ProyectoDAO {
    private Connection conexion;

    public ProyectoH2Impl() {
        try {
            this.conexion = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardarProyecto(Proyecto proyecto) {
        String sql = "INSERT INTO PROYECTO (NOMBRE, DESCRIPCION) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getDescripcion());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Proyecto obtenerProyectoPorNombre(String nombre) {
        Proyecto proyecto = null;
        String sql = "SELECT * FROM PROYECTO WHERE NOMBRE = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                proyecto = new Proyecto(rs.getString("NOMBRE"), rs.getString("DESCRIPCION"));
                // Aquí debes agregar la lógica para cargar las listas de Empleados, Tareas y Sprints
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proyecto;
    }

    @Override
    public List<Proyecto> obtenerTodosLosProyectos() {
        List<Proyecto> proyectos = new ArrayList<>();
        String sql = "SELECT * FROM PROYECTO";
        try (Statement stmt = conexion.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Proyecto proyecto = new Proyecto(rs.getString("NOMBRE"), rs.getString("DESCRIPCION"));
                // Aquí debes agregar la lógica para cargar las listas de Empleados, Tareas y Sprints
                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proyectos;
    }

    @Override
    public void actualizarProyecto(Proyecto proyecto) {
        String sql = "UPDATE PROYECTO SET DESCRIPCION = ? WHERE NOMBRE = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, proyecto.getDescripcion());
            stmt.setString(2, proyecto.getNombre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarProyecto(String nombre) {
        String sql = "DELETE FROM PROYECTO WHERE NOMBRE = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Métodos adicionales para manejar el historial de tareas

    public void guardarHistorialTarea(HistorialTarea historial) {
        String sql = "INSERT INTO HISTORIAL_TAREA (ESTADO_ANTERIOR, NUEVO_ESTADO, RESPONSABLE, FECHA_CAMBIO) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, historial.getEstadoAnterior());
            stmt.setString(2, historial.getNuevoEstado());
            stmt.setString(3, historial.getResponsable().getNombre());
            stmt.setTimestamp(4, Timestamp.valueOf(historial.getFechaCambio()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<HistorialTarea> obtenerHistorialPorTarea(String tareaTitulo) {
        List<HistorialTarea> historial = new ArrayList<>();
        String sql = "SELECT * FROM HISTORIAL_TAREA WHERE TAREA_TITULO = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, tareaTitulo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HistorialTarea hist = new HistorialTarea(
                        rs.getString("ESTADO_ANTERIOR"),
                        rs.getString("NUEVO_ESTADO"),
                        new Empleado(rs.getString("RESPONSABLE"), 0),  // Aquí deberías recuperar el objeto Empleado completo
                        rs.getTimestamp("FECHA_CAMBIO").toLocalDateTime()
                );
                historial.add(hist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historial;
    }
}
