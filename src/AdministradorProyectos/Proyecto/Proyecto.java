package AdministradorProyectos.Proyecto;

import java.util.ArrayList;
import java.util.List;

import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Tarea.Tarea;
import AdministradorProyectos.Sprint.Sprint;

public class Proyecto {
	private String nombre;
	private String descripcion;
	private List<Empleado> empleadosAsignados;
	private List<Tarea> tareas;
	private List<Sprint> sprints;

	public Proyecto(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.empleadosAsignados = new ArrayList<>();
		this.tareas = new ArrayList<>();
		this.sprints = new ArrayList<>();
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

	public List<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}

	public List<Sprint> getSprints() {
		return sprints;
	}

	public void setSprints(List<Sprint> sprints) {
		this.sprints = sprints;
	}

	public void agregarTarea(Tarea tarea) {
		this.tareas.add(tarea);
	}

	public void agregarEmpleado(Empleado empleado) {
		this.empleadosAsignados.add(empleado);
	}

	public void agregarSprint(Sprint sprint) {
		this.sprints.add(sprint);
	}
}
