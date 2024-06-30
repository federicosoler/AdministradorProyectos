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
    private JComboBox<String> empleadosComboBox;

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

        empleadosComboBox = new JComboBox<>();
        cargarEmpleadosDisponibles();
        panel.add(new JLabel("Asignar Empleado:"));
        panel.add(empleadosComboBox);

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

        JButton assignButton = new JButton("Asignar Empleado");
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarEmpleadoAProyecto();
            }
        });
        panel.add(assignButton);

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
            JOptionPane.showMessageDialog(this, "Error al cargar proyectos: " + e.getMessage());
        }
    }

    private void cargarEmpleadosDisponibles() {
        try {
            List<Empleado> empleados = empleadoService.obtenerTodosLosEmpleados();
            empleadosComboBox.removeAllItems();
            for (Empleado empleado : empleados) {
                empleadosComboBox.addItem(empleado.getNombre());
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar empleados disponibles: " + e.getMessage());
        }
    }

    private void agregarProyecto() {
        try {
            String nombre = nombreField.getText();
            String descripcion = descripcionField.getText();
            Proyecto nuevoProyecto = new Proyecto(nombre, descripcion);
            proyectoService.guardarProyecto(nuevoProyecto);
            cargarProyectos();
            JOptionPane.showMessageDialog(this, "Proyecto agregado correctamente.");
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar proyecto: " + e.getMessage());
        }
    }

    private void actualizarProyecto() {
        try {
            String nombre = nombreField.getText();
            String descripcion = descripcionField.getText();
            Proyecto proyecto = new Proyecto(nombre, descripcion);
            proyectoService.actualizarProyecto(proyecto);
            cargarProyectos();
            JOptionPane.showMessageDialog(this, "Proyecto actualizado correctamente.");
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar proyecto: " + e.getMessage());
        }
    }

    private void eliminarProyecto() {
        try {
            String nombre = nombreField.getText();
            proyectoService.eliminarProyecto(nombre);
            cargarProyectos();
            JOptionPane.showMessageDialog(this, "Proyecto eliminado correctamente.");
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar proyecto: " + e.getMessage());
        }
    }

    private void asignarEmpleadoAProyecto() {
        try {
            String nombreProyecto = nombreField.getText();
            String nombreEmpleado = (String) empleadosComboBox.getSelectedItem();
            if (nombreEmpleado != null && !nombreEmpleado.isEmpty()) {
                proyectoService.asignarEmpleadoAProyecto(nombreProyecto, nombreEmpleado);
                cargarProyectos(); // Actualizar la tabla de proyectos
                JOptionPane.showMessageDialog(this, "Empleado asignado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un empleado para asignar.");
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al asignar empleado al proyecto: " + e.getMessage());
        }
    }
}
