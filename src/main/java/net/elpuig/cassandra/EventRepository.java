package net.elpuig.cassandra;

import org.springframework.data.cassandra.repository.CassandraRepository;
import java.util.UUID;

public interface EventRepository extends CassandraRepository<Event, UUID> {
    // Puedes agregar métodos personalizados de consulta si es necesario
}
