package AdministradorProyectos.HistorialTarea;

import java.time.LocalDateTime;

import AdministradorProyectos.Empleado.Empleado;

public class HistorialTarea {
	private String estadoAnterior;
	private String nuevoEstado;
	private Empleado responsable;
	private LocalDateTime fechaCambio;

	public HistorialTarea(String estadoAnterior, String nuevoEstado, Empleado responsable, LocalDateTime fechaCambio) {
		this.estadoAnterior = estadoAnterior;
		this.nuevoEstado = nuevoEstado;
		this.responsable = responsable;
		this.fechaCambio = fechaCambio;
	}

	public String getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public String getNuevoEstado() {
		return nuevoEstado;
	}

	public void setNuevoEstado(String nuevoEstado) {
		this.nuevoEstado = nuevoEstado;
	}

	public Empleado getResponsable() {
		return responsable;
	}

	public void setResponsable(Empleado responsable) {
		this.responsable = responsable;
	}

	public LocalDateTime getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(LocalDateTime fechaCambio) {
		this.fechaCambio = fechaCambio;
	}
}
