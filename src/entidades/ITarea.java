package entidades;

public interface ITarea {
    void AsignarEmpleado(Empleado empleado);
    void registrarRetrasoTarea(int horas);
    String verTitulo();
    int verIdTarea();
    void modificarTitulo(String nuevoTitulo);
    String verDescripcion();
    void modificarDescripcion(String nuevaDescripcion);
    double VerCantidadDiasFinalizacion();
    void modificarCantidadDiasFinalizacion(double dias);
    Empleado VerEmpleado();
    Double verTiempoFinalizacionHoras();
    void RetrasarTarea(int horas);
    Double calcularCosto();
    int verCosto();
    void finalizarTarea();
}
