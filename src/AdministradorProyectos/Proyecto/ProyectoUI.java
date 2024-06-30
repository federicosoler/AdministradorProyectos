package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Empleado.EmpleadoService;
import AdministradorProyectos.Exceptions.ServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProyectoUI extends JFrame {
    private ProyectoService proyectoService;
    private EmpleadoService empleadoService;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nombreField;
    private JTextField descripcionField;
    private JComboBox<String> empleadoComboBox;

    public ProyectoUI(ProyectoService proyectoService, EmpleadoService empleadoService) {
        this.proyectoService = proyectoService;
        this.empleadoService = empleadoService;

        setTitle("Gestión de Proyectos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Nombre", "Descripción", "Empleados Asignados"}, 0);
        table = new JTable(tableModel);
        cargarProyectos();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panel.add(nombreField);

        panel.add(new JLabel("Descripción:"));
        descripcionField = new JTextField();
        panel.add(descripcionField);

        panel.add(new JLabel("Asignar Empleado:"));
        empleadoComboBox = new JComboBox<>();
        cargarEmpleadosDisponibles();
        panel.add(empleadoComboBox);

        JButton reloadButton = new JButton("Recargar Lista de Empleados");
        reloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarEmpleadosDisponibles();
            }
        });
        panel.add(reloadButton);

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

        add(panel, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(event -> {
            if (table.getSelectedRow() >= 0) {
                nombreField.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                descripcionField.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
            }
        });
    }

    private void cargarProyectos() {
        try {
            List<Proyecto> proyectos = proyectoService.obtenerTodosLosProyectos();
            tableModel.setRowCount(0);
            for (Proyecto proyecto : proyectos) {
                String empleadosAsignados = String.join(", ", proyecto.getEmpleadosAsignados());
                tableModel.addRow(new Object[]{proyecto.getNombre(), proyecto.getDescripcion(), empleadosAsignados});
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los proyectos: " + e.getMessage());
        }
    }

    private void cargarEmpleadosDisponibles() {
        try {
            List<Empleado> empleados = empleadoService.obtenerTodosLosEmpleados();
            empleadoComboBox.removeAllItems();
            for (Empleado empleado : empleados) {
                empleadoComboBox.addItem(empleado.getNombre());
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los empleados: " + e.getMessage());
        }
    }

    private void agregarProyecto() {
        try {
            String nombre = nombreField.getText();
            String descripcion = descripcionField.getText();
            Proyecto nuevoProyecto = new Proyecto(nombre, descripcion);

            String empleadoSeleccionado = (String) empleadoComboBox.getSelectedItem();
            if (empleadoSeleccionado != null) {
                nuevoProyecto.agregarEmpleado(empleadoSeleccionado);
            }

            proyectoService.guardarProyecto(nuevoProyecto);
            cargarProyectos();
            JOptionPane.showMessageDialog(this, "Proyecto agregado correctamente.");
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar el proyecto: " + e.getMessage());
        }
    }

    private void actualizarProyecto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String nombreAntiguo = (String) tableModel.getValueAt(selectedRow, 0);
                String nombreNuevo = nombreField.getText();
                String descripcion = descripcionField.getText();

                proyectoService.eliminarProyecto(nombreAntiguo);

                Proyecto proyecto = new Proyecto(nombreNuevo, descripcion);
                String empleadoSeleccionado = (String) empleadoComboBox.getSelectedItem();
                if (empleadoSeleccionado != null) {
                    proyecto.agregarEmpleado(empleadoSeleccionado);
                }

                proyectoService.guardarProyecto(proyecto);

                cargarProyectos();
                JOptionPane.showMessageDialog(this, "Proyecto actualizado correctamente.");
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar el proyecto: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proyecto para actualizar.");
        }
    }

    private void eliminarProyecto() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String nombre = (String) tableModel.getValueAt(selectedRow, 0);
                proyectoService.eliminarProyecto(nombre);
                cargarProyectos();
                JOptionPane.showMessageDialog(this, "Proyecto eliminado correctamente.");
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el proyecto: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proyecto para eliminar.");
        }
    }
}
