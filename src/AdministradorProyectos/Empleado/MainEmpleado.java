package AdministradorProyectos.Empleado;

import javax.swing.SwingUtilities;

public class MainEmpleado {
    public static void main(String[] args) {
        EmpleadoDAO empleadoDAO = new EmpleadoH2Impl();
        EmpleadoService empleadoService = new EmpleadoService(empleadoDAO);

        // Iniciar la interfaz gráfica
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                EmpleadoUI frame = new EmpleadoUI(empleadoService);
                frame.setVisible(true);
            }
        });
    }
}
