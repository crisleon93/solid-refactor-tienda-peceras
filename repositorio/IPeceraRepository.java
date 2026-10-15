package repositorio;

import modelo.Pecera;

// Abstracción para el almacenamiento (DIP: el servicio depende de esto, no de MySQL)
public interface IPeceraRepository {
    void guardar(Pecera pecera);
}
