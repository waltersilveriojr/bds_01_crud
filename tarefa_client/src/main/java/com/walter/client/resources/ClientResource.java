package com.walter.client.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.walter.client.dto.ClientDto;
import com.walter.client.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	@Autowired
	private ClientService clientService;
	
	@GetMapping
	public ResponseEntity<Page<ClientDto>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
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
	
	@PostMapping
	private ResponseEntity<ClientDto> insert(@RequestBody ClientDto client) {
		client = clientService.insert(client);
		URI uri = ServletUriComponentsBuilder
				  .fromCurrentRequest().path("/{id}")
				  .buildAndExpand(client.getId())
				  .toUri();
		return ResponseEntity.created(uri).body(client);
	}
	
	@PutMapping(value = "/{id}")
	private ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody ClientDto client) {
		client = clientService.update(id,client);
		return ResponseEntity. ok().body(client);
	}
	
	@DeleteMapping(value = "/{id}")
	private ResponseEntity<Void> delete(@PathVariable Long id) {
		clientService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
