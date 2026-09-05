package com.techjourney.api.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techjourney.api.dto.TimelineEventDto;
import com.techjourney.api.repository.TimelineEventRepository;
import com.techjourney.api.service.TimelineEventService;

@Service
public class TimelineEventServiceImpl implements TimelineEventService {

	private final TimelineEventRepository timelineEventRepository;

	public TimelineEventServiceImpl(TimelineEventRepository timelineEventRepository) {
		this.timelineEventRepository = timelineEventRepository;
	}

	@Override
	public List<TimelineEventDto> getAllEvents() {

	    return timelineEventRepository.findAll()
	            .stream()
	            .map(event -> new TimelineEventDto(
	                    event.getId(),
	                    event.getYear(),
	                    event.getCategory(),
	                    event.getTitle(),
	                    event.getDescription(),
	                    event.getTechnology()
	            ))
	            .toList();
	}
}
