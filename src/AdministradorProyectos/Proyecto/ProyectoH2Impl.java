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
        if (proyecto.getNombre() == null || proyecto.getDescripcion() == null) {
            System.out.println("Error: nombre o descripción del proyecto es null");
            return;
        }

        String sql = "INSERT INTO PROYECTO (NOMBRE, DESCRIPCION) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getDescripcion());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Proyecto guardado correctamente: " + proyecto.getNombre());
            } else {
                System.out.println("Error al guardar el proyecto: " + proyecto.getNombre());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Proyecto obtenerProyectoPorNombre(String nombre) {
        Proyecto proyecto = null;
        String sql = "SELECT * FROM PROYECTO WHERE NOMBRE = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Proyecto encontrado: " + rs.getString("NOMBRE"));
                proyecto = new Proyecto(rs.getString("NOMBRE"), rs.getString("DESCRIPCION"));
            } else {
                System.out.println("Proyecto no encontrado: " + nombre);
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
                String nombre = rs.getString("NOMBRE");
                String descripcion = rs.getString("DESCRIPCION");
                if (nombre != null && descripcion != null) {
                    Proyecto proyecto = new Proyecto(nombre, descripcion);
                    proyectos.add(proyecto);
                    System.out.println("Proyecto recuperado: " + nombre);
                } else {
                    System.out.println("Datos del proyecto nulos encontrados");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proyectos;
    }

    @Override
    public void actualizarProyecto(Proyecto proyecto) {
        String sql = "UPDATE PROYECTO SET DESCRIPCION = ? WHERE NOMBRE = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, proyecto.getDescripcion());
            stmt.setString(2, proyecto.getNombre());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Proyecto actualizado correctamente: " + proyecto.getNombre());
            } else {
                System.out.println("Error al actualizar el proyecto: " + proyecto.getNombre());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarProyecto(String nombre) {
        String sql = "DELETE FROM PROYECTO WHERE NOMBRE = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Proyecto eliminado correctamente: " + nombre);
            } else {
                System.out.println("No se encontró el proyecto con nombre: " + nombre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
