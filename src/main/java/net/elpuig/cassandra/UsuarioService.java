package net.elpuig.cassandra;

import net.elpuig.cassandra.Usuario;
import net.elpuig.cassandra.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear o actualizar usuario
    public Usuario saveUsuario(Usuario usuario) {
        if (usuario.getUsuario_id() == null) {
            usuario.setUsuario_id(UUID.randomUUID());  // Genera un UUID si es nuevo
        }
        return usuarioRepository.save(usuario);
    }

    // Obtener todos los usuarios
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener usuario por ID
    public Usuario getUsuarioById(UUID id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Eliminar usuario por ID
    public void deleteUsuario(UUID id) {
        usuarioRepository.deleteById(id);
    }
}