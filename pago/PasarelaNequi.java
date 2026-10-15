package pago;

public class PasarelaNequi implements IPasarelaPago {
    @Override
    public boolean procesar(double monto) {
        System.out.println("Conectando con API de Nequi... Procesando $" + monto);
        return true;
    }

    @Override
    public String getNombre() { return "NEQUI"; }
}
