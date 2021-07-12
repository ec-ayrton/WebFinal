package com.ayrton.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayrton.project.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

}
