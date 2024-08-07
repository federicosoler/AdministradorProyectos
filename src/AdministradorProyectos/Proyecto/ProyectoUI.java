package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Empleado.EmpleadoService;
import AdministradorProyectos.Exceptions.ServiceException;
import AdministradorProyectos.Tarea.Tarea;
import AdministradorProyectos.Tarea.TareaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProyectoUI extends JFrame {
    private ProyectoService proyectoService;
    private EmpleadoService empleadoService;
    private TareaService tareaService;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nombreField;
    private JTextField descripcionField;
    private JComboBox<String> empleadosComboBox;
    private JComboBox<String> tareasComboBox;

    public ProyectoUI(ProyectoService proyectoService, EmpleadoService empleadoService, TareaService tareaService) {
        this.proyectoService = proyectoService;
        this.empleadoService = empleadoService;
        this.tareaService = tareaService;
        setTitle("Gestión de Proyectos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Nombre", "Descripción", "Empleados Asignados", "Tareas Asignadas"}, 0);
        table = new JTable(tableModel);
        cargarProyectos();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2));

        panel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panel.add(nombreField);

        panel.add(new JLabel("Descripción:"));
        descripcionField = new JTextField();
        panel.add(descripcionField);

        JButton addButton = new JButton("Agregar");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProyecto();
            }
        });
        panel.add(addButton);

        JButton updateButton = new JButton("Actualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProyecto();
            }
        });
        panel.add(updateButton);

        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProyecto();
            }
        });
        panel.add(deleteButton);

        JButton recargarButton = new JButton("Refrescar Empleados y Tareas");
        recargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recargarEmpleadosYtareas();
            }
        });
        panel.add(recargarButton);

        panel.add(new JLabel("Empleados Libres:"));
        empleadosComboBox = new JComboBox<>();
        cargarEmpleados();
        panel.add(empleadosComboBox);

        JButton asignarButton = new JButton("Asignar Empleado");
        asignarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarEmpleadoAProyecto();
            }
        });
        panel.add(asignarButton);

        JButton desasignarTodosButton = new JButton("Desasignar Empleados");
        desasignarTodosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desasignarTodosEmpleadosDeProyecto();
            }
        });
        panel.add(desasignarTodosButton);

        panel.add(new JLabel("Tareas sin Proyecto:"));
        tareasComboBox = new JComboBox<>();
        cargarTareas();
        panel.add(tareasComboBox);

        JButton asignarTareaButton = new JButton("Asignar Tarea");
        asignarTareaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarTareaAProyecto();
            }
        });
        panel.add(asignarTareaButton);

        JButton desasignarTareaButton = new JButton("Desasignar Tarea");
        desasignarTareaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desasignarTareaDeProyecto();
            }
        });
        panel.add(desasignarTareaButton);

        JButton reportButton = new JButton("Generar Reporte");
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte();
            }
        });
        panel.add(reportButton);

        add(panel, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(event -> {
            if (table.getSelectedRow() >= 0) {
                nombreField.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                descripcionField.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                // Aquí actualizamos el ComboBox con los empleados asignados
                String empleadosAsignados = table.getValueAt(table.getSelectedRow(), 2).toString();
                empleadosComboBox.setSelectedItem(empleadosAsignados);
                // Aquí actualizamos el ComboBox con las tareas asignadas
                String tareasAsignadas = table.getValueAt(table.getSelectedRow(), 3).toString();
                tareasComboBox.setSelectedItem(tareasAsignadas);
            }
        });
    }
    
    private void recargarEmpleadosYtareas() {
        cargarEmpleados();
        cargarTareas();
    }

    private void cargarProyectos() {
        try {
            List<Proyecto> proyectos = proyectoService.obtenerTodosLosProyectos();
            tableModel.setRowCount(0);
            for (Proyecto proyecto : proyectos) {
                String empleados = String.join(", ", proyecto.getEmpleadosAsignados().stream().map(Empleado::getNombre).toArray(String[]::new));
                String tareas = String.join(", ", proyecto.getTareasAsignadas().stream().map(Tarea::getTitulo).toArray(String[]::new));
                tableModel.addRow(new Object[]{proyecto.getNombre(), proyecto.getDescripcion(), empleados, tareas});
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar proyectos: " + e.getMessage());
        }
    }

    private void cargarEmpleados() {
        try {
            List<Empleado> empleados = empleadoService.obtenerEmpleadosNoAsignados();
            empleadosComboBox.removeAllItems();
            for (Empleado empleado : empleados) {
                empleadosComboBox.addItem(empleado.getNombre());
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + e.getMessage());
        }
    }


    private void cargarTareas() {
        try {
            List<Tarea> tareas = tareaService.obtenerTodasLasTareas();
            tareasComboBox.removeAllItems();
            for (Tarea tarea : tareas) {
                tareasComboBox.addItem(tarea.getTitulo());
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar tareas: " + e.getMessage());
        }
    }

    private void agregarProyecto() {
        String nombre = nombreField.getText();
        String descripcion = descripcionField.getText();
        Proyecto nuevoProyecto = new Proyecto(nombre, descripcion, new ArrayList<>(), new ArrayList<>());
        try {
            proyectoService.guardarProyecto(nuevoProyecto);
            cargarProyectos();
            JOptionPane.showMessageDialog(this, "Proyecto agregado correctamente.");
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar proyecto: " + e.getMessage());
        }
    }

    private void actualizarProyecto() {
        String nombre = nombreField.getText();
        String descripcion = descripcionField.getText();
        Proyecto proyecto = new Proyecto(nombre, descripcion, new ArrayList<>(), new ArrayList<>());
        try {
            proyectoService.guardarProyecto(proyecto);
            cargarProyectos();
            JOptionPane.showMessageDialog(this, "Proyecto actualizado correctamente.");
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar proyecto: " + e.getMessage());
        }
    }

    private void eliminarProyecto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String nombre = tableModel.getValueAt(selectedRow, 0).toString();
            try {
                proyectoService.eliminarProyecto(nombre);
                cargarProyectos();
                JOptionPane.showMessageDialog(this, "Proyecto eliminado correctamente.");
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar proyecto: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proyecto para eliminar.");
        }
    }

    private void asignarEmpleadoAProyecto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String nombreProyecto = tableModel.getValueAt(selectedRow, 0).toString();
            String nombreEmpleado = empleadosComboBox.getSelectedItem().toString();
            try {
                proyectoService.asignarEmpleadoAProyecto(nombreProyecto, nombreEmpleado);
                cargarProyectos();
                JOptionPane.showMessageDialog(this, "Empleado asignado correctamente.");
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al asignar empleado al proyecto: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proyecto para asignar el empleado.");
        }
    }

    private void desasignarTodosEmpleadosDeProyecto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String nombreProyecto = tableModel.getValueAt(selectedRow, 0).toString();
            try {
                proyectoService.desasignarTodosEmpleadosDeProyecto(nombreProyecto);
                cargarProyectos();
                JOptionPane.showMessageDialog(this, "Todos los empleados desasignados correctamente.");
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al desasignar empleados del proyecto: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proyecto para desasignar empleados.");
        }
    }

    private void asignarTareaAProyecto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String nombreProyecto = tableModel.getValueAt(selectedRow, 0).toString();
            String tituloTarea = tareasComboBox.getSelectedItem().toString();
            try {
                proyectoService.asignarTareaAProyecto(nombreProyecto, tituloTarea);
                cargarProyectos();
                JOptionPane.showMessageDialog(this, "Tarea asignada correctamente.");
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al asignar tarea al proyecto: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proyecto para asignar la tarea.");
        }
    }

    private void desasignarTareaDeProyecto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String nombreProyecto = tableModel.getValueAt(selectedRow, 0).toString();
            String tituloTarea = tareasComboBox.getSelectedItem().toString();
            try {
                proyectoService.desasignarTareaDeProyecto(nombreProyecto, tituloTarea);
                cargarProyectos();
                JOptionPane.showMessageDialog(this, "Tarea desasignada correctamente.");
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al desasignar tarea del proyecto: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proyecto para desasignar la tarea.");
        }
    }

    private void generarReporte() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String nombreProyecto = tableModel.getValueAt(selectedRow, 0).toString();
            try {
                String reporte = proyectoService.generarReporteProyecto(nombreProyecto);
                JOptionPane.showMessageDialog(this, reporte);
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al generar el reporte: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proyecto para generar el reporte.");
        }
    }
}
