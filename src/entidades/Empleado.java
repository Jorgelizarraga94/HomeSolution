package entidades;

public class Empleado implements Iempleado{
    public String nombre;
    public int legajo;
    private boolean disponible;
    private boolean retrasoEnTarea;
    private int contadorRetrasos;

    @Override
    public boolean estaDisponible() {
        return this.disponible;
    }

    @Override
    public boolean modificarDisponible(boolean disponibilidad) {
        return this.disponible = disponibilidad;
    }

    @Override
    public String mostrarNombre() {
        return this.nombre;
    }

    @Override
    public void modificarNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int mostrarLegajo() {
        return this.legajo;
    }

    @Override
    public boolean estaRetrasado() {
        return this.contadorRetrasos>0;
    }

    @Override
    public void retrasarTarea() {
        this.retrasoEnTarea = true;
    }

    @Override
    public int mostrarCantidadRetrasos() {
        return this.contadorRetrasos;
    }

    @Override
    public void aumentarRetrasos() {
        this.contadorRetrasos++;
    }
}
