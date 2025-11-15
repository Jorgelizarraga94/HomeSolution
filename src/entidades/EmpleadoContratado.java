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

    @Override
    public double calculoCostoEmpleado(double cantidadDiasFinalizacion) {
        double costoPorDia = 0;
        if(cantidadDiasFinalizacion >= 1){
            costoPorDia = this.costoHora * 8;
        }
        if(cantidadDiasFinalizacion == 0.5){
           costoPorDia = this.costoHora * 4;
        }

        return cantidadDiasFinalizacion * costoPorDia;
    }
}

