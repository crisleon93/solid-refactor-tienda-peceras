# DIAGNÓSTICO — Violaciones SOLID en TiendaPecerasLegacy.java

| # | Principio | Método(s) afectado(s) | Violación | Consecuencia |
|---|-----------|----------------------|-----------|--------------|
| 1 | **SRP** — Responsabilidad Única | Toda la clase | Una sola clase gestiona inventario, calcula impuestos/descuentos, procesa pagos y envía notificaciones. Tiene 3 razones para cambiar. | Cambios en BD, fiscalidad o pasarelas de pago obligan a tocar la misma clase; alto riesgo de romper funcionalidades no relacionadas (igual que el escenario de Nequi de la guía). |
| 2 | **OCP** — Abierto/Cerrado | calcularTotalConImpuesto | if/else con tipos de cliente hardcodeados ("VIP", "MAYORISTA"). Agregar un tipo nuevo exige MODIFICAR el método. | Cada nuevo requisito de negocio rompe código probado; lógica de descuento mezclada con la de impuesto. |
| 3 | **OCP** — Abierto/Cerrado | procesarPagoYEnviarNotificacion | if/else con métodos de pago hardcodeados ("TARJETA", "NEQUI"); además mezcla pago y notificación en un solo método. | Agregar "PSE" o "Daviplata" requiere modificar el método y vuelve a probar notificaciones que no cambiaron. |
| 4 | **DIP** — Inversión de Dependencias | agregarPecera | Depende directamente de la infraestructura concreta (System.out simulando INSERT en MySQL). El servicio de alto nivel depende de un detalle de bajo nivel. | No se puede cambiar a PostgreSQL/MongoDB ni mockear la BD para pruebas unitarias sin editar la clase. |

## Evidencias adicionales (olores de código)
- Impuesto IVA (0.19) como "número mágico" dentro del método → debería ser constante o servicio configurable.
- Concatenación de Strings para construir SQL → riesgo de inyección SQL y acoplamiento a sintaxis MySQL.
- Parámetros tipo String usados como "tipos" (tipoPecera, tipoCliente, metodoPago) → sin tipado seguro, dependen del valor exacto del texto.
