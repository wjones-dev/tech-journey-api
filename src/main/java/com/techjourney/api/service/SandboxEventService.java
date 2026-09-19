package com.techjourney.api.service;

import java.util.List;

import com.techjourney.api.dto.SandboxEventDto;

public interface SandboxEventService {

	List<SandboxEventDto> getAllEvents();

	SandboxEventDto getEventById(Long id);

	SandboxEventDto createEvent(SandboxEventDto sandboxEventDto);

	SandboxEventDto updateEvent(Long id, SandboxEventDto sandboxEventDto);

	void deleteEvent(Long id);
}