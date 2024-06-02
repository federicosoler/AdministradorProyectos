package AdministradorProyectos.Proyecto;

import java.util.List;

public interface ProyectoDAO {
	void guardarProyecto(Proyecto proyecto);

	Proyecto obtenerProyectoPorId(int id);

	List<Proyecto> obtenerTodosLosProyectos();

	void actualizarProyecto(Proyecto proyecto);

	void eliminarProyecto(int id);
}
