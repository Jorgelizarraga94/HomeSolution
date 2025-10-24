package entidades;

public class EmpleadoPermanente extends Empleado implements IempleadoPermanente{
    private double valorDiaTrabajo;
    private String categoria;
    private double adicional;
    private int contadorLegajo;

    //El legajo de empleadosPermanentes debe comenzar en 200
    public EmpleadoPermanente(String nombre, double valorDiaTrabajo, String categoria){
        this.legajo += this.contadorLegajo+1;
        this.nombre = nombre;
        this.valorDiaTrabajo = valorDiaTrabajo;
        this.categoria = categoria;
        adicional = 0;
    }
    @Override
    public double calcularAdicional() {
        return 0;
    }

    @Override
    public void modificarValorDia() {

    }

    @Override
    public int verValorDia() {
        return 0;
    }

    @Override
    public String verCategoria() {
        return "";
    }

    @Override
    public double verAdicional() {
        return 0;
    }
}
