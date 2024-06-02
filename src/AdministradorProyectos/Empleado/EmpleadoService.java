package AdministradorProyectos.Empleado;

import java.util.List;

public class EmpleadoService {
	private EmpleadoDAO empleadoDAO;

	public EmpleadoService(EmpleadoDAO empleadoDAO) {
		this.empleadoDAO = empleadoDAO;
	}

	public void agregarEmpleado(Empleado empleado) {
		empleadoDAO.guardarEmpleado(empleado);
	}

	public Empleado obtenerEmpleadoPorNombre(String nombre) {
		return empleadoDAO.obtenerEmpleadoPorNombre(nombre);
	}

	public List<Empleado> obtenerTodosLosEmpleados() {
		return empleadoDAO.obtenerTodosLosEmpleados();
	}

	public void actualizarEmpleado(Empleado empleado) {
		empleadoDAO.actualizarEmpleado(empleado);
	}

	public void eliminarEmpleado(String nombre) {
		empleadoDAO.eliminarEmpleado(nombre);
	}
}
