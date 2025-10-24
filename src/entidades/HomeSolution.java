package entidades;

import java.util.HashSet;
import java.util.List;

public class HomeSolution implements IHomeSolution{
    private HashSet<Empleado> empleados;
    private HashSet<Proyecto> proyectos;

    public HomeSolution(){
        this.empleados = new HashSet<>();
        this.proyectos = new HashSet<>();
    }
    @Override
    public void registrarEmpleado(String nombre, double valor) throws IllegalArgumentException {
        Empleado empleado = new EmpleadoContratado(nombre,valor);
        this.empleados.add(empleado);
    }

    @Override
    public void registrarEmpleado(String nombre, double valor, String categoria) throws IllegalArgumentException {
        Empleado empleado = new EmpleadoPermanente(nombre, valor, categoria);
        this.empleados.add(empleado);
    }

    @Override
    public void registrarProyecto(String[] titulos, String[] descripcion, double[] dias, String domicilio, String[] cliente, String inicio, String fin) throws IllegalArgumentException {

    }

    @Override
    public void asignarResponsableEnTarea(Integer numero, String titulo) throws Exception {

    }

    @Override
    public void asignarResponsableMenosRetraso(Integer numero, String titulo) throws Exception {

    }

    @Override
    public void registrarRetrasoEnTarea(Integer numero, String titulo, double cantidadDias) throws IllegalArgumentException {

    }

    @Override
    public void agregarTareaEnProyecto(Integer numero, String titulo, String descripcion, double dias) throws IllegalArgumentException {

    }

    @Override
    public void finalizarTarea(Integer numero, String titulo) throws Exception {

    }

    @Override
    public void finalizarProyecto(Integer numero, String fin) throws IllegalArgumentException {

    }

    @Override
    public void reasignarEmpleadoEnProyecto(Integer numero, Integer legajo, String titulo) throws Exception {

    }

    @Override
    public void reasignarEmpleadoConMenosRetraso(Integer numero, String titulo) throws Exception {

    }

    @Override
    public double costoProyecto() {
        return 0;
    }

    @Override
    public List<Tupla<Integer, String>> proyectosFinalizados() {
        return List.of();
    }

    @Override
    public List<Tupla<Integer, String>> proyectosPendientes() {
        return List.of();
    }

    @Override
    public List<Tupla<Integer, String>> proyectosActivos() {
        return List.of();
    }

    @Override
    public Object[] empleadosNoAsignados() {
        return new Object[0];
    }

    @Override
    public boolean estaFinalizado(Integer numero) {
        return false;
    }

    @Override
    public int consultarCantidadRetrasosEmpleado(Integer legajo) {
        return 0;
    }

    @Override
    public List<Tupla<Integer, String>> empleadosAsignadosAProyecto(Integer numero) {
        return List.of();
    }

    @Override
    public Object[] tareasProyectoNoAsignadas(Integer numero) {
        return new Object[0];
    }

    @Override
    public Object[] tareasDeUnProyecto(Integer numero) {
        return new Object[0];
    }

    @Override
    public String consultarDomicilioProyecto(Integer numero) {
        return "";
    }

    @Override
    public boolean tieneRestrasos(String legajo) {
        return false;
    }

    @Override
    public List<Tupla<Integer, String>> empleados() {
        return List.of();
    }

    @Override
    public String consultarProyecto(Integer numero) {
        return "";
    }
}
