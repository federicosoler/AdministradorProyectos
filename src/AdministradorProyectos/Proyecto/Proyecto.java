package AdministradorProyectos.Proyecto;

import java.util.List;
import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Tarea.Tarea;
import AdministradorProyectos.Sprint.Sprint;

public class Proyecto {
    private int id;
    private String nombre;
    private List<Empleado> empleadosAsignados;
    private List<Tarea> tareas;
    private List<Sprint> sprints;

    // Constructor, getters y setters

    public Proyecto(int id, String nombre, List<Empleado> empleadosAsignados, List<Tarea> tareas, List<Sprint> sprints) {
        this.setId(id);
        this.setNombre(nombre);
        this.setEmpleadosAsignados(empleadosAsignados);
        this.setTareas(tareas);
        this.setSprints(sprints);
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

    // Getters y Setters
    // (agregar todos los métodos necesarios aquí)
}
