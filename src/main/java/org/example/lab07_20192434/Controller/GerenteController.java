package org.example.lab07_20192434.Controller;

import jakarta.validation.Valid;
import org.example.lab07_20192434.Entity.Funciones;
import org.example.lab07_20192434.Entity.Obras;
import org.example.lab07_20192434.Entity.Rooms;
import org.example.lab07_20192434.Repository.FuncionesRepository;
import org.example.lab07_20192434.Repository.ObraRepository;
import org.example.lab07_20192434.Repository.SalaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/funciones")
public class GerenteController {

    final FuncionesRepository funcionRepository;
    final SalaRepository salaRepository;
    final ObraRepository obraRepository;

    public GerenteController(FuncionesRepository funcionRepository, SalaRepository salaRepository, ObraRepository obraRepository) {
        this.funcionRepository = funcionRepository;
        this.salaRepository = salaRepository;
        this.obraRepository = obraRepository;
    }
    //FUNCIONES

    //List
    @GetMapping("/list")
    public String showLista(Model model) {
        List<Funciones> funciones = funcionRepository.findAll();
        model.addAttribute("funciones", funciones);
        return "/Funciones/listar";
    }

    //Edit
    @GetMapping("/edit")
    public String showForm(@RequestParam("id") int id, Model model) {

        Optional<Funciones> funcion = funcionRepository.findById(id);
        if (funcion.isPresent()) {
            model.addAttribute("funcion", funcion.get());
            List<Obras> listarObras = obraRepository.findAll();
            List<Rooms> listarRooms = salaRepository.findAll();
            model.addAttribute("obras", listarObras);
            model.addAttribute("rooms", listarRooms);
            return "/Funciones/Editar";
        }else {
            return "/Funciones/listar";
        }
    }

    //New
    @GetMapping("/new")
    public String showForm(Model model) {
        List<Funciones> funciones = funcionRepository.findAll();
        List<Obras> listarObras = obraRepository.findAll();
        List<Rooms> listarRooms = salaRepository.findAll();
        model.addAttribute("obras", listarObras);
        model.addAttribute("rooms", listarRooms);
        model.addAttribute("funciones", funciones);
        return "/Funciones/Nuevo";
    }
    //Save
    @PostMapping("/save")
    public String saveFuncion(@Valid @RequestParam("funciones") Funciones funciones, BindingResult result, RedirectAttributes attr, Model model) {

        if(result.hasErrors()) {
            model.addAttribute("funcion", funciones);
            return "/Funciones/Nuevo";
        }
        funcionRepository.save(funciones);
        attr.addFlashAttribute("msg", "Función guardado exitosamente");
        return "redirect:/list";
    }

    //Delete
    @GetMapping("/delete")
    public String delete(Model model, @RequestParam("id") int id, RedirectAttributes attr) {
        Optional<Funciones> funcion = funcionRepository.findById(id);
        if(funcion.isPresent()) {
            funcionRepository.deleteById(id);
        }
        attr.addFlashAttribute("msg", "Función eliminado exitosamente");

        return "redirect:/list";
    }


}
