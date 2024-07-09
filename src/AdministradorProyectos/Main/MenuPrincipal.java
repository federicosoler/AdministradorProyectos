package AdministradorProyectos.Main;

import AdministradorProyectos.Empleado.EmpleadoService;
import AdministradorProyectos.Empleado.EmpleadoUI;
import AdministradorProyectos.Exceptions.DAOException;
import AdministradorProyectos.Proyecto.ProyectoService;
import AdministradorProyectos.Proyecto.ProyectoUI;
import AdministradorProyectos.Tarea.TareaService;
import AdministradorProyectos.Tarea.TareaUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {
    private EmpleadoService empleadoService;
    private ProyectoService proyectoService;
    private TareaService tareaService;

    public MenuPrincipal(EmpleadoService empleadoService, ProyectoService proyectoService, TareaService tareaService) {
        this.empleadoService = empleadoService;
        this.proyectoService = proyectoService;
        this.tareaService = tareaService;

        setTitle("Menú Principal");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton gestionEmpleadosButton = new JButton("Gestión de Empleados");
        gestionEmpleadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmpleadoUI empleadoUI = new EmpleadoUI(empleadoService);
                empleadoUI.setVisible(true);
            }
        });

        JButton gestionProyectosButton = new JButton("Gestión de Proyectos");
        gestionProyectosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProyectoUI proyectoUI = new ProyectoUI(proyectoService, empleadoService, tareaService);
                proyectoUI.setVisible(true);
            }
        });

        JButton gestionTareasButton = new JButton("Gestión de Tareas");
        gestionTareasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TareaUI tareaUI = new TareaUI(tareaService, empleadoService);
                tareaUI.setVisible(true);
            }
        });

        JButton cerrarProgramaButton = new JButton("Cerrar Programa");
        cerrarProgramaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(gestionEmpleadosButton);
        add(gestionProyectosButton);
        add(gestionTareasButton);
        add(cerrarProgramaButton);
    }
}
