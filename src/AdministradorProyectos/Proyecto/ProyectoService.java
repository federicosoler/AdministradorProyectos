package AdministradorProyectos.Proyecto;

import java.util.List;

public class ProyectoService {
    private ProyectoDAO proyectoDAO;

    public ProyectoService(ProyectoDAO proyectoDAO) {
        this.proyectoDAO = proyectoDAO;
    }

    public void agregarProyecto(Proyecto proyecto) {
        proyectoDAO.guardarProyecto(proyecto);
    }

    public Proyecto obtenerProyectoPorNombre(String nombre) {
        return proyectoDAO.obtenerProyectoPorNombre(nombre);
    }

    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoDAO.obtenerTodosLosProyectos();
    }

    public void actualizarProyecto(Proyecto proyecto) {
        proyectoDAO.actualizarProyecto(proyecto);
    }

    public void eliminarProyecto(String nombre) {
        proyectoDAO.eliminarProyecto(nombre);
    }
}
