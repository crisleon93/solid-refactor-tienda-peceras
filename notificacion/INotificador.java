package notificacion;

// Abstracción para notificaciones: hoy correo, mañana SMS o WhatsApp (OCP)
public interface INotificador {
    void notificar(String destino, String mensaje);
}
