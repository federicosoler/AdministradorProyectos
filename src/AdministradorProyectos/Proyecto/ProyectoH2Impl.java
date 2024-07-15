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
            for (Tarea tarea : proyecto.getTareasAsignadas()) {
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

    public List<Empleado> obtenerEmpleadosAsignados(String nombreProyecto) throws DAOException {
        List<Empleado> empleados = new ArrayList<>();
        try (PreparedStatement stmt = conexion
                .prepareStatement("SELECT EMPLEADO.NOMBRE, EMPLEADO.COSTO_HORA, EMPLEADO.ESTADO FROM PROYECTO_EMPLEADO JOIN EMPLEADO ON PROYECTO_EMPLEADO.EMPLEADO_NOMBRE = EMPLEADO.NOMBRE WHERE PROYECTO_NOMBRE = ?")) {
            stmt.setString(1, nombreProyecto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Empleado empleado = new Empleado(rs.getString("NOMBRE"), rs.getDouble("COSTO_HORA"), rs.getString("ESTADO"));
                    empleados.add(empleado);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener los empleados asignados al proyecto", e);
        }
        return empleados;
    }


    @Override
    public List<Tarea> obtenerTareasAsignadas(String nombreProyecto) throws DAOException {
        List<Tarea> tareas = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement(
                "SELECT TAREA.TITULO, TAREA.DESCRIPCION, TAREA.HORAS_ESTIMADAS, TAREA.HORAS_REALES, TAREA.EMPLEADO_ASIGNADO, TAREA.ESTADO " + // Añadir TAREA.ESTADO aquí
                        "FROM PROYECTO_TAREA " +
                        "JOIN TAREA ON PROYECTO_TAREA.TAREA_TITULO = TAREA.TITULO " +
                        "WHERE PROYECTO_TAREA.PROYECTO_NOMBRE = ?")) {
            stmt.setString(1, nombreProyecto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Tarea tarea = new Tarea(rs.getString("TITULO"), rs.getString("DESCRIPCION"),
                            rs.getDouble("HORAS_ESTIMADAS"), rs.getDouble("HORAS_REALES"),
                            rs.getString("EMPLEADO_ASIGNADO"), rs.getString("ESTADO")); // Añadir rs.getString("ESTADO") aquí
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

    public void eliminarEmpleadosDeProyecto(String nombreProyecto) throws DAOException {
        try (PreparedStatement stmt = conexion
                .prepareStatement("DELETE FROM PROYECTO_EMPLEADO WHERE PROYECTO_NOMBRE = ?")) {
            stmt.setString(1, nombreProyecto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar los empleados del proyecto", e);
        }
    }

    public void eliminarTareasDeProyecto(String nombreProyecto) throws DAOException {  // Añadir este método
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

            // Actualizar estado del empleado
            try (PreparedStatement updateStmt = conexion
                    .prepareStatement("UPDATE EMPLEADO SET ESTADO = ? WHERE NOMBRE = ?")) {
                updateStmt.setString(1, nombreProyecto);
                updateStmt.setString(2, nombreEmpleado);
                updateStmt.executeUpdate();
            }
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

            // Actualizar estado del empleado
            try (PreparedStatement updateStmt = conexion
                    .prepareStatement("UPDATE EMPLEADO SET ESTADO = NULL WHERE NOMBRE = ?")) {
                updateStmt.setString(1, nombreEmpleado);
                updateStmt.executeUpdate();
            }
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
