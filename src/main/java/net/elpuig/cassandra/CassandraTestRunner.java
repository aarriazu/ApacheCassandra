package net.elpuig.cassandra;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CassandraTestRunner implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final EventoUsuarioRepository eventoUsuarioRepository;

    public CassandraTestRunner(UsuarioService usuarioService,
                               EventoUsuarioRepository eventoUsuarioRepository) {
        this.usuarioService = usuarioService;
        this.eventoUsuarioRepository = eventoUsuarioRepository;
    }

    @Override
    public void run(String... args) {
        try {
            System.out.println("\n=== INICIANDO PRUEBAS DE CASSANDRA ===\n");
            testUsuarios();
            testEventosUsuario();
            System.out.println("\n=== TODAS LAS PRUEBAS COMPLETADAS ===");
        } catch (Exception e) {
            System.err.println("Error en las pruebas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void testUsuarios() {
        try {
            System.out.println("\n--- INICIANDO PRUEBAS DE USUARIOS ---");

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre("UsuarioPrueba");
            nuevoUsuario.setEmail("prueba@ejemplo.com");
            nuevoUsuario.setFecha_registro(new Date());
            nuevoUsuario.setUltima_conexion(new Date());

            Usuario usuarioGuardado = usuarioService.saveUsuario(nuevoUsuario);
            System.out.println("Usuario guardado: " + usuarioGuardado.getNombre() +
                    " con ID: " + usuarioGuardado.getUsuario_id());

            System.out.println("\nTodos los usuarios:");
            usuarioService.getAllUsuarios().forEach(usuario ->
                    System.out.println(" - " + usuario.getNombre() + " (" + usuario.getEmail() + ")")
            );

            Usuario usuarioEncontrado = usuarioService.getUsuarioById(usuarioGuardado.getUsuario_id());
            System.out.println(usuarioEncontrado != null ?
                    "\nUsuario encontrado: " + usuarioEncontrado.getNombre() :
                    "\nUsuario no encontrado");

            usuarioService.deleteUsuario(usuarioGuardado.getUsuario_id());
            System.out.println("\nUsuario eliminado. Verificando...");

            if (usuarioService.getUsuarioById(usuarioGuardado.getUsuario_id()) == null) {
                System.out.println("Usuario fue eliminado correctamente");
            } else {
                System.out.println("Error: El usuario no fue eliminado");
            }
        } catch (Exception e) {
            System.err.println("Error en testUsuarios: " + e.getMessage());
            throw e;
        }
    }

    private void testEventosUsuario() {
        try {
            System.out.println("\n--- INICIANDO PRUEBAS DE EVENTOS USUARIO ---");

            // Usamos UUIDs aleatorios para evitar colisiones
            UUID usuarioId1 = UUID.randomUUID();
            UUID usuarioId2 = UUID.randomUUID();

            insertarEventosDePrueba(usuarioId1, usuarioId2);

            System.out.println("\nEventos para usuario " + usuarioId1 + ":");
            eventoUsuarioRepository.findByUsuarioId(usuarioId1).forEach(evento ->
                    System.out.printf(" - [%s] %s en %s (%d segundos)%n",
                            evento.getFechaEvento(),
                            evento.getAccion(),
                            evento.getElemento(),
                            evento.getDuracionSegundos())
            );

            Date fechaInicio = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
            Date fechaFin = new Date();

            System.out.println("\nEventos recientes para usuario " + usuarioId2 + ":");
            eventoUsuarioRepository.findByUsuarioIdAndFechaEventoBetween(
                    usuarioId2, fechaInicio, fechaFin).forEach(evento ->
                    System.out.printf(" - [%s] %s en %s%n",
                            evento.getFechaEvento(),
                            evento.getAccion(),
                            evento.getElemento())
            );

            System.out.println("\nConteo de eventos por acción:");
            eventoUsuarioRepository.findAll().stream()
                    .collect(Collectors.groupingBy(EventoUsuario::getAccion, Collectors.counting()))
                    .forEach((accion, count) -> System.out.printf(" - %s: %d eventos%n", accion, count));
        } catch (Exception e) {
            System.err.println("Error en testEventosUsuario: " + e.getMessage());
            throw e;
        }
    }

    private void insertarEventosDePrueba(UUID usuarioId1, UUID usuarioId2) {
        try {
            System.out.println("Insertando eventos de prueba...");

            // Eventos para usuario 1
            EventoUsuario evento1 = new EventoUsuario(
                    usuarioId1,
                    new Date(),
                    "https://www.miweb.com/login",
                    "miweb.com",
                    "login",
                    "boton_login",
                    "{\"navegador\":\"Chrome\"}",
                    2
            );
            eventoUsuarioRepository.save(evento1);

            EventoUsuario evento2 = new EventoUsuario(
                    usuarioId1,
                    new Date(System.currentTimeMillis() + 1000),
                    "https://www.miweb.com/productos/123",
                    "miweb.com",
                    "view",
                    "producto_123",
                    "{\"categoria\":\"electrónica\"}",
                    45
            );
            eventoUsuarioRepository.save(evento2);

            // Eventos para usuario 2
            EventoUsuario evento3 = new EventoUsuario(
                    usuarioId2,
                    new Date(),
                    "https://www.miweb.com/busqueda",
                    "miweb.com",
                    "search",
                    "campo_busqueda",
                    "{\"termino\":\"smartphone\"}",
                    8
            );
            eventoUsuarioRepository.save(evento3);

            EventoUsuario evento4 = new EventoUsuario(
                    usuarioId2,
                    new Date(System.currentTimeMillis() + 2000),
                    "https://www.miweb.com/productos/456",
                    "miweb.com",
                    "view",
                    "producto_456",
                    "{\"marca\":\"Xiaomi\"}",
                    60
            );
            eventoUsuarioRepository.save(evento4);

            System.out.println("4 eventos de prueba insertados (2 por usuario)");
        } catch (Exception e) {
            System.err.println("Error al insertar eventos: " + e.getMessage());
            throw e;
        }
    }
}