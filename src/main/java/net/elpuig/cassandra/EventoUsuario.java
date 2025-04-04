package net.elpuig.cassandra;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("eventos_usuario")
public class EventoUsuario {

    @PrimaryKey
    private EventoUsuarioKey key;

    private String urlPagina;
    private String dominio;
    private String detalles;
    private Integer duracionSegundos;

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
}