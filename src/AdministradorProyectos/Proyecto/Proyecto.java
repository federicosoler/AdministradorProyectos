package AdministradorProyectos.Proyecto;

import java.util.List;
import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Tarea.Tarea;
import AdministradorProyectos.Sprint.Sprint;

public class Proyecto {
	private int id;
	private String nombre;
	private String descripcion;
	private List<Empleado> empleadosAsignados;
	private List<Tarea> tareas;
	private List<Sprint> sprints;

	public Proyecto(int id, String nombre, List<Empleado> empleadosAsignados, List<Tarea> tareas,
			List<Sprint> sprints) {
		this.setId(id);
		this.setNombre(nombre);
		this.setEmpleadosAsignados(empleadosAsignados);
		this.setTareas(tareas);
		this.setSprints(sprints);
	}

	public Proyecto(String string, String string2) {
		// TODO Auto-generated constructor stub
	}

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

}
