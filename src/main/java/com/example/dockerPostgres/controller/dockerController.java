package com.example.dockerPostgres.controller;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Cadastrar um Cliente")
    @ApiResponses(value= {
            @ApiResponse(code= 201, message = "Cliente cadastrado com sucesso"),
            @ApiResponse(code= 409, message = "Já existe Cliente com o mesmo CPF"),
            @ApiResponse(code = 400, message = "Parametros de entrada inválidos")
    })
    @ResponseStatus(HttpStatus.CREATED)
    private ClienteRespostaDto cadastrarCliente(@Valid @RequestBody ClienteDto clienteDto) {
    	return clienteService.cadastrarCliente(clienteDto);
   }
    
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Lista todos os Clientes ou faz uma buscar por meio da QueryString passada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Todos os Clientes listados",response = ClienteDto.class),
            @ApiResponse(code = 302, message = "Cliente encontrado, com o parametro passado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado, com o parametro passado")
    })
    private ResponseEntity<Page<ClienteRespostaDto>> buscarCliente(Pageable pageable){
      
            return new ResponseEntity<>(clienteService.listarClientes(pageable), HttpStatus.OK);
        

    }
    
    @PatchMapping(path = "/{id}", consumes = {MediaType.ALL_VALUE})
    @ApiOperation(value = "Atualizar nome de um cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dados do Cliente atualizado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado, não pode ser atualizado")
    })
    private ResponseEntity<Cliente> atualizarDadosCliente(@RequestBody String nome, @PathVariable(value = "id") Long id){
        try {
            Optional<Cliente> clienteOp = clienteService.buscarClienteId(id);
            System.out.println(nome);
            if (!clienteOp.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Cliente cliente = new Cliente(clienteOp.get().getId(), nome.intern(), clienteOp.get().getCpf(), clienteOp.get().getDataNascimento());
            return new ResponseEntity<>(clienteService.atualizarNomeCliente(cliente), HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"testando",e);
        }
    }

//    @PutMapping(path = "/{id}")
//    @ApiOperation(value = "Atualizar um cliente")
//    @ApiResponses(value = {
//            @ApiResponse(code = 404, message = "Cliente não encontrado, não pode ser atualizado" ),
//            @ApiResponse(code = 200, message = "Cliente atualizado"),
//            @ApiResponse(code = 400, message = "Dados para atualizar Cliente, inválidos, ou cliente está duplicado")
//    })
//    private ResponseEntity<Cliente> atualizarCliente(@Valid @RequestBody ClienteDto clienteDto, @PathVariable(value = "id") Long id){
//        try {
//            Optional<Cliente> clienteOp = clienteService.buscarClienteId(id);
//            if (!clienteOp.isPresent())
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//            Cliente cliente = new Cliente(clienteOp.get().getId(), clienteDto.getNome(), clienteDto.getCpf(), clienteDto.getDataNascimento());
//            return new ResponseEntity<>(clienteService.atualizarCliente(cliente), HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//    }

//    @DeleteMapping(path = "/{cpf}")
//    @ApiOperation(value = "deleta um cliente pelo cpf passado")
//    @ApiResponses(value = {
//            @ApiResponse(code = 404, message = "Cliente não encontrado, não pode ser deletado"),
//            @ApiResponse(code = 200, message = "Cliente Deletado"),
//            @ApiResponse(code = 400, message = "Cliente não retornado por haver duplicidade")
//    })
//    private ResponseEntity<Void> deletarCliente(@PathVariable(value = "cpf") String cpf){
//        try{
//            Optional<Cliente> cliBuscado = clienteService.buscarClientePorCpf(cpf);
//            if(!cliBuscado.isPresent())
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            Cliente cliDeletar = new Cliente(cliBuscado.get().getId(), cliBuscado.get().getNome(),
//                    cliBuscado.get().getCpf(), cliBuscado.get().getDataNascimento());
//            clienteService.removerCliente(cliDeletar);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

	
}
