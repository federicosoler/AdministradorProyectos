package AdministradorProyectos.Proyecto;

import java.util.List;

public interface ProyectoDAO {
	void guardarProyecto(Proyecto proyecto);

	Proyecto obtenerProyectoPorNombre(String nombre);

	List<Proyecto> obtenerTodosLosProyectos();

	void actualizarProyecto(Proyecto proyecto);

	void eliminarProyecto(String nombre);
}
