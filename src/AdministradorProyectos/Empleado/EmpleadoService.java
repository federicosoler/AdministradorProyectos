package AdministradorProyectos.Empleado;

import AdministradorProyectos.Exceptions.ServiceException;
import AdministradorProyectos.Exceptions.DAOException;
import java.util.List;
import java.util.stream.Collectors;

public class EmpleadoService {
    private EmpleadoDAO empleadoDAO;

    public EmpleadoService(EmpleadoDAO empleadoDAO) {
        this.empleadoDAO = empleadoDAO;
    }

    public void guardarEmpleado(Empleado empleado) throws ServiceException {
        try {
            empleadoDAO.guardarEmpleado(empleado);
        } catch (DAOException e) {
            throw new ServiceException("Error al guardar empleado", e);
        }
    }

    public void actualizarEmpleado(Empleado empleado) throws ServiceException {
        try {
            empleadoDAO.actualizarEmpleado(empleado);
        } catch (DAOException e) {
            throw new ServiceException("Error al actualizar empleado", e);
        }
    }

    public void eliminarEmpleado(String nombre) throws ServiceException {
        try {
            empleadoDAO.eliminarEmpleado(nombre);
        } catch (DAOException e) {
            throw new ServiceException("Error al eliminar empleado", e);
        }
    }
    
    public Empleado obtenerEmpleadoPorNombre(String nombre) throws ServiceException {
        try {
            return empleadoDAO.obtenerEmpleadoPorNombre(nombre);
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener el empleado por nombre", e);
        }
    }

    public List<Empleado> obtenerTodosLosEmpleados() throws ServiceException {
        try {
            return empleadoDAO.obtenerTodosLosEmpleados();
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener empleados", e);
        }
    }

    public List<String> obtenerTodosLosNombresEmpleados() throws ServiceException {
        try {
            return obtenerTodosLosEmpleados().stream()
                    .map(Empleado::getNombre)
                    .collect(Collectors.toList());
        } catch (ServiceException e) {
            throw new ServiceException("Error al obtener nombres de empleados", e);
        }
    }
    
    public List<Empleado> obtenerEmpleadosNoAsignados() throws ServiceException {
        try {
            return empleadoDAO.obtenerEmpleadosNoAsignados();
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener empleados no asignados", e);
        }
    }

}
