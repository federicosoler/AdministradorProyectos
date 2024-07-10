package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Tarea.Tarea;
import java.util.List;

public class Proyecto {
    private String nombre;
    private String descripcion;
    private List<Empleado> empleadosAsignados;
    private List<Tarea> tareasAsignadas;  // Añadir esta línea

    public Proyecto(String nombre, String descripcion, List<Empleado> empleadosAsignados, List<Tarea> tareasAsignadas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empleadosAsignados = empleadosAsignados;
        this.tareasAsignadas = tareasAsignadas;  // Añadir esta línea
    }

    // Getters y setters

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
}
