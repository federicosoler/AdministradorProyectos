package AdministradorProyectos.Main;

import AdministradorProyectos.Empleado.*;
import AdministradorProyectos.Exceptions.*;

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
