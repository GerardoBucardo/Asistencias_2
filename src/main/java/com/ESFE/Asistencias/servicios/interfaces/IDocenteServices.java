package com.ESFE.Asistencias.servicios.interfaces;

import com.ESFE.Asistencias.entidades.Docente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IDocenteServices {
    Page<Docente> BuscarTodosPaginados(Pageable pageable);
    List<Docente> ObtenerTodos();
    Optional<Docente> BuscarPorId(Integer id);
    Docente CrearOeditar(Docente docente);
    void EliminarPorId(Integer id);
}
