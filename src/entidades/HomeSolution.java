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

    //Registra empleado contratado
    @Override
    public void registrarEmpleado(String nombre, double valor) throws IllegalArgumentException {
        if(nombre == null || nombre.isEmpty()){ throw new IllegalArgumentException("El nombre no puede estar vacio"); };
        if(valor < 0){ throw new IllegalArgumentException("El valor no puede ser negativo"); }
        Empleado empleado = new EmpleadoContratado(nombre,valor);
        empleados.put(empleado.mostrarLegajo(),empleado);
    }
    //Registra empleado permanente
    @Override
    public void registrarEmpleado(String nombre, double valor, String categoria) throws IllegalArgumentException {
        if(nombre == null || nombre.isEmpty()){ throw new IllegalArgumentException("El nombre no puede estar vacio"); };
        if(valor < 0){ throw new IllegalArgumentException("El valor no puede ser negativo"); }
        List<String> categoriasValidas = List.of("INICIAL", "TECNICO", "EXPERTO");
        if (!categoriasValidas.contains(categoria) || categoria == null || categoria.isEmpty()) { throw new IllegalArgumentException("Ingrese una categoria valida"); }

        Empleado empleado = new EmpleadoPermanente(nombre, valor, categoria);
        empleados.put(empleado.mostrarLegajo(), empleado);
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
        if(LocalDate.parse(inicio).isAfter(LocalDate.parse(fin))){
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        // Validar datos del cliente
        if (cliente == null || cliente.length < 3) {
            throw new IllegalArgumentException("Los datos del cliente son incompletos.");
        }

        String nombre = cliente[0];
        String email = cliente[1];

        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }
        if(!email.isEmpty() && !email.contains("@")){
            throw new IllegalArgumentException("Formato incorrecto de email");
        }

        // -------- Validación de arrays de tareas --------
        if (titulos == null || descripcion == null || dias == null) {
            throw new IllegalArgumentException("Debe ingresar los datos de las tareas del proyecto.");
        }

        if (titulos.length != descripcion.length || titulos.length != dias.length) {
            throw new IllegalArgumentException("títulos, descripciones y días deben tener el mismo tamaño.");
        }

        for (int i = 0; i < titulos.length; i++) {
            String titulo = titulos[i];
            double cantDias = dias[i];

            if (titulo == null || titulo.isEmpty()) {
                throw new IllegalArgumentException("El título de la tarea no puede estar vacío.");
            }
            if (cantDias <= 0) {
                throw new IllegalArgumentException("La cantidad de días de la tarea debe ser mayor a cero.");
            }
        }

        // -------- Validación de domicilio --------
        if (domicilio == null || domicilio.isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un domicilio para el proyecto.");
        }

        Proyecto proyecto = new Proyecto(cliente,titulos,descripcion,dias,domicilio, inicio, fin);

        proyectos.put(proyecto.verId(), proyecto);
    }

    // ============================================================
    // ASIGNACIÓN Y GESTIÓN DE TAREAS
    // ============================================================

    //se asigna responsable en tarea
    @Override
    public void asignarResponsableEnTarea(Integer numero, String titulo) throws Exception {
        int minLegajo = Integer.MAX_VALUE;
        Empleado empleadoDisponible = null;

        Proyecto proyecto = proyectos.get(numero);
        Tarea tarea = proyecto.seleccionarTarea(titulo);

        if(proyecto.verEstado().equals(Estado.finalizado)){ throw new Exception("el proyecto se encuentra finalizado"); }

        for (Empleado empleado : empleados.values()){
            if(empleado.estaDisponible() && empleado.mostrarLegajo() < minLegajo){
                minLegajo = empleado.mostrarLegajo();
                empleadoDisponible = empleado;
            }
        }

        if(empleadoDisponible == null){
            throw new Exception("no hay empleados disponibles");
        }

        if(tarea != null){
            if(tarea.verTitulo().equals(titulo) && tarea.estaFinalizada()){
                throw new Exception("tarea finalizada");
            }
            if(tarea.verTitulo().equals(titulo) && tarea.verEmpleado() != null){
                throw new Exception("tarea ya asignada");
            }
            if(tarea.verTitulo().equals(titulo) && tarea.verEmpleado() == null){
                tarea.asignarEmpleado(empleadoDisponible);
                proyecto.cambiarEstado(Estado.activo);
                empleadoDisponible.modificarDisponible(false);
                for (Empleado empleado : empleados.values()){
                    if(empleado.mostrarLegajo() == empleadoDisponible.mostrarLegajo()){
                        empleado.modificarDisponible(false);
                    }
                }
            }
        }
    }

    //Se asigna responsable con menos retraso
    @Override
    public void asignarResponsableMenosRetraso(Integer numero, String titulo) throws Exception {
        Empleado empleadoConMenorRetraso = null;
        boolean proyectoEncontrado = false;

        Proyecto proyecto = proyectos.get(numero);
        Tarea tarea = proyecto.seleccionarTarea(titulo);

        if(proyecto != null){ proyectoEncontrado = true; }
        if (proyecto.verEstado().equals(Estado.finalizado)) { throw new Exception("El proyecto se encuentra finalizado"); }
        if (!proyectoEncontrado) { throw new Exception("Proyecto no encontrado"); }

        for (Empleado empleado : empleados.values()) {
            if (empleado.estaDisponible()) {
                if (empleadoConMenorRetraso == null || empleado.mostrarCantidadRetrasos() < empleadoConMenorRetraso.mostrarCantidadRetrasos()) {
                    empleadoConMenorRetraso = empleado;
                }
            }
        }

        if (empleadoConMenorRetraso == null) { throw new Exception("No se encuentra empleado disponible"); }

        if(tarea != null){
            if (tarea.verTitulo().equals(titulo)) {
                if (tarea.verEmpleado() != null) { throw new Exception("La tarea ya tiene un empleado asignado"); }
                tarea.asignarEmpleado(empleadoConMenorRetraso);
                empleadoConMenorRetraso.modificarDisponible(false);
            }
        }
    }

    //Registra Retrasos En una Tarea especifica
    @Override
    public void registrarRetrasoEnTarea(Integer numero, String titulo, double cantidadDias) throws IllegalArgumentException {
        if(cantidadDias < 1){ throw new IllegalArgumentException("La cantidad de dias debe ser mayor a 0"); }

        Proyecto p = null;
        Proyecto proyecto = proyectos.get(numero);
        Tarea tarea = proyecto.seleccionarTarea(titulo);

        if(proyecto != null){
            p = proyecto;
        }

        if(tarea != null){
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

    //Se agrega tarea proyecto
    @Override
    public void agregarTareaEnProyecto(Integer numero, String titulo, String descripcion, double dias) throws IllegalArgumentException {
        if(titulo.isEmpty() || descripcion.isEmpty() || dias == 0){ throw new IllegalArgumentException("Ingrese datos correctos"); }
        if (dias != 0.5 && dias < 1) { throw new IllegalArgumentException("La cantidad de días debe ser 0.5 o >= 1"); }
        if(titulo.matches(".*\\d.*")){ throw new IllegalArgumentException("no se admite numeros"); }
        if(descripcion.matches(".*\\d.*")){ throw new IllegalArgumentException("no se admite numeros"); }

        Proyecto proyecto = proyectos.get(numero);

        if(proyecto != null){
            if(proyecto.estaFinalizado()){ throw new IllegalArgumentException("El proyecto se encuentra finalizado"); }
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

    //Finaliza una tarea especifica
    @Override
    public void finalizarTarea(Integer numero, String titulo) throws Exception {
        Proyecto proyecto = proyectos.get(numero);
        Tarea tarea = proyecto.seleccionarTarea(titulo);

        if(proyecto != null){
            if(proyecto.seleccionarTarea(titulo).estaFinalizada()){ throw new Exception("la tarea se encuentra finalizada"); }
            tarea.finalizarTarea();
            tarea.verEmpleado().modificarDisponible(true);
        }
    }

    //Finaliza un proyecto
    @Override
    public void finalizarProyecto(Integer numero, String fin) throws IllegalArgumentException {
        Proyecto proyecto = proyectos.get(numero);
        List<Tarea> tareas = proyecto.verTareas();

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
    }
    // ============================================================
    // REASIGNACIÓN DE EMPLEADOS
    // ============================================================
    //Reasignar Empleado En Proyecto
    @Override
    public void reasignarEmpleadoEnProyecto(Integer numero, Integer legajo, String titulo) throws Exception {
        Proyecto proyecto = proyectos.get(numero);
        Tarea tarea = proyecto.seleccionarTarea(titulo);

        if(proyecto != null){
            Empleado empleado = empleados.get(legajo);
            if(tarea != null){
                if(tarea.verEmpleado() == null){ throw new Exception("No se encuentra empleado asignado anteriormente"); }
                if(empleado != null && empleado.estaDisponible()){
                    tarea.verEmpleado().modificarDisponible(true);
                    tarea.asignarEmpleado(empleado);
                    empleado.modificarDisponible(false);
                }
            }
        }
    }

    //Reasigna empleado con menor retraso
    @Override
    public void reasignarEmpleadoConMenosRetraso(Integer numero, String titulo) throws Exception {
        Empleado empleadoConMenorRetraso = null;
        boolean proyectoEncontrado = false;
        Proyecto proyecto = proyectos.get(numero);
        Tarea tarea = proyecto.seleccionarTarea(titulo);

        if(proyecto != null){
            proyectoEncontrado = true;
            if (proyecto.verEstado().equals(Estado.finalizado)) { throw new Exception("El proyecto se encuentra finalizado"); }
        }

        if (!proyectoEncontrado) {throw new Exception("Proyecto no encontrado");}

        for (Empleado empleado : empleados.values()) {
            if (empleado.estaDisponible()) {
                if (empleadoConMenorRetraso == null || empleado.mostrarCantidadRetrasos() < empleadoConMenorRetraso.mostrarCantidadRetrasos()) {
                    empleadoConMenorRetraso = empleado;
                }
            }
        }

        if (empleadoConMenorRetraso == null) { throw new Exception("No se encuentra empleado disponible"); }

        if(tarea != null){
            tarea.asignarEmpleado(empleadoConMenorRetraso);
            empleadoConMenorRetraso.modificarDisponible(false);
        }
    }

    // ============================================================
    // CONSULTAS Y REPORTES
    // ============================================================

    //Costo del Proyecto
    @Override
    public double costoProyecto(Integer numero) {
        double costoProyecto = 0;
        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            costoProyecto = proyecto.calculoCostoFinal();
        }
        return costoProyecto;
    }

    //Tupla de proyectos finalizados
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

    //Tupla de proyectos pendientes
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

    //Tupla de proyectos activos
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

    //Array de empleados nos asignados
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

    //Proyecto finalizado o no finalizado.
    @Override
    public boolean estaFinalizado(Integer numero) {
        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            if(proyecto.verEstado().equals(Estado.finalizado)){ return true; }
        }
        return false;
    }

    //Cantidad de retrasos de un empleado especifico
    @Override
    public int consultarCantidadRetrasosEmpleado(Integer legajo) {
        Empleado empleado = empleados.get(legajo);
        if(empleado != null){return empleado.mostrarCantidadRetrasos();}
        return 0;
    }

    //Tupla de empleados asignados a un proyecto
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
        return empleados;
    }

// ============================================================
    // NUEVOS REQUERIMIENTOS
    // ============================================================

    //Listado de tareas sin asignar
    @Override
    public Object[] tareasProyectoNoAsignadas(Integer numero) {
        List<Object> listaTareas = new ArrayList<>();
        List<Tarea> tareas = new ArrayList<>();

        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null) { tareas = proyecto.verTareas(); }
        if (proyecto == null) { throw new IllegalArgumentException("Proyecto inexistente"); }
        if (proyecto.estaFinalizado()) { throw new IllegalArgumentException("Proyecto finalizado"); }

        for (Tarea tarea : tareas){
            if(tarea.verEmpleado() == null){ listaTareas.add(tarea); }
        }
        return listaTareas.toArray();
    }

    //Array de tareas asignadas a un proyecto
    @Override
    public Object[] tareasDeUnProyecto(Integer numero) {
        List<Tarea> tareas = new ArrayList<>();
        Proyecto proyecto = proyectos.get(numero);

        if(proyecto != null){ tareas = proyecto.verTareas(); }
        List<Object> listaTareas = new ArrayList<>(tareas);
        return listaTareas.toArray();
    }

    //Devuelve domicilio de un proyecto en especifico
    @Override
    public String consultarDomicilioProyecto(Integer numero) {
        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            return proyecto.verDireccion();
        }
        return "";
    }

    //Devuelve si un empleado tiene retrasos o no
    @Override
    public boolean tieneRestrasos(Integer legajo) {
        Empleado empleado = empleados.get(legajo);
        if(empleado != null && empleado.estaRetrasado()){
            return true;
        }
        return false;
    }

    //Devuelve una lista de tuplas de empleados con su legajo y nombre
    @Override
    public List<Tupla<Integer, String>> empleados() {
        List<Tupla<Integer, String>> listaEmpleados = new ArrayList<>();

        for (Empleado empleado : empleados.values()) {
            listaEmpleados.add(new Tupla<>(empleado.mostrarLegajo(), empleado.mostrarNombre()));
        }
        return listaEmpleados;
    }

    //Devuelve los datos de un proyecto en especifico
    @Override
    public String consultarProyecto(Integer numero) {
        Proyecto proyecto = proyectos.get(numero);
        if(proyecto != null){
            return proyecto.toString();
        }
        return "Proyecto no encontrado";
    }
}
