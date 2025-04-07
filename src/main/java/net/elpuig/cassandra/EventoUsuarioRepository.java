package net.elpuig.cassandra;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventoUsuarioRepository extends CassandraRepository<EventoUsuario, EventoUsuarioKey> {

    @Query("SELECT * FROM eventos_usuario WHERE usuario_id = ?0 ALLOW FILTERING")
    List<EventoUsuario> findByUsuarioId(UUID usuarioId);

    @Query("SELECT * FROM eventos_usuario WHERE usuario_id = ?0 AND fecha_evento >= ?1 AND fecha_evento <= ?2 ALLOW FILTERING")
    List<EventoUsuario> findByUsuarioIdAndFechaEventoBetween(UUID usuarioId, Date inicio, Date fin);
}