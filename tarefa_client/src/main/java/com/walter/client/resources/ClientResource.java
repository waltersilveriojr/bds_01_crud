package com.walter.client.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.walter.client.dto.ClientDto;
import com.walter.client.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	@Autowired
	private ClientService clientService;
	
	@GetMapping
	public ResponseEntity<Page<ClientDto>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<ClientDto> listClient = clientService.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(listClient);		
	}
	
	@GetMapping(value = "/{id}")
	private ResponseEntity<ClientDto> findById(@PathVariable Long id) {
		ClientDto clientDto = clientService.findById(id);
		return ResponseEntity. ok().body(clientDto);
	}
	
}
