package AdministradorProyectos.Tarea;

import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Proyecto.Proyecto;

public class Tarea {
	private String titulo;
	private String descripcion;
	private int estimacionHoras;
	private int horasReales;
	private String estado;
	private Empleado responsable;
	private Proyecto proyecto;

	public Tarea(String titulo, String descripcion, int estimacionHoras, Proyecto proyecto, Empleado responsable) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.estimacionHoras = estimacionHoras;
		this.horasReales = 0;
		this.estado = "Pendiente";
		this.proyecto = proyecto;
		this.responsable = responsable;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Empleado getResponsable() {
		return responsable;
	}

	public void setResponsable(Empleado responsable) {
		this.responsable = responsable;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
}
