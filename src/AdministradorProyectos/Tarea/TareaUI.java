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
    private JTextField horasEstimadasField;
    private JTextField horasRealesField;
    private JComboBox<String> empleadoComboBox;
    private JComboBox<String> estadoComboBox;

    public TareaUI(TareaService tareaService, EmpleadoService empleadoService) {
        this.tareaService = tareaService;
        this.empleadoService = empleadoService;

        setTitle("Gestión de Tareas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(
                new String[]{"Título", "Descripción", "Horas Estimadas", "Horas Reales", "Empleado Asignado", "Estado"}, 0);
        table = new JTable(tableModel);
        cargarTareas();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2));

        panel.add(new JLabel("Título:"));
        tituloField = new JTextField();
        panel.add(tituloField);

        panel.add(new JLabel("Descripción:"));
        descripcionField = new JTextField();
        panel.add(descripcionField);

        panel.add(new JLabel("Horas Estimadas:"));
        horasEstimadasField = new JTextField();
        panel.add(horasEstimadasField);

        panel.add(new JLabel("Horas Reales (Poner 0 si no se sabe todavía):"));
        horasRealesField = new JTextField();
        panel.add(horasRealesField);

        JButton addButton = new JButton("Crear Nueva Tarea");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarTarea();
            }
        });
        panel.add(addButton);


        JButton updateButton = new JButton("Modificar Tarea Seleccionada");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTarea();
            }
        });
        panel.add(updateButton);

        JButton deleteButton = new JButton("Eliminar Tarea Seleccionada");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarTarea();
            }
        });
        panel.add(deleteButton);

        JButton reloadButton = new JButton("Refrescar Listado de Empleados");
        reloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarEmpleadosDisponibles();
            }
        });
        panel.add(reloadButton);

        panel.add(new JLabel("Pool de Empleados del Proyecto:"));
        empleadoComboBox = new JComboBox<>();
        cargarEmpleadosDisponibles();
        panel.add(empleadoComboBox);

        JButton asignarButton = new JButton("Asignar Empleado a Tarea Seleccionada");
        asignarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarEmpleado();
            }
        });
        panel.add(asignarButton);

        JButton desasignarButton = new JButton("Desasignar Empleado de Tarea Seleccionada");
        desasignarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desasignarEmpleado();
            }
        });
        panel.add(desasignarButton);
        
        panel.add(new JLabel("Estado:"));
        estadoComboBox = new JComboBox<>(new String[]{"NO_INICIADA", "EN_PROCESO", "TERMINADA"});
        panel.add(estadoComboBox);

        JButton asignarEstadoButton = new JButton("Asignar Estado a Tarea Seleccionada");
        asignarEstadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                asignarEstado();
            }
        });
        panel.add(asignarEstadoButton);

        JButton desasignarEstadoButton = new JButton("Desasignar Estado de Tarea Seleccionada");
        desasignarEstadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desasignarEstado();
            }
        });
        panel.add(desasignarEstadoButton);

        add(panel, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(event -> {
            if (table.getSelectedRow() >= 0) {
                tituloField.setText(getValueFromTable(table, table.getSelectedRow(), 0));
                descripcionField.setText(getValueFromTable(table, table.getSelectedRow(), 1));
                horasEstimadasField.setText(getValueFromTable(table, table.getSelectedRow(), 2));
                horasRealesField.setText(getValueFromTable(table, table.getSelectedRow(), 3));
                empleadoComboBox.setSelectedItem(getValueFromTable(table, table.getSelectedRow(), 4));
                estadoComboBox.setSelectedItem(getValueFromTable(table, table.getSelectedRow(), 5));
            }
        });
    }

    private String getValueFromTable(JTable table, int row, int column) {
        Object value = table.getValueAt(row, column);
        return value != null ? value.toString() : "";
    }

    private void cargarTareas() {
        try {
            List<Tarea> tareas = tareaService.obtenerTodasLasTareas();
            tableModel.setRowCount(0);
            for (Tarea tarea : tareas) {
                tableModel.addRow(new Object[]{tarea.getTitulo(), tarea.getDescripcion(), tarea.getHorasEstimadas(),
                        tarea.getHorasReales(), tarea.getEmpleadoAsignado(), tarea.getEstado()});
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
            double horasEstimadas = Double.parseDouble(horasEstimadasField.getText());
            double horasReales = Double.parseDouble(horasRealesField.getText());

            if (titulo.isEmpty() || descripcion.isEmpty()) {
                throw new IllegalArgumentException("Todos los campos deben estar llenos.");
            }

            Tarea nuevaTarea = new Tarea(titulo, descripcion, horasEstimadas, horasReales, null, null);

            tareaService.guardarTarea(nuevaTarea);
            cargarTareas();
            JOptionPane.showMessageDialog(this, "Tarea agregada correctamente.");
        } catch (ServiceException e) {
            e.printStackTrace();  // Imprimir el stack trace de la excepción
            JOptionPane.showMessageDialog(this, "Error al agregar la tarea: " + e.getMessage());
        } catch (NumberFormatException e) {
            e.printStackTrace();  // Imprimir el stack trace de la excepción
            JOptionPane.showMessageDialog(this, "Error de formato numérico en las horas: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();  // Imprimir el stack trace de la excepción
            JOptionPane.showMessageDialog(this, "Error en los datos ingresados: " + e.getMessage());
        }
    }



    private void actualizarTarea() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String tituloAntiguo = (String) tableModel.getValueAt(selectedRow, 0);
                String tituloNuevo = tituloField.getText();
                String descripcion = descripcionField.getText();
                double horasEstimadas = Double.parseDouble(horasEstimadasField.getText());
                double horasReales = Double.parseDouble(horasRealesField.getText());
                String empleadoAsignado = (String) empleadoComboBox.getSelectedItem();
                String estado = (String) estadoComboBox.getSelectedItem();

                tareaService.eliminarTarea(tituloAntiguo);

                Tarea tarea = new Tarea(tituloNuevo, descripcion, horasEstimadas, horasReales, empleadoAsignado, estado);
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

    private void asignarEmpleado() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String titulo = (String) tableModel.getValueAt(selectedRow, 0);
                String empleadoAsignado = (String) empleadoComboBox.getSelectedItem();
                Tarea tarea = tareaService.obtenerTareaPorTitulo(titulo);
                tarea.setEmpleadoAsignado(empleadoAsignado);
                tareaService.actualizarTarea(tarea);
                cargarTareas();
                JOptionPane.showMessageDialog(this, "Empleado asignado correctamente.");
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al asignar el empleado: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea para asignar el empleado.");
        }
    }

    private void desasignarEmpleado() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String titulo = (String) tableModel.getValueAt(selectedRow, 0);
                Tarea tarea = tareaService.obtenerTareaPorTitulo(titulo);
                tarea.setEmpleadoAsignado(null);
                tareaService.actualizarTarea(tarea);
                cargarTareas();
                JOptionPane.showMessageDialog(this, "Empleado desasignado correctamente.");
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al desasignar el empleado: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea para desasignar el empleado.");
        }
    }

    private void asignarEstado() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String titulo = (String) tableModel.getValueAt(selectedRow, 0);
                String estado = (String) estadoComboBox.getSelectedItem();
                Tarea tarea = tareaService.obtenerTareaPorTitulo(titulo);
                tarea.setEstado(estado);
                tareaService.actualizarTarea(tarea);
                cargarTareas();
                JOptionPane.showMessageDialog(this, "Estado asignado correctamente.");
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al asignar el estado: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea para asignar el estado.");
        }
    }

    private void desasignarEstado() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String titulo = (String) tableModel.getValueAt(selectedRow, 0);
                Tarea tarea = tareaService.obtenerTareaPorTitulo(titulo);
                tarea.setEstado(null);
                tareaService.actualizarTarea(tarea);
                cargarTareas();
                JOptionPane.showMessageDialog(this, "Estado desasignado correctamente.");
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, "Error al desasignar el estado: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea para desasignar el estado.");
        }
    }
}
