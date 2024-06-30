package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Exceptions.DAOException;

import java.util.List;

public interface ProyectoDAO {
	void guardarProyecto(Proyecto proyecto) throws DAOException;

	void actualizarProyecto(Proyecto proyecto) throws DAOException;

	void eliminarProyecto(String nombre) throws DAOException;

	List<Proyecto> obtenerTodosLosProyectos() throws DAOException;
}
