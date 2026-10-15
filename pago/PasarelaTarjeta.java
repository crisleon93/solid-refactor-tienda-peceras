package pago;

public class PasarelaTarjeta implements IPasarelaPago {
    @Override
    public boolean procesar(double monto) {
        System.out.println("Conectando con pasarela de pago con tarjeta... Procesando $" + monto);
        return true;
    }

    @Override
    public String getNombre() { return "TARJETA"; }
}
