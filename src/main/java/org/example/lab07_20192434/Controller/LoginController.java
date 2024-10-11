package org.example.lab07_20192434.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/loginForm")
    public String loginForm() {

        return "loginWindow"; //mis vista del formulario de login
    }
    @GetMapping("/Registro")
    public String Registro() {
        return "registro";
    }
}
