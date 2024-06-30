package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProyectoH2Impl implements ProyectoDAO {
    private Connection conexion;

    public ProyectoH2Impl() throws DAOException {
        try {
            String url = "jdbc:h2:file:E:/UP/Laboratorio I/Eclipse Workspace/AdministradorProyectos/db/PROYECTO";
            conexion = DriverManager.getConnection(url, "sa", "");
            crearTabla();
        } catch (SQLException e) {
            throw new DAOException("Error al conectar con la base de datos", e);
        }
    }

    private void crearTabla() throws DAOException {
        try {
            Statement stmt = conexion.createStatement();
            String sqlProyecto = "CREATE TABLE IF NOT EXISTS PROYECTO " +
                    "(NOMBRE VARCHAR(255) PRIMARY KEY, " +
                    "DESCRIPCION VARCHAR(255))";
            stmt.executeUpdate(sqlProyecto);

            String sqlProyectoEmpleado = "CREATE TABLE IF NOT EXISTS PROYECTO_EMPLEADO " +
                    "(PROYECTO_NOMBRE VARCHAR(255), " +
                    "EMPLEADO_NOMBRE VARCHAR(255), " +
                    "PRIMARY KEY (PROYECTO_NOMBRE, EMPLEADO_NOMBRE))";
            stmt.executeUpdate(sqlProyectoEmpleado);

            System.out.println("Tablas PROYECTO y PROYECTO_EMPLEADO creadas exitosamente.");
        } catch (SQLException e) {
            throw new DAOException("Error al crear las tablas", e);
        }
    }

    @Override
    public void guardarProyecto(Proyecto proyecto) throws DAOException {
        try {
            String sql = "INSERT INTO PROYECTO (NOMBRE, DESCRIPCION) VALUES (?, ?)";
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, proyecto.getNombre());
            pstmt.setString(2, proyecto.getDescripcion());
            pstmt.executeUpdate();

            for (String empleado : proyecto.getEmpleadosAsignados()) {
                String sqlProyectoEmpleado = "INSERT INTO PROYECTO_EMPLEADO (PROYECTO_NOMBRE, EMPLEADO_NOMBRE) VALUES (?, ?)";
                PreparedStatement pstmtProyectoEmpleado = conexion.prepareStatement(sqlProyectoEmpleado);
                pstmtProyectoEmpleado.setString(1, proyecto.getNombre());
                pstmtProyectoEmpleado.setString(2, empleado);
                pstmtProyectoEmpleado.executeUpdate();
            }

            System.out.println("Proyecto guardado correctamente: " + proyecto.getNombre());
        } catch (SQLException e) {
            throw new DAOException("Error al guardar el proyecto", e);
        }
    }

    @Override
    public List<Proyecto> obtenerTodosLosProyectos() throws DAOException {
        try {
            String sql = "SELECT * FROM PROYECTO";
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            List<Proyecto> proyectos = new ArrayList<>();
            while (rs.next()) {
                Proyecto proyecto = new Proyecto(rs.getString("NOMBRE"), rs.getString("DESCRIPCION"));
                proyectos.add(proyecto);
            }

            for (Proyecto proyecto : proyectos) {
                String sqlEmpleados = "SELECT EMPLEADO_NOMBRE FROM PROYECTO_EMPLEADO WHERE PROYECTO_NOMBRE = ?";
                PreparedStatement pstmt = conexion.prepareStatement(sqlEmpleados);
                pstmt.setString(1, proyecto.getNombre());
                ResultSet rsEmpleados = pstmt.executeQuery();
                List<String> empleadosAsignados = new ArrayList<>();
                while (rsEmpleados.next()) {
                    empleadosAsignados.add(rsEmpleados.getString("EMPLEADO_NOMBRE"));
                }
                proyecto.setEmpleadosAsignados(empleadosAsignados);
            }

            return proyectos;
        } catch (SQLException e) {
            throw new DAOException("Error al obtener los proyectos", e);
        }
    }

    @Override
    public Proyecto obtenerProyectoPorNombre(String nombre) throws DAOException {
        try {
            String sql = "SELECT * FROM PROYECTO WHERE NOMBRE = ?";
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Proyecto proyecto = new Proyecto(rs.getString("NOMBRE"), rs.getString("DESCRIPCION"));
                String sqlEmpleados = "SELECT EMPLEADO_NOMBRE FROM PROYECTO_EMPLEADO WHERE PROYECTO_NOMBRE = ?";
                PreparedStatement pstmtEmpleados = conexion.prepareStatement(sqlEmpleados);
                pstmtEmpleados.setString(1, nombre);
                ResultSet rsEmpleados = pstmtEmpleados.executeQuery();
                List<String> empleadosAsignados = new ArrayList<>();
                while (rsEmpleados.next()) {
                    empleadosAsignados.add(rsEmpleados.getString("EMPLEADO_NOMBRE"));
                }
                proyecto.setEmpleadosAsignados(empleadosAsignados);
                return proyecto;
            } else {
                throw new DAOException("Proyecto no encontrado: " + nombre);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener el proyecto por nombre", e);
        }
    }

    @Override
    public void actualizarProyecto(Proyecto proyecto) throws DAOException {
        try {
            String sql = "UPDATE PROYECTO SET DESCRIPCION = ? WHERE NOMBRE = ?";
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, proyecto.getDescripcion());
            pstmt.setString(2, proyecto.getNombre());
            pstmt.executeUpdate();

            String sqlEliminarEmpleados = "DELETE FROM PROYECTO_EMPLEADO WHERE PROYECTO_NOMBRE = ?";
            PreparedStatement pstmtEliminarEmpleados = conexion.prepareStatement(sqlEliminarEmpleados);
            pstmtEliminarEmpleados.setString(1, proyecto.getNombre());
            pstmtEliminarEmpleados.executeUpdate();

            for (String empleado : proyecto.getEmpleadosAsignados()) {
                String sqlProyectoEmpleado = "INSERT INTO PROYECTO_EMPLEADO (PROYECTO_NOMBRE, EMPLEADO_NOMBRE) VALUES (?, ?)";
                PreparedStatement pstmtProyectoEmpleado = conexion.prepareStatement(sqlProyectoEmpleado);
                pstmtProyectoEmpleado.setString(1, proyecto.getNombre());
                pstmtProyectoEmpleado.setString(2, empleado);
                pstmtProyectoEmpleado.executeUpdate();
            }

            System.out.println("Proyecto actualizado correctamente: " + proyecto.getNombre());
        } catch (SQLException e) {
            throw new DAOException("Error al actualizar el proyecto", e);
        }
    }

    @Override
    public void eliminarProyecto(String nombre) throws DAOException {
        try {
            String sql = "DELETE FROM PROYECTO WHERE NOMBRE = ?";
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();

            String sqlEliminarEmpleados = "DELETE FROM PROYECTO_EMPLEADO WHERE PROYECTO_NOMBRE = ?";
            PreparedStatement pstmtEliminarEmpleados = conexion.prepareStatement(sqlEliminarEmpleados);
            pstmtEliminarEmpleados.setString(1, nombre);
            pstmtEliminarEmpleados.executeUpdate();

            System.out.println("Proyecto eliminado correctamente: " + nombre);
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar el proyecto", e);
        }
    }

    @Override
    public void asignarEmpleadoAProyecto(String nombreProyecto, String nombreEmpleado) throws DAOException {
        try {
            String sql = "INSERT INTO PROYECTO_EMPLEADO (PROYECTO_NOMBRE, EMPLEADO_NOMBRE) VALUES (?, ?)";
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, nombreProyecto);
            pstmt.setString(2, nombreEmpleado);
            pstmt.executeUpdate();
            System.out.println("Empleado " + nombreEmpleado + " asignado al proyecto " + nombreProyecto);
        } catch (SQLException e) {
            throw new DAOException("Error al asignar empleado al proyecto", e);
        }
    }
}
