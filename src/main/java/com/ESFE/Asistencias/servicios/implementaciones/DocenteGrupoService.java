package com.ESFE.Asistencias.servicios.implementaciones;

import com.ESFE.Asistencias.entidades.DocenteGrupo;
import com.ESFE.Asistencias.repositorios.IDocenteGrupoRepository;
import com.ESFE.Asistencias.repositorios.IDocenteRepository;
import com.ESFE.Asistencias.servicios.interfaces.IDocenteGrupoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class DocenteGrupoService implements IDocenteGrupoServices {
    @Autowired
    private IDocenteGrupoRepository docenteGrupoRepository;

    @Override
    public Page<DocenteGrupo> BuscarTodosPaginados(Pageable pageable) {
        return docenteGrupoRepository.findByOrderByTeacherDesc(pageable);
    }

    @Override
    public List<DocenteGrupo> ObtenerTodos() {
        return docenteGrupoRepository.findAll();
    }

    @Override
    public Optional<DocenteGrupo> BuscarPorId(Integer id) {
        return docenteGrupoRepository.findById(id);
    }

    @Override
    public DocenteGrupo CrearOeditar(DocenteGrupo docenteGrupo) {
        return docenteGrupoRepository.save(docenteGrupo);
    }

    @Override
    public void EliminarPorId(Integer id) {
        docenteGrupoRepository.deleteById(id);
    }
}
