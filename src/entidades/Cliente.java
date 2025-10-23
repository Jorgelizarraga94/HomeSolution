package entidades;

public class Cliente {
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
}
