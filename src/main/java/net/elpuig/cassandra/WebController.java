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
    private EventService eventService;

    @Autowired
    private CqlSession session;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        if (session.getAttribute("usuario") != null) {
            return "dashboard";
        }
        return "web";
    }

    @GetMapping("web")
    public String web(HttpSession session, Model model) {
        if (session.getAttribute("usuario") != null) {
            return "dashboard";
        }
        return "web";
    }

    @PostMapping("/submitForm")
    public String handleForm(@RequestParam String action, @RequestParam String username,
                             @RequestParam String email, @RequestParam(required = false) String password,
                             HttpSession session, Model model) {

        if ("registrarse".equals(action)) {
            Usuario usuario = new Usuario();
            usuario.setNombre(username);
            usuario.setEmail(email);
            usuario.setFecha_registro(new Date());
            usuario.setUltima_conexion(new Date());

            usuarioService.saveUsuario(usuario);
            model.addAttribute("mensaje", "Usuario registrado con éxito");

            System.out.println("Usuario creado: " + usuario);
            System.out.println("Nombre: " + usuario.getNombre() + ", Email: " + usuario.getEmail());

            session.setAttribute("usuario", usuario);  // Guardar en la sesión
            System.out.println("Usuario guardado en sesión: " + session.getAttribute("usuario"));

            return "redirect:/dashboard"; // Redirigir a la página de inicio después del registro
        } else if ("logearse".equals(action)) {
            Usuario usuario = findUsuarioByNombreAndEmail(username, email);

            //System.out.println(usuario.getNombre() + " " + usuario.getEmail());

            if (usuario != null) {
                usuarioService.actualizarUltimaConexion(usuario);

                session.setAttribute("usuario", usuario); // Guardar usuario en la sesión
                model.addAttribute("mensaje", "Inicio de sesión exitoso");

                System.out.println("Usuario guardado en sesión: " + session.getAttribute("usuario"));

                return "redirect:/dashboard";
            } else {
                model.addAttribute("mensaje", "Usuario o correo incorrecto");
                return "web";
            }
        }

        return "web";
    }



    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:web";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/specificError")
    public String specificError() {
        return "specificError";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        System.out.println("Sesión en dashboard: " + session.getAttribute("usuario"));
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        //System.out.println(usuario.getNombre());
        if (usuario == null) {
            return "redirect:web";
        }
        model.addAttribute("usuario", usuario);
        return "dashboard";
    }

    public Usuario findUsuarioByNombreAndEmail(String nombre, String email) {
        String query = "SELECT * FROM usuario WHERE nombre = ? AND email = ? ALLOW FILTERING";
        Row row = session.execute(query, nombre, email).one();

        if (row != null) {
            UUID usuarioId = row.getUuid("usuario_id");
            String nombreUsuario = row.getString("nombre");
            String emailUsuario = row.getString("email");

            Date fechaRegistroUsuario = Date.from(Objects.requireNonNull(row.getInstant("fecha_registro")));
            Date ultimaConexionUsuario = Date.from(Objects.requireNonNull(row.getInstant("ultima_conexion")));

            //System.out.println("UsuarioId: " + usuarioId + " Nombre: " + nombreUsuario + " Email: " + emailUsuario);

            return new Usuario(usuarioId, nombreUsuario, emailUsuario, fechaRegistroUsuario, ultimaConexionUsuario);
        }

        return null;
    }

    @PostMapping("/submitEvent")
    public String submitEvent(@RequestParam String urlPagina, @RequestParam String dominio,
                              @RequestParam String accion, @RequestParam String elemento,
                              @RequestParam String detalles, @RequestParam Integer duracionSegundos,
                              HttpSession session, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        UUID usuarioId = usuario.getUsuario_id();

        if (usuarioId == null) {
            model.addAttribute("mensaje", "No se ha encontrado al usuario en sesión.");
            return "specificError";
        }

        Event event = new Event(
                usuarioId,
                new Date(),
                urlPagina,
                dominio,
                accion,
                elemento,
                detalles,
                duracionSegundos
        );

        eventService.saveEvent(event);

        model.addAttribute("mensaje", "Evento registrado con éxito.");
        return "redirect:dashboard";
    }
}
