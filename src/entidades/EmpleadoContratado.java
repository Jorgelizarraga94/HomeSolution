package entidades;

public class EmpleadoContratado extends Empleado implements IempleadoContratado{
    private double costoHora;
    private int contadorLegajo;

    public EmpleadoContratado(String nombre, double costoHora){
        this.nombre = nombre;
        this.legajo = contadorLegajo+1;
        this.costoHora = costoHora;
    }
    @Override
    public double verCostoHora() {
        return costoHora;
    }

    @Override
    public void asignarCostoHora(double costoHora) {
        this.costoHora = costoHora;
    }
}
