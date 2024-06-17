package AdministradorProyectos.Empleado;

import java.util.List;

import AdministradorProyectos.Exceptions.DAOException;

public interface EmpleadoDAO {
	void guardarEmpleado(Empleado empleado) throws DAOException;

	Empleado obtenerEmpleadoPorNombre(String nombre) throws DAOException;

	List<Empleado> obtenerTodosLosEmpleados() throws DAOException;

	void actualizarEmpleado(Empleado empleado) throws DAOException;

	void eliminarEmpleado(String nombre) throws DAOException;
}
