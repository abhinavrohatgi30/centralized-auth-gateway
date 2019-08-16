package com.github.abhinavrohatgi30.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/endpoint/v0")
public class TestEndpointV0Controller {

	@GetMapping("/test/{id}")
	public ResponseEntity<String> getDetails(@PathVariable("id") String id){
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/test/{id}/ping")
	public ResponseEntity<String> ping(@PathVariable("id") String id){
		return ResponseEntity.ok(id);
	}	
}
