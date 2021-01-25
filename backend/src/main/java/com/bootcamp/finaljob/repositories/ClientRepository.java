package com.bootcamp.finaljob.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.finaljob.entities.ClientEntity;


@Repository
public interface ClientRepository extends JpaRepository <ClientEntity, Long> {

}
