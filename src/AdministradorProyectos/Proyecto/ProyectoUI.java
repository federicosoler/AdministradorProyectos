package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Empleado.EmpleadoService;
import AdministradorProyectos.Exceptions.ServiceException;
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

    public ProyectoUI(ProyectoService proyectoService, EmpleadoService empleadoService, TareaService tareaService) {
        this.proyectoService = proyectoService;
        this.empleadoService = empleadoService;
        this.tareaService = tareaService;
        setTitle("Gestión de Proyectos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Nombre", "Descripción", "Empleados Asignados"}, 0);
        table = new JTable(tableModel);
        cargarProyectos();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

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

        JButton recargarEmpleadosButton = new JButton("Recargar Lista de Empleados");
        recargarEmpleadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarEmpleados();
            }
        });
        panel.add(recargarEmpleadosButton);

        panel.add(new JLabel("Empleados:"));
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

        JButton desasignarButton = new JButton("Desasignar Empleado");
        desasignarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desasignarEmpleadoDeProyecto();
            }
        });
        panel.add(desasignarButton);

        add(panel, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(event -> {
            if (table.getSelectedRow() >= 0) {
                nombreField.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                descripcionField.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                // Aquí actualizamos el ComboBox con los empleados asignados
                String empleadosAsignados = table.getValueAt(table.getSelectedRow(), 2).toString();
                empleadosComboBox.setSelectedItem(empleadosAsignados);
            }
        });
    }

    private void cargarProyectos() {
        try {
            List<Proyecto> proyectos = proyectoService.obtenerTodosLosProyectos();
            tableModel.setRowCount(0);
            for (Proyecto proyecto : proyectos) {
                String empleados = String.join(", ", proyecto.getEmpleadosAsignados().stream().map(Empleado::getNombre).toArray(String[]::new));
                tableModel.addRow(new Object[]{proyecto.getNombre(), proyecto.getDescripcion(), empleados});
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar proyectos: " + e.getMessage());
        }
    }

    private void cargarEmpleados() {
        try {
            List<Empleado> empleados = empleadoService.obtenerTodosLosEmpleados();
            empleadosComboBox.removeAllItems();
            for (Empleado empleado : empleados) {
                empleadosComboBox.addItem(empleado.getNombre());
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + e.getMessage());
        }
    }

    private void agregarProyecto() {
        String nombre = nombreField.getText();
        String descripcion = descripcionField.getText();
        Proyecto nuevoProyecto = new Proyecto(nombre, descripcion, new ArrayList<>());
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
        Proyecto proyecto = new Proyecto(nombre, descripcion, new ArrayList<>());
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
    
    private void desasignarEmpleadoDeProyecto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String nombreProyecto = tableModel.getValueAt(selectedRow, 0).toString();
            String nombreEmpleado = empleadosComboBox.getSelectedItem().toString();
            try {
                proyectoService.desasignarEmpleadoDeProyecto(nombreProyecto, nombreEmpleado);
                cargarProyectos();
                JOptionPane.showMessageDialog(this, "Empleado desasignado correctamente.");
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al desasignar empleado del proyecto: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proyecto para desasignar el empleado.");
        }
    }

}
