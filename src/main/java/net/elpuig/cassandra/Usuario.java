package net.elpuig.cassandra;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;
import java.util.Date;

@Table("usuario")
public class Usuario implements Serializable {

    @PrimaryKey
    private UUID usuario_id;
    private String nombre;
    private String email;
    private Date fecha_registro;
    private Date ultima_conexion;

    public Usuario() {}

    public Usuario(UUID usuario_id, String nombre, String email, Date fecha_registro, Date ultima_conexion) {
        this.usuario_id = usuario_id;
        this.nombre = nombre;
        this.email = email;
        this.fecha_registro = fecha_registro;
        this.ultima_conexion = ultima_conexion;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + usuario_id + ", nombre=" + nombre + ", email=" + email + ", fecha registro=" + fecha_registro + ", ultima conexion=" + ultima_conexion + "]";
    }

    public UUID getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(UUID usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Date getUltima_conexion() {
        return ultima_conexion;
    }

    public void setUltima_conexion(Date ultima_conexion) {
        this.ultima_conexion = ultima_conexion;
    }
}