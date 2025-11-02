package entidades;
import java.security.PrivilegedActionException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class HomeSolution implements IHomeSolution{
    private LinkedHashSet<Empleado> empleados;
    private LinkedHashSet<Proyecto> proyectos;
    private Empleado empleadoConMenorRetraso = null;

    public HomeSolution(){
        this.empleados = new LinkedHashSet<>();
        this.proyectos = new LinkedHashSet<>();
    }

    // ============================================================
    // REGISTRO DE EMPLEADOS
    // ============================================================
    //Registra empleado contratado / utilizamos sobreEscritura
    @Override
    public void registrarEmpleado(String nombre, double valor) throws IllegalArgumentException {
        if(valor < 0){
            throw new IllegalArgumentException("El valor no puede ser negativo");
        }
        Empleado empleado = new EmpleadoContratado(nombre,valor);
        this.empleados.add(empleado);
        System.out.println("Empleado contratado registrado");
    }
    //Registra empleado permanenete // utilizamos sobreEscritura
    @Override
    public void registrarEmpleado(String nombre, double valor, String categoria) throws IllegalArgumentException {
        if(valor < 0){
            throw new IllegalArgumentException("El valor no puede ser negativo");
        }

        List<String> categoriasValidas = List.of("INICIAL", "TECNICO", "EXPERTO");
        if (!categoriasValidas.contains(categoria)) {
            throw new IllegalArgumentException("Ingrese una categoria valida");
        }

        Empleado empleado = new EmpleadoPermanente(nombre, valor, categoria);
        this.empleados.add(empleado);
        System.out.println("Empleado Permanente registrado");
    }

    // ============================================================
    // REGISTRO Y GESTIÓN DE PROYECTOS
    // ============================================================

    //Registra el proyecto y lo agrega a la lista
    @Override
    public void registrarProyecto(String[] titulos, String[] descripcion, double[] dias, String domicilio, String[] cliente, String inicio, String fin) throws IllegalArgumentException {
        Proyecto proyecto = new Proyecto(cliente,titulos,descripcion,dias,domicilio, inicio, fin);
        if(LocalDate.parse(inicio).isAfter(LocalDate.parse(fin))){
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        this.proyectos.add(proyecto);
        System.out.println("proyecto registrado exitosamente");
    }

    // ============================================================
    // ASIGNACIÓN Y GESTIÓN DE TAREAS
    // ============================================================

    @Override
    public void asignarResponsableEnTarea(Integer numero, String titulo) throws Exception {
        int minLegajo = Integer.MAX_VALUE;
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
            if(empleado.estaDisponible() && empleado.mostrarLegajo() < minLegajo){
                minLegajo = empleado.mostrarLegajo();
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
                for (Empleado empleado : empleados){
                    if(empleado.mostrarLegajo() == empleadoDisponible.mostrarLegajo()){
                        empleado.modificarDisponible(false);
                    }
                }
            }
        }
        for (Empleado empleado : empleados){
            System.out.println(empleado.mostrarNombre() + " " + empleado.estaDisponible());
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
        if(cantidadDias < 1){
            throw new IllegalArgumentException("La cantidad de dias debe ser mayor a 0");
        }

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
                tarea.retrasarTarea();
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
        if (dias != 0.5 && dias < 1) {
            throw new IllegalArgumentException("La cantidad de días debe ser 0.5 o >= 1");
        }

        if(titulo.matches(".*\\d.*")){
            throw new IllegalArgumentException("no se admite numeros");
        }
        if(descripcion.matches(".*\\d.*")){
            throw new IllegalArgumentException("no se admite numeros");
        }
        for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                if(proyecto.estaFinalizado()){
                    throw new IllegalArgumentException("El proyecto se encuentra finalizado");
                }
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
        Empleado empleado = null;
        for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                if(proyecto.seleccionarTarea(titulo).estaFinalizada()){
                    throw new Exception("la tarea se encuentra finalizada");
                }
                Tarea tarea = proyecto.seleccionarTarea(titulo);
                tarea.finalizarTarea();
                System.out.println(tarea.verEmpleado() + "" + tarea.verEmpleado().estaDisponible());
            }
        }
    }

    @Override
    public void finalizarProyecto(Integer numero, String fin) throws IllegalArgumentException {
        for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                if(proyecto.verFechaDeInicio().isAfter(LocalDate.parse(fin))){
                    throw new IllegalArgumentException("la fecha de finalización no puede ser anterior a la de inicio");
                }
                if(proyecto.verFechaEstimadaFinalizacion().isAfter(LocalDate.parse(fin))){
                    throw new IllegalArgumentException("La fecha de finalización no puede ser menor a la fecha estimada de finalización");
                }
                List<Tarea> tareas = proyecto.verTareas();
                for(Tarea tarea : tareas) {
                    if(tarea.verEmpleado() != null) {
                        tarea.verEmpleado().modificarDisponible(true);
                    }
                }
                proyecto.actualizarFinalizado();
            }
        }
    }
    // ============================================================
    // REASIGNACIÓN DE EMPLEADOS
    // ============================================================

    @Override
    public void reasignarEmpleadoEnProyecto(Integer numero, Integer legajo, String titulo) throws Exception {
        System.out.println("reasignarEmpleadoEnProyectooooooooooooooooooooooooooooooooooooooooo");
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
                if(tarea.verEmpleado() == null){
                    throw new Exception("No se encuentra empleado asignado anteriormente");
                }
                for (Empleado e : empleados){
                    if(e.estaDisponible() && e.mostrarLegajo() == legajo){
                        tarea.asignarEmpleado(e);
                        e.modificarDisponible(false);
                    }
                    /*//solucionar esto
                    if(!e.estaDisponible()){
                        throw new Exception("No se encuentran empleados disponibles");
                    }*/
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
                tarea.asignarEmpleado(empleadoConMenorRetraso);
                empleadoConMenorRetraso.modificarDisponible(false);
                System.out.println("Responsable con menor retraso asignado: " + empleadoConMenorRetraso.mostrarNombre());
                break;
            }
        }
    }

    // ============================================================
    // CONSULTAS Y REPORTES
    // ============================================================

    @Override
    public double costoProyecto(Integer numero) {
        double costoProyecto = 0;
        List<Tarea> tareas = new ArrayList<>();
        for(Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                costoProyecto = proyecto.calculoCostoFinal();
                tareas = proyecto.verTareas();
            }
        }
        boolean tieneRetraso = false;
        for (Tarea tarea : tareas){
            tieneRetraso = tieneRetraso || tarea.tieneRetrasos();
        }

        if(tieneRetraso){
            costoProyecto = costoProyecto * 1.25;
        }
        else{
            costoProyecto = costoProyecto * 1.35;
        }
        System.out.println("Costo proyecto = " + costoProyecto);
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

// ============================================================
    // NUEVOS REQUERIMIENTOS
    // ============================================================

    @Override
    public Object[] tareasProyectoNoAsignadas(Integer numero) {
        List<Object> listaTareas = new ArrayList<>();
        List<Tarea> tareas = new ArrayList<>();

        for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){
                 tareas = proyecto.verTareas();
            }
            if (proyecto == null) {
                throw new IllegalArgumentException("Proyecto inexistente");
            }
            if (proyecto.estaFinalizado()) {
                throw new IllegalArgumentException("Proyecto finalizado");
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
