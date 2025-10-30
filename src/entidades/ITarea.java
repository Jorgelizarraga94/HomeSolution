package entidades;

public interface ITarea {
    void asignarEmpleado(Empleado empleado);
    String verTitulo();
    int verIdTarea();
    void modificarTitulo(String nuevoTitulo);
    String verDescripcion();
    void modificarDescripcion(String nuevaDescripcion);
    double VerCantidadDiasFinalizacion();
    void modificarCantidadDiasFinalizacion(double dias);
    Empleado verEmpleado();
    Double verTiempoFinalizacionHoras();
    void retrasarTarea(int horas);
    void calcularCosto();
    double verCosto();
    void finalizarTarea();
    boolean estaFinalizada();
}
