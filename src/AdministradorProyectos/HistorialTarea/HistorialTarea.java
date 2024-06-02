package AdministradorProyectos.HistorialTarea;

import java.util.Date;

import AdministradorProyectos.Tarea.Tarea;
import AdministradorProyectos.Empleado.Empleado;

public class HistorialTarea {
    private int id;
    private Tarea tarea;
    private String estadoAnterior;
    private String nuevoEstado;
    private Date fechaCambio;
    private Empleado responsable;

    public HistorialTarea(int id, Tarea tarea, String estadoAnterior, String nuevoEstado, Date fechaCambio, Empleado responsable) {
        this.id = id;
        this.tarea = tarea;
        this.estadoAnterior = estadoAnterior;
        this.nuevoEstado = nuevoEstado;
        this.fechaCambio = fechaCambio;
        this.responsable = responsable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(String estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public String getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(String nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }

    public Date getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public Empleado getResponsable() {
        return responsable;
    }

    public void setResponsable(Empleado responsable) {
        this.responsable = responsable;
    }
}

