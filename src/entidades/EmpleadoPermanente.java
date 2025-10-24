package entidades;

public class EmpleadoPermanente extends Empleado implements IempleadoPermanente{
    double valorDiaTrabajo;
    String categoria;
    double adicional;

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
