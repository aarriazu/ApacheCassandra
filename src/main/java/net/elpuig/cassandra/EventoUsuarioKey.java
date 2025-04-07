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

    // Constructor vacío (requerido por Spring Data)
    public EventoUsuarioKey() {}

    // Constructor completo
    public EventoUsuarioKey(UUID usuarioId, Date fechaEvento, String accion, String elemento) {
        this.usuarioId = usuarioId;
        this.fechaEvento = fechaEvento;
        this.accion = accion;
        this.elemento = elemento;
    }

    // Getters y Setters
    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getElemento() {
        return elemento;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    // equals() y hashCode() (IMPORTANTE para operaciones en Cassandra)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventoUsuarioKey that = (EventoUsuarioKey) o;
        return Objects.equals(usuarioId, that.usuarioId) &&
                Objects.equals(fechaEvento, that.fechaEvento) &&
                Objects.equals(accion, that.accion) &&
                Objects.equals(elemento, that.elemento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, fechaEvento, accion, elemento);
    }

    // toString() útil para debugging
    @Override
    public String toString() {
        return "EventoUsuarioKey{" +
                "usuarioId=" + usuarioId +
                ", fechaEvento=" + fechaEvento +
                ", accion='" + accion + '\'' +
                ", elemento='" + elemento + '\'' +
                '}';
    }
}