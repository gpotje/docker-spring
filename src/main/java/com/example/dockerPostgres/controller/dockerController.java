package com.example.dockerPostgres.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dockerPostgres.dto.ClienteDto;
import com.example.dockerPostgres.dto.ClienteRespostaDto;
import com.example.dockerPostgres.models.Cliente;
import com.example.dockerPostgres.service.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/api/clientes")
@Api(value = "Api Rest Cliente")
@CrossOrigin(origins = "*")
public class dockerController {
	
    @Autowired
    private ClienteService clienteService;
    

	@GetMapping("/teste")
	public String EndPontDocker(){
		return "Deu certo o docker";
	}
	

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Cadastrar um Cliente")
    @ApiResponses(value= {
            @ApiResponse(code= 201, message= "Cliente persistido no banco"),
            @ApiResponse(code= 409, message = "Já existe Cliente com o mesmo CPF"),
            @ApiResponse(code = 400, message = "Parametros de entrada inválidos")
    })
    private ResponseEntity<ClienteRespostaDto> cadastrarCliente(@RequestBody @Valid ClienteDto clienteDto) {
    	Optional<Cliente> cliente = clienteService.buscarClientePorCpf(clienteDto.getCpf());
        if (!cliente.isPresent()) {
            return new ResponseEntity<>(clienteService.cadastrarCliente(clienteDto), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Lista todos os Clientes ou faz uma buscar por meio da QueryString passada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Todos os Clientes listados"),
            @ApiResponse(code = 302, message = "Cliente encontrado, com o parametro passado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado, com o parametro passado")
    })
    private ResponseEntity<Page<ClienteRespostaDto>> buscarCliente(Pageable pageable,
                                              @RequestParam(required = false, defaultValue = "") String nome,
                                              @RequestParam(required = false, defaultValue = "") String cpf){
        if (nome.isEmpty() && cpf.isEmpty()){
            return new ResponseEntity<>(clienteService.listarClientes(pageable), HttpStatus.OK);
        }
        else {
            //Da forma que está implementada prioriza a busca pelo nome, não fará a busca combinando Cpf e nome
            if (nome.isEmpty()) {
                try {
                    return new ResponseEntity<>(clienteService.buscarClienteCpf(cpf, pageable), HttpStatus.FOUND);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            else{
                try {
                    return new ResponseEntity<>(clienteService.buscarClienteNome(nome, pageable), HttpStatus.FOUND);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        }

    }
    
    

	
}
