package entidades;

public class EmpleadoPermanente extends Empleado implements IempleadoPermanente{
    private double valorDiaTrabajo;
    private String categoria;
    private double adicional;

    public EmpleadoPermanente(String nombre, double valorDiaTrabajo, String categoria){
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
