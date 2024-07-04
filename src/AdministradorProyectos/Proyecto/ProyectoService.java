package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Exceptions.DAOException;
import AdministradorProyectos.Exceptions.ServiceException;

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

    public Proyecto obtenerProyectoPorNombre(String nombre) throws ServiceException {
        try {
            return proyectoDAO.obtenerProyectoPorNombre(nombre);
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener el proyecto", e);
        }
    }

    public List<Proyecto> obtenerTodosLosProyectos() throws ServiceException {
        try {
            return proyectoDAO.obtenerTodosLosProyectos();
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener los proyectos", e);
        }
    }

    public void eliminarProyecto(String nombre) throws ServiceException {
        try {
            proyectoDAO.eliminarProyecto(nombre);
        } catch (DAOException e) {
            throw new ServiceException("Error al eliminar el proyecto", e);
        }
    }

    public void actualizarProyecto(Proyecto proyecto) throws ServiceException {
        try {
            proyectoDAO.actualizarProyecto(proyecto);
        } catch (DAOException e) {
            throw new ServiceException("Error al actualizar el proyecto", e);
        }
    }

    public void asignarEmpleadoAProyecto(String proyectoNombre, Empleado empleado) throws ServiceException {
        try {
            proyectoDAO.asignarEmpleadoAProyecto(proyectoNombre, empleado);
        } catch (DAOException e) {
            throw new ServiceException("Error al asignar el empleado al proyecto", e);
        }
    }
}
