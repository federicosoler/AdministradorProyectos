package AdministradorProyectos.Empleado;

public class Empleado {
	private String nombre;
	private double costoHora;
	private String estado;

	public Empleado(String nombre, double costoHora, String estado) {
		this.nombre = nombre;
		this.costoHora = costoHora;
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getCostoHora() {
		return costoHora;
	}

	public void setCostoHora(double costoHora) {
		this.costoHora = costoHora;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
