package AdministradorProyectos.Empleado;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import AdministradorProyectos.Exceptions.DAOException;

public class MainEmpleado {
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
