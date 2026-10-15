package negocio;

import modelo.Pecera;

// SRP: única responsabilidad = calcular precios. Recibe la estrategia por DI (DIP)
public class CalculadoraPrecio {
    private static final double IVA = 0.19;

    public double calcularTotal(Pecera pecera, IEstrategiaDescuento descuento) {
        double subtotalConImpuesto = pecera.getPrecio() * (1 + IVA);
        return descuento.aplicar(subtotalConImpuesto);
    }
}
