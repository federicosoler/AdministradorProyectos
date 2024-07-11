package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Exceptions.DAOException;
import AdministradorProyectos.Tarea.Tarea;

import java.util.List;

public interface ProyectoDAO {
	void guardarProyecto(Proyecto proyecto) throws DAOException;

	List<Proyecto> obtenerTodosLosProyectos() throws DAOException;

	Proyecto obtenerProyectoPorNombre(String nombre) throws DAOException;

	void actualizarProyecto(Proyecto proyecto) throws DAOException;

	void eliminarProyecto(String nombre) throws DAOException;

	void asignarEmpleadoAProyecto(String nombreProyecto, String nombreEmpleado) throws DAOException;

	void desasignarEmpleadoDeProyecto(String nombreProyecto, String nombreEmpleado) throws DAOException;

	void asignarTareaAProyecto(String nombreProyecto, String tituloTarea) throws DAOException;

	void desasignarTareaDeProyecto(String nombreProyecto, String tituloTarea) throws DAOException;

	List<Tarea> obtenerTareasAsignadas(String nombreProyecto) throws DAOException; // Nuevo método

    List<Empleado> obtenerEmpleadosAsignados(String nombreProyecto) throws DAOException;
}
