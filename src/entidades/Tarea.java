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
    public Tarea(String titulo, String descripcion, double cantidadDiasFinalizacion, int costo) {
        this.idTarea = contadorId+1;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cantidadDiasFinalizacion = cantidadDiasFinalizacion;
        this.costo = costo;
        this.tiempoFinalizacionHoras = 0.0;
        this.empleado = null;
        this.finalizada = false;
    }

    @Override
    public void AsignarEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public void registrarRetrasoTarea(int horas) { /// /////////////////////////

    }

    @Override
    public String verTitulo() {
        return this.titulo;
    }

    @Override
    public int verIdTarea() {
        return this.idTarea;
    }

    @Override
    public void modificarTitulo(String nuevoTitulo) {
        this.titulo = nuevoTitulo;
    }

    @Override
    public String verDescripcion() {
        return this.descripcion;
    }

    @Override
    public void modificarDescripcion(String nuevaDescripcion) {
        this.descripcion = nuevaDescripcion;
    }

    @Override
    public double VerCantidadDiasFinalizacion() {
        return this.cantidadDiasFinalizacion;
    }

    @Override
    public void modificarCantidadDiasFinalizacion(double dias) { //sumamos o restamos?
        this.cantidadDiasFinalizacion += dias;
    }

    @Override
    public Empleado VerEmpleado() {
        return this.empleado;
    }

    @Override
    public Double verTiempoFinalizacionHoras() {
        return this.tiempoFinalizacionHoras;
    }

    @Override
    public void RetrasarTarea(int horas) {
        this.tiempoFinalizacionHoras += horas;
    }

    @Override
    public Double calcularCosto() { /// ///////////////////
        return 0.0;
    }

    @Override
    public int verCosto() {
        return this.costo;
    }

    @Override
    public void finalizarTarea() {
        this.finalizada = true;
    }
}
