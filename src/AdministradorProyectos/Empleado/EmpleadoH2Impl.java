package AdministradorProyectos.Empleado;

import AdministradorProyectos.Exceptions.DAOException;
import AdministradorProyectos.Main.DBManager;
import AdministradorProyectos.Main.TableManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoH2Impl implements EmpleadoDAO {
    private Connection conexion;

    public EmpleadoH2Impl() throws DAOException {
        this.conexion = DBManager.getConnection();
    }

    @Override
    public void guardarEmpleado(Empleado empleado) throws DAOException {
        try (PreparedStatement stmt = conexion.prepareStatement("INSERT INTO EMPLEADO (NOMBRE, COSTO_HORA) VALUES (?, ?)")) {
            stmt.setString(1, empleado.getNombre());
            stmt.setDouble(2, empleado.getCostoHora());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al guardar el empleado", e);
        }
    }
    
    @Override
    public Empleado obtenerEmpleadoPorNombre(String nombre) throws DAOException {
        Empleado empleado = null;
        try (PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM EMPLEADO WHERE NOMBRE = ?")) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    empleado = new Empleado(rs.getString("NOMBRE"), rs.getDouble("COSTO_HORA"), rs.getString("ESTADO"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener el empleado por nombre", e);
        }
        return empleado;
    }

    @Override
    public List<Empleado> obtenerTodosLosEmpleados() throws DAOException {
        List<Empleado> empleados = new ArrayList<>();
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLEADO")) {
            while (rs.next()) {
                Empleado empleado = new Empleado(rs.getString("NOMBRE"), rs.getDouble("COSTO_HORA"), rs.getString("ESTADO"));
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener los empleados", e);
        }
        return empleados;
    }
    
    @Override
    public List<Empleado> obtenerEmpleadosNoAsignados() throws DAOException {
        List<Empleado> empleados = new ArrayList<>();
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLEADO WHERE ESTADO IS NULL")) {
            while (rs.next()) {
                Empleado empleado = new Empleado(rs.getString("NOMBRE"), rs.getDouble("COSTO_HORA"), rs.getString("ESTADO"));
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener empleados no asignados", e);
        }
        return empleados;
    }

    @Override
    public void actualizarEmpleado(Empleado empleado) throws DAOException {
        try (PreparedStatement stmt = conexion.prepareStatement("UPDATE EMPLEADO SET COSTO_HORA = ? WHERE NOMBRE = ?")) {
            stmt.setDouble(1, empleado.getCostoHora());
            stmt.setString(2, empleado.getNombre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al actualizar el empleado", e);
        }
    }

    @Override
    public void eliminarEmpleado(String nombre) throws DAOException {
        try (PreparedStatement stmt = conexion.prepareStatement("DELETE FROM EMPLEADO WHERE NOMBRE = ?")) {
            stmt.setString(1, nombre);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar el empleado", e);
        }
    }
}
