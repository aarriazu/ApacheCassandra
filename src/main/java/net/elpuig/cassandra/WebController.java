package net.elpuig.cassandra;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "web"; // Nombre de tu archivo HTML sin extensión
    }
}
