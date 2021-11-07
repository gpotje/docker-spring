package com.example.dockerPostgres.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRespostaDto {

	private Long id;
	private String nome;
	private Integer idade;

}
