package entidades;

public interface Iempleado {
    boolean estaDisponible();
    void modificarDisponible(boolean disponibilidad);
    String mostrarNombre();
    void modificarNombre(String nombre);
    int mostrarLegajo();
    boolean estaRetrasado();
    void retrasarTarea();
    int mostrarCantidadRetrasos();
    void aumentarRetrasos();
}
