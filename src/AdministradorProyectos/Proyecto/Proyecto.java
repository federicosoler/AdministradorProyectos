package AdministradorProyectos.Proyecto;

import java.util.ArrayList;
import java.util.List;

public class Proyecto {
    private String nombre;
    private String descripcion;
    private List<String> empleadosAsignados;

    public Proyecto(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empleadosAsignados = new ArrayList<>();
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

    public List<String> getEmpleadosAsignados() {
        return empleadosAsignados;
    }

    public void setEmpleadosAsignados(List<String> empleadosAsignados) {
        this.empleadosAsignados = empleadosAsignados;
    }

    public void agregarEmpleado(String nombreEmpleado) {
        this.empleadosAsignados.add(nombreEmpleado);
    }
}
