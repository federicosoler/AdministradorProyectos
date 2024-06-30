package AdministradorProyectos.Main;

import AdministradorProyectos.Empleado.EmpleadoDAO;
import AdministradorProyectos.Empleado.EmpleadoH2Impl;
import AdministradorProyectos.Empleado.EmpleadoService;
import AdministradorProyectos.Empleado.EmpleadoUI;
import AdministradorProyectos.Exceptions.DAOException;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        try {
            EmpleadoDAO empleadoDAO = new EmpleadoH2Impl();
            EmpleadoService empleadoService = new EmpleadoService(empleadoDAO);
            EmpleadoUI frame = new EmpleadoUI(empleadoService);
            frame.setVisible(true);
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage());
            System.exit(1);
        }
    }
}
