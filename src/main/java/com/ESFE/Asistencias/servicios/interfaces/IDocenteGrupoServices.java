package com.ESFE.Asistencias.servicios.interfaces;

import com.ESFE.Asistencias.entidades.Docente;
import com.ESFE.Asistencias.entidades.DocenteGrupo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface IDocenteGrupoServices {
    Page<DocenteGrupo> BuscarTodosPaginados(Pageable pageable);
    List<DocenteGrupo> ObtenerTodos();
    Optional<DocenteGrupo> BuscarPorId(Integer id);
    DocenteGrupo CrearOeditar(DocenteGrupo docenteGrupo);
    void EliminarPorId(Integer id);

}
