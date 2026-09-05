package com.techjourney.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techjourney.api.dto.TimelineEventDto;
import com.techjourney.api.service.TimelineEventService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/timeline")
public class TimelineEventController {

    private final TimelineEventService timelineEventService;

    public TimelineEventController(TimelineEventService timelineEventService) {
        this.timelineEventService = timelineEventService;
    }

    @GetMapping
    public List<TimelineEventDto> getAllEvents() {
        return timelineEventService.getAllEvents();
    }
}