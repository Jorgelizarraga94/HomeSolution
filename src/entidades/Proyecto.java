package entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Proyecto implements Iproyecto{
    private int idProyecto;
    private static int contadorIdProyecto=1;
    private String direccionVivienda;
    private LocalDate fechaInicioProyecto;
    private LocalDate fechaEstimadaDeFinalizacion;
    private LocalDate fechaRealDeFinalizacion;
    private double costoProyecto;
    private String estado;
    private boolean finalizado;
    private List<Tarea> tareas;
    private Cliente cliente;

    // Inicializar campos y estado inicial
    public Proyecto(String[] cliente, String [] titulos, String [] descripcion, double [] duracion, String direccionVivienda, String fechaInicioProyecto, String fechaEstimadaFinProyecto) {
        this.idProyecto = contadorIdProyecto++;
        this.cliente = new Cliente(cliente[0], cliente[1], cliente[2]);
        this.direccionVivienda = direccionVivienda;
        this.tareas = new ArrayList<>();
        for(int i=0 ; i<titulos.length; i++){
            Tarea tarea = new Tarea(titulos[i],descripcion[i],duracion[i]);
            this.tareas.add(tarea);
        }
        this.fechaInicioProyecto = LocalDate.parse(fechaInicioProyecto);
        this.fechaEstimadaDeFinalizacion = LocalDate.parse(fechaEstimadaFinProyecto);
        this.fechaRealDeFinalizacion = LocalDate.parse(fechaEstimadaFinProyecto);
        this.costoProyecto = 0;
        this.estado = Estado.pendiente;
        this.finalizado = false;
    }

    @Override
    public int verId() {
        return this.idProyecto;
    }

    @Override
    public void agregarTarea(String titulo, String descripcion, double cantidadDiasFinalizacion) {
        Tarea tarea = new Tarea(titulo, descripcion, cantidadDiasFinalizacion);
        this.tareas.add(tarea);
    }

    @Override
    public void actualizarFinalizado() {
        this.finalizado = true;
        this.estado = Estado.finalizado;
    }

    @Override
    public double calculoCostoFinal() {  /// /////////////////////////////////////
        double costoFinal=0;
        boolean tieneRetraso = false;
        for (Tarea tarea : tareas){
            costoFinal += tarea.verCosto();
            tieneRetraso = tieneRetraso || tarea.tieneRetrasos();
        }
        System.out.println("alguna tarea tiene retraso? " + tieneRetraso);
        if(tieneRetraso){
            costoFinal = costoFinal * 1.25;
        }
        else{
            costoFinal = costoFinal * 1.35;
        }
        System.out.println("Costo proyecto = " + costoFinal);

        return costoFinal;
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
    public String verEstado() {
        return this.estado;
    }

    @Override
    public Tarea seleccionarTarea(String titulo) {
        for (Tarea tarea : tareas){
            if(tarea.verTitulo().equals(titulo)){
                return tarea;
            }
        }
        return null;
    }


    @Override
    public void modificarTarea(String titulo, Tarea tareaNueva) {
        Tarea t = seleccionarTarea(titulo);
        if (t != null && tareaNueva != null) {
            t.modificarTitulo(tareaNueva.verTitulo());
            t.modificarDescripcion(tareaNueva.verDescripcion());
            t.modificarCantidadDiasFinalizacion(tareaNueva.VerCantidadDiasFinalizacion());
        }
    }

    @Override
    public void eliminarTarea(String titulo) {
        Tarea tarea = seleccionarTarea(titulo);
        if (tarea != null) {
            tareas.remove(tarea);
        }
    }


    @Override
    public void actualizarFechaRealFinalizacion(LocalDate fecha) {
        this.fechaRealDeFinalizacion = fecha;
    }

    @Override
    public void actualizarFechaEstimadaFinalizacion(LocalDate fecha) {
        this.fechaEstimadaDeFinalizacion = fecha;
    }


    @Override
    public LocalDate verFechaRealFinalizacion() {
        return this.fechaRealDeFinalizacion;
    }

    @Override
    public LocalDate verFechaEstimadaFinalizacion() {
        return this.fechaEstimadaDeFinalizacion;
    }

    @Override
    public LocalDate verFechaDeInicio() {
        return fechaInicioProyecto;
    }


    @Override
    public boolean estaFinalizado() {
        return this.finalizado;
    }

    @Override
    public void cambiarEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return
                "idProyecto =" + idProyecto + '\n' +
                "direccionVivienda = " + direccionVivienda + '\n' +
                "fechaInicioProyecto = " + fechaInicioProyecto + '\n' +
                "fechaEstimadaFinProyecto = " + fechaEstimadaDeFinalizacion + '\n' +
                "fechaRealFinProyecto = " + fechaRealDeFinalizacion + '\n' +
                "estado= " + estado + '\n' +
                "finalizado =" + finalizado + '\n' +
                "tareas =" + tareas + '\n' +
                "cliente =" +  cliente;
    }
}
