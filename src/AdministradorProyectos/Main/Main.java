package AdministradorProyectos.Main;

import AdministradorProyectos.Empleado.*;
import AdministradorProyectos.Exceptions.DAOException;
import AdministradorProyectos.Proyecto.*;
import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		try {
			// Inicializar Empleados
			EmpleadoDAO empleadoDAO = new EmpleadoH2Impl();
			EmpleadoService empleadoService = new EmpleadoService(empleadoDAO);
			EmpleadoUI empleadoUI = new EmpleadoUI(empleadoService);

			// Inicializar Proyectos
			ProyectoDAO proyectoDAO = new ProyectoH2Impl();
			ProyectoService proyectoService = new ProyectoService(proyectoDAO);
			ProyectoUI proyectoUI = new ProyectoUI(proyectoService);

			// Mostrar las interfaces de usuario
			SwingUtilities.invokeLater(() -> {
				empleadoUI.setVisible(true);
				proyectoUI.setVisible(true);
			});
		} catch (DAOException e) {
			JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage());
			System.exit(1);
		}
	}
}
