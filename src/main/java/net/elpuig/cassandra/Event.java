package net.elpuig.cassandra;

import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Table("eventos_usuario")
public class Event {

    @Id
    private UUID usuario_id;
    private java.util.Date fecha_evento;
    private String url_pagina;
    private String dominio;
    private String accion;
    private String elemento;
    private String detalles;
    private Integer duracion_segundos;

    public Event(UUID usuario_id, java.util.Date fechaEvento, String urlPagina, String dominio, String accion,
                  String elemento, String detalles, Integer duracion_segundos) {
        this.usuario_id = usuario_id;
        this.fecha_evento = fechaEvento;
        this.url_pagina = urlPagina;
        this.dominio = dominio;
        this.accion = accion;
        this.elemento = elemento;
        this.detalles = detalles;
        this.duracion_segundos = duracion_segundos;
    }

    public UUID getUsuarioId() {
        return usuario_id;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuario_id = usuarioId;
    }

    public java.util.Date getFecha_evento() {
        return fecha_evento;
    }

    public void setFecha_evento(java.util.Date fecha_evento) {
        this.fecha_evento = fecha_evento;
    }

    public String getUrlPagina() {
        return url_pagina;
    }

    public void setUrlPagina(String urlPagina) {
        this.url_pagina = urlPagina;
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
        return duracion_segundos;
    }

    public void setDuracionSegundos(Integer duracionSegundos) {
        this.duracion_segundos = duracionSegundos;
    }
}
