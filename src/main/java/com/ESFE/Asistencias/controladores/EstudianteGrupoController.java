package com.ESFE.Asistencias.controladores;

import com.ESFE.Asistencias.entidades.*;
import com.ESFE.Asistencias.servicios.interfaces.*;
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
@RequestMapping("/estudianteGrupos")
public class EstudianteGrupoController {
    @Autowired
    private IEstudianteGrupoServices estudianteGrupoServices;
    @Autowired
    private IEstudianteServices estudianteServices;
    @Autowired
    private IGrupoServices grupoServices;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(10);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<EstudianteGrupo> estudianteGrupos = estudianteGrupoServices.BuscarTodosPaginados(pageable);
        model.addAttribute("estudianteGrupos", estudianteGrupos);

        int totalPage = estudianteGrupos.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumber = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumber", pageNumber);
        }
        return "estudianteGrupo/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("estudianteGrupo", new DocenteGrupo());
        model.addAttribute("estudiantes", estudianteServices.ObtenerTodos());
        model.addAttribute("grupos", grupoServices.ObtenerTodos());
        return "estudianteGrupo/create";
    }

    @PostMapping("/save")
    public String save(@RequestParam Integer estudianteId,
                       @RequestParam Integer grupoId,
                       RedirectAttributes attributes){
        Estudiante estudiante = estudianteServices.BuscarPorId(estudianteId).get();
        Grupo grupo = grupoServices.BuscarPorId(grupoId).get();

        if(estudiante != null && grupo != null){
            EstudianteGrupo estudianteGrupo = new EstudianteGrupo();
            estudianteGrupo.setEstudiante(estudiante);
            estudianteGrupo.setGrupo(grupo);
            estudianteGrupoServices.CrearOeditar(estudianteGrupo);
            attributes.addFlashAttribute("msg", "EstudianteGrupo creado");
        }
        return "redirect:/estudianteGrupos";
    }

//    @PostMapping("/save")
//    public String save(@ModelAttribute EstudianteGrupo estudianteGrupo,
//                       RedirectAttributes attributes) {
//        // Se asume que estudianteGrupo ya tiene estudiante y grupo asignados
//        estudianteGrupoServices.CrearOeditar(estudianteGrupo);
//        attributes.addFlashAttribute("msg", "EstudianteGrupo creado");
//        return "redirect:/estudianteGrupos";
//    }


    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Optional<EstudianteGrupo> estudianteGrupoOpt = estudianteGrupoServices.BuscarPorId(id);
        if (estudianteGrupoOpt.isPresent()) {
            EstudianteGrupo estudianteGrupo = estudianteGrupoOpt.get();
            model.addAttribute("estudianteGrupo", estudianteGrupo);
            return "estudianteGrupo/details";
        } else {
            return "estudianteGrupo/not_found";
        }

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Optional<EstudianteGrupo> estudianteGrupoOpt = estudianteGrupoServices.BuscarPorId(id);
        if (estudianteGrupoOpt.isPresent()) {
            EstudianteGrupo estudianteGrupo = estudianteGrupoOpt.get();
            model.addAttribute("estudianteGrupo", estudianteGrupo);
            model.addAttribute("estudiantes", estudianteServices.ObtenerTodos());
            model.addAttribute("grupos", grupoServices.ObtenerTodos());
            return "estudianteGrupo/edit";
        } else {
            return "estudianteGrupo/not_found"; // Manejo de caso donde el estudianteGrupo no es encontrado.
        }
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer id,
                         @RequestParam Integer estudianteId,
                         @RequestParam Integer grupoId,
                         RedirectAttributes attributes){
        Estudiante estudiante = estudianteServices.BuscarPorId(estudianteId).get();
        Grupo grupo = grupoServices.BuscarPorId(grupoId).get();

        if(estudiante != null && grupo != null){
            EstudianteGrupo estudianteGrupo = new EstudianteGrupo();
            estudianteGrupo.setId(id);
            estudianteGrupo.setEstudiante(estudiante);
            estudianteGrupo.setGrupo(grupo);

            estudianteGrupoServices.CrearOeditar(estudianteGrupo);
            attributes.addFlashAttribute("msg", "asignación modificado");
        }
        return "redirect:/estudianteGrupos";
}

//    @PostMapping("/update")
//    public String update(@ModelAttribute EstudianteGrupo estudianteGrupo,
//                         RedirectAttributes attributes){
//        // El objeto estudianteGrupo debería tener todos los datos necesarios
//        estudianteGrupoServices.CrearOeditar(estudianteGrupo);
//        attributes.addFlashAttribute("msg", "Asignación modificada");
//        return "redirect:/estudianteGrupos";
//    }


    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        EstudianteGrupo estudianteGrupo = estudianteGrupoServices.BuscarPorId(id).get();
        model.addAttribute("estudianteGrupo", estudianteGrupo);
        return "estudianteGrupo/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute EstudianteGrupo estudianteGrupo, RedirectAttributes attributes){
        estudianteGrupoServices.EliminarPorId(estudianteGrupo.getId());
        attributes.addFlashAttribute("msg", "Estudiante eliminado correctamente");
        return "redirect:/estudianteGrupos";
    }
}
