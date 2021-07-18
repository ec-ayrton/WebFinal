package com.ayrton.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayrton.project.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
	Optional<Client> findByCPF(String cPF);
}
