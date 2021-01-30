package com.bootcamp.finaljob.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.finaljob.dto.ClientDTO;
import com.bootcamp.finaljob.entities.ClientEntity;
import com.bootcamp.finaljob.repositories.ClientRepository;

import com.bootcamp.finaljob.services.exceptions.ResourceNotFoundException;

import com.bootcamp.finaljob.services.exceptions.DataBaseException;


@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest){
		Page<ClientEntity> list = repository.findAll(pageRequest);
		
		return list.map(x -> new ClientDTO(x));

	
		
	}
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional <ClientEntity> obj = repository.findById(id);
		ClientEntity entity = obj.orElseThrow(()-> new EntityNotFoundException("Entity Not Found"));
		return new ClientDTO(entity);
		
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO dto) {

		ClientEntity entity = new ClientEntity();
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthdate(dto.getBirthdate());
		entity.setChildren(dto.getChildren());
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO update(Long id , ClientDTO dto) {
		try {
		ClientEntity entity = repository.getOne(id);
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthdate(dto.getBirthdate());
		entity.setChildren(dto.getChildren());
		entity = repository.save(entity);
		return new ClientDTO(entity);
		}
		catch(EntityNotFoundException e){
		throw new ResourceNotFoundException("Id not Found" + id);
		}
	}
	
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
		    throw new ResourceNotFoundException("Id not Found"+ id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity Violation");
		}
			
		}
		
	}

