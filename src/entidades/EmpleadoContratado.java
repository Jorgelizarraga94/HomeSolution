package entidades;

public class EmpleadoContratado extends Empleado implements IempleadoContratado{
    private double costoHora;

    public EmpleadoContratado(String nombre, double costoHora){
        this.nombre = nombre;
        this.legajo = contadorLegajo++;
        this.costoHora = costoHora;
        this.disponible = true;
        this.contadorRetrasos = 0;
    }
    @Override
    public double verCostoHora() {
        return this.costoHora;
    }

    @Override
    public void asignarCostoHora(double costoHora) {
        this.costoHora = costoHora;
    }

    @Override
    public String toString() {
        return Integer.toString(legajo);
    }
}

