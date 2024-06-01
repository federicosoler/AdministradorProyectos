package AdministradorProyectos.Tarea;

import AdministradorProyectos.Empleado.Empleado;

public class Tarea {
	private String titulo;
	private String descripcion;
	private int estimacionHoras;
	private int horasReales;
	private Empleado empleadoAsignado;

	// Constructor
	public Tarea(String titulo, String descripcion, int estimacionHoras) {
		this.setTitulo(titulo);
		this.setDescripcion(descripcion);
		this.setEstimacionHoras(estimacionHoras);
		this.setHorasReales(0); // Por defecto, las horas reales son 0 al crear una nueva tarea
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

}
