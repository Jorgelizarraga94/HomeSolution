package entidades;

public interface Iempleado {
    boolean estaDisponible();
    boolean modificarDisponible(boolean disponibilidad);
    String mostrarNombre();
    void modificarNombre(String nombre);
    Long mostrarLegajo();
    Double calcularCostoHora();
    void asignarCostoHora(Integer costoHoraNuevo);
    boolean estaRetrasado();
    void retrasarTarea();
    int mostrarCantidadRetrasos();
    void aumentarRetrasos();
}
