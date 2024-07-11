package AdministradorProyectos.Main;

import AdministradorProyectos.Empleado.EmpleadoH2Impl;
import AdministradorProyectos.Empleado.EmpleadoService;
import AdministradorProyectos.Exceptions.DAOException;
import AdministradorProyectos.Proyecto.ProyectoH2Impl;
import AdministradorProyectos.Proyecto.ProyectoService;
import AdministradorProyectos.Tarea.TareaH2Impl;
import AdministradorProyectos.Tarea.TareaService;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        try {
            // Inicializar servicios
            EmpleadoService empleadoService = new EmpleadoService(new EmpleadoH2Impl());
            TareaService tareaService = new TareaService(new TareaH2Impl());
            ProyectoService proyectoService = new ProyectoService(new ProyectoH2Impl(), empleadoService, tareaService);

            // Crear y mostrar el menú principal
            SwingUtilities.invokeLater(() -> {
                MenuPrincipal menuPrincipal = new MenuPrincipal(empleadoService, proyectoService, tareaService);
                menuPrincipal.setVisible(true);
            });
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage());
            System.exit(1);
        }
    }
}
