package net.elpuig.cassandra;

import com.datastax.oss.driver.api.core.cql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.datastax.oss.driver.api.core.CqlSession;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Controller
public class WebController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CqlSession session;

    // Mostrar el formulario
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        // Si ya hay un usuario en la sesión, redirigir al dashboard
        if (session.getAttribute("usuario") != null) {
            return "dashboard"; // Página de inicio tras login
        }
        return "web"; // Página principal con los formularios
    }

    // Manejar tanto el registro como el login
    @PostMapping("/submitForm")
    public String handleForm(@RequestParam String action, @RequestParam String username,
                             @RequestParam String email, @RequestParam(required = false) String password,
                             HttpSession session, Model model) {

        if ("registrarse".equals(action)) {
            // Acción de registro
            Usuario usuario = new Usuario();
            usuario.setNombre(username);
            usuario.setEmail(email);
            usuario.setFecha_registro(new Date());
            usuario.setUltima_conexion(new Date());

            // Guardamos al nuevo usuario
            usuarioService.saveUsuario(usuario);
            model.addAttribute("mensaje", "Usuario registrado con éxito");

            // Depuración: Verificar si el usuario se ha creado correctamente
            System.out.println("Usuario creado: " + usuario);
            System.out.println("Nombre: " + usuario.getNombre() + ", Email: " + usuario.getEmail());

            // Iniciamos sesión para el usuario registrado
            session.setAttribute("usuario", usuario);  // Guardar en la sesión
            System.out.println("Usuario guardado en sesión: " + session.getAttribute("usuario"));

            return "redirect:/dashboard"; // Redirigir a la página de inicio después del registro
        } else if ("logearse".equals(action)) {
            // Acción de login
            Usuario usuario = findUsuarioByNombreAndEmail(username, email);

            System.out.println(usuario.getNombre() + " " + usuario.getEmail());

            if (usuario != null) {
                session.setAttribute("usuario", usuario); // Guardar usuario en la sesión
                model.addAttribute("mensaje", "Inicio de sesión exitoso");

                // Depuración: Verificar si el usuario se ha guardado en la sesión
                System.out.println("Usuario guardado en sesión: " + session.getAttribute("usuario"));

                return "redirect:/dashboard"; // Redirigir al dashboard después de login exitoso
            } else {
                model.addAttribute("mensaje", "Usuario o correo incorrecto");
                return "web"; // Volver al formulario si hay error
            }
        }

        return "web"; // En caso de acción no válida
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida la sesión para logout
        return "web"; // Redirige a la página de inicio tras cerrar sesión
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // Verificar si el usuario está logueado
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "web"; // Redirigir a la página de inicio si no está logueado
        }
        model.addAttribute("usuario", usuario);
        return "dashboard"; // Página de bienvenida o dashboard
    }

    public Usuario findUsuarioByNombreAndEmail(String nombre, String email) {
        // Usamos ALLOW FILTERING para permitir el filtrado
        String query = "SELECT * FROM usuario WHERE nombre = ? AND email = ? ALLOW FILTERING";
        Row row = session.execute(query, nombre, email).one();

        // Verificar si la fila no es nula
        if (row != null) {
            // Extraer los valores de la fila y convertirlos en un objeto Usuario
            UUID usuarioId = row.getUuid("usuario_id");  // Obtenemos el UUID
            String nombreUsuario = row.getString("nombre");  // Obtenemos el nombre
            String emailUsuario = row.getString("email");  // Obtenemos el email

            // Obtener la fecha de registro (si existe)
            Date fechaRegistroUsuario = Date.from(Objects.requireNonNull(row.getInstant("fecha_registro")));
            Date ultimaConexionUsuario = Date.from(Objects.requireNonNull(row.getInstant("ultima_conexion")));

            //System.out.println("UsuarioId: " + usuarioId + " Nombre: " + nombreUsuario + " Email: " + emailUsuario);

            // Crear el objeto Usuario
            return new Usuario(usuarioId, nombreUsuario, emailUsuario, fechaRegistroUsuario, ultimaConexionUsuario);
        }

        // Si la fila es nula, significa que no se encontró el usuario
        return null;
    }


}
