package AdministradorProyectos.Tarea;

import AdministradorProyectos.Exceptions.DAOException;
import AdministradorProyectos.Exceptions.ServiceException;

import java.util.List;

public class TareaService {
	private TareaDAO tareaDAO;

	public TareaService(TareaDAO tareaDAO) {
		this.tareaDAO = tareaDAO;
	}

	public void guardarTarea(Tarea tarea) throws ServiceException {
		try {
			tareaDAO.guardarTarea(tarea);
		} catch (DAOException e) {
			throw new ServiceException("Error al guardar la tarea", e);
		}
	}

	public List<Tarea> obtenerTodasLasTareas() throws ServiceException {
		try {
			return tareaDAO.obtenerTodasLasTareas();
		} catch (DAOException e) {
			throw new ServiceException("Error al obtener las tareas", e);
		}
	}

	public void actualizarTarea(Tarea tarea) throws ServiceException {
		try {
			tareaDAO.actualizarTarea(tarea);
		} catch (DAOException e) {
			throw new ServiceException("Error al actualizar la tarea", e);
		}
	}

	public void eliminarTarea(String titulo) throws ServiceException {
		try {
			tareaDAO.eliminarTarea(titulo);
		} catch (DAOException e) {
			throw new ServiceException("Error al eliminar la tarea", e);
		}
	}
}
