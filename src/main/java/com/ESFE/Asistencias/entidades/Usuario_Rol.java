package com.ESFE.Asistencias.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario_rol")
public class Usuario_Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRoles() {
        return roles;
    }

    public void setRoles(Rol roles) {
        this.roles = roles;
    }
}
