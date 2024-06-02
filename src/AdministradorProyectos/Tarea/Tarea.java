package AdministradorProyectos.Tarea;

import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Proyecto.Proyecto;
import AdministradorProyectos.Sprint.Sprint;

public class Tarea {
    private int id;
    private String titulo;
    private String descripcion;
    private int estimacionHoras;
    private int horasReales;
    private Empleado empleadoAsignado;
    private Proyecto proyecto;
    private Sprint sprint;
    private String estado;

    public Tarea(int id, String titulo, String descripcion, int estimacionHoras, int horasReales, Empleado empleadoAsignado, Proyecto proyecto, Sprint sprint, String estado) {
        this.setId(id);
        this.setTitulo(titulo);
        this.setDescripcion(descripcion);
        this.setEstimacionHoras(estimacionHoras);
        this.setHorasReales(horasReales);
        this.setEmpleadoAsignado(empleadoAsignado);
        this.setProyecto(proyecto);
        this.setSprint(sprint);
        this.setEstado(estado);
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getEstimacionHoras() {
		return estimacionHoras;
	}

	public void setEstimacionHoras(int estimacionHoras) {
		this.estimacionHoras = estimacionHoras;
	}

	public int getHorasReales() {
		return horasReales;
	}

	public void setHorasReales(int horasReales) {
		this.horasReales = horasReales;
	}

	public Empleado getEmpleadoAsignado() {
		return empleadoAsignado;
	}

	public void setEmpleadoAsignado(Empleado empleadoAsignado) {
		this.empleadoAsignado = empleadoAsignado;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}


}
