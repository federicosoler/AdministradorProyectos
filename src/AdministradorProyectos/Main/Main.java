package AdministradorProyectos.Main;

import AdministradorProyectos.Empleado.EmpleadoDAO;
import AdministradorProyectos.Empleado.EmpleadoH2Impl;
import AdministradorProyectos.Empleado.EmpleadoService;
import AdministradorProyectos.Empleado.EmpleadoUI;
import AdministradorProyectos.Exceptions.DAOException;
import AdministradorProyectos.Proyecto.ProyectoDAO;
import AdministradorProyectos.Proyecto.ProyectoH2Impl;
import AdministradorProyectos.Proyecto.ProyectoService;
import AdministradorProyectos.Proyecto.ProyectoUI;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Crear tablas necesarias una sola vez
            TableManager tableManager = new TableManager("DB");
            tableManager.crearTablaEmpleado();
            tableManager.crearTablaProyecto();
            tableManager.crearTablaProyectoEmpleado();

            // Inicializar Empleados
            EmpleadoDAO empleadoDAO = new EmpleadoH2Impl();
            EmpleadoService empleadoService = new EmpleadoService(empleadoDAO);
            EmpleadoUI empleadoUI = new EmpleadoUI(empleadoService);

            // Inicializar Proyectos
            ProyectoDAO proyectoDAO = new ProyectoH2Impl();
            ProyectoService proyectoService = new ProyectoService(proyectoDAO);
            ProyectoUI proyectoUI = new ProyectoUI(proyectoService, empleadoService); // Pasar EmpleadoService

            // Mostrar las interfaces de usuario
            SwingUtilities.invokeLater(() -> {
                empleadoUI.setVisible(true);
                proyectoUI.setVisible(true);
            });
        } catch (DAOException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage());
            System.exit(1);
        }
    }
}
