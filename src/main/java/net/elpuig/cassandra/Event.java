package net.elpuig.cassandra;

import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Table("eventos_usuario")
public class Event {

    @Id
    private UUID usuarioId;
    private java.util.Date fechaEvento;
    private String urlPagina;
    private String dominio;
    private String accion;
    private String elemento;
    private String detalles;
    private Integer duracionSegundos;

    public Event(UUID usuarioId, java.util.Date fechaEvento, String urlPagina, String dominio, String accion,
                  String elemento, String detalles, Integer duracionSegundos) {
        this.usuarioId = usuarioId;
        this.fechaEvento = fechaEvento;
        this.urlPagina = urlPagina;
        this.dominio = dominio;
        this.accion = accion;
        this.elemento = elemento;
        this.detalles = detalles;
        this.duracionSegundos = duracionSegundos;
    }

    // Getters y setters

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public java.util.Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(java.util.Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getUrlPagina() {
        return urlPagina;
    }

    public void setUrlPagina(String urlPagina) {
        this.urlPagina = urlPagina;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
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

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Integer getDuracionSegundos() {
        return duracionSegundos;
    }

    public void setDuracionSegundos(Integer duracionSegundos) {
        this.duracionSegundos = duracionSegundos;
    }
}
