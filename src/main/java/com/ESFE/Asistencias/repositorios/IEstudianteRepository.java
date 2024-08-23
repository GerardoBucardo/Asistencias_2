package com.ESFE.Asistencias.repositorios;

import com.ESFE.Asistencias.entidades.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstudianteRepository extends JpaRepository<Estudiante,Integer> {
}
