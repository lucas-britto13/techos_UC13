package br.com.bugnacio.techos.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 

public class ClienteController {

        @GetMapping("/clientes")
        @ResponseBody 
        public String listarClientes() {
            return "Clientes do Seu Bugnácio";
        }


}
