package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Exceptions.DAOException;
import AdministradorProyectos.Exceptions.ServiceException;
import AdministradorProyectos.Tarea.Tarea;

import java.util.List;

public class ProyectoService {
    private ProyectoDAO proyectoDAO;

    public ProyectoService(ProyectoDAO proyectoDAO) {
        this.proyectoDAO = proyectoDAO;
    }

    public void guardarProyecto(Proyecto proyecto) throws ServiceException {
        try {
            proyectoDAO.guardarProyecto(proyecto);
        } catch (DAOException e) {
            throw new ServiceException("Error al guardar el proyecto", e);
        }
    }

    public List<Proyecto> obtenerTodosLosProyectos() throws ServiceException {
        try {
            return proyectoDAO.obtenerTodosLosProyectos();
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener los proyectos", e);
        }
    }

    public Proyecto obtenerProyectoPorNombre(String nombre) throws ServiceException {
        try {
            return proyectoDAO.obtenerProyectoPorNombre(nombre);
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener el proyecto", e);
        }
    }

    public void actualizarProyecto(Proyecto proyecto) throws ServiceException {
        try {
            proyectoDAO.actualizarProyecto(proyecto);
        } catch (DAOException e) {
            throw new ServiceException("Error al actualizar el proyecto", e);
        }
    }

    public void eliminarProyecto(String nombre) throws ServiceException {
        try {
            proyectoDAO.eliminarProyecto(nombre);
        } catch (DAOException e) {
            throw new ServiceException("Error al eliminar el proyecto", e);
        }
    }

    public void asignarEmpleadoAProyecto(String nombreProyecto, String nombreEmpleado) throws ServiceException {
        try {
            proyectoDAO.asignarEmpleadoAProyecto(nombreProyecto, nombreEmpleado);
        } catch (DAOException e) {
            throw new ServiceException("Error al asignar el empleado al proyecto", e);
        }
    }

    public void desasignarEmpleadoDeProyecto(String nombreProyecto, String nombreEmpleado) throws ServiceException {
        try {
            proyectoDAO.desasignarEmpleadoDeProyecto(nombreProyecto, nombreEmpleado);
        } catch (DAOException e) {
            throw new ServiceException("Error al desasignar el empleado del proyecto", e);
        }
    }

    public void asignarTareaAProyecto(String nombreProyecto, String tituloTarea) throws ServiceException {  // Añadir este método
        try {
            proyectoDAO.asignarTareaAProyecto(nombreProyecto, tituloTarea);
        } catch (DAOException e) {
            throw new ServiceException("Error al asignar la tarea al proyecto", e);
        }
    }

    public void desasignarTareaDeProyecto(String nombreProyecto, String tituloTarea) throws ServiceException {  // Añadir este método
        try {
            proyectoDAO.desasignarTareaDeProyecto(nombreProyecto, tituloTarea);
        } catch (DAOException e) {
            throw new ServiceException("Error al desasignar la tarea del proyecto", e);
        }
    }
}
