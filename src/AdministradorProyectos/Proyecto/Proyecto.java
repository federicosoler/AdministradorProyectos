package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Tarea.Tarea;
import java.util.List;

public class Proyecto {
    private String nombre;
    private String descripcion;
    private List<Empleado> empleadosAsignados;
    private List<Tarea> tareasAsignadas;

    // Constructor, getters y setters
    public Proyecto(String nombre, String descripcion, List<Empleado> empleadosAsignados, List<Tarea> tareasAsignadas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empleadosAsignados = empleadosAsignados;
        this.tareasAsignadas = tareasAsignadas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Empleado> getEmpleadosAsignados() {
        return empleadosAsignados;
    }

    public void setEmpleadosAsignados(List<Empleado> empleadosAsignados) {
        this.empleadosAsignados = empleadosAsignados;
    }

    public List<Tarea> getTareasAsignadas() {
        return tareasAsignadas;
    }

    public void setTareasAsignadas(List<Tarea> tareasAsignadas) {
        this.tareasAsignadas = tareasAsignadas;
    }

    public double calcularCostoHoras() {
        double totalHoras = 0;
        for (Tarea tarea : tareasAsignadas) {
            totalHoras += tarea.getHorasReales();
        }
        return totalHoras;
    }

    public double calcularCostoDinero() {
        double totalDinero = 0;
        for (Tarea tarea : tareasAsignadas) {
            for (Empleado empleado : empleadosAsignados) {
                if (tarea.getEmpleadoAsignado() != null && tarea.getEmpleadoAsignado().equals(empleado.getNombre())) {
                    totalDinero += tarea.getHorasReales() * empleado.getCostoHora();
                }
            }
        }
        return totalDinero;
    }
}

