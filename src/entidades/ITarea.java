package entidades;

public interface ITarea {
    void asignarEmpleado(Empleado empleado);
    String verTitulo();
    void modificarTitulo(String nuevoTitulo);
    String verDescripcion();
    void modificarDescripcion(String nuevaDescripcion);
    double VerCantidadDiasFinalizacion();
    void modificarCantidadDiasFinalizacion(double dias);
    Empleado verEmpleado();
    void retrasarTarea(int horas);
    void retrasarTarea();
    void calcularCosto();
    double verCosto();
    void finalizarTarea();
    boolean tieneRetrasos();
    boolean estaFinalizada();
}
