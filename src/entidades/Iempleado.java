package entidades;

public interface Iempleado {
    boolean estaDisponible();
    boolean modificarDisponible(boolean disponibilidad);
    String mostrarNombre();
    void modificarNombre(String nombre);
    int mostrarLegajo();
    boolean estaRetrasado();
    void retrasarTarea();
    int mostrarCantidadRetrasos();
    void aumentarRetrasos();
}
