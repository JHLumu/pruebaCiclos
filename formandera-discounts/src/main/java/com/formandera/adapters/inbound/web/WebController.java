package com.formandera.adapters.inbound.web;

import com.formandera.adapters.outbound.DiscountService;
import com.formandera.application.usecases.CalculateDiscountUseCase;
import com.formandera.domain.model.DiscountResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/descuento")
public class WebController {

    private final CalculateDiscountUseCase useCase;

    public WebController(DiscountService discountService) {
        this.useCase = new CalculateDiscountUseCase(discountService);
    }

    @GetMapping
    public String form(Model model) {
        model.addAttribute("porcentaje", null);
        model.addAttribute("precioFinal", null);
        return "discount_form";
    }

    @PostMapping
    public String calcular(@RequestParam int nCursosPrevios,
                           @RequestParam(required = false) boolean esPremium,
                           @RequestParam double precioBase,
                           Model model) {

    	// Validación simple del número de cursos
        if (nCursosPrevios < 0) {
            model.addAttribute("error", "El número de cursos no puede ser negativo.");
            model.addAttribute("nCursosPrevios", nCursosPrevios);
            model.addAttribute("esPremium", esPremium);
            model.addAttribute("precioBase", precioBase);
            return "discount_form"; // vuelve a mostrar el formulario con mensaje
        }

        // Validación de precio base
        if (precioBase <= 0) {
            model.addAttribute("error", "El precio base debe ser mayor que 0.");
            return "discount_form";
        }
    	
    	DiscountResult result = useCase.execute(nCursosPrevios, esPremium, precioBase);

        model.addAttribute("nCursosPrevios", nCursosPrevios);
        model.addAttribute("esPremium", esPremium);
        model.addAttribute("precioBase", precioBase);
        model.addAttribute("porcentaje", result.getPorcentaje());
        model.addAttribute("precioFinal", result.getPrecioFinal());
        return "discount_form";
    }
}
