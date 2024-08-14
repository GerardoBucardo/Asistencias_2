package com.ESFE.Asistencias.repositorios;

import com.ESFE.Asistencias.entidades.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDocenteRepository extends JpaRepository<Docente,Integer> {
}
