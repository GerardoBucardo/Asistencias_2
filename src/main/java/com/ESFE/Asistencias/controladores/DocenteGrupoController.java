package com.ESFE.Asistencias.controladores;

import com.ESFE.Asistencias.entidades.Docente;
import com.ESFE.Asistencias.entidades.DocenteGrupo;
import com.ESFE.Asistencias.entidades.Grupo;
import com.ESFE.Asistencias.servicios.interfaces.IDocenteGrupoServices;
import com.ESFE.Asistencias.servicios.interfaces.IDocenteServices;
import com.ESFE.Asistencias.servicios.interfaces.IGrupoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/docenteGrupos")
public class DocenteGrupoController {
    @Autowired
    private IDocenteGrupoServices docenteGrupoServices;
    @Autowired
    private IDocenteServices docenteServices;
    @Autowired
    private IGrupoServices grupoServices;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(10);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<DocenteGrupo> docenteGrupos = docenteGrupoServices.BuscarTodosPaginados(pageable);
        model.addAttribute("docenteGrupos", docenteGrupos);

        int totalPage = docenteGrupos.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumber = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumber", pageNumber);
        }
        return "docenteGrupo/index";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("docenteGrupo", new DocenteGrupo());
        model.addAttribute("docentes", docenteServices.ObtenerTodos());
        model.addAttribute("grupos", grupoServices.ObtenerTodos());
        return "docenteGrupo/create";
    }

    @PostMapping("/save")
    public String save(@RequestParam Integer docenteId,
                       @RequestParam Integer grupoId,
                       @RequestParam Integer anio,
                       @RequestParam String ciclo,
                       RedirectAttributes attributes){
        Docente docente = docenteServices.BuscarPorId(docenteId).get();
        Grupo grupo = grupoServices.BuscarPorId(grupoId).get();

        if(docente != null && grupo != null){
            DocenteGrupo docenteGrupo = new DocenteGrupo();
            docenteGrupo.setDocente(docente);
            docenteGrupo.setGrupo(grupo);
            docenteGrupo.setAnio(anio);
            docenteGrupo.setCiclo(ciclo);
            docenteGrupoServices.CrearOeditar(docenteGrupo);
            attributes.addFlashAttribute("msg", "DocenteGrupo creado");

        }
        return "redirect:/docenteGrupos";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Optional<DocenteGrupo> docenteGrupoOpt = docenteGrupoServices.BuscarPorId(id);
        if (docenteGrupoOpt.isPresent()){
            DocenteGrupo docenteGrupo = docenteGrupoOpt.get();
            model.addAttribute("docenteGrupo", docenteGrupo);
            return "docenteGrupo/details";
        } else {
            return "docenteGrupo/not_found";
        }

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Optional<DocenteGrupo> docenteGrupoOpt = docenteGrupoServices.BuscarPorId(id);
        if (docenteGrupoOpt.isPresent()) {
            DocenteGrupo docenteGrupo = docenteGrupoOpt.get();
            model.addAttribute("docenteGrupo", docenteGrupo);
            model.addAttribute("docentes", docenteServices.ObtenerTodos());
            model.addAttribute("grupos", grupoServices.ObtenerTodos());
            return "docenteGrupo/edit";
        } else {
            return "docenteGrupo/not_found"; // Manejo de caso donde el docenteGrupo no es encontrado.
        }
    }


    @PostMapping("/update")
    public String update(@RequestParam Integer id,
                         @RequestParam Integer docenteId,
                         @RequestParam Integer grupoId,
                         @RequestParam Integer anio,
                         @RequestParam String ciclo,
                         RedirectAttributes attributes){
        Docente docente = docenteServices.BuscarPorId(docenteId).get();
        Grupo grupo = grupoServices.BuscarPorId(grupoId).get();

        if(docente != null && grupo != null){
            DocenteGrupo docenteGrupo = new DocenteGrupo();
            docenteGrupo.setId(id);
            docenteGrupo.setDocente(docente);
            docenteGrupo.setGrupo(grupo);
            docenteGrupo.setAnio(anio);
            docenteGrupo.setCiclo(ciclo);

            docenteGrupoServices.CrearOeditar(docenteGrupo);
            attributes.addFlashAttribute("msg", "asignación modificado");

        }
        return "redirect:/docenteGrupos";
    }
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        DocenteGrupo docenteGrupo = docenteGrupoServices.BuscarPorId(id).get();
        model.addAttribute("docenteGrupo", docenteGrupo);
        return "docenteGrupo/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute DocenteGrupo docenteGrupo, RedirectAttributes attributes){
        docenteGrupoServices.EliminarPorId(docenteGrupo.getId());
        attributes.addFlashAttribute("msg", "Docente eliminado correctamente");
        return "redirect:/docenteGrupos";
    }
}
