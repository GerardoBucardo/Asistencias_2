package com.ESFE.Asistencias.controladores;

import com.ESFE.Asistencias.entidades.Grupo;
import com.ESFE.Asistencias.servicios.interfaces.IGrupoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private IGrupoServices grupoServices;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(10);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Grupo> grupos = grupoServices.BuscarTodosPaginados(pageable);
        model.addAttribute("grupos", grupos);

        int totalPage = grupos.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumber = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumber", pageNumber);
        }
        return "grupo/index"; // Asegúrate de que la vista existe en src/main/resources/templates/grupo/index.html
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("grupo", new Grupo());
        return "grupo/create"; // Asegúrate de que la vista existe en src/main/resources/templates/grupos/create.html
    }

    @PostMapping("/save")
    public String save(Grupo grupo, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("grupo", grupo); // Añadir el grupo al modelo en caso de error
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "grupo/create"; // Redirige a la vista de creación
        }

        grupoServices.CrearOeditar(grupo);
        attributes.addFlashAttribute("msg", "Grupo creado correctamente");
        return "redirect:/grupos";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Optional<Grupo> grupoOpt = grupoServices.BuscarPorId(id);
        if (grupoOpt.isPresent()){
            Grupo grupo = grupoOpt.get();
            model.addAttribute("grupo", grupo);
            return "grupo/details";
        } else {
            return "grupo/not_found";
        }

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Grupo grupo = grupoServices.BuscarPorId(id).get();
        model.addAttribute("grupo", grupo);
        return "grupo/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        Grupo grupo = grupoServices.BuscarPorId(id).get();
        model.addAttribute("grupo", grupo);
        return "grupo/delete";
    }

    @PostMapping("/delete")
    public String delete(Grupo grupo, RedirectAttributes attributes){
        grupoServices.EliminarPorId(grupo.getId());
        attributes.addFlashAttribute("msg", "Grupo eliminado correctamente");
        return "redirect:/grupos";
    }
}
