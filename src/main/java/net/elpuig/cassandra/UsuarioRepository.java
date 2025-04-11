package net.elpuig.cassandra;

import net.elpuig.cassandra.Usuario;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends CassandraRepository<Usuario, UUID> {

    Usuario findByNombre(String nombre);
    void deleteByNombre(String nombre);  // Requiere ALLOW FILTERING (no recomendado en producción)

    //Usuario findByNombreAndEmail(String nombre, String email);
}