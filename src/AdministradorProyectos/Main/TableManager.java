package AdministradorProyectos.Main;

import AdministradorProyectos.Exceptions.DAOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableManager {
    public static void crearTablas(Connection conexion) throws DAOException {
        try (Statement stmt = conexion.createStatement()) {
            String sqlProyecto = "CREATE TABLE IF NOT EXISTS PROYECTO (NOMBRE VARCHAR(255) PRIMARY KEY, DESCRIPCION VARCHAR(255))";
            String sqlProyectoEmpleado = "CREATE TABLE IF NOT EXISTS PROYECTO_EMPLEADO (PROYECTO_NOMBRE VARCHAR(255), EMPLEADO_NOMBRE VARCHAR(255), PRIMARY KEY(PROYECTO_NOMBRE, EMPLEADO_NOMBRE))";
            String sqlEmpleado = "CREATE TABLE IF NOT EXISTS EMPLEADO (NOMBRE VARCHAR(255) PRIMARY KEY, COSTO_HORA DOUBLE)";
            String sqlTarea = "CREATE TABLE IF NOT EXISTS TAREA (TITULO VARCHAR(255) PRIMARY KEY, DESCRIPCION VARCHAR(255), HORAS_ESTIMADAS DOUBLE, HORAS_REALES DOUBLE, EMPLEADO_NOMBRE VARCHAR(255), PROYECTO_NOMBRE VARCHAR(255))";
            
            stmt.execute(sqlProyecto);
            stmt.execute(sqlProyectoEmpleado);
            stmt.execute(sqlEmpleado);
            stmt.execute(sqlTarea);
            
            System.out.println("Tablas creadas exitosamente.");
        } catch (SQLException e) {
            throw new DAOException("Error al crear las tablas", e);
        }
    }
}
