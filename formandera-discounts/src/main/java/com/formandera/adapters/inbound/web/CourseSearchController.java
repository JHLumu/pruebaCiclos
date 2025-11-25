package com.formandera.adapters.inbound.web;

import com.formandera.application.ports.inbound.PortBusquedaCurso;
import com.formandera.domain.model.PerfilUsuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/buscar")
public class CourseSearchController {

    private final PortBusquedaCurso courseSearchPort;

    public CourseSearchController(PortBusquedaCurso courseSearchPort) {
        this.courseSearchPort = courseSearchPort;
    }

    @GetMapping
    public String form() {
        return "course_search";
    }

    @PostMapping
    public String buscarCursos(@RequestParam int edad,
                               @RequestParam String tematica,
                               @RequestParam double puntuacionMinima,
                               @RequestParam int cursosCompletados,
                               Model model) {
        try {
            PerfilUsuario user = new PerfilUsuario(edad, cursosCompletados, tematica, puntuacionMinima);
            model.addAttribute("resultados", courseSearchPort.findRecommendedCourses(user));
            model.addAttribute("ok", true);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("edad", edad);
        model.addAttribute("tematica", tematica);
        model.addAttribute("puntuacionMinima", puntuacionMinima);
        model.addAttribute("cursosCompletados", cursosCompletados);
        return "course_search";
    }
}

