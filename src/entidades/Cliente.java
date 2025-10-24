package entidades;

public class Cliente implements Icliente{
    String nombre;
    String telefono;
    String email;

    // Constructor con validaciones
    public Cliente(String nombre, String telefono, String email) {
        // Validar formato de teléfono y email
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    @Override
    public void modificarNombre(String nombre) {

    }

    @Override
    public String verNombre() {
        return "";
    }

    @Override
    public void modificarTelefono(String telefono) {

    }

    @Override
    public String verTelefono() {
        return "";
    }

    @Override
    public void modificarEmail(String email) {

    }

    @Override
    public String verEmail() {
        return "";
    }
}
