package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Exceptions.DAOException;
import AdministradorProyectos.Tarea.Tarea;
import AdministradorProyectos.Main.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProyectoH2Impl implements ProyectoDAO {
    private Connection conexion;

    public ProyectoH2Impl() throws DAOException {
        this.conexion = DBManager.getConnection();
    }

    @Override
    public void guardarProyecto(Proyecto proyecto) throws DAOException {
        try (PreparedStatement stmt = conexion.prepareStatement(
                "INSERT INTO PROYECTO (NOMBRE, DESCRIPCION) VALUES (?, ?)")) {
            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getDescripcion());
            stmt.executeUpdate();
            for (Empleado empleado : proyecto.getEmpleadosAsignados()) {
                asignarEmpleadoAProyecto(proyecto.getNombre(), empleado.getNombre());
            }
            for (Tarea tarea : proyecto.getTareasAsignadas()) {  // Añadir esta línea
                asignarTareaAProyecto(proyecto.getNombre(), tarea.getTitulo());
            }
        } catch (SQLException e) {
            throw new DAOException("Error al guardar el proyecto", e);
        }
    }

    @Override
    public List<Proyecto> obtenerTodosLosProyectos() throws DAOException {
        List<Proyecto> proyectos = new ArrayList<>();
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM PROYECTO")) {
            while (rs.next()) {
                Proyecto proyecto = new Proyecto(rs.getString("NOMBRE"), rs.getString("DESCRIPCION"),
                        obtenerEmpleadosAsignados(rs.getString("NOMBRE")), obtenerTareasAsignadas(rs.getString("NOMBRE")));  // Modificar esta línea
                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener los proyectos", e);
        }
        return proyectos;
    }

    private List<Empleado> obtenerEmpleadosAsignados(String nombreProyecto) throws DAOException {
        List<Empleado> empleados = new ArrayList<>();
        try (PreparedStatement stmt = conexion
                .prepareStatement("SELECT EMPLEADO_NOMBRE FROM PROYECTO_EMPLEADO WHERE PROYECTO_NOMBRE = ?")) {
            stmt.setString(1, nombreProyecto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Empleado empleado = new Empleado(rs.getString("EMPLEADO_NOMBRE"), 0);
                    empleados.add(empleado);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener los empleados asignados al proyecto", e);
        }
        return empleados;
    }

    private List<Tarea> obtenerTareasAsignadas(String nombreProyecto) throws DAOException {  // Añadir este método
        List<Tarea> tareas = new ArrayList<>();
        try (PreparedStatement stmt = conexion
                .prepareStatement("SELECT TAREA_TITULO FROM PROYECTO_TAREA WHERE PROYECTO_NOMBRE = ?")) {
            stmt.setString(1, nombreProyecto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Tarea tarea = new Tarea(rs.getString("TAREA_TITULO"), "", 0, 0, null);
                    tareas.add(tarea);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener las tareas asignadas al proyecto", e);
        }
        return tareas;
    }

    @Override
    public Proyecto obtenerProyectoPorNombre(String nombre) throws DAOException {
        Proyecto proyecto = null;
        try (PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM PROYECTO WHERE NOMBRE = ?")) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    proyecto = new Proyecto(rs.getString("NOMBRE"), rs.getString("DESCRIPCION"),
                            obtenerEmpleadosAsignados(nombre), obtenerTareasAsignadas(nombre));  // Modificar esta línea
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener el proyecto por nombre", e);
        }
        return proyecto;
    }

    @Override
    public void actualizarProyecto(Proyecto proyecto) throws DAOException {
        try (PreparedStatement stmt = conexion
                .prepareStatement("UPDATE PROYECTO SET DESCRIPCION = ? WHERE NOMBRE = ?")) {
            stmt.setString(1, proyecto.getDescripcion());
            stmt.setString(2, proyecto.getNombre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al actualizar el proyecto", e);
        }
    }

    @Override
    public void eliminarProyecto(String nombre) throws DAOException {
        try (PreparedStatement stmt = conexion.prepareStatement("DELETE FROM PROYECTO WHERE NOMBRE = ?")) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
            eliminarEmpleadosDeProyecto(nombre);
            eliminarTareasDeProyecto(nombre);  // Añadir esta línea
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar el proyecto", e);
        }
    }

    private void eliminarEmpleadosDeProyecto(String nombreProyecto) throws DAOException {
        try (PreparedStatement stmt = conexion
                .prepareStatement("DELETE FROM PROYECTO_EMPLEADO WHERE PROYECTO_NOMBRE = ?")) {
            stmt.setString(1, nombreProyecto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar los empleados del proyecto", e);
        }
    }

    private void eliminarTareasDeProyecto(String nombreProyecto) throws DAOException {  // Añadir este método
        try (PreparedStatement stmt = conexion
                .prepareStatement("DELETE FROM PROYECTO_TAREA WHERE PROYECTO_NOMBRE = ?")) {
            stmt.setString(1, nombreProyecto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar las tareas del proyecto", e);
        }
    }

    @Override
    public void asignarEmpleadoAProyecto(String nombreProyecto, String nombreEmpleado) throws DAOException {
        try (PreparedStatement stmt = conexion
                .prepareStatement("INSERT INTO PROYECTO_EMPLEADO (PROYECTO_NOMBRE, EMPLEADO_NOMBRE) VALUES (?, ?)")) {
            stmt.setString(1, nombreProyecto);
            stmt.setString(2, nombreEmpleado);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al asignar empleado al proyecto", e);
        }
    }

    @Override
    public void desasignarEmpleadoDeProyecto(String nombreProyecto, String nombreEmpleado) throws DAOException {
        try (PreparedStatement stmt = conexion
                .prepareStatement("DELETE FROM PROYECTO_EMPLEADO WHERE PROYECTO_NOMBRE = ? AND EMPLEADO_NOMBRE = ?")) {
            stmt.setString(1, nombreProyecto);
            stmt.setString(2, nombreEmpleado);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al desasignar empleado del proyecto", e);
        }
    }

    @Override
    public void asignarTareaAProyecto(String nombreProyecto, String tituloTarea) throws DAOException {  // Añadir este método
        try (PreparedStatement stmt = conexion
                .prepareStatement("INSERT INTO PROYECTO_TAREA (PROYECTO_NOMBRE, TAREA_TITULO) VALUES (?, ?)")) {
            stmt.setString(1, nombreProyecto);
            stmt.setString(2, tituloTarea);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al asignar tarea al proyecto", e);
        }
    }

    @Override
    public void desasignarTareaDeProyecto(String nombreProyecto, String tituloTarea) throws DAOException {  // Añadir este método
        try (PreparedStatement stmt = conexion
                .prepareStatement("DELETE FROM PROYECTO_TAREA WHERE PROYECTO_NOMBRE = ? AND TAREA_TITULO = ?")) {
            stmt.setString(1, nombreProyecto);
            stmt.setString(2, tituloTarea);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al desasignar tarea del proyecto", e);
        }
    }
}
