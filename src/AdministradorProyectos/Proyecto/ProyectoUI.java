package AdministradorProyectos.Proyecto;

import AdministradorProyectos.Exceptions.ServiceException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProyectoUI extends JFrame {
	private ProyectoService proyectoService;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField nombreField;
	private JTextField descripcionField;

	public ProyectoUI(ProyectoService proyectoService) {
		this.proyectoService = proyectoService;
		setTitle("Gestión de Proyectos");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		tableModel = new DefaultTableModel(new String[] { "Nombre", "Descripción" }, 0);
		table = new JTable(tableModel);
		obtenerTodosLosProyectos();

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));

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

		add(panel, BorderLayout.SOUTH);

		table.getSelectionModel().addListSelectionListener(event -> {
			if (table.getSelectedRow() >= 0) {
				nombreField.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
				descripcionField.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
			}
		});
	}

	private void obtenerTodosLosProyectos() {
		try {
			List<Proyecto> proyectos = proyectoService.obtenerTodosLosProyectos();
			tableModel.setRowCount(0);
			for (Proyecto proyecto : proyectos) {
				tableModel.addRow(new Object[] { proyecto.getNombre(), proyecto.getDescripcion() });
			}
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(this, "Error al cargar proyectos: " + e.getMessage());
		}
	}

	private void agregarProyecto() {
		try {
			String nombre = nombreField.getText();
			String descripcion = descripcionField.getText();
			Proyecto nuevoProyecto = new Proyecto(nombre, descripcion);
			proyectoService.guardarProyecto(nuevoProyecto);
			obtenerTodosLosProyectos();
			JOptionPane.showMessageDialog(this, "Proyecto agregado correctamente.");
		} catch (ServiceException ex) {
			JOptionPane.showMessageDialog(this, "Error al agregar proyecto: " + ex.getMessage());
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
				proyectoService.guardarProyecto(proyecto);

				obtenerTodosLosProyectos();
				JOptionPane.showMessageDialog(this, "Proyecto actualizado correctamente.");
			} catch (ServiceException ex) {
				JOptionPane.showMessageDialog(this, "Error al actualizar proyecto: " + ex.getMessage());
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
				obtenerTodosLosProyectos();
				JOptionPane.showMessageDialog(this, "Proyecto eliminado correctamente.");
			} catch (ServiceException ex) {
				JOptionPane.showMessageDialog(this, "Error al eliminar proyecto: " + ex.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(this, "Seleccione un proyecto para eliminar.");
		}
	}
}
