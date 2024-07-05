package AdministradorProyectos.Main;

import AdministradorProyectos.Exceptions.DAOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final String URL = "jdbc:h2:file:E:/UP/Laboratorio I/Eclipse Workspace/AdministradorProyectos/db/DB";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static Connection connection;

    public static Connection getConnection() throws DAOException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                TableManager.crearTablas(connection);
            } catch (SQLException e) {
                throw new DAOException("Error al conectar con la base de datos", e);
            }
        }
        return connection;
    }
}
