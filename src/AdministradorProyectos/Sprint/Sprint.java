package AdministradorProyectos.Sprint;

import java.util.Date;
import java.util.List;

import AdministradorProyectos.Tarea.Tarea;

public class Sprint {
	private String nombre;
	private Date fechaInicio;
	private Date fechaFin;
	private List<Tarea> tareas;

	public Sprint(String nombre, Date fechaInicio, Date fechaFin, List<Tarea> tareas) {
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tareas = tareas;
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

	public List<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}

	public void agregarTarea(Tarea tarea) {
		this.tareas.add(tarea);
	}
}
