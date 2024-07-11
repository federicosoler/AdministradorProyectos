package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Empleado.EmpleadoService;
import AdministradorProyectos.Exceptions.DAOException;
import AdministradorProyectos.Exceptions.ServiceException;
import AdministradorProyectos.Tarea.Tarea;
import AdministradorProyectos.Tarea.TareaService;

import java.util.List;

public class ProyectoService {
    private ProyectoDAO proyectoDAO;
    private EmpleadoService empleadoService;
    private TareaService tareaService;

    public ProyectoService(ProyectoDAO proyectoDAO, EmpleadoService empleadoService, TareaService tareaService) {
        this.proyectoDAO = proyectoDAO;
        this.empleadoService = empleadoService;
        this.tareaService = tareaService;
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

    public void guardarProyecto(Proyecto proyecto) throws ServiceException {
        try {
            proyectoDAO.guardarProyecto(proyecto);
        } catch (DAOException e) {
            throw new ServiceException("Error al guardar el proyecto", e);
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

    public void asignarTareaAProyecto(String nombreProyecto, String tituloTarea) throws ServiceException {
        try {
            proyectoDAO.asignarTareaAProyecto(nombreProyecto, tituloTarea);
        } catch (DAOException e) {
            throw new ServiceException("Error al asignar la tarea al proyecto", e);
        }
    }

    public void desasignarTareaDeProyecto(String nombreProyecto, String tituloTarea) throws ServiceException {
        try {
            proyectoDAO.desasignarTareaDeProyecto(nombreProyecto, tituloTarea);
        } catch (DAOException e) {
            throw new ServiceException("Error al desasignar la tarea del proyecto", e);
        }
    }

    public List<Tarea> obtenerTareasAsignadas(String nombreProyecto) throws ServiceException {
        try {
            return proyectoDAO.obtenerTareasAsignadas(nombreProyecto);
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener las tareas asignadas al proyecto", e);
        }
    }

    public double calcularCostoHoras(String nombreProyecto) throws ServiceException {
        try {
            Proyecto proyecto = proyectoDAO.obtenerProyectoPorNombre(nombreProyecto);
            double totalHoras = 0;
            for (Tarea tarea : proyecto.getTareasAsignadas()) {
                totalHoras += tarea.getHorasReales();
            }
            return totalHoras;
        } catch (DAOException e) {
            throw new ServiceException("Error al calcular el costo en horas del proyecto", e);
        }
    }

    public double calcularCostoDinero(String nombreProyecto) throws ServiceException {
        try {
            Proyecto proyecto = proyectoDAO.obtenerProyectoPorNombre(nombreProyecto);
            double totalDinero = 0;
            for (Tarea tarea : proyecto.getTareasAsignadas()) {
                if (tarea.getEmpleadoAsignado() != null) { // Verificar que hay un empleado asignado
                    Empleado empleado = empleadoService.obtenerEmpleadoPorNombre(tarea.getEmpleadoAsignado());
                    totalDinero += tarea.getHorasReales() * empleado.getCostoHora();
                }
            }
            return totalDinero;
        } catch (DAOException e) {
            throw new ServiceException("Error al calcular el costo en dinero del proyecto", e);
        }
    }

    public String generarReporteProyecto(String nombreProyecto) throws ServiceException {
        try {
            Proyecto proyecto = proyectoDAO.obtenerProyectoPorNombre(nombreProyecto);
            List<Tarea> tareasAsignadas = proyecto.getTareasAsignadas();
            List<Empleado> empleadosAsignados = proyecto.getEmpleadosAsignados();

            double costoHoras = 0;
            double costoDinero = 0;

            for (Tarea tarea : tareasAsignadas) {
                costoHoras += tarea.getHorasReales();
                if (tarea.getEmpleadoAsignado() != null) { // Verificar que hay un empleado asignado
                    Empleado empleado = empleadoService.obtenerEmpleadoPorNombre(tarea.getEmpleadoAsignado());
                    costoDinero += tarea.getHorasReales() * empleado.getCostoHora();
                }
            }

            return String.format("Reporte de Proyecto: %s\nCosto en Horas: %.2f\nCosto en Dinero: $%.2f",
                    proyecto.getNombre(), costoHoras, costoDinero);
        } catch (DAOException e) {
            throw new ServiceException("Error al generar el reporte del proyecto", e);
        }
    }
}

