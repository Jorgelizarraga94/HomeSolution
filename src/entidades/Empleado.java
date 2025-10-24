package entidades;

public class Empleado implements Iempleado{
    public String nombre;
    public int legajo;
    private boolean disponible;
    private boolean retrasoEnTarea;
    private int contadorRetrasos;

    @Override
    public boolean estaDisponible() {
        return false;
    }

    @Override
    public boolean modificarDisponible(boolean disponibilidad) {
        return false;
    }

    @Override
    public String mostrarNombre() {
        return "";
    }

    @Override
    public void modificarNombre(String nombre) {

    }

    @Override
    public Long mostrarLegajo() {
        return 0L;
    }

    @Override
    public Double calcularCostoHora() {
        return 0.0;
    }

    @Override
    public void asignarCostoHora(Integer costoHoraNuevo) {

    }

    @Override
    public boolean estaRetrasado() {
        return false;
    }

    @Override
    public void retrasarTarea() {

    }

    @Override
    public int mostrarCantidadRetrasos() {
        return 0;
    }

    @Override
    public void aumentarRetrasos() {

    }
}
