package com.example.dockerPostgres.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.dockerPostgres.dto.ClienteRespostaDto;
import com.example.dockerPostgres.models.Cliente;


public class ClienteMapperRespota {

	public static ClienteRespostaDto transformarEmDto(Cliente cliente) {
		return new ClienteRespostaDto(cliente.getId(), cliente.getNome(), calcularIdade(cliente.getDataNascimento()));
	}

	public static List<ClienteRespostaDto> converterEm(List<Cliente> clientes) {
		List<ClienteRespostaDto> dtoList = new ArrayList<ClienteRespostaDto>();
		for (Cliente iterator : clientes) {
			dtoList.add(transformarEmDto(iterator));
		}
		return dtoList;
	}

	/**
	 *
	 * @param dataNascimento
	 * @return valor negativo se ano de nascimento maior que ano atual
	 * @return 0 se ano de nascimento igual a ano atual
	 * @return idade correta
	 */
	public static Integer calcularIdade(LocalDate dataNascimento) {
		if (dataNascimento == null)
			return 0;

		LocalDate data = LocalDate.now();
		Integer idade = data.getYear() - dataNascimento.getYear();
		if (dataNascimento.getMonthValue() > data.getMonthValue()) {
			idade -= 1;
		} else {
			if (dataNascimento.getMonthValue() == data.getMonthValue()
					&& dataNascimento.getDayOfMonth() > data.getDayOfMonth()) {
				idade -= 1;
			}
		}
		return idade;
	}

}
