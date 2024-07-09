package AdministradorProyectos.Proyecto;

import java.util.List;
import AdministradorProyectos.Empleado.Empleado;

public class Proyecto {
    private String nombre;
    private String descripcion;
    private List<Empleado> empleadosAsignados;

    public Proyecto(String nombre, String descripcion, List<Empleado> empleadosAsignados) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empleadosAsignados = empleadosAsignados;
    }

    // Getters y Setters

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
}
