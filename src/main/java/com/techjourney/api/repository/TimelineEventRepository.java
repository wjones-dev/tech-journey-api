package com.techjourney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techjourney.api.entity.TimelineEvent;

public interface TimelineEventRepository extends JpaRepository<TimelineEvent, Long> {

}
