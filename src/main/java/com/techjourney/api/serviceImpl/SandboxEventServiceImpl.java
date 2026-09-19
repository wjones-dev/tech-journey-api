package com.techjourney.api.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techjourney.api.dto.SandboxEventDto;
import com.techjourney.api.entity.SandboxEvent;
import com.techjourney.api.exception.SandboxEventNotFoundException;
import com.techjourney.api.repository.SandboxEventRepository;
import com.techjourney.api.service.SandboxEventService;

@Service
public class SandboxEventServiceImpl implements SandboxEventService {

    private final SandboxEventRepository sandboxEventRepository;

    public SandboxEventServiceImpl(
            SandboxEventRepository sandboxEventRepository
    ) {
        this.sandboxEventRepository = sandboxEventRepository;
    }

    @Override
    public List<SandboxEventDto> getAllEvents() {

        return sandboxEventRepository.findAll()
                .stream()
                .map(event -> new SandboxEventDto(
                        event.getId(),
                        event.getYear(),
                        event.getCategory(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getTechnology(),
                        event.getCreatedAt()
                ))
                .toList();
    }

    @Override
    public SandboxEventDto getEventById(Long id) {

        return sandboxEventRepository.findById(id)
                .map(event -> new SandboxEventDto(
                        event.getId(),
                        event.getYear(),
                        event.getCategory(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getTechnology(),
                        event.getCreatedAt()
                ))
                .orElseThrow(() ->
                        new SandboxEventNotFoundException(id)
                );
    }

    @Override
    public SandboxEventDto createEvent(
            SandboxEventDto sandboxEventDto
    ) {

        SandboxEvent event = new SandboxEvent(
                sandboxEventDto.getYear(),
                sandboxEventDto.getCategory(),
                sandboxEventDto.getTitle(),
                sandboxEventDto.getDescription(),
                sandboxEventDto.getTechnology()
        );

        SandboxEvent savedEvent =
                sandboxEventRepository.save(event);

        return new SandboxEventDto(
                savedEvent.getId(),
                savedEvent.getYear(),
                savedEvent.getCategory(),
                savedEvent.getTitle(),
                savedEvent.getDescription(),
                savedEvent.getTechnology(),
                savedEvent.getCreatedAt()
        );
    }

    @Override
    public SandboxEventDto updateEvent(
            Long id,
            SandboxEventDto sandboxEventDto
    ) {

        SandboxEvent event = sandboxEventRepository.findById(id)
                .orElseThrow(() ->
                        new SandboxEventNotFoundException(id)
                );

        event.setYear(sandboxEventDto.getYear());
        event.setCategory(sandboxEventDto.getCategory());
        event.setTitle(sandboxEventDto.getTitle());
        event.setDescription(sandboxEventDto.getDescription());
        event.setTechnology(sandboxEventDto.getTechnology());

        SandboxEvent updatedEvent =
                sandboxEventRepository.save(event);

        return new SandboxEventDto(
                updatedEvent.getId(),
                updatedEvent.getYear(),
                updatedEvent.getCategory(),
                updatedEvent.getTitle(),
                updatedEvent.getDescription(),
                updatedEvent.getTechnology(),
                updatedEvent.getCreatedAt()
        );
    }

    @Override
    public void deleteEvent(Long id) {

        SandboxEvent event = sandboxEventRepository.findById(id)
                .orElseThrow(() ->
                        new SandboxEventNotFoundException(id)
                );

        sandboxEventRepository.delete(event);
    }
}