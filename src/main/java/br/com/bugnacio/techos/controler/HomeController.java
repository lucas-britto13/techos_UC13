package br.com.bugnacio.techos.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 

public class HomeController {

    @GetMapping("/")
    @ResponseBody 

    public String inicio(){
        return "TechOS no ar";
    }

}
