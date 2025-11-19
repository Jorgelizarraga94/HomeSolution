package entidades;

public class EmpleadoPermanente extends Empleado implements IempleadoPermanente{
    private double valorDiaTrabajo;
    private String categoria;
    private double adicional;

    //El legajo de empleadosPermanentes debe comenzar en 200
    public EmpleadoPermanente(String nombre, double valorDiaTrabajo, String categoria){
        super(nombre);
        this.valorDiaTrabajo = valorDiaTrabajo;
        this.categoria = categoria;
        this.adicional = 1.02;
    }

    @Override
    public void modificarValorDia(double nuevoValor) {
        this.valorDiaTrabajo = nuevoValor;
    }

    @Override
    public double verValorDia() {
        return this.valorDiaTrabajo;
    }

    @Override
    public String verCategoria() {
        return this.categoria;
    }

    @Override
    public double verAdicional() {
        return this.adicional;
    }

    @Override
    public String toString() {
        return Integer.toString(legajo);
    }

    @Override
    public double calculoCostoEmpleado(double cantidadDiasFinalizacion) {
        double costoFinal=0;
        if(cantidadDiasFinalizacion == 0.5){
            cantidadDiasFinalizacion = 1;
        }
        costoFinal = cantidadDiasFinalizacion * this.valorDiaTrabajo;
        costoFinal = costoFinal * this.verAdicional();
        return costoFinal;
    }
}
