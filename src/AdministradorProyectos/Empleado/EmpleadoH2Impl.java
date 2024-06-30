package AdministradorProyectos.Empleado;

import AdministradorProyectos.Exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoH2Impl implements EmpleadoDAO {
    private Connection conexion;

    public EmpleadoH2Impl() throws DAOException {
        try {
            String url = "jdbc:h2:file:E:/UP/Laboratorio I/Eclipse Workspace/AdministradorProyectos/db/EmpleadoDB";
            conexion = DriverManager.getConnection(url, "sa", "");
            crearTabla();
        } catch (SQLException e) {
            throw new DAOException("Error al conectar con la base de datos", e);
        }
    }

    private void crearTabla() throws DAOException {
        try (Statement stmt = conexion.createStatement()) {
            String sqlEmpleado = "CREATE TABLE IF NOT EXISTS EMPLEADO (NOMBRE VARCHAR(255) PRIMARY KEY, COSTO_HORA DOUBLE)";
            stmt.execute(sqlEmpleado);
            System.out.println("Tabla EMPLEADO creada exitosamente.");
        } catch (SQLException e) {
            throw new DAOException("Error al crear la tabla EMPLEADO", e);
        }
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
    public List<Empleado> obtenerTodosLosEmpleados() throws DAOException {
        List<Empleado> empleados = new ArrayList<>();
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLEADO")) {
            while (rs.next()) {
                Empleado empleado = new Empleado(rs.getString("NOMBRE"), rs.getDouble("COSTO_HORA"));
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener los empleados", e);
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
