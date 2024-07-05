package AdministradorProyectos.Tarea;

import AdministradorProyectos.Empleado.Empleado;
import AdministradorProyectos.Empleado.EmpleadoService;
import AdministradorProyectos.Exceptions.ServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TareaUI extends JFrame {
    private TareaService tareaService;
    private EmpleadoService empleadoService;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tituloField;
    private JTextField descripcionField;
    private JTextField estimacionHorasField;
    private JTextField horasRealesField;
    private JComboBox<String> empleadoComboBox;

    public TareaUI(TareaService tareaService, EmpleadoService empleadoService) {
        this.tareaService = tareaService;
        this.empleadoService = empleadoService;

        setTitle("Gestión de Tareas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Título", "Descripción", "Estimación Horas", "Horas Reales", "Empleado Asignado"}, 0);
        table = new JTable(tableModel);
        cargarTareas();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        panel.add(new JLabel("Título:"));
        tituloField = new JTextField();
        panel.add(tituloField);

        panel.add(new JLabel("Descripción:"));
        descripcionField = new JTextField();
        panel.add(descripcionField);

        panel.add(new JLabel("Estimación Horas:"));
        estimacionHorasField = new JTextField();
        panel.add(estimacionHorasField);

        panel.add(new JLabel("Horas Reales:"));
        horasRealesField = new JTextField();
        panel.add(horasRealesField);

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
                agregarTarea();
            }
        });
        panel.add(addButton);

        JButton updateButton = new JButton("Actualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTarea();
            }
        });
        panel.add(updateButton);

        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarTarea();
            }
        });
        panel.add(deleteButton);

        add(panel, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(event -> {
            if (table.getSelectedRow() >= 0) {
                tituloField.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                descripcionField.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                estimacionHorasField.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
                horasRealesField.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
                empleadoComboBox.setSelectedItem(table.getValueAt(table.getSelectedRow(), 4).toString());
            }
        });
    }

    private void cargarTareas() {
        try {
            List<Tarea> tareas = tareaService.obtenerTodasLasTareas();
            tableModel.setRowCount(0);
            for (Tarea tarea : tareas) {
                tableModel.addRow(new Object[]{tarea.getTitulo(), tarea.getDescripcion(), tarea.getEstimacionHoras(), tarea.getHorasReales(), tarea.getEmpleadoAsignado()});
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las tareas: " + e.getMessage());
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

    private void agregarTarea() {
        try {
            String titulo = tituloField.getText();
            String descripcion = descripcionField.getText();
            int estimacionHoras = Integer.parseInt(estimacionHorasField.getText());
            int horasReales = Integer.parseInt(horasRealesField.getText());
            String empleadoAsignado = (String) empleadoComboBox.getSelectedItem();
            Tarea nuevaTarea = new Tarea(titulo, descripcion, estimacionHoras, horasReales, empleadoAsignado);

            tareaService.guardarTarea(nuevaTarea);
            cargarTareas();
            JOptionPane.showMessageDialog(this, "Tarea agregada correctamente.");
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar la tarea: " + e.getMessage());
        }
    }

    private void actualizarTarea() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String tituloAntiguo = (String) tableModel.getValueAt(selectedRow, 0);
                String tituloNuevo = tituloField.getText();
                String descripcion = descripcionField.getText();
                int estimacionHoras = Integer.parseInt(estimacionHorasField.getText());
                int horasReales = Integer.parseInt(horasRealesField.getText());
                String empleadoAsignado = (String) empleadoComboBox.getSelectedItem();

                tareaService.eliminarTarea(tituloAntiguo);

                Tarea tarea = new Tarea(tituloNuevo, descripcion, estimacionHoras, horasReales, empleadoAsignado);
                tareaService.guardarTarea(tarea);

                cargarTareas();
                JOptionPane.showMessageDialog(this, "Tarea actualizada correctamente.");
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar la tarea: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea para actualizar.");
        }
    }

    private void eliminarTarea() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String titulo = (String) tableModel.getValueAt(selectedRow, 0);
                tareaService.eliminarTarea(titulo);
                cargarTareas();
                JOptionPane.showMessageDialog(this, "Tarea eliminada correctamente.");
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar la tarea: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea para eliminar.");
        }
    }
}
