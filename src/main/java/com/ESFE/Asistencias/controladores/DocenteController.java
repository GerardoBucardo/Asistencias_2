package com.ESFE.Asistencias.controladores;

import com.ESFE.Asistencias.entidades.Docente;
import com.ESFE.Asistencias.servicios.interfaces.IDocenteServices;
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
@RequestMapping("/docentes")

public class DocenteController {

    @Autowired
    private IDocenteServices docenteServices;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(10);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Docente> docentes = docenteServices.BuscarTodosPaginados(pageable);
        model.addAttribute("docentes", docentes);

        int totalPage = docentes.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumber = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumber", pageNumber);
        }
        return "docente/index"; // Asegúrate de que la vista existe en src/main/resources/templates/grupo/index.html
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("docente", new Docente());
        return "docente/create"; // Asegúrate de que la vista existe en src/main/resources/templates/grupos/create.html
    }

    @PostMapping("/save")
    public String save(Docente docente, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("docente", docente); // Añadir el grupo al modelo en caso de error
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "docente/create"; // Redirige a la vista de creación
        }

        docenteServices.CrearOeditar(docente);
        attributes.addFlashAttribute("msg", "Docente creado correctamente");
        return "redirect:/docentes";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Optional<Docente> grupoOpt = docenteServices.BuscarPorId(id);
        if (grupoOpt.isPresent()){
            Docente docente = grupoOpt.get();
            model.addAttribute("docente", docente);
            return "docente/details";
        } else {
            return "docente/not_found";
        }

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Docente docente = docenteServices.BuscarPorId(id).get();
        model.addAttribute("docente", docente);
        return "docente/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        Docente docente = docenteServices.BuscarPorId(id).get();
        model.addAttribute("docente", docente);
        return "docente/delete";
    }

    @PostMapping("/delete")
    public String delete(Docente docente, RedirectAttributes attributes){
        docenteServices.EliminarPorId(docente.getId());
        attributes.addFlashAttribute("msg", "Docente eliminado correctamente");
        return "redirect:/docentes";
    }
}
