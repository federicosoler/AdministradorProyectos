package AdministradorProyectos.Empleado;

public class Empleado {
    private String nombre;
    private double costoHora;

    public Empleado(String nombre, double costoHora) {
        this.nombre = nombre;
        this.costoHora = costoHora;
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
}
