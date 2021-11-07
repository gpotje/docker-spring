package com.example.dockerPostgres.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_cliente")
public class Cliente {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "{Nome não pode ser nulo}")
    @NotEmpty(message = "{Nome precisa ser inserido}")
    @NotBlank(message = "Nome não pode estar em branco")
    private String nome;

    @Size(min = 11, max = 11, message = "Digite um CPF válido, mínimo onze dígitos")
    @NotBlank(message = "CPF não pode estar em branco")
    @NotNull(message = "CPF não pode ser nulo")
    private String cpf;

    @NotNull(message = "Data de nascimento não pode ser nulo")
    private LocalDate dataNascimento;
    
    public Cliente(String nome, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }
}
