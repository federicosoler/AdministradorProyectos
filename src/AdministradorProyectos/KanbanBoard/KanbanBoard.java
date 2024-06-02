package AdministradorProyectos.KanbanBoard;

import java.util.List;
import AdministradorProyectos.Tarea.Tarea;

public class KanbanBoard {
    private List<Tarea> backlog;
    private List<Tarea> toDo;
    private List<Tarea> inProgress;
    private List<Tarea> done;

    public KanbanBoard(List<Tarea> backlog, List<Tarea> toDo, List<Tarea> inProgress, List<Tarea> done) {
        this.backlog = backlog;
        this.toDo = toDo;
        this.inProgress = inProgress;
        this.done = done;
    }

    // Getters y Setters
    public List<Tarea> getBacklog() {
        return backlog;
    }

    public void setBacklog(List<Tarea> backlog) {
        this.backlog = backlog;
    }

    public List<Tarea> getToDo() {
        return toDo;
    }

    public void setToDo(List<Tarea> toDo) {
        this.toDo = toDo;
    }

    public List<Tarea> getInProgress() {
        return inProgress;
    }

    public void setInProgress(List<Tarea> inProgress) {
        this.inProgress = inProgress;
    }

    public List<Tarea> getDone() {
        return done;
    }

    public void setDone(List<Tarea> done) {
        this.done = done;
    }
}

