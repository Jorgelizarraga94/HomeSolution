package entidades;
import java.time.LocalDate;
import java.util.*;

public class HomeSolution implements IHomeSolution{
    private LinkedHashMap<Integer, Empleado> empleados;
    private LinkedHashMap<Integer, Proyecto> proyectos;

    public HomeSolution(){
        this.empleados = new LinkedHashMap<>();
        this.proyectos = new LinkedHashMap<>();
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
        empleados.put(empleado.mostrarLegajo(),empleado);
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
        empleados.put(empleado.mostrarLegajo(), empleado);
        System.out.println("Empleado Permanente registrado");
    }

    // ============================================================
    // REGISTRO Y GESTIÓN DE PROYECTOS
    // ============================================================

    //Registra el proyecto y lo agrega a la lista
    @Override
    public void registrarProyecto(String[] titulos, String[] descripcion, double[] dias, String domicilio, String[] cliente, String inicio, String fin) throws IllegalArgumentException {
        if(fin.isEmpty() || fin.equals("    -  -  ") || inicio.isEmpty() || inicio.equals("    -  -  ")){
            throw new IllegalArgumentException("Debe ingresar una fecha válida antes de registrar el proyecto.");
        }
        Proyecto proyecto = new Proyecto(cliente,titulos,descripcion,dias,domicilio, inicio, fin);
        if(LocalDate.parse(inicio).isAfter(LocalDate.parse(fin))){
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        proyectos.put(proyecto.verId(), proyecto);
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
        Proyecto proyecto = proyectos.get(numero);
                if(proyecto.verEstado().equals(Estado.finalizado)){
                    throw new Exception("el proyecto se encuentra finalizado");
                }
                proyecto.cambiarEstado(Estado.activo);
                tareas = proyecto.verTareas();

        for (Empleado empleado : empleados.values()){
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
                for (Empleado empleado : empleados.values()){
                    if(empleado.mostrarLegajo() == empleadoDisponible.mostrarLegajo()){
                        empleado.modificarDisponible(false);
                    }
                }
            }
        }
        for (Empleado empleado : empleados.values()){
            System.out.println(empleado.mostrarNombre() + " " + empleado.estaDisponible());
        }
    }

    @Override
    public void asignarResponsableMenosRetraso(Integer numero, String titulo) throws Exception {
        List<Tarea> tareas = new ArrayList<>();
        Empleado empleadoConMenorRetraso = null;
        boolean proyectoEncontrado = false;

        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            proyectoEncontrado = true;
            tareas = proyecto.verTareas();
        }
        if (proyecto.verEstado().equals(Estado.finalizado)) {
            throw new Exception("El proyecto se encuentra finalizado");
        }
        if (!proyectoEncontrado) {
            throw new Exception("Proyecto no encontrado");
        }

        for (Empleado empleado : empleados.values()) {
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

        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            tareas = proyecto.verTareas();
            p = proyecto;
        }
        for (Tarea tarea : tareas){
            if(tarea.verTitulo().equals(titulo)){
                tarea.modificarCantidadDiasFinalizacion(cantidadDias);
                tarea.verEmpleado().aumentarRetrasos();
                tarea.retrasarTarea();
                System.out.println("tiene retraso?????" + tarea.tieneRetrasos());
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
        System.out.println("entra");
        if(titulo.isEmpty() || descripcion.isEmpty() || dias == 0){
            throw new IllegalArgumentException("Ingrese datos correctos");
        }
        if (dias != 0.5 && dias < 1) {
            throw new IllegalArgumentException("La cantidad de días debe ser 0.5 o >= 1");
        }

        if(titulo.matches(".*\\d.*")){
            throw new IllegalArgumentException("no se admite numeros");
        }
        if(descripcion.matches(".*\\d.*")){
            throw new IllegalArgumentException("no se admite numeros");
        }
        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
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
        /*for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){

            }
        }*/

    }

    @Override
    public void finalizarTarea(Integer numero, String titulo) throws Exception {
        Empleado empleado = null;

        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            if(proyecto.seleccionarTarea(titulo).estaFinalizada()){
                throw new Exception("la tarea se encuentra finalizada");
            }
            Tarea tarea = proyecto.seleccionarTarea(titulo);
            tarea.finalizarTarea();
            System.out.println(tarea.verEmpleado() + "" + tarea.verEmpleado().estaDisponible());
        }
        /*for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){

            }
        }*/
    }

    @Override
    public void finalizarProyecto(Integer numero, String fin) throws IllegalArgumentException {
        Proyecto proyecto = proyectos.get(numero);
        if(fin.isEmpty() || fin.equals("-  -")){
            throw new IllegalArgumentException("Debe ingresar una fecha válida antes de finalizar el proyecto.");
        }
        if(proyecto != null && !fin.isEmpty()){
            if(proyecto.verFechaDeInicio().isAfter(LocalDate.parse(fin))){
                throw new IllegalArgumentException("la fecha de finalización no puede ser anterior a la de inicio");
            }
            if(proyecto.verFechaEstimadaFinalizacion().isAfter(LocalDate.parse(fin))){
                throw new IllegalArgumentException("La fecha de finalización no puede ser menor a la fecha estimada de finalización");
            }

            List<Tarea> tareas = proyecto.verTareas();
            for(Tarea tarea : tareas) {
                if(proyecto.verFechaEstimadaFinalizacion().isBefore(LocalDate.parse(fin))){
                    tarea.retrasarTarea();
                }
                if(tarea.verEmpleado() != null) {
                    tarea.verEmpleado().modificarDisponible(true);
                }
            }
            proyecto.actualizarFinalizado();
        }
        /*for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){

            }
        }*/
    }
    // ============================================================
    // REASIGNACIÓN DE EMPLEADOS
    // ============================================================

    @Override
    public void reasignarEmpleadoEnProyecto(Integer numero, Integer legajo, String titulo) throws Exception {
        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            Tarea tarea = proyecto.seleccionarTarea(titulo);
            Empleado empleado = empleados.get(legajo);
            if(tarea != null){
                if(tarea.verEmpleado() == null){
                    throw new Exception("No se encuentra empleado asignado anteriormente");
                }
                if(empleado != null && empleado.estaDisponible()){
                    tarea.verEmpleado().modificarDisponible(true);
                    tarea.asignarEmpleado(empleado);
                    empleado.modificarDisponible(false);
                }
            }
        }
    }

    @Override
    public void reasignarEmpleadoConMenosRetraso(Integer numero, String titulo) throws Exception {
        Empleado empleadoConMenorRetraso = null;
        boolean proyectoEncontrado = false;

        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            proyectoEncontrado = true;
            if (proyecto.verEstado().equals(Estado.finalizado)) {
                throw new Exception("El proyecto se encuentra finalizado");
            }
        }

        if (!proyectoEncontrado) {
            throw new Exception("Proyecto no encontrado");
        }

        for (Empleado empleado : empleados.values()) {
            if (empleado.estaDisponible()) {
                if (empleadoConMenorRetraso == null || empleado.mostrarCantidadRetrasos() < empleadoConMenorRetraso.mostrarCantidadRetrasos()) {
                    empleadoConMenorRetraso = empleado;
                }
            }
        }

        if (empleadoConMenorRetraso == null) {
            throw new Exception("No se encuentra empleado disponible");
        }
        Tarea tarea = proyecto.seleccionarTarea(titulo);
        if(tarea != null){
            tarea.asignarEmpleado(empleadoConMenorRetraso);
            empleadoConMenorRetraso.modificarDisponible(false);
            System.out.println("Responsable con menor retraso asignado: " + empleadoConMenorRetraso.mostrarNombre());
        }
    }

    // ============================================================
    // CONSULTAS Y REPORTES
    // ============================================================

    @Override
    public double costoProyecto(Integer numero) {
        double costoProyecto = 0;
        List<Tarea> tareas = new ArrayList<>();
        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            costoProyecto = proyecto.calculoCostoFinal();
            tareas = proyecto.verTareas();
        }
        /*for(Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){

            }
        }*/
        return costoProyecto;
    }


    @Override
    public List<Tupla<Integer, String>> proyectosFinalizados() {
        List<Tupla<Integer, String>> listaTupla = new ArrayList<>();
        for (Proyecto proyecto : proyectos.values()){
            if(proyecto.verEstado().equals(Estado.finalizado)){
                listaTupla.add(new Tupla<Integer, String>(proyecto.verId(), proyecto.verDireccion()));
            }
        }
        return listaTupla;
    }

    @Override
    public List<Tupla<Integer, String>> proyectosPendientes() {
        List<Tupla<Integer,String>> listaTupla = new ArrayList<>();
        for (Proyecto proyecto : proyectos.values()){
            if(proyecto.verEstado().equals(Estado.pendiente)){
                listaTupla.add(new Tupla<Integer, String>(proyecto.verId(),proyecto.verDireccion()));
            }
        }
        return listaTupla;
    }

    @Override
    public List<Tupla<Integer, String>> proyectosActivos() {
        List<Tupla<Integer, String>> listaTupla = new ArrayList<>();
        for (Proyecto proyecto : proyectos.values()){
            if(proyecto.verEstado().equals(Estado.activo)){
                listaTupla.add(new Tupla<Integer, String>(proyecto.verId(), proyecto.verDireccion()));
            }
        }
        return listaTupla;
    }

    @Override
    public Object[] empleadosNoAsignados() {
        List<Object> lista = new ArrayList<>();
        Iterator<Empleado> it = empleados.values().iterator();

        while (it.hasNext()) {
            Empleado empleado = it.next();
            if (empleado.estaDisponible()) {
                lista.add(empleado);
            }
        }
        return lista.toArray();
    }


    @Override
    public boolean estaFinalizado(Integer numero) {
        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            if(proyecto.verEstado().equals(Estado.finalizado)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int consultarCantidadRetrasosEmpleado(Integer legajo) {
        System.out.println("consultarCantidadRetrasoEmpleado");
        Empleado empleado = empleados.get(legajo);
        if(empleado != null){
            return empleado.mostrarCantidadRetrasos();
        }
        /*for (Empleado empleado: empleados){
            if(empleado.mostrarLegajo() == legajo){

            }
        }*/
        return 0;
    }

    @Override
    public List<Tupla<Integer, String>> empleadosAsignadosAProyecto(Integer numero) {
        List<Tupla<Integer, String>> empleados = new ArrayList<>();

        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            List<Tarea> tareas = proyecto.verTareas();
            for (Tarea tarea : tareas) {
                Empleado empleado = tarea.verEmpleado();
                if (empleado != null) { // Comprobamos que la tarea tenga empleado
                    empleados.add(new Tupla<>(empleado.mostrarLegajo(), empleado.mostrarNombre()));
                }
            }
        }
        /*for (Proyecto proyecto : proyectos) {
            if (proyecto.verId() == numero) { // Filtramos solo el proyecto indicado

            }
        }*/
        return empleados;
    }

// ============================================================
    // NUEVOS REQUERIMIENTOS
    // ============================================================

    @Override
    public Object[] tareasProyectoNoAsignadas(Integer numero) {
        List<Object> listaTareas = new ArrayList<>();
        List<Tarea> tareas = new ArrayList<>();

        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            tareas = proyecto.verTareas();
        }
        if (proyecto == null) {
            throw new IllegalArgumentException("Proyecto inexistente");
        }
        if (proyecto.estaFinalizado()) {
            throw new IllegalArgumentException("Proyecto finalizado");
        }
        /*for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){

            }

        }*/
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
        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            tareas = proyecto.verTareas();
        }
        /*for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){

            }
        }*/
        List<Object> listaTareas = new ArrayList<>(tareas);
        return listaTareas.toArray();
    }

    @Override
    public String consultarDomicilioProyecto(Integer numero) {
        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            return proyecto.verDireccion();
        }
        /*for (Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){

            }
        }*/
        return "";
    }

    @Override
    public boolean tieneRestrasos(Integer legajo) {
        Empleado empleado = empleados.get(legajo);
        if(empleado != null && empleado.estaRetrasado()){
            return true;
        }
        /*for (Empleado empleado : empleados.values()){
            if(empleado.mostrarLegajo() == legajo && ){

            }
        }*/
        return false;
    }


    @Override
    public List<Tupla<Integer, String>> empleados() {
        List<Tupla<Integer, String>> listaEmpleados = new ArrayList<>();

        for (Empleado empleado : empleados.values()) {
            listaEmpleados.add(new Tupla<>(empleado.mostrarLegajo(), empleado.mostrarNombre()));
        }

        return listaEmpleados;
    }

    @Override
    public String consultarProyecto(Integer numero) {
        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            return proyecto.toString();
        }
        /*for(Proyecto proyecto : proyectos){
            if(proyecto.verId() == numero){

            }
        }*/
        return "Proyecto no encontrado";
    }
}
