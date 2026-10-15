package negocio;

// Estrategia: cada tipo de cliente es una clase nueva (OCP)
public interface IEstrategiaDescuento {
    double aplicar(double subtotalConImpuesto);
    String getTipoCliente();
}
