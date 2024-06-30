package AdministradorProyectos.Proyecto;

import java.util.List;

public class Proyecto {
	private String nombre;
	private String descripcion;
	private List<String> empleadosAsignados;
	private List<String> tareas;

	public Proyecto(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
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

	public List<String> getEmpleadosAsignados() {
		return empleadosAsignados;
	}

	public void setEmpleadosAsignados(List<String> empleadosAsignados) {
		this.empleadosAsignados = empleadosAsignados;
	}

	public List<String> getTareas() {
		return tareas;
	}

	public void setTareas(List<String> tareas) {
		this.tareas = tareas;
	}
}
