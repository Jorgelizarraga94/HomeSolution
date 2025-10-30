package entidades;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface Iproyecto {
    int verId();
    void agregarTarea(String titulo, String descripcion, double cantidadDiasFinalizacion);
    void actualizarFinalizado();
    double CalculoCostoFinal();
    void modificarDireccionVivienda(String nuevaDireccion);
    String verDireccion();
    List<Tarea> verTareas();
    String verEstado();
    Tarea seleccionarTarea(String titulo);
    void modificarTarea(String titulo, Tarea tarea);
    void eliminarTarea(String titulo);
    void actualizarFechaRealFinalizacion(LocalDate fecha);
    LocalDate verFechaRealFinalizacion();
    boolean estaFinalizado();
    void cambiarEstado(String estado);
}
