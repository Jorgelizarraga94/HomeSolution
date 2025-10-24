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
    Tarea seleccionarTarea(Long idTarea);
    void modificarTarea(Tarea tarea);
    void eliminarTarea(Tarea tarea);
    void actualizarFechaRealFinalización(Date fecha);
    Date verFechaRealFinalización();
    boolean estaFinalizado();
    List<Empleado> empleadosConRetraso();
}
