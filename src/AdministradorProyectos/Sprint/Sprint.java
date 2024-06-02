package AdministradorProyectos.Sprint;

import java.util.List;
import java.util.Date;
import AdministradorProyectos.Tarea.Tarea;
import AdministradorProyectos.Proyecto.Proyecto;

public class Sprint {
    private int id;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private Proyecto proyecto;
    private List<Tarea> tareas;

    public Sprint(int id, String nombre, Date fechaInicio, Date fechaFin, Proyecto proyecto, List<Tarea> tareas) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.proyecto = proyecto;
        this.tareas = tareas;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }
}

