package entidades;

public class Cliente implements Icliente{
    private String nombre;
    private String email;
    private String telefono;

    // Constructor con validaciones
    public Cliente(String nombre, String email, String telefono) {
        // Validar formato de teléfono y email
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
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

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        st.append("Nombre :").append(nombre).append('\n');
        st.append("Telefono :").append(telefono).append('\n');
        st.append("Email :").append(email).append('\n');
        return st.toString();
    }
}
