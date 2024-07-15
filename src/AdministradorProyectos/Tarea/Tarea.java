package AdministradorProyectos.Tarea;

public class Tarea {
	private String titulo;
	private String descripcion;
	private double horasEstimadas;
	private double horasReales;
	private String empleadoAsignado;
	private String estado;

	public Tarea(String titulo, String descripcion, double horasEstimadas, double horasReales, String empleadoAsignado, String estado) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.horasEstimadas = horasEstimadas;
		this.horasReales = horasReales;
		this.empleadoAsignado = empleadoAsignado;
	    this.estado = estado;
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

	public double getHorasEstimadas() {
		return horasEstimadas;
	}

	public void setHorasEstimadas(double horasEstimadas) {
		this.horasEstimadas = horasEstimadas;
	}

	public double getHorasReales() {
		return horasReales;
	}

	public void setHorasReales(double horasReales) {
		this.horasReales = horasReales;
	}

	public String getEmpleadoAsignado() {
		return empleadoAsignado;
	}

	public void setEmpleadoAsignado(String empleadoAsignado) {
		this.empleadoAsignado = empleadoAsignado;
	}
	
	public String getEstado() {
	    return estado;
	}

	public void setEstado(String estado) {
	    this.estado = estado;
	}
}
