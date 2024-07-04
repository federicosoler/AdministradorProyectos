package AdministradorProyectos.Tarea;

public class Tarea {
	private String titulo;
	private String descripcion;
	private int estimacionHoras;
	private int horasReales;
	private String empleadoAsignado;

	public Tarea(String titulo, String descripcion, int estimacionHoras, int horasReales, String empleadoAsignado) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.estimacionHoras = estimacionHoras;
		this.horasReales = horasReales;
		this.empleadoAsignado = empleadoAsignado;
	}

	// Getters y Setters
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

	public String getEmpleadoAsignado() {
		return empleadoAsignado;
	}

	public void setEmpleadoAsignado(String empleadoAsignado) {
		this.empleadoAsignado = empleadoAsignado;
	}
}
