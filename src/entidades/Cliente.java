package entidades;

public class Cliente implements Icliente{
    private String nombre;
    private String telefono;
    private String email;

    // Constructor con validaciones
    public Cliente(String nombre, String telefono, String email) {
        // Validar formato de teléfono y email
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    @Override
    public void modificarNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String verNombre() {
        return this.nombre;
    }

    @Override
    public void modificarTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String verTelefono() {
        return this.telefono;
    }

    @Override
    public void modificarEmail(String email) {
        this.email = email;
    }

    @Override
    public String verEmail() {
        return this.email;
    }
}
