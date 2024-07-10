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
            String sqlTarea = "CREATE TABLE IF NOT EXISTS TAREA (TITULO VARCHAR(255) PRIMARY KEY, DESCRIPCION VARCHAR(255), HORAS_ESTIMADAS DOUBLE, HORAS_REALES DOUBLE, EMPLEADO_NOMBRE VARCHAR(255))";
            String sqlProyectoTarea = "CREATE TABLE IF NOT EXISTS PROYECTO_TAREA (PROYECTO_NOMBRE VARCHAR(255), TAREA_TITULO VARCHAR(255), PRIMARY KEY(PROYECTO_NOMBRE, TAREA_TITULO))";  // Añadir esta línea

            stmt.execute(sqlProyecto);
            stmt.execute(sqlProyectoEmpleado);
            stmt.execute(sqlEmpleado);
            stmt.execute(sqlTarea);
            stmt.execute(sqlProyectoTarea);

            System.out.println("Tablas creadas exitosamente.");
        } catch (SQLException e) {
            throw new DAOException("Error al crear las tablas", e);
        }
    }
}
