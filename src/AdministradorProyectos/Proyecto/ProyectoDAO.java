package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Exceptions.DAOException;
import java.util.List;

public interface ProyectoDAO {
	void guardarProyecto(Proyecto proyecto) throws DAOException;

	void eliminarProyecto(String nombre) throws DAOException;

	Proyecto obtenerProyectoPorNombre(String nombre) throws DAOException;

	List<Proyecto> obtenerTodosLosProyectos() throws DAOException;
	
	void actualizarProyecto(Proyecto proyecto) throws DAOException;

	void asignarEmpleadoAProyecto(String nombreProyecto, String nombreEmpleado) throws DAOException;
	
	void desasignarEmpleadoDeProyecto(String nombreProyecto, String nombreEmpleado) throws DAOException;

}
