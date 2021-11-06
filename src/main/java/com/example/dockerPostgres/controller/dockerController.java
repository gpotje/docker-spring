package com.example.dockerPostgres.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/docker")
public class dockerController {

	@GetMapping
	public String EndPontDocker(){
		return "Deu certo o docker";
	}
}
