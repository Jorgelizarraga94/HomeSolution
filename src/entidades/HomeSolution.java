package entidades;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HomeSolution implements IHomeSolution{
    private HashSet<Empleado> empleados;
    private HashSet<Proyecto> proyectos;
    private Empleado empleadoConMenorRetraso = null;

    public HomeSolution(){
        this.empleados = new HashSet<>();
        this.proyectos = new HashSet<>();
    }

    //Registra empleado contratado / utilizamos sobreEscritura
    @Override
    public void registrarEmpleado(String nombre, double valor) throws IllegalArgumentException {
        Empleado empleado = new EmpleadoContratado(nombre,valor);
        this.empleados.add(empleado);
        System.out.println("Empleado contratado registrado");
    }
    //Registra empleado permanenete // utilizamos sobreEscritura
    @Override
    public void registrarEmpleado(String nombre, double valor, String categoria) throws IllegalArgumentException {
        Empleado empleado = new EmpleadoPermanente(nombre, valor, categoria);
        this.empleados.add(empleado);
        System.out.println("Empleado Permanente registrado");
    }
    //Registra el proyecto y lo agrega a la lista
    @Override
    public void registrarProyecto(String[] titulos, String[] descripcion, double[] dias, String domicilio, String[] cliente, String inicio, String fin) throws IllegalArgumentException {
        Proyecto proyecto = new Proyecto(cliente,titulos,descripcion,dias,domicilio, inicio, fin);
        this.proyectos.add(proyecto);
        System.out.println("proyecto registrado exitosamente");
    }

    @Override
    public void asignarResponsableEnTarea(Integer numero, String titulo) throws Exception {
        List<Tarea> tareas = new ArrayList<>();
        Empleado empleadoDisponible = null;
        for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                if(proyecto.verEstado().equals(Estado.finalizado)){
                    throw new Exception("el proyecto se encuentra finalizado");
                }
                proyecto.cambiarEstado(Estado.activo);
                tareas = proyecto.verTareas();
            }
        }

        for (Empleado empleado : empleados){
            if(empleado.estaDisponible()){
                empleadoDisponible = empleado;
            }
        }

        if(empleadoDisponible == null){
            throw new Exception("no hay empleados disponibles");
        }

        for(Tarea tarea : tareas){
            if(tarea.verTitulo().equals(titulo) && tarea.estaFinalizada()){
                throw new Exception("tarea finalizada");
            }
            if(tarea.verTitulo().equals(titulo) && tarea.verEmpleado() != null){
                throw new Exception("tarea ya asignada");
            }
            if(tarea.verTitulo().equals(titulo) && tarea.verEmpleado() == null){
                tarea.asignarEmpleado(empleadoDisponible);
                empleadoDisponible.modificarDisponible(false);
                System.out.println("asignar responsable" +" "+empleadoDisponible.mostrarNombre());
            }

        }
    }

    @Override
    public void asignarResponsableMenosRetraso(Integer numero, String titulo) throws Exception {
        List<Tarea> tareas = new ArrayList<>();
        Empleado empleadoConMenorRetraso = null;
        boolean proyectoEncontrado = false;

        for (Proyecto proyecto : proyectos) {
            if (proyecto.verId() == numero) {
                proyectoEncontrado = true;
                if (proyecto.verEstado().equals(Estado.finalizado)) {
                    throw new Exception("El proyecto se encuentra finalizado");
                }
                tareas = proyecto.verTareas();
            }
        }

        if (!proyectoEncontrado) {
            throw new Exception("Proyecto no encontrado");
        }

        for (Empleado empleado : empleados) {
            if (empleado.estaDisponible()) {
                if (empleadoConMenorRetraso == null || empleado.mostrarCantidadRetrasos() < empleadoConMenorRetraso.mostrarCantidadRetrasos()) {
                    empleadoConMenorRetraso = empleado;
                }
            }
        }

        if (empleadoConMenorRetraso == null) {
            throw new Exception("No se encuentra empleado disponible");
        }

        for (Tarea tarea : tareas) {
            if (tarea.verTitulo().equals(titulo)) {
                if (tarea.verEmpleado() != null) {
                    throw new Exception("La tarea ya tiene un empleado asignado");
                }

                tarea.asignarEmpleado(empleadoConMenorRetraso);
                empleadoConMenorRetraso.modificarDisponible(false);
                System.out.println("Responsable con menor retraso asignado: " + empleadoConMenorRetraso.mostrarNombre());
                break;
            }
        }
    }


    @Override
    public void registrarRetrasoEnTarea(Integer numero, String titulo, double cantidadDias) throws IllegalArgumentException {
        List<Tarea> tareas = new ArrayList<>();
        Proyecto p = null;
        for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                tareas = proyecto.verTareas();
                p = proyecto;
            }
        }
        for (Tarea tarea : tareas){
            if(tarea.verTitulo().equals(titulo)){
                tarea.modificarCantidadDiasFinalizacion(cantidadDias);
                tarea.verEmpleado().aumentarRetrasos();
            }
        }
        if(p != null){
            //Actualización de fecha de finalización
            LocalDate nuevaFechaFinalizacion = p.verFechaRealFinalizacion();
            nuevaFechaFinalizacion = nuevaFechaFinalizacion.plusDays((long) cantidadDias);
            p.actualizarFechaRealFinalizacion(nuevaFechaFinalizacion);
        }
    }

    @Override // faltan cosas
    public void agregarTareaEnProyecto(Integer numero, String titulo, String descripcion, double dias) throws IllegalArgumentException {
        for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                proyecto.agregarTarea(titulo, descripcion, dias);
                //Actualización de fecha de finalización
                LocalDate nuevaFechaFinalizacion = proyecto.verFechaRealFinalizacion();
                nuevaFechaFinalizacion = nuevaFechaFinalizacion.plusDays((long) dias);
                proyecto.actualizarFechaRealFinalizacion(nuevaFechaFinalizacion);
                //Actualización de fecha Estimada
                LocalDate nuevaFechaEstimadaFinalizacion = proyecto.verFechaEstimadaFinalizacion();
                nuevaFechaFinalizacion = nuevaFechaEstimadaFinalizacion.plusDays((long) dias);
                proyecto.actualizarFechaEstimadaFinalizacion(nuevaFechaFinalizacion);
            }
        }
    }

    @Override
    public void finalizarTarea(Integer numero, String titulo) throws Exception {
        for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                Tarea tarea = proyecto.seleccionarTarea(titulo);
                tarea.finalizarTarea();
                Empleado empleado = tarea.verEmpleado();
                empleado.modificarDisponible(true);
            }
        }
    }

    @Override
    public void finalizarProyecto(Integer numero, String fin) throws IllegalArgumentException {
        for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                proyecto.actualizarFinalizado();
                proyecto.actualizarFechaRealFinalizacion(LocalDate.parse(fin));
            }
        }
    }

    @Override
    public void reasignarEmpleadoEnProyecto(Integer numero, Integer legajo, String titulo) throws Exception {
        List<Tarea> tareas = new ArrayList<>();
        //Recorremos la lista de proyectos buscando que el id del proyecto sea igual al numero del parametro
        //si lo encontramos guardamos la lista de tareas
        for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                tareas = proyecto.verTareas();
            }
        }
        /*recorremos la lista de tareas del proyecto en busqueda de encontrar el titulo pasado por parametro
        si lo encontramos asignamos un empleado disponible a la tarea y le sacamos el disponible
         */
        for (Tarea tarea : tareas){
            if(tarea.verTitulo().equals(titulo)){
                for (Empleado e : empleados){
                    if(e.estaDisponible()){
                        tarea.asignarEmpleado(e);
                        e.modificarDisponible(false);
                    }
                }
            }
        }
        /*Recorremos la lista de empleados buscando el legajo del empleado anterior para luego ponerlo como disponible*/
        for (Empleado empleado : empleados){
            if(empleado.mostrarLegajo() == legajo){
                empleado.modificarDisponible(true);
            }
        }
    }

    @Override
    public void reasignarEmpleadoConMenosRetraso(Integer numero, String titulo) throws Exception {
        List<Tarea> tareas = new ArrayList<>();
        //Recorremos la lista de proyectos buscando que el id del proyecto sea igual al numero del parametro
        //si lo encontramos guardamos la lista de tareas
        for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                tareas = proyecto.verTareas();
            }
        }
        /*recorremos la lista de tareas del proyecto en busqueda de encontrar el titulo pasado por parametro
        si lo encontramos asignamos un empleado disponible a la tarea y le sacamos el disponible
         */
        for (Tarea tarea : tareas){
            if(tarea.verTitulo().equals(titulo)){
                Empleado empleado = tarea.verEmpleado();
                empleado.modificarDisponible(true);
                for (Empleado e : empleados){
                    if(e.mostrarCantidadRetrasos()<2 && e.estaDisponible()) {
                        tarea.asignarEmpleado(e);
                        e.modificarDisponible(false);
                    }
                }
            }
        }
    }

    @Override
    public double costoProyecto(Integer numero) {
        double costoProyecto = 0;
        for(Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                costoProyecto = proyecto.calculoCostoFinal();
            }
        }
        return costoProyecto;
    }


    @Override
    public List<Tupla<Integer, String>> proyectosFinalizados() {
        List<Tupla<Integer, String>> listaTupla = new ArrayList<>();
        for (Proyecto proyecto : proyectos){
            if(proyecto.verEstado().equals(Estado.finalizado)){
                listaTupla.add(new Tupla<Integer, String>(proyecto.verId(), proyecto.verDireccion()));
            }
        }
        return listaTupla;
    }

    @Override
    public List<Tupla<Integer, String>> proyectosPendientes() {
        List<Tupla<Integer,String>> listaTupla = new ArrayList<>();
        for (Proyecto proyecto : proyectos){
            if(proyecto.verEstado().equals(Estado.pendiente)){
                listaTupla.add(new Tupla<Integer, String>(proyecto.verId(),proyecto.verDireccion()));
            }
        }
        return listaTupla;
    }

    @Override
    public List<Tupla<Integer, String>> proyectosActivos() {
        List<Tupla<Integer, String>> listaTupla = new ArrayList<>();
        for (Proyecto proyecto : proyectos){
            if(proyecto.verEstado().equals(Estado.activo)){
                listaTupla.add(new Tupla<Integer, String>(proyecto.verId(), proyecto.verDireccion()));
            }
        }
        return listaTupla;
    }

    @Override
    public Object[] empleadosNoAsignados() {
        List<Object> lista = new ArrayList<>();
        for(Empleado empleado : empleados){
            if(empleado.estaDisponible()){
                lista.add(empleado);
            }
        }
        return lista.toArray();
    }

    @Override
    public boolean estaFinalizado(Integer numero) {
        for(Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero && proyecto.verEstado().equals(Estado.finalizado)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int consultarCantidadRetrasosEmpleado(Integer legajo) {
        System.out.println("consultarCantidadRetrasoEmpleado");
        for (Empleado empleado: empleados){
            if(empleado.mostrarLegajo() == legajo){
                return empleado.mostrarCantidadRetrasos();
            }
        }
        return 5;
    }

    @Override
    public List<Tupla<Integer, String>> empleadosAsignadosAProyecto(Integer numero) {
        List<Tupla<Integer, String>> empleados = new ArrayList<>();

        for (Proyecto proyecto : proyectos) {
            if (proyecto.verId() == numero) { // Filtramos solo el proyecto indicado
                List<Tarea> tareas = proyecto.verTareas();
                for (Tarea tarea : tareas) {
                    Empleado empleado = tarea.verEmpleado();
                    if (empleado != null) { // Comprobamos que la tarea tenga empleado
                        empleados.add(new Tupla<>(empleado.mostrarLegajo(), empleado.mostrarNombre()));
                    }
                }
            }
        }
        return empleados;
    }


    @Override
    public Object[] tareasProyectoNoAsignadas(Integer numero) {
        List<Object> listaTareas = new ArrayList<>();
        List<Tarea> tareas = new ArrayList<>();
        for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                 tareas = proyecto.verTareas();
            }
        }
        for (Tarea tarea : tareas){
            if(tarea.verEmpleado() == null){
                listaTareas.add(tarea);
            }
        }
        return listaTareas.toArray();
    }

    @Override
    public Object[] tareasDeUnProyecto(Integer numero) {
        List<Tarea> tareas = new ArrayList<>();
        for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                tareas = proyecto.verTareas();
            }
        }
        List<Object> listaTareas = new ArrayList<>(tareas);
        return listaTareas.toArray();
    }

    @Override
    public String consultarDomicilioProyecto(Integer numero) {
        for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                return proyecto.verDireccion();
            }
        }
        return "";
    }

    @Override
    public boolean tieneRestrasos(Integer legajo) {
        for (Empleado empleado : empleados){
            if(empleado.mostrarLegajo() == legajo && empleado.estaRetrasado()){
                return true;
            }
        }
        return false;
    }


    @Override
    public List<Tupla<Integer, String>> empleados() {
        List<Tupla<Integer, String>> listaEmpleados = new ArrayList<>();

        for (Empleado empleado : empleados) {
            listaEmpleados.add(new Tupla<>(empleado.mostrarLegajo(), empleado.mostrarNombre()));
        }

        return listaEmpleados;
    }

    @Override
    public String consultarProyecto(Integer numero) {
        for(Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                return proyecto.toString();
            }
        }
        return "Proyecto no encontrado";
    }
}
