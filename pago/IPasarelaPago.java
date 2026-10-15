package pago;

// Abstracción de pasarelas: agregar PSE o Daviplata NO modifica código existente (OCP)
public interface IPasarelaPago {
    boolean procesar(double monto);
    String getNombre();
}
