package com.example.dockerPostgres.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dockerPostgres.constantes.MensagemConstants;
import com.example.dockerPostgres.dto.ClienteDto;
import com.example.dockerPostgres.dto.ClienteRespostaDto;
import com.example.dockerPostgres.exception.ObjectBadRequestException;
import com.example.dockerPostgres.mapper.ClienteMapper;
import com.example.dockerPostgres.mapper.ClienteMapperRespota;
import com.example.dockerPostgres.models.Cliente;
import com.example.dockerPostgres.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public ClienteRespostaDto cadastrarCliente(ClienteDto clienteDto) {
		Optional<Cliente> cliente = buscarClientePorCpf(clienteDto.getCpf());
		if (!cliente.isPresent()) {
			return ClienteMapperRespota.transformarEmDto(repository.save(ClienteMapper.transformarEmCliente(clienteDto)));
		}
		throw new ObjectBadRequestException(MensagemConstants.CPF_JA_EXISTE);

	}

	public Optional<Cliente> buscarClientePorCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	public Page<ClienteRespostaDto> listarClientes(Pageable pageable) {
		Page<Cliente> pageCliente = repository.findAll(pageable);
		return new PageImpl<ClienteRespostaDto>(ClienteMapperRespota.converterEm(pageCliente.getContent()), pageable, pageCliente.getTotalElements());
	}

	public Page<ClienteRespostaDto> buscarClienteCpf(String cpf, Pageable pageable) {
		Page<Cliente> cliente = repository.findByCpf(cpf, pageable);
		return new PageImpl<>(ClienteMapperRespota.converterEm(cliente.getContent()), pageable, cliente.getTotalElements());
	}

	public Page<ClienteRespostaDto> buscarClienteNome(String nome, Pageable pageable) {
		Page<Cliente> cliente = repository.findByNomeContainingIgnoreCase(nome, pageable);
		return new PageImpl<ClienteRespostaDto>(ClienteMapperRespota.converterEm(cliente.getContent()), pageable, cliente.getTotalElements());
	}

	public void removerCliente(Cliente cliente) {
		repository.delete(cliente);
	}

	public Optional<Cliente> buscarClienteId(Long id) {
		return repository.findById(id);
	}

	public Cliente atualizarCliente(Cliente cliente) throws Exception {
		Optional<Cliente> cli = repository.findByCpf(cliente.getCpf());
		if (cli.isPresent())
			throw new Exception("CPF já existe no banco");
		return repository.save(cliente);
	}

	public Cliente atualizarNomeCliente(Cliente cliente) {
		return repository.save(cliente);
	}

}
