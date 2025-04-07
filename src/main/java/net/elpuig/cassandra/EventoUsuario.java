package net.elpuig.cassandra;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.util.Date;
import java.util.UUID;

@Table("eventos_usuario")
public class EventoUsuario {

    @PrimaryKey
    private EventoUsuarioKey key;

    @Column("url_pagina")  // Coincide con la columna en Cassandra
    private String urlPagina;

    @Column("dominio")
    private String dominio;

    @Column("detalles")
    private String detalles;

    @Column("duracion_segundos")  // Coincide con la columna en Cassandra
    private Integer duracionSegundos;

    // Constructores
    public EventoUsuario() {}

    public EventoUsuario(UUID usuarioId, Date fechaEvento, String urlPagina, String dominio,
                         String accion, String elemento, String detalles, Integer duracionSegundos) {
        this.key = new EventoUsuarioKey(usuarioId, fechaEvento, accion, elemento);
        this.urlPagina = urlPagina;
        this.dominio = dominio;
        this.detalles = detalles;
        this.duracionSegundos = duracionSegundos;
    }

    // Getters y Setters
    public EventoUsuarioKey getKey() {
        return key;
    }

    public void setKey(EventoUsuarioKey key) {
        this.key = key;
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

    // Métodos de conveniencia
    public UUID getUsuarioId() {
        return this.key != null ? this.key.getUsuarioId() : null;
    }

    public Date getFechaEvento() {
        return this.key != null ? this.key.getFechaEvento() : null;
    }

    public String getAccion() {
        return this.key != null ? this.key.getAccion() : null;
    }

    public String getElemento() {
        return this.key != null ? this.key.getElemento() : null;
    }
}