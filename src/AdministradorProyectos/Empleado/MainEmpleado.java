package AdministradorProyectos.Empleado;

public class MainEmpleado {
	public static void main(String[] args) {
		TableManagerEmpleado.crearTabla();

		EmpleadoDAO empleadoDAO = new EmpleadoH2Impl();
		EmpleadoService empleadoService = new EmpleadoService(empleadoDAO);

		// Crear y guardar un nuevo empleado
		Empleado nuevoEmpleado = new Empleado("Juan Pérez", 50.0);
		empleadoService.agregarEmpleado(nuevoEmpleado);

		// Obtener y mostrar todos los empleados
		empleadoService.obtenerTodosLosEmpleados().forEach(empleado -> {
			System.out.println("Nombre: " + empleado.getNombre());
			System.out.println("Costo por hora: " + empleado.getCostoHora());
		});

		// Actualizar un empleado
		nuevoEmpleado.setCostoHora(55.0);
		empleadoService.actualizarEmpleado(nuevoEmpleado);

		// Obtener y mostrar el empleado actualizado
		Empleado empleadoActualizado = empleadoService.obtenerEmpleadoPorNombre("Juan Pérez");
		System.out.println("Empleado actualizado:");
		System.out.println("Nombre: " + empleadoActualizado.getNombre());
		System.out.println("Costo por hora: " + empleadoActualizado.getCostoHora());

		// Eliminar un empleado
		empleadoService.eliminarEmpleado("Juan Pérez");

		// Verificar que el empleado ha sido eliminado
		System.out.println("Empleados después de la eliminación:");
		empleadoService.obtenerTodosLosEmpleados().forEach(empleado -> {
			System.out.println("Nombre: " + empleado.getNombre());
			System.out.println("Costo por hora: " + empleado.getCostoHora());
		});
	}
}
