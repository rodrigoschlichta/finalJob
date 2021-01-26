package com.bootcamp.finaljob.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.finaljob.dto.ClientDTO;
import com.bootcamp.finaljob.entities.ClientEntity;
import com.bootcamp.finaljob.repositories.ClientRepository;



@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public List<ClientDTO> findAll(){
		List<ClientEntity> list = repository.findAll();
		
		return list.stream().map(x -> new ClientDTO(x)).collect(Collectors.toList());

	
		
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
		
		ClientEntity entity = repository.getOne(id);
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthdate(dto.getBirthdate());
		entity.setChildren(dto.getChildren());
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}

}
