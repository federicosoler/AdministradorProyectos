package AdministradorProyectos.Proyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableManagerProyecto {
    public static void crearTabla() {
        String url = "jdbc:h2:~/test";
        String user = "sa";
        String password = "";
        String sqlProyecto = "CREATE TABLE IF NOT EXISTS PROYECTO " +
                             "(NOMBRE VARCHAR(255) PRIMARY KEY, " +
                             "DESCRIPCION VARCHAR(255))";
        String sqlHistorial = "CREATE TABLE IF NOT EXISTS HISTORIAL_TAREA " +
                              "(ESTADO_ANTERIOR VARCHAR(255), " +
                              "NUEVO_ESTADO VARCHAR(255), " +
                              "RESPONSABLE VARCHAR(255), " +
                              "FECHA_CAMBIO TIMESTAMP, " +
                              "TAREA_TITULO VARCHAR(255))";

        try (Connection conexion = DriverManager.getConnection(url, user, password);
             Statement stmt = conexion.createStatement()) {
            stmt.execute(sqlProyecto);
            stmt.execute(sqlHistorial);
            System.out.println("Tablas PROYECTO y HISTORIAL_TAREA creadas exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
