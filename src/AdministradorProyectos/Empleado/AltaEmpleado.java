package AdministradorProyectos.Empleado;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AltaEmpleado extends JFrame {
    private JTextField nombreField;
    private JTextField costoHoraField;
    private JButton guardarButton;
    private EmpleadoService empleadoService;

    public AltaEmpleado(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;

        setTitle("Alta de Empleado");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        nombreField = new JTextField(20);
        costoHoraField = new JTextField(20);
        guardarButton = new JButton("Guardar");

        guardarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreField.getText();
                double costoHora = Double.parseDouble(costoHoraField.getText());
                Empleado nuevoEmpleado = new Empleado(nombre, costoHora);
                empleadoService.agregarEmpleado(nuevoEmpleado);
                JOptionPane.showMessageDialog(null, "Empleado guardado correctamente.");
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Costo por hora:"));
        panel.add(costoHoraField);
        panel.add(guardarButton);

        add(panel);
    }

    public static void main(String[] args) {
        EmpleadoDAO empleadoDAO = new EmpleadoH2Impl();
        EmpleadoService empleadoService = new EmpleadoService(empleadoDAO);
        AltaEmpleado frame = new AltaEmpleado(empleadoService);
        frame.setVisible(true);
    }

    public JTextField getNombreField() {
        return nombreField;
    }

    public void setNombreField(JTextField nombreField) {
        this.nombreField = nombreField;
    }

    public JTextField getCostoHoraField() {
        return costoHoraField;
    }

    public void setCostoHoraField(JTextField costoHoraField) {
        this.costoHoraField = costoHoraField;
    }

    public JButton getGuardarButton() {
        return guardarButton;
    }

    public void setGuardarButton(JButton guardarButton) {
        this.guardarButton = guardarButton;
    }

    public EmpleadoService getEmpleadoService() {
        return empleadoService;
    }

    public void setEmpleadoService(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }
}
