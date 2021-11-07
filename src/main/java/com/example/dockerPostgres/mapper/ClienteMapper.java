package com.example.dockerPostgres.mapper;

import com.example.dockerPostgres.dto.ClienteDto;
import com.example.dockerPostgres.models.Cliente;

public class ClienteMapper {

	  public static Cliente transformarEmCliente(ClienteDto clienteDto){
	        return new Cliente(clienteDto.getNome(), clienteDto.getCpf(), clienteDto.getDataNascimento());
	    }
}
