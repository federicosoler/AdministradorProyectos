package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Tarea.Tarea;
import AdministradorProyectos.Empleado.Empleado;

import java.util.ArrayList;
import java.util.List;

public class Proyecto {
	private String nombre;
	private String descripcion;
	private List<Tarea> tareas;
	private List<Empleado> empleados;

	// Constructor
	public Proyecto(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tareas = new ArrayList<>();
		this.empleados = new ArrayList<>();
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

	public List<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}

	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}

	// Otros métodos si los necesitas
}
