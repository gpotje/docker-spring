package com.example.dockerPostgres.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.dockerPostgres.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	 @Query(value = "select nome, extract(year from age(cliente.data_nascimento)) " +
	            "from cliente", nativeQuery = true)
	    public Page<List<Map<String, String>>> find(Pageable pageable);

	    public Optional<Cliente> findByCpf(String cpf);

	    public Page<Cliente> findByCpf(String cpf, Pageable pageable);

	    public Page<Cliente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

}
