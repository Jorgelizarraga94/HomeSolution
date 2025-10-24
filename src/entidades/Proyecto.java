package entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Proyecto implements Iproyecto{
    private int idProyecto;
    private int contadorIdProyecto;
    private String direccionVivienda;
    private String fechaInicioProyecto;
    private String fechaEstimadaFinProyecto;
    private boolean finalizado;
    private List<Tarea> tareas;
    private Cliente cliente;

    // Inicializar campos y estado inicial
    public Proyecto(String[] cliente, String [] titulos, String [] descripcion, double [] duracion, String direccionVivienda, String fechaInicioProyecto, String fechaEstimadaFinProyecto) {
        this.idProyecto = contadorIdProyecto+1;
        this.cliente = new Cliente(cliente[0], cliente[1], cliente[2]);
        this.direccionVivienda = direccionVivienda;
        this.tareas = new ArrayList<>();
        for(int i=0 ; i<titulos.length; i++){
            Tarea tarea = new Tarea(titulos[i],descripcion[i],duracion[i]);
            this.tareas.add(tarea);
        }
        this.fechaInicioProyecto = fechaInicioProyecto;
        this.fechaEstimadaFinProyecto = fechaEstimadaFinProyecto;
        this.finalizado = false;
    }

    @Override
    public void agregarTarea(String titulo, String descripcion, Integer cantidadDiasFinalizacion, Integer costo) {

    }

    @Override
    public void actualizarFinalizado() {

    }

    @Override
    public double CalculoCostoFinal() {
        return 0;
    }

    @Override
    public void modificarDireccionVivienda(String nuevaDireccion) {

    }

    @Override
    public String verDireccion() {
        return "";
    }

    @Override
    public List<Tarea> verTareas() {
        return List.of();
    }

    @Override
    public Tarea seleccionarTarea(Long idTarea) {
        return null;
    }

    @Override
    public void modificarTarea(Tarea tarea) {

    }

    @Override
    public void eliminarTarea(Tarea tarea) {

    }

    @Override
    public void actualizarFechaRealFinalización(Date fecha) {

    }

    @Override
    public Date verFechaRealFinalización() {
        return null;
    }

    @Override
    public boolean estaFinalizado() {
        return false;
    }

    @Override
    public List<Empleado> empleadosConRetraso() {
        return List.of();
    }
}
