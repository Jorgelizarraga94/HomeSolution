package entidades;

public interface ITarea {
    void asignarEmpleado(Empleado empleado);
    void registrarRetrasoTarea(int horas);
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
    Double calcularCosto();
    int verCosto();
    void finalizarTarea();
    boolean estaFinalizada();
}
