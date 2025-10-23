package entidades;

public class Tarea {
    Long idTarea;
    Long contadorId;
    String titulo;
    String descripcion;
    int cantidadDiasFinalizacion;
    int costo;
    double tiempoFinalizacionHoras;
    Empleado empleado;
    boolean finalizada;

    // Inicializa tarea sin empleado asignado
    public Tarea(String titulo, String descripcion, Integer cantidadDiasFinalizacion, Integer costo) {
        this.idTarea = contadorId+1;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cantidadDiasFinalizacion = cantidadDiasFinalizacion;
        this.costo = costo;
        this.tiempoFinalizacionHoras = 0.0;
        this.empleado = null;
        this.finalizada = false;
    }
}
