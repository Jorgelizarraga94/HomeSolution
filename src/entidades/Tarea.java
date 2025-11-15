package entidades;

public class Tarea implements ITarea{
    private String titulo;
    private String descripcion;
    private double cantidadDiasFinalizacion;
    private double costo;
    private Empleado empleado;
    private boolean tieneRetraso;
    private boolean finalizada;

    // Inicializa tarea sin empleado asignado
    public Tarea(String titulo, String descripcion, double cantidadDiasFinalizacion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        if(cantidadDiasFinalizacion>0){
            this.cantidadDiasFinalizacion = cantidadDiasFinalizacion;
        }

        this.costo = costo;
        this.empleado = null;
        this.tieneRetraso = false;
        this.finalizada = false;
    }

    @Override
    public void asignarEmpleado(Empleado empleado) {
        this.empleado = empleado;
        calcularCosto();
    }

    @Override
    public String verTitulo() {
        return this.titulo;
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
    public Empleado verEmpleado() {
        return this.empleado;
    }

    @Override
    public void retrasarTarea(int horas) {
        this.tieneRetraso = true;
    }

    @Override
    public void retrasarTarea() {
        this.tieneRetraso = true;
    }


    @Override
    public void calcularCosto() {
        double costoFinal = 0;

        costoFinal = this.empleado.calculoCostoEmpleado(cantidadDiasFinalizacion);

        this.costo = costoFinal;
    }

    @Override
    public double verCosto() {
        return this.costo;
    }

    @Override
    public void finalizarTarea() {
        this.finalizada = true;
        this.empleado.modificarDisponible(true);
    }

    @Override
    public boolean tieneRetrasos() {
        return this.tieneRetraso;
    }

    @Override
    public boolean estaFinalizada() {
        return this.finalizada;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
