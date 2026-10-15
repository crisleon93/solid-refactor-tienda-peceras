package negocio;

public class ClienteMayorista implements IEstrategiaDescuento {
    private static final double DESCUENTO = 0.25; // 25% de descuento Mayorista

    @Override
    public double aplicar(double subtotalConImpuesto) {
        return subtotalConImpuesto * (1 - DESCUENTO);
    }

    @Override
    public String getTipoCliente() { return "MAYORISTA"; }
}
