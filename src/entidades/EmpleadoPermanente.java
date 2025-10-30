package entidades;

public class EmpleadoPermanente extends Empleado implements IempleadoPermanente{
    private double valorDiaTrabajo;
    private String categoria;
    private double adicional;
    private static int contadorLegajo=200;

    //El legajo de empleadosPermanentes debe comenzar en 200
    public EmpleadoPermanente(String nombre, double valorDiaTrabajo, String categoria){
        this.legajo += this.contadorLegajo++;
        this.nombre = nombre;
        this.valorDiaTrabajo = valorDiaTrabajo;
        this.categoria = categoria;
        this.adicional = 0.02;
        this.disponible = true;
        this.contadorRetrasos = 0;
    }

    @Override
    public void modificarValorDia() {

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
}
