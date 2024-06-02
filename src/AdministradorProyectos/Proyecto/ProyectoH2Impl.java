package AdministradorProyectos.Proyecto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProyectoH2Impl implements ProyectoDAO {
    private Connection conexion;

    public ProyectoH2Impl() {
        try {
            this.conexion = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardarProyecto(Proyecto proyecto) {
        String sql = "INSERT INTO PROYECTO (NOMBRE, DESCRIPCION) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getDescripcion());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Proyecto obtenerProyectoPorId(int id) {
        Proyecto proyecto = null;
        String sql = "SELECT * FROM PROYECTO WHERE ID = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                proyecto = new Proyecto(rs.getString("NOMBRE"), rs.getString("DESCRIPCION"));
                // Set other attributes as needed
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proyecto;
    }

    @Override
    public List<Proyecto> obtenerTodosLosProyectos() {
        List<Proyecto> proyectos = new ArrayList<>();
        String sql = "SELECT * FROM PROYECTO";
        try (Statement stmt = conexion.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Proyecto proyecto = new Proyecto(rs.getString("NOMBRE"), rs.getString("DESCRIPCION"));
                // Set other attributes as needed
                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proyectos;
    }

    @Override
    public void actualizarProyecto(Proyecto proyecto) {
        String sql = "UPDATE PROYECTO SET NOMBRE = ?, DESCRIPCION = ? WHERE ID = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getDescripcion());
            // Set ID parameter
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarProyecto(int id) {
        String sql = "DELETE FROM PROYECTO WHERE ID = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
