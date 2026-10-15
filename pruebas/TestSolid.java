package pruebas;

import modelo.Pecera;
import negocio.*;
import notificacion.INotificador;
import notificacion.NotificadorCorreo;
import pago.IPasarelaPago;
import pago.PasarelaNequi;
import pago.PasarelaTarjeta;
import repositorio.IPeceraRepository;
import servicio.PeceraService;

// Pruebas manuales clase por clase (sin JUnit, solo verificaciones)
public class TestSolid {

    static int pruebasOk = 0;
    static int pruebasFallidas = 0;

    public static void main(String[] args) {
        // ---------- 1. MODELO ----------
        System.out.println("=== 1. modelo.Pecera ===");
        Pecera p = new Pecera("Test 50L", 100000);
        verificar("Pecera guarda tipo", p.getTipo().equals("Test 50L"));
        verificar("Pecera guarda precio", p.getPrecio() == 100000);

        // ---------- 2. NEGOCIO: CalculadoraPrecio ----------
        System.out.println("=== 2. negocio.CalculadoraPrecio ===");
        CalculadoraPrecio calc = new CalculadoraPrecio();
        verificar("IVA 19% correcto",
                calc.calcularTotal(p, new ClienteRegular()) == 119000.0);
        verificar("Descuento VIP 15% correcto",
                calc.calcularTotal(p, new ClienteVip()) == 101150.0);
        verificar("Descuento Mayorista 25% correcto",
                calc.calcularTotal(p, new ClienteMayorista()) == 89250.0);

        // ---------- 3. PAGO: pasarelas ----------
        System.out.println("=== 3. pago.Pasarelas ===");
        IPasarelaPago tarjeta = new PasarelaTarjeta();
        IPasarelaPago nequi = new PasarelaNequi();
        verificar("PasarelaTarjeta procesa", tarjeta.procesar(5000));
        verificar("PasarelaNequi procesa", nequi.procesar(5000));
        verificar("Nombres correctos",
                tarjeta.getNombre().equals("TARJETA") && nequi.getNombre().equals("NEQUI"));

        // ---------- 4. DIP EN ACCIÓN: fakes ----------
        System.out.println("=== 4. PeceraService con FAKES (demuestra DIP) ===");
        FakeRepository repoFalso = new FakeRepository();
        FakeNotificador notifFalso = new FakeNotificador();

        PeceraService servicio = new PeceraService(
                repoFalso, new CalculadoraPrecio(), new PasarelaNequi(), notifFalso);

        servicio.registrarPecera(p);
        verificar("El fake recibió el guardado (DIP)", repoFalso.guardado);

        servicio.vender(p, new ClienteVip(), "test@correo.com");
        verificar("El fake recibió la notificación (DIP)", notifFalso.notificado);

        // ---------- 5. NOTIFICADOR ----------
        System.out.println("=== 5. notificacion.NotificadorCorreo ===");
        INotificador correo = new NotificadorCorreo();
        correo.notificar("demo@correo.com", "Mensaje de prueba");
        verificar("NotificadorCorreo no lanza excepción", true);

        // ---------- RESUMEN ----------
        System.out.println("==============================");
        System.out.println("PRUEBAS OK: " + pruebasOk + " | FALLIDAS: " + pruebasFallidas);
        if (pruebasFallidas == 0) {
            System.out.println("TODAS LAS CLASES FUNCIONAN CORRECTAMENTE");
        }
    }

    static void verificar(String nombre, boolean condicion) {
        if (condicion) {
            pruebasOk++;
            System.out.println("  [OK] " + nombre);
        } else {
            pruebasFallidas++;
            System.out.println("  [FALLO] " + nombre);
        }
    }

    static class FakeRepository implements IPeceraRepository {
        boolean guardado = false;
        @Override
        public void guardar(Pecera pecera) { guardado = true; }
    }

    static class FakeNotificador implements INotificador {
        boolean notificado = false;
        @Override
        public void notificar(String destino, String mensaje) { notificado = true; }
    }
}