package net.elpuig.cassandra;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@PrimaryKeyClass
public class EventoUsuarioKey {

    @PrimaryKeyColumn(name = "usuario_id", type = PrimaryKeyType.PARTITIONED)
    private UUID usuarioId;

    @PrimaryKeyColumn(name = "fecha_evento", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Date fechaEvento;

    @PrimaryKeyColumn(name = "accion", type = PrimaryKeyType.CLUSTERED)
    private String accion;

    @PrimaryKeyColumn(name = "elemento", type = PrimaryKeyType.CLUSTERED)
    private String elemento;

    // Constructores, equals, hashCode, getters y setters
    // ...
}