package entidades;

public class Tarea implements ITarea{
    private int idTarea;
    private static int contadorId=1;
    private String titulo;
    private String descripcion;
    private double cantidadDiasFinalizacion;
    private double costo;
    private Double tiempoFinalizacionHoras;
    private Empleado empleado;
    private boolean tieneRetraso;
    private boolean finalizada;

    // Inicializa tarea sin empleado asignado
    public Tarea(String titulo, String descripcion, double cantidadDiasFinalizacion) {
        this.idTarea = contadorId++;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cantidadDiasFinalizacion = cantidadDiasFinalizacion;
        this.costo = costo;
        this.tiempoFinalizacionHoras = 0.0;
        this.empleado = null;
        this.tieneRetraso = false;
        this.finalizada = false;
    }

    @Override
    public void asignarEmpleado(Empleado empleado) {
        this.empleado = empleado;
        calcularCosto();
    }


    @Override
    public String verTitulo() {
        return this.titulo;
    }

    @Override
    public int verIdTarea() {
        return this.idTarea;
    }

    @Override
    public void modificarTitulo(String nuevoTitulo) {
        this.titulo = nuevoTitulo;
    }

    @Override
    public String verDescripcion() {
        return this.descripcion;
    }

    @Override
    public void modificarDescripcion(String nuevaDescripcion) {
        this.descripcion = nuevaDescripcion;
    }

    @Override
    public double VerCantidadDiasFinalizacion() {
        return this.cantidadDiasFinalizacion;
    }

    @Override
    public void modificarCantidadDiasFinalizacion(double dias) { //sumamos o restamos?
        this.cantidadDiasFinalizacion += dias;
    }

    @Override
    public Empleado verEmpleado() {
        return this.empleado;
    }

    @Override
    public Double verTiempoFinalizacionHoras() {
        return this.tiempoFinalizacionHoras;
    }

    @Override
    public void retrasarTarea(int horas) {
        this.tiempoFinalizacionHoras += horas;
        this.tieneRetraso = true;
    }

    @Override
    public void retrasarTarea() {
        this.tieneRetraso = true;
    }


    @Override
    public void calcularCosto() { /// //////////////////////////////////////////
        double costoFinal = 0;
        double costoPorDia = 0;
        if(this.empleado instanceof EmpleadoContratado){
            System.out.println("empleado contratado");
            if(cantidadDiasFinalizacion >= 1){
                costoPorDia = ((EmpleadoContratado) empleado).verCostoHora() * 8;
            }
            if(cantidadDiasFinalizacion == 0.5){
                costoPorDia = ((EmpleadoContratado) empleado).verCostoHora() * 4;
            }
            costoFinal = this.cantidadDiasFinalizacion * costoPorDia;
        }
        if(this.empleado instanceof EmpleadoPermanente){
            System.out.println("empleado permanente");
            if(this.cantidadDiasFinalizacion == 0.5){
                cantidadDiasFinalizacion = 1;
            }
            costoFinal = this.cantidadDiasFinalizacion * (((EmpleadoPermanente) empleado).verValorDia());
            costoFinal = costoFinal * 1.02;

           /* if(!empleado.estaRetrasado()){
                System.out.println("no esta retrasado");
                costoFinal += costoFinal * ((EmpleadoPermanente) empleado).verAdicional();
                System.out.println(costoFinal);
            }*/
        }
        this.costo = costoFinal;
    }

    @Override
    public double verCosto() {
        return this.costo;
    }

    @Override
    public void finalizarTarea() {
        this.finalizada = true;
        this.empleado.modificarDisponible(true);
    }

    @Override
    public boolean tieneRetrasos() {
        return this.tieneRetraso;
    }

    @Override
    public boolean estaFinalizada() {
        return this.finalizada;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
