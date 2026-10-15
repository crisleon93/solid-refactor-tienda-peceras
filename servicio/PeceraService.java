package servicio;

import modelo.Pecera;
import negocio.CalculadoraPrecio;
import negocio.IEstrategiaDescuento;
import notificacion.INotificador;
import pago.IPasarelaPago;
import repositorio.IPeceraRepository;

// SRP: coordina el caso de uso, cada detalle vive en su clase.
// DIP: todas las dependencias son interfaces, inyectadas por constructor.
public class PeceraService {
    private final IPeceraRepository repository;
    private final CalculadoraPrecio calculadora;
    private final IPasarelaPago pasarela;
    private final INotificador notificador;

    public PeceraService(IPeceraRepository repository,
                         CalculadoraPrecio calculadora,
                         IPasarelaPago pasarela,
                         INotificador notificador) {
        this.repository = repository;
        this.calculadora = calculadora;
        this.pasarela = pasarela;
        this.notificador = notificador;
    }

    public void registrarPecera(Pecera pecera) {
        repository.guardar(pecera);
    }

    public double cotizar(Pecera pecera, IEstrategiaDescuento descuento) {
        return calculadora.calcularTotal(pecera, descuento);
    }

    public void vender(Pecera pecera, IEstrategiaDescuento descuento, String emailCliente) {
        double total = cotizar(pecera, descuento);
        boolean exito = pasarela.procesar(total);

        if (exito) {
            notificador.notificar(emailCliente,
                    "Su pago de $" + total + " fue exitoso.");
        } else {
            notificador.notificar(emailCliente,
                    "Hubo un problema procesando su pago de $" + total + ".");
        }
    }
}
