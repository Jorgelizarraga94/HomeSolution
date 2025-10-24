package entidades;

public class Tarea implements ITarea{
    private int idTarea;
    private int contadorId;
    private String titulo;
    private String descripcion;
    private double cantidadDiasFinalizacion;
    private int costo;
    private Double tiempoFinalizacionHoras;
    private Empleado empleado;
    private boolean finalizada;

    // Inicializa tarea sin empleado asignado
    public Tarea(String titulo, String descripcion, double cantidadDiasFinalizacion) {
        this.idTarea = contadorId+1;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cantidadDiasFinalizacion = cantidadDiasFinalizacion;
        this.costo = 0;
        this.tiempoFinalizacionHoras = 0.0;
        this.empleado = null;
        this.finalizada = false;
    }

    @Override
    public void AsignarEmpleado(Empleado empleado) {

    }

    @Override
    public void registrarRetrasoTarea(int horas) {

    }

    @Override
    public String verTitulo() {
        return "";
    }

    @Override
    public void modificarTitulo(String nuevoTitulo) {

    }

    @Override
    public String verDescripcion() {
        return "";
    }

    @Override
    public void modificarDescripcion(String nuevaDescripcion) {

    }

    @Override
    public int VerCantidadDiasFinalización() {
        return 0;
    }

    @Override
    public void modificarCantidadDiasFinalizacion(int dias) {

    }

    @Override
    public String VerEmpleado() {
        return "";
    }

    @Override
    public Double verTiempoFinalizaciónHoras() {
        return 0.0;
    }

    @Override
    public void RetrasarTarea(int horas) {

    }

    @Override
    public Double calcularCosto() {
        return 0.0;
    }

    @Override
    public Double verCosto() {
        return 0.0;
    }

    @Override
    public void finalizarTarea() {

    }
}
