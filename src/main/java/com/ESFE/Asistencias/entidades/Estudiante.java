package com.ESFE.Asistencias.entidades;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "estudiantes")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Nullable
    private String nombre;

    @Nullable
    private String email;

    @Nullable
    private String pin;

    @ManyToMany
    @JoinTable(
            name = "estudiantes_grupos",
            joinColumns = @JoinColumn(name = "estudiante_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id")
    )
    private Set<Grupo> grupos = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Nullable
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@Nullable String nombre) {
        this.nombre = nombre;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getPin() {
        return pin;
    }

    public void setPin(@Nullable String pin) {
        this.pin = pin;
    }
}
