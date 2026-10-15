package app;

import modelo.Pecera;
import negocio.*;
import notificacion.NotificadorCorreo;
import pago.PasarelaNequi;
import pago.PasarelaTarjeta;
import repositorio.MySqlPeceraRepository;
import servicio.PeceraService;

// Composición raíz: aquí se decide qué implementaciones concretas usar (DI manual)
public class Main {
    public static void main(String[] args) {
        PeceraService servicioTarjeta = new PeceraService(
                new MySqlPeceraRepository(),   // repository (DIP)
                new CalculadoraPrecio(),       // lógica de precios
                new PasarelaTarjeta(),         // pasarela de tarjeta
                new NotificadorCorreo()        // notificador
        );

        PeceraService servicioNequi = new PeceraService(
                new MySqlPeceraRepository(),
                new CalculadoraPrecio(),
                new PasarelaNequi(),           // OCP: misma interfaz, otra implementación
                new NotificadorCorreo()
        );

        Pecera pecera = new Pecera("Pecera Marina 100L", 350000);

        // 1. Registrar en inventario
        servicioTarjeta.registrarPecera(pecera);

        // 2. Cotizar con distintos tipos de cliente (OCP: cada tipo es una clase)
        IEstrategiaDescuento cliente = new ClienteVip();
        double total = servicioTarjeta.cotizar(pecera, cliente);
        System.out.println("Total para " + cliente.getTipoCliente() + ": $" + total);

        // 3. Vender pagando con TARJETA (cliente mayorista)
        servicioTarjeta.vender(pecera, new ClienteMayorista(), "cliente@correo.com");

        // 4. Vender la misma pecera pagando con NEQUI (cliente regular)
        //    El servicio NO cambió: solo se inyectó otra pasarela (OCP + DIP)
        servicioNequi.vender(pecera, new ClienteRegular(), "otrocliente@correo.com");
    }
}