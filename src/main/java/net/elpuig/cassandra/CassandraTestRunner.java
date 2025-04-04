package net.elpuig.cassandra;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.UUID;

@Component
public class CassandraTestRunner implements CommandLineRunner {

    private final UsuarioService usuarioService;

    public CassandraTestRunner(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n=== INICIANDO PRUEBAS DE CASSANDRA ===\n");

        // Crear un nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("UsuarioPrueba");
        nuevoUsuario.setEmail("prueba@ejemplo.com");
        nuevoUsuario.setFecha_registro(new Date());
        nuevoUsuario.setUltima_conexion(new Date());

        Usuario usuarioGuardado = usuarioService.saveUsuario(nuevoUsuario);
        System.out.println("Usuario guardado: " + usuarioGuardado.getNombre() + " con ID: " + usuarioGuardado.getUsuario_id());

        // Obtener todos los usuarios
        System.out.println("\nTodos los usuarios:");
        usuarioService.getAllUsuarios().forEach(usuario ->
                System.out.println(" - " + usuario.getNombre() + " (" + usuario.getEmail() + ")")
        );

        // Buscar usuario por nombre (usando el repositorio directamente)
        System.out.println("\nBuscando usuario por nombre:");
        Usuario usuarioEncontrado = usuarioService.getUsuarioById(usuarioGuardado.getUsuario_id());
        if (usuarioEncontrado != null) {
            System.out.println("Usuario encontrado: " + usuarioEncontrado.getNombre());
        } else {
            System.out.println("Usuario no encontrado");
        }

        // Eliminar el usuario de prueba
        usuarioService.deleteUsuario(usuarioGuardado.getUsuario_id());
        System.out.println("\nUsuario eliminado. Verificando...");

        // Verificar que se eliminó
        Usuario usuarioEliminado = usuarioService.getUsuarioById(usuarioGuardado.getUsuario_id());
        if (usuarioEliminado == null) {
            System.out.println("Usuario fue eliminado correctamente");
        } else {
            System.out.println("Error: El usuario no fue eliminado");
        }

        System.out.println("\n=== PRUEBAS COMPLETADAS ===");
    }
}