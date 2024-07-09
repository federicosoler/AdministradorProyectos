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

    public void eliminarProyecto(String nombre) throws ServiceException {
        try {
            proyectoDAO.eliminarProyecto(nombre);
        } catch (DAOException e) {
            throw new ServiceException("Error al eliminar el proyecto", e);
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

    public void asignarEmpleadoAProyecto(String nombreProyecto, String nombreEmpleado) throws ServiceException {
        try {
            proyectoDAO.asignarEmpleadoAProyecto(nombreProyecto, nombreEmpleado);
        } catch (DAOException e) {
            throw new ServiceException("Error al asignar empleado al proyecto", e);
        }
    }
    
    public void desasignarEmpleadoDeProyecto(String nombreProyecto, String nombreEmpleado) throws ServiceException {
        try {
            proyectoDAO.desasignarEmpleadoDeProyecto(nombreProyecto, nombreEmpleado);
        } catch (DAOException e) {
            throw new ServiceException("Error al desasignar empleado del proyecto", e);
        }
    }

}
