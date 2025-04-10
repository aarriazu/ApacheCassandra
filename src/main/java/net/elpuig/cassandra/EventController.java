package net.elpuig.cassandra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.Date;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    // Maneja el formulario de registro de eventos
    @PostMapping("/submitEvent")
    public String submitEvent(@RequestParam String urlPagina, @RequestParam String dominio,
                              @RequestParam String accion, @RequestParam String elemento,
                              @RequestParam String detalles, @RequestParam Integer duracionSegundos,
                              HttpSession session, Model model) {

        // Obtener el usuario de la sesión
        UUID usuarioId = (UUID) session.getAttribute("usuarioId");

        if (usuarioId == null) {
            model.addAttribute("mensaje", "No se ha encontrado al usuario en sesión.");
            return "error"; // Puedes redirigir a una página de error si no se ha encontrado usuario en sesión
        }

        // Crear el evento
        Event event = new Event(
                usuarioId,
                new Date(),  // Fecha y hora actual
                urlPagina,
                dominio,
                accion,
                elemento,
                detalles,
                duracionSegundos
        );

        // Guardar el evento en la base de datos
        eventService.saveEvent(event);

        // Mostrar mensaje de éxito
        model.addAttribute("mensaje", "Evento registrado con éxito.");
        return "dashboard"; // Redirigir a la página de dashboard
    }
}
