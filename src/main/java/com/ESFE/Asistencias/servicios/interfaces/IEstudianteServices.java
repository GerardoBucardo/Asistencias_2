package com.ESFE.Asistencias.servicios.interfaces;

import com.ESFE.Asistencias.entidades.Estudiante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IEstudianteServices {
    Page<Estudiante> BuscarTodosPaginados(Pageable pageable);
    List<Estudiante> ObtenerTodos();
    Optional<Estudiante> BuscarPorId(Integer id);
    Estudiante CrearOeditar(Estudiante estudiante);
    void EliminarPorId(Integer id);
}
