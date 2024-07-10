package AdministradorProyectos.Tarea;

import AdministradorProyectos.Exceptions.DAOException;

import java.util.List;

public interface TareaDAO {
    void guardarTarea(Tarea tarea) throws DAOException;

    List<Tarea> obtenerTodasLasTareas() throws DAOException;

    void actualizarTarea(Tarea tarea) throws DAOException;

    void eliminarTarea(String titulo) throws DAOException;

    Tarea obtenerTareaPorTitulo(String titulo) throws DAOException;  // Nuevo método
}
