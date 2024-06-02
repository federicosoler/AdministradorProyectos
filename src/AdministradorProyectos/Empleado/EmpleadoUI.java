package AdministradorProyectos.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmpleadoUI extends JFrame {
	private EmpleadoService empleadoService;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField nombreField;
	private JTextField costoHoraField;

	public EmpleadoUI(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
		setTitle("Gestión de Empleados");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		tableModel = new DefaultTableModel(new String[] { "Nombre", "Costo por hora" }, 0);
		table = new JTable(tableModel);
		cargarEmpleados();

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));

		panel.add(new JLabel("Nombre:"));
		nombreField = new JTextField();
		panel.add(nombreField);

		panel.add(new JLabel("Costo por hora:"));
		costoHoraField = new JTextField();
		panel.add(costoHoraField);

		JButton addButton = new JButton("Agregar");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarEmpleado();
			}
		});
		panel.add(addButton);

		JButton updateButton = new JButton("Actualizar");
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarEmpleado();
			}
		});
		panel.add(updateButton);

		JButton deleteButton = new JButton("Eliminar");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminarEmpleado();
			}
		});
		panel.add(deleteButton);

		add(panel, BorderLayout.SOUTH);

		table.getSelectionModel().addListSelectionListener(event -> {
			if (table.getSelectedRow() >= 0) {
				nombreField.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
				costoHoraField.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
			}
		});
	}

	private void cargarEmpleados() {
		List<Empleado> empleados = empleadoService.obtenerTodosLosEmpleados();
		tableModel.setRowCount(0); // Clear the table
		for (Empleado empleado : empleados) {
			tableModel.addRow(new Object[] { empleado.getNombre(), empleado.getCostoHora() });
		}
	}

	private void agregarEmpleado() {
		String nombre = nombreField.getText();
		double costoHora = Double.parseDouble(costoHoraField.getText());
		Empleado nuevoEmpleado = new Empleado(nombre, costoHora);
		empleadoService.agregarEmpleado(nuevoEmpleado);
		cargarEmpleados();
		JOptionPane.showMessageDialog(this, "Empleado agregado correctamente.");
	}

	private void actualizarEmpleado() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			String nombreAntiguo = (String) tableModel.getValueAt(selectedRow, 0);
			String nombreNuevo = nombreField.getText();
			double costoHora = Double.parseDouble(costoHoraField.getText());

			empleadoService.eliminarEmpleado(nombreAntiguo);

			Empleado empleado = new Empleado(nombreNuevo, costoHora);
			empleadoService.agregarEmpleado(empleado);

			cargarEmpleados();
			JOptionPane.showMessageDialog(this, "Empleado actualizado correctamente.");
		} else {
			JOptionPane.showMessageDialog(this, "Seleccione un empleado para actualizar.");
		}
	}

	private void eliminarEmpleado() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			String nombre = (String) tableModel.getValueAt(selectedRow, 0);
			empleadoService.eliminarEmpleado(nombre);
			cargarEmpleados();
			JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente.");
		} else {
			JOptionPane.showMessageDialog(this, "Seleccione un empleado para eliminar.");
		}
	}

	public static void main(String[] args) {
		EmpleadoDAO empleadoDAO = new EmpleadoH2Impl();
		EmpleadoService empleadoService = new EmpleadoService(empleadoDAO);
		EmpleadoUI frame = new EmpleadoUI(empleadoService);
		frame.setVisible(true);
	}
}
