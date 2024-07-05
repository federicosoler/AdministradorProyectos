package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Exceptions.DAOException;
import AdministradorProyectos.Main.DBManager;
import AdministradorProyectos.Main.TableManager;

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
        try (PreparedStatement stmt = conexion.prepareStatement("INSERT INTO PROYECTO (NOMBRE, DESCRIPCION) VALUES (?, ?)")) {
            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getDescripcion());
            stmt.executeUpdate();
            for (String empleado : proyecto.getEmpleadosAsignados()) {
                asignarEmpleadoAProyecto(proyecto.getNombre(), empleado);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al guardar el proyecto", e);
        }
    }

    @Override
    public List<Proyecto> obtenerTodosLosProyectos() throws DAOException {
        List<Proyecto> proyectos = new ArrayList<>();
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM PROYECTO")) {
            while (rs.next()) {
                Proyecto proyecto = new Proyecto(rs.getString("NOMBRE"), rs.getString("DESCRIPCION"));
                proyecto.setEmpleadosAsignados(obtenerEmpleadosAsignados(rs.getString("NOMBRE")));
                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener los proyectos", e);
        }
        return proyectos;
    }

    private List<String> obtenerEmpleadosAsignados(String nombreProyecto) throws DAOException {
        List<String> empleados = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement("SELECT EMPLEADO_NOMBRE FROM PROYECTO_EMPLEADO WHERE PROYECTO_NOMBRE = ?")) {
            stmt.setString(1, nombreProyecto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    empleados.add(rs.getString("EMPLEADO_NOMBRE"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener los empleados asignados al proyecto", e);
        }
        return empleados;
    }

    @Override
    public void actualizarProyecto(Proyecto proyecto) throws DAOException {
        try (PreparedStatement stmt = conexion.prepareStatement("UPDATE PROYECTO SET DESCRIPCION = ? WHERE NOMBRE = ?")) {
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
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar el proyecto", e);
        }
    }

    private void eliminarEmpleadosDeProyecto(String nombreProyecto) throws DAOException {
        try (PreparedStatement stmt = conexion.prepareStatement("DELETE FROM PROYECTO_EMPLEADO WHERE PROYECTO_NOMBRE = ?")) {
            stmt.setString(1, nombreProyecto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar los empleados del proyecto", e);
        }
    }

    @Override
    public void asignarEmpleadoAProyecto(String nombreProyecto, String nombreEmpleado) throws DAOException {
        try (PreparedStatement stmt = conexion.prepareStatement(
                "INSERT INTO PROYECTO_EMPLEADO (PROYECTO_NOMBRE, EMPLEADO_NOMBRE) VALUES (?, ?)")) {
            stmt.setString(1, nombreProyecto);
            stmt.setString(2, nombreEmpleado);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al asignar empleado al proyecto", e);
        }
    }
}
