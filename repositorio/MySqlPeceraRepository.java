package repositorio;

import modelo.Pecera;

// Implementación concreta de bajo nivel: detalle de infraestructura
public class MySqlPeceraRepository implements IPeceraRepository {
    @Override
    public void guardar(Pecera pecera) {
        System.out.println("[DB MySQL] INSERT INTO peceras (tipo, precio) VALUES ('"
                + pecera.getTipo() + "', " + pecera.getPrecio() + ")");
    }
}
