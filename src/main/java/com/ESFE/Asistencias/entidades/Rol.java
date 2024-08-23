package com.ESFE.Asistencias.entidades;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Nullable
    private String nombre;

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
}
