package com.crm.gestiontickets.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/clientes")
public class ClienteController {
    
    @GetMapping("/obtener-cliente")
    public String obtenerCliente(@RequestParam String param) {
        return new String();
    }

}
