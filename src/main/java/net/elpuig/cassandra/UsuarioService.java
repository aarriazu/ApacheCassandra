package net.elpuig.cassandra;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import net.elpuig.cassandra.Usuario;
import net.elpuig.cassandra.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CqlSession session;

    public Usuario saveUsuario(Usuario usuario) {
        if (usuario.getUsuario_id() == null) {
            usuario.setUsuario_id(UUID.randomUUID());  // Genera un UUID si es nuevo
        }
        return usuarioRepository.save(usuario);
    }

    public void actualizarUltimaConexion(Usuario usuario) {
        usuario.setUltima_conexion(new Date());
        usuarioRepository.save(usuario);
    }

    public Usuario findUsuarioByNombreAndEmail(String nombre, String email) {
        String query = "SELECT * FROM usuarios WHERE nombre = ? AND email = ? ALLOW FILTERING";

        ResultSet resultSet = session.execute(query, nombre, email);

        Row row = resultSet.one();
        if (row != null) {
            return new Usuario(row.getUuid("usuario_id"), row.getString("nombre"), row.getString("email"), Date.from(row.getInstant("fecha_registro")), Date.from(row.getInstant("ultima_conexion")));
        }
        return null;
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(UUID id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void deleteUsuario(UUID id) {
        usuarioRepository.deleteById(id);
    }
}