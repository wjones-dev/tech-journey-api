package com.techjourney.api.service;

import java.util.List;

import com.techjourney.api.dto.TimelineEventDto;

public interface TimelineEventService {

	List<TimelineEventDto> getAllEvents();

	TimelineEventDto getEventById(Long id);

}