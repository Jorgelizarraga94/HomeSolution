package entidades;

public class EmpleadoContratado extends Empleado implements IempleadoContratado{
    double costoHora;

    @Override
    public double verCostoHora() {
        return 0;
    }

    @Override
    public void asignarCostoHora(double costoHora) {

    }
}
