package negocio;

public class ClienteVip implements IEstrategiaDescuento {
    private static final double DESCUENTO = 0.15; // 15% de descuento VIP

    @Override
    public double aplicar(double subtotalConImpuesto) {
        return subtotalConImpuesto * (1 - DESCUENTO);
    }

    @Override
    public String getTipoCliente() { return "VIP"; }
}
