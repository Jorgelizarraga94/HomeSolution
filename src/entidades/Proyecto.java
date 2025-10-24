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
            Tarea tarea = new Tarea(titulos[i],descripcion[i],duracion[i],0);
            this.tareas.add(tarea);
        }
        this.fechaInicioProyecto = fechaInicioProyecto;
        this.fechaEstimadaFinProyecto = fechaEstimadaFinProyecto;
        this.finalizado = false;
    }

    @Override
    public void agregarTarea(String titulo, String descripcion, Integer cantidadDiasFinalizacion, Integer costo) {
        Tarea tarea = new Tarea(titulo, descripcion, cantidadDiasFinalizacion, costo);
        this.tareas.add(tarea);
    }

    @Override
    public void actualizarFinalizado() {
        this.finalizado = true;
    }

    @Override
    public double CalculoCostoFinal() {  /// /////////////////////////////////////
        return 0;
    }

    @Override
    public void modificarDireccionVivienda(String nuevaDireccion) {
        this.direccionVivienda = nuevaDireccion;
    }

    @Override
    public String verDireccion() {
        return direccionVivienda;
    }

    @Override
    public List<Tarea> verTareas() {
        return this.tareas;
    }

    @Override
    public Tarea seleccionarTarea(int idTarea) {
        for (Tarea tarea : tareas){
            if(tarea.verIdTarea() == idTarea){
                return tarea;
            }
        }
        return null;
    }

    @Override
    public void modificarTarea(int idTarea, Tarea tarea) {
        Tarea t = seleccionarTarea(idTarea);
        t.modificarTitulo(tarea.verTitulo());
        t.modificarDescripcion(tarea.verDescripcion());
        t.modificarCantidadDiasFinalizacion(tarea.VerCantidadDiasFinalizacion());
    }

    @Override
    public void eliminarTarea(int idTarea) { /// ////////////////////////////////////////
        Tarea tarea = seleccionarTarea(idTarea);

    }

    @Override
    public void actualizarFechaRealFinalizacion(String fecha) {
        this.fechaEstimadaFinProyecto = fecha;
    }

    @Override
    public String verFechaRealFinalizacion() {
        return this.fechaEstimadaFinProyecto;
    }

    @Override
    public boolean estaFinalizado() {
        return this.finalizado;
    }
}
