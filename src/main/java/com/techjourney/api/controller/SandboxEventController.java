package com.techjourney.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techjourney.api.dto.SandboxEventDto;
import com.techjourney.api.service.SandboxEventService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/sandbox/events")
public class SandboxEventController {

	private final SandboxEventService sandboxEventService;

	public SandboxEventController(SandboxEventService sandboxEventService) {
		this.sandboxEventService = sandboxEventService;
	}

	@GetMapping
	public List<SandboxEventDto> getAllEvents() {
		return sandboxEventService.getAllEvents();
	}

	@GetMapping("/{id}")
	public SandboxEventDto getEventById(@PathVariable Long id) {
		return sandboxEventService.getEventById(id);
	}

	@PostMapping
	public ResponseEntity<SandboxEventDto> createEvent(@RequestBody SandboxEventDto sandboxEventDto) {

		SandboxEventDto createdEvent = sandboxEventService.createEvent(sandboxEventDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
	}

	@PutMapping("/{id}")
	public SandboxEventDto updateEvent(@PathVariable Long id, @RequestBody SandboxEventDto sandboxEventDto) {

		return sandboxEventService.updateEvent(id, sandboxEventDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {

		sandboxEventService.deleteEvent(id);

		return ResponseEntity.noContent().build();
	}
}