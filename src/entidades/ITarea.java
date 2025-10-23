package entidades;

public interface ITarea {
    void AsignarEmpleado(Empleado empleado);
    void registrarRetrasoTarea(int horas);
    String verTitulo();
    void modificarTitulo(String nuevoTitulo);
    String verDescripcion();
    void modificarDescripcion(String nuevaDescripcion);
    int VerCantidadDiasFinalización();
    void modificarCantidadDiasFinalizacion(int dias);
    String VerEmpleado();
    Double verTiempoFinalizaciónHoras();
    void RetrasarTarea(int horas);
    Double calcularCosto();
    Double verCosto();
    void finalizarTarea();
}
