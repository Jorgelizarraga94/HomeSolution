package entidades;

import java.util.Date;
import java.util.List;

public interface Iproyecto {
    void agregarTarea(String titulo, String descripcion, Integer cantidadDiasFinalizacion, Integer costo);
    void actualizarFinalizado();
    double CalculoCostoFinal();
    void modificarDireccionVivienda(String nuevaDireccion);
    String verDireccion();
    List<Tarea> verTareas();
    Tarea seleccionarTarea(int idTarea);
    void modificarTarea(int idTarea, Tarea tarea);
    void eliminarTarea(int idTarea);
    void actualizarFechaRealFinalizacion(String fecha);
    String verFechaRealFinalizacion();
    boolean estaFinalizado();
}
