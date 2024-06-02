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
        String sql = "CREATE TABLE IF NOT EXISTS PROYECTO " +
                     "(ID INT PRIMARY KEY AUTO_INCREMENT, " +
                     "NOMBRE VARCHAR(255), " +
                     "DESCRIPCION VARCHAR(255))";

        try (Connection conexion = DriverManager.getConnection(url, user, password);
             Statement stmt = conexion.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
