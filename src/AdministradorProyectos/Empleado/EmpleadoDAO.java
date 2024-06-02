package AdministradorProyectos.Empleado;

import java.util.List;

public interface EmpleadoDAO {
	void guardarEmpleado(Empleado empleado);

	Empleado obtenerEmpleadoPorNombre(String nombre);

	List<Empleado> obtenerTodosLosEmpleados();

	void actualizarEmpleado(Empleado empleado);

	void eliminarEmpleado(String nombre);
}
