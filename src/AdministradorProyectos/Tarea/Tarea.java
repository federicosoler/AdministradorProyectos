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
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estimacionHoras = estimacionHoras;
        this.horasReales = 0; // Por defecto, las horas reales son 0 al crear una nueva tarea
    }

    // Métodos getters y setters (omitiendo para simplificar)
}

