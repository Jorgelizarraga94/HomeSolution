package entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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
    private LinkedHashMap<String, Tarea> tareas;
    private Cliente cliente;

    // Inicializar campos y estado inicial
    public Proyecto(String[] cliente, String [] titulos, String [] descripcion, double [] duracion, String direccionVivienda, String fechaInicioProyecto, String fechaEstimadaFinProyecto) {
        this.idProyecto = contadorIdProyecto++;
        this.cliente = new Cliente(cliente[0], cliente[1], cliente[2]);
        this.direccionVivienda = direccionVivienda;
        this.tareas = new LinkedHashMap<>();
        for(int i=0 ; i<titulos.length; i++){
            Tarea tarea = new Tarea(titulos[i],descripcion[i],duracion[i]);
            tareas.put(tarea.verTitulo(), tarea);
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
        tareas.put(tarea.verTitulo(), tarea);
    }

    @Override
    public void actualizarFinalizado() {
        this.finalizado = true;
        this.estado = Estado.finalizado;
    }

    @Override
    public double calculoCostoFinal() {
        double costoFinal=0;
        boolean tieneRetraso = false;

        for (Tarea tarea : tareas.values()){
            costoFinal += tarea.verCosto();
            tieneRetraso = tieneRetraso || tarea.tieneRetrasos();
        }

        if(tieneRetraso){
            costoFinal = costoFinal * 1.25;
        }
        else{
            costoFinal = costoFinal * 1.35;
        }

        return costoFinal;
    }

    @Override
    public void modificarDireccionVivienda(String nuevaDireccion) {
        this.direccionVivienda = nuevaDireccion;
    }

    @Override
    public String verDireccion() {
        return this.direccionVivienda;
    }

    @Override
    public List<Tarea> verTareas() {
        return new ArrayList<>(this.tareas.values());
    }

    @Override
    public String verEstado() {
        return this.estado;
    }

    @Override
    public Tarea seleccionarTarea(String titulo) {
        Tarea tarea = tareas.get(titulo);
        if(tarea != null){
            return tarea;
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
        String fin;
        String retraso;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("==================Detalle de Proyecto==================").append('\n');
        stringBuilder.append("idProyecto: ").append(idProyecto).append('\n');
        stringBuilder.append("direccion de la vivienda: ").append(direccionVivienda).append('\n');
        stringBuilder.append("fecha de inicio de proyecto: ").append(fechaInicioProyecto).append('\n');
        stringBuilder.append("fecha Estimada de Fin del Proyecto: ").append(fechaEstimadaDeFinalizacion).append('\n');
        stringBuilder.append("fecha real de finalización: ").append(fechaRealDeFinalizacion).append('\n');
        stringBuilder.append("estado del proyecto: ").append(estado).append('\n');
        if(finalizado){
            fin = "Si";
        }
        else{
            fin = "No";
        }
        stringBuilder.append("esta finalizado: ").append(fin).append('\n');
        stringBuilder.append("costo final: ").append(costoProyecto).append('\n');
        if(fechaRealDeFinalizacion.isAfter(fechaEstimadaDeFinalizacion)){
            retraso = "Si";
        }
        else{
            retraso = "No";
        }
        stringBuilder.append("Retraso: ").append(retraso).append('\n');
        stringBuilder.append("================Detalle de Cliente====================").append('\n');
        stringBuilder.append("cliente: ").append(cliente).append('\n');
        stringBuilder.append("================Detalle de Tarea======================").append('\n');
        stringBuilder.append("tareas: ").append(tareas).append('\n');
        return stringBuilder.toString();
    }
}
