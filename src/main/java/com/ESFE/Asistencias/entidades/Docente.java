package com.ESFE.Asistencias.entidades;

import jakarta.annotation.Nullable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "docente")
public class Docente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Nullable
    private String nombre;

    @Nullable
    private String apellido;

    @Nullable
    private String email;

    @Nullable
    private String telefono;

    @Nullable
    private String escuela;

    @ManyToMany
    @JoinTable(
            name = "docentes_grupos",
            joinColumns = @JoinColumn(name = "docente_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id")
    )
    private Set<Grupo> grupos = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(Set<Grupo> grupos) {
        this.grupos = grupos;
    }

    @Nullable
    public String getApellido() {
        return apellido;
    }

    public void setApellido(@Nullable String apellido) {
        this.apellido = apellido;
    }

    @Nullable
    public String getEmail() {
        return email;
    }



    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(@Nullable String telefono) {
        this.telefono = telefono;
    }

    @Nullable
    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(@Nullable String escuela) {
        this.escuela = escuela;
    }
}
