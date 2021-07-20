package com.walter.client.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.walter.client.dto.ClientDto;
import com.walter.client.entities.Client;
import com.walter.client.repositories.ClientRepository;
import com.walter.client.services.exceptions.ClientNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Transactional(readOnly = true)
	public Page<ClientDto> findAllPaged(PageRequest pageRequest){
		Page<Client> list = clientRepository.findAll(pageRequest);
		return list.map(x -> new ClientDto(x));
	}
	
	@Transactional(readOnly = true)
	public ClientDto findById(Long id){
		Optional<Client> objClient = clientRepository.findById(id);
		Client entity = objClient.orElseThrow(() -> new ClientNotFoundException("Client not found"));
		return new ClientDto(entity);
	}
	
	
}
