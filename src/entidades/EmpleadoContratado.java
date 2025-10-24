package entidades;

public class EmpleadoContratado extends Empleado implements IempleadoContratado{
    private double costoHora;
    private int contadorLegajo;

    //tenemos que hacer que los legajos de empleadosContratados comienzen a partir del 100
    public EmpleadoContratado(String nombre, double costoHora){
        this.nombre = nombre;
        this.legajo = contadorLegajo+1;
        this.costoHora = costoHora;
    }
    @Override
    public double verCostoHora() {
        return this.costoHora;
    }

    @Override
    public void asignarCostoHora(double costoHora) {
        this.costoHora = costoHora;
    }
}
