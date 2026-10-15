package negocio;

public class ClienteRegular implements IEstrategiaDescuento {
    @Override
    public double aplicar(double subtotalConImpuesto) {
        return subtotalConImpuesto; // sin descuento
    }

    @Override
    public String getTipoCliente() { return "REGULAR"; }
}
